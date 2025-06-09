////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.util;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;

/**
 * {@link io.github.classgraph.ClassGraph} 的外观。
 * 它是与 {@link ReflectionUtils} 分开的类，因此只有在必要时才进行类路径扫描，
 * 这可能需要几秒钟。
 *
 * @author 胡海星
 */
abstract class ClassGraphFacade {

  private static final ConcurrentHashMap<Class<?>, List<Class<?>>>
          typeToConcreteSubTypes = new ConcurrentHashMap<>();

  private static final ScanResult scanResult =
          new ClassGraph().enableSystemJarsAndModules()
                          .enableClassInfo()
                          .scan();

  /**
   * 在类路径中搜索给定接口或抽象类的所有公共具体子类型。
   *
   * @param type
   *         要搜索其具体子类型的类型
   * @param <T>
   *         要搜索其具体子类型的类型
   * @return 找到的所有具体子类型的列表
   */
  public static <T> List<Class<?>> getPublicConcreteSubTypesOf(final Class<T> type) {
    return typeToConcreteSubTypes.computeIfAbsent(type, ClassGraphFacade::search);
  }

  private static <T> List<Class<?>> search(final Class<T> type) {
    final String typeName = type.getName();
    final ClassInfoList subTypes = type.isInterface()
                                   ? scanResult.getClassesImplementing(typeName)
                                   : scanResult.getSubclasses(typeName);
    final List<Class<?>> loadedSubTypes = subTypes.filter(
        subType -> subType.isPublic() && !subType.isAbstract()
    ).loadClasses(true);
    return Collections.unmodifiableList(loadedSubTypes);
  }
}
