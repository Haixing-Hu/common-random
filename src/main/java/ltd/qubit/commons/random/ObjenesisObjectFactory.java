////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random;

import java.lang.reflect.Constructor;

import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

import ltd.qubit.commons.random.api.ObjectFactory;

import static ltd.qubit.commons.lang.ClassUtils.isAbstract;
import static ltd.qubit.commons.random.util.CollectionUtils.randomElementOf;
import static ltd.qubit.commons.random.util.ReflectionUtils.getPublicConcreteSubTypesOf;

/**
 * 基于Objenesis的工厂，用于创建"复杂"对象：不可变的Java Bean、
 * 泛型类型、抽象类型和接口类型。
 *
 * @author 胡海星
 */
@SuppressWarnings({"unchecked"})
class ObjenesisObjectFactory implements ObjectFactory {

  private final Objenesis objenesis = new ObjenesisStd();

  @Override
  public <T> T createInstance(final Class<T> type, final Context context) {
    if (context.getParameters().isScanClasspathForConcreteTypes() && isAbstract(
        type)) {
      final Class<?> randomConcreteSubType = randomElementOf(
          getPublicConcreteSubTypesOf((type)));
      if (randomConcreteSubType == null) {
        throw new InstantiationError("Unable to find a matching concrete "
            + "subtype of type: " + type + " in the classpath");
      } else {
        return (T) createNewInstance(randomConcreteSubType);
      }
    } else {
      try {
        return createNewInstance(type);
      } catch (final Error e) {
        throw new ObjectCreationException(
            "Unable to create an instance of type: "
                + type, e);
      }
    }
  }

  @SuppressWarnings("deprecation")
  private <T> T createNewInstance(final Class<T> type) {
    try {
      final Constructor<T> noArgConstructor = type.getDeclaredConstructor();
      if (!noArgConstructor.isAccessible()) {
        noArgConstructor.setAccessible(true);
      }
      return noArgConstructor.newInstance();
    } catch (final Exception exception) {
      return objenesis.newInstance(type);
    }
  }

}
