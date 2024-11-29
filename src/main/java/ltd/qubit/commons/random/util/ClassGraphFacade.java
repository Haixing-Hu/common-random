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
 * Facade for {@link io.github.classgraph.ClassGraph}. It is a separate class
 * from {@link ReflectionUtils}, so that the classpath scanning - which can take
 * a few seconds - is only done when necessary.
 *
 * @author Pascal Schumacher (https://github.com/PascalSchumacher)
 */
abstract class ClassGraphFacade {

  private static final ConcurrentHashMap<Class<?>, List<Class<?>>>
          typeToConcreteSubTypes = new ConcurrentHashMap<>();

  private static final ScanResult scanResult =
          new ClassGraph().enableSystemJarsAndModules()
                          .enableClassInfo()
                          .scan();

  /**
   * Searches the classpath for all public concrete subtypes of the given
   * interface or abstract class.
   *
   * @param type
   *         to search concrete subtypes of
   * @return a list of all concrete subtypes found
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
