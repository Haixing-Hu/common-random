////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.randomizers.misc;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ltd.qubit.commons.annotation.Priority;
import ltd.qubit.commons.annotation.TypeCodec;
import ltd.qubit.commons.annotation.Unique;
import ltd.qubit.commons.lang.Comparison;
import ltd.qubit.commons.random.Context;
import ltd.qubit.commons.random.EasyRandom;
import ltd.qubit.commons.random.Parameters;
import ltd.qubit.commons.random.api.ContextAwareRandomizer;
import ltd.qubit.commons.random.api.Randomizer;
import ltd.qubit.commons.random.api.RandomizerProvider;
import ltd.qubit.commons.random.util.ReflectionUtils;
import ltd.qubit.commons.reflect.BeanInfo;
import ltd.qubit.commons.reflect.ConstructorUtils;
import ltd.qubit.commons.reflect.Property;
import ltd.qubit.commons.reflect.ReflectionException;
import ltd.qubit.commons.util.codec.Codec;
import ltd.qubit.commons.util.codec.EncodingException;

import static java.util.Objects.requireNonNull;

import static ltd.qubit.commons.random.Parameters.DEFAULT_MAX_LOOPS;

/**
 * 唯一值随机化器。
 *
 * @param <T>
 *     要生成的对象类型。
 * @author 胡海星
 */
@Priority(Integer.MAX_VALUE - 1)
public class UniqueValueRandomizer<T> implements ContextAwareRandomizer<T> {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  private final Field field;
  private final String fieldName;
  private final String[] respectTo;
  private final boolean ignoreCase;
  private final EasyRandom random;
  private final RandomizerProvider provider;
  private final Set<Object> cache;
  private Context context;

  /**
   * 构造一个 {@link UniqueValueRandomizer}。
   *
   * @param field
   *     被 {@link Unique} 注解的字段。
   * @param respectTo
   *     一个字符串数组，表示唯一性约束所依赖的其他字段的名称。
   * @param ignoreCase
   *     指示唯一性检查是否应忽略大小写。
   * @param random
   *     {@link EasyRandom} 实例。
   * @param provider
   *     随机化器提供者。
   * @param cache
   *     用于存储已生成的唯一值的缓存。
   */
  public UniqueValueRandomizer(final Field field,
      final @Nullable String[] respectTo, final boolean ignoreCase,
      final EasyRandom random, final RandomizerProvider provider,
      final Set<Object> cache) {
    this.field = requireNonNull(field, "field cannot be null");
    this.fieldName = field.getDeclaringClass().getSimpleName() + "." + field.getName();
    this.respectTo = respectTo;
    this.ignoreCase = ignoreCase;
    this.random = requireNonNull(random, "random cannot be null");
    this.provider = requireNonNull(provider, "provider cannot be null");
    this.cache = requireNonNull(cache, "cache cannot be null");
  }

  @Override
  public void setContext(final Context context) {
    this.context = requireNonNull(context, "context cannot be null");
  }

  @Override
  public T getRandomValue() {
    final Class<?> type = field.getType();
    final Parameters parameters = random.getParameters();
    final Randomizer<?> randomizer = provider.getByField(field, context);
    if (randomizer != null) {
      return generateRandomValue(randomizer);
    } else {
      return generateRandomValue(() -> random.nextObject(type));
    }
  }

  private int getMaxLoops() {
    if (context == null) {
      return DEFAULT_MAX_LOOPS;
    } else {
      return context.getParameters().getMaxLoops();
    }
  }

  private T generateRandomValue(final Randomizer<?> randomizer) {
    if (randomizer instanceof ContextAwareRandomizer) {
      ((ContextAwareRandomizer<?>) randomizer).setContext(context);
    }
    // final List<Object> prefixes = buildPrefixes();   // 不再需要，见下面注释
    final int maxLoops = getMaxLoops();
    for (int i = 0; i < maxLoops; ++i) {
      @SuppressWarnings("unchecked")
      final T value = (T) randomizer.getRandomValue();
      if (value == null) {
        logger.trace("Generating a null value for field: {}", fieldName);
        return null;    // for @Nullable annotation
      }
      // 应忽略唯一属性的 respectTo 属性，不将其作为 cache 的 value 的一部分
      // 在某些情况下，生成的随机对象的 respectTo 属性值在将该对象插入数据库前会被修改，
      // 例如： Payload.key 是相对于 Payload.owner 的唯一属性，
      // 但在将 organization 的 payloads 插入数据库前，会将这些 payloads 的 owner 全部
      // 换为该 organization 对象对应的 owner。
      // 现在假设随机生成了两个 Payload 对象 a 和 b，
      //   a.owner = ("owner_a", 123, "property_a") , a.key = "k"
      //   b.owner = ("owner_b", 456, "property_b") , b.key = "k"
      // 显然 a 和 b 虽然 key 相同但 owner 不同，因此都是可以被生成的
      // 现在我们把 a 和 b 作为某个 Organization 对象 o 的 payloads 属性，然后将 o 插入数据库
      // 注意到在把 o 插入数据库前，a 和 b 的 owner 会被 OrganizationDao 代码替换为 o 所对应的
      // owner，即
      //    a.owner = ("ORGANIZATION", o.id, "PAYLOADS"), a.key = "k"
      //    b.owner = ("ORGANIZATION", o.id, "PAYLOADS"), b.key = "k"
      // 此后再将 a, b 插入 payload 表会导致唯一索引冲突。
      // 因此，我们在随机生成 Payload 对象时应放宽条件，即确保唯一属性 "key" 的值在任何情况下
      // 都唯一 （而不是仅仅在相同的 "owner" 下唯一），这样就肯定不会出现上述情况了。

      // 下面的代码注释不能去掉，原因见上面说明
      //      final List<Object> valueList = new ArrayList<>();
      //      valueList.addAll(prefixes);
      //      valueList.add(fixFieldValue(value));
      // 根据上述说明，修改为下面新的代码
      final Class<?> type = field.getType();
      final Object cachedValue = fixFieldValue(type, value);
      synchronized (cache) {
        if (! cache.contains(cachedValue)) {
          cache.add(cachedValue);
          logger.trace("Generated a unique value for the field {}: {}", fieldName, value);
          return value;
        }
      }
    }
    throw new RuntimeException("Failed to generate an unique value after "
            + maxLoops + " loops");
  }

  private List<Object> buildPrefixes() {
    final List<Object> prefixes = new ArrayList<>();
    if (respectTo != null && respectTo.length > 0) {
      final Object obj = context.getCurrentObject();
      final Class<?> cls = obj.getClass();
      try {
        for (final String name : respectTo) {
          final Field f = cls.getDeclaredField(name);
          final Object value = ReflectionUtils.getFieldValue(obj, f);
          prefixes.add(fixFieldValue(f.getType(), value));
        }
      } catch (final NoSuchFieldException e) {
        throw new ReflectionException(e);
      }
    }
    return prefixes;
  }

  /**
   * 修复用于构建缓存的唯一字段的值。
   *
   * <p>默认情况下，MySQL数据库将{@code VARCHAR}列视为不区分大小写。当使用包含
   * {@link Unique}注解字段的模型测试DAO时，该字段映射到MySQL表中的唯一
   * {@code VARCHAR}列，我们应该以不区分大小写的方式比较唯一字段的字符串值。
   *
   * @param type
   *     字段的类型。
   * @param value
   *     唯一字段的值。
   * @return 修复后的结果。
   */
  private Object fixFieldValue(final Class<?> type, final Object value) {
    if (value == null) {
      return null;
    } else if (ignoreCase) {
      final String str = toStringRepresentation(type, value);
      return str.toUpperCase();
    } else {
      return value;
    }
  }

  /**
   * 获取指定的值的字符串形式。
   *
   * @param type
   *      指定的值的类对象。
   * @param value
   *     指定的值。
   * @return
   *     该主键值的字符串形式。
   */
  public static String toStringRepresentation(final Class<?> type,
      @Nullable final Object value) {
    if (value == null) {
      return "";
    }
    if (type.getName().startsWith("java")) {                  // 对于Java内部类，直接返回其 toString()
      return value.toString();
    } else if (Enum.class.isAssignableFrom(type)) {           // 对于枚举类，返回其名称
      return ((Enum<?>) value).name();
    } else if (type.isAnnotationPresent(TypeCodec.class)) {   // 对于标记了 @TypeCodec 的类，根据编码器返回其字符串形式
      final Class<?> codecClass = type.getAnnotation(TypeCodec.class).value();
      @SuppressWarnings("unchecked")
      final Codec<Object, String> codec =
            (Codec<Object, String>) ConstructorUtils.newInstance(codecClass);
      try {
        return codec.encode(value);
      } catch (final EncodingException e) {
        throw new RuntimeException(e);
      }
    } else {
      final BeanInfo info = BeanInfo.of(type);
      if (info.hasIdProperty()) {                           // 如果该对象有ID属性，直接返回其ID属性值
        final Object id = info.getId(value);
        return (id == null ? "" : id.toString());
      } else {
        // if there is no ID property, use all the properties to build a string
        final StringBuilder builder = new StringBuilder();
        // ignore the computed and JDK built-in fields
        final List<Property> props = info.getProperties(
            p -> ((!p.isComputed()) && (!p.isJdkBuiltIn())));
        // sort the property by their key indexes
        props.sort((x, y) -> Comparison.compare(x.getKeyIndex(), y.getKeyIndex()));
        for (final Property prop : props) {
          final Object propValue = prop.getValue(value);
          if (builder.length() > 0) {
            builder.append('-');
          }
          builder.append(toStringRepresentation(prop.getType(), propValue));
        }
        return builder.toString();
      }
    }
  }

}
