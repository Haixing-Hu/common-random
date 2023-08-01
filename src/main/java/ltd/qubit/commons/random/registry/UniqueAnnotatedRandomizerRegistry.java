////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.registry;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;

import ltd.qubit.commons.annotation.Identifier;
import ltd.qubit.commons.annotation.Priority;
import ltd.qubit.commons.annotation.Unique;
import ltd.qubit.commons.random.Context;
import ltd.qubit.commons.random.EasyRandom;
import ltd.qubit.commons.random.Parameters;
import ltd.qubit.commons.random.api.Randomizer;
import ltd.qubit.commons.random.api.RandomizerProvider;
import ltd.qubit.commons.random.api.RandomizerRegistry;
import ltd.qubit.commons.random.randomizers.misc.UniqueValueRandomizer;

import static java.util.Objects.requireNonNull;

import static ltd.qubit.commons.reflect.FieldUtils.getAnnotation;
import static ltd.qubit.commons.reflect.FieldUtils.isAnnotationPresent;

/**
 * A registry of randomizers to support fields annotated with the {@link Unique}
 * annotation.
 *
 * @author Haixing Hu
 */
@Priority(Integer.MAX_VALUE - 1)
public class UniqueAnnotatedRandomizerRegistry implements RandomizerRegistry {

  private EasyRandom random;
  private Parameters parameters;
  private Map<String, Set<Object>> cacheMap;
  private RandomizerProvider provider;

  @Override
  public void init(final EasyRandom random, final Parameters parameters) {
    this.random = requireNonNull(random, "random cannot be null");
    this.parameters = requireNonNull(parameters, "parameters cannot be null");
    this.cacheMap = new HashMap<>();
    // NOTE: we cannot get the randomizer provider in this place, since the
    // random.getRandomizerProvider() may returns null here.
  }

  private RandomizerProvider getRandomizerProvider() {
    if (this.provider == null) {
      this.provider = random.getRandomizerProvider()
                            .clone()
                            .removeRegistry(UniqueAnnotatedRandomizerRegistry.class)
                            .removeRegistry(NullableAnnotatedRandomizerRegistry.class);
    }
    return this.provider;
  }

  @Override
  public Randomizer<?> get(final Field field, final Context context) {
    final boolean applied;
    final String[] respectTo;
    final boolean ignoreCase;
    if (isAnnotationPresent(field, Unique.class)) {
      final Unique unique = getAnnotation(field, Unique.class);
      applied = true;
      respectTo = unique.respectTo();
      ignoreCase = unique.ignoreCase();
    } else if (isAnnotationPresent(field, Identifier.class)) {
      // identifier field is also treated as unique field
      final Identifier unique = getAnnotation(field, Identifier.class);
      applied = true;
      respectTo = null;
      ignoreCase = unique.ignoreCase();
    } else {
      applied = false;
      respectTo = null;
      ignoreCase = false;
    }
    if (applied) {
      final Set<Object> cache = getCache(field, respectTo);
      final RandomizerProvider specialProvider = getRandomizerProvider();
      return new UniqueValueRandomizer<>(field, respectTo, ignoreCase,
          random, specialProvider, cache);
    } else {
      return null;
    }
  }

  @Override
  public Randomizer<?> get(final Class<?> fieldType, final Context context) {
    return null;
  }

  private synchronized Set<Object> getCache(final Field field,
      @Nullable final String[] respectTo) {
    final String key = buildKey(field, respectTo);
    return cacheMap.computeIfAbsent(key, k -> new HashSet<>());
  }

  private static String buildKey(final Field field,
      @Nullable final String[] respectTo) {
    final StringBuilder builder = new StringBuilder();
    builder.append(field.getDeclaringClass().getName())
           .append('.');
    // 下面应忽略唯一属性的 respectTo 属性，不将其作为 cache 的主键的一部分
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
    //    if (respectTo != null && respectTo.length > 0) {
    //      for (final String parent : respectTo) {
    //        builder.append(parent).append("->");
    //      }
    //    }
    builder.append(field.getName());
    return builder.toString();
  }
}
