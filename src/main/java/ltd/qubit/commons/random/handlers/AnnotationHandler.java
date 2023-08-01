////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.handlers;

import java.lang.reflect.Field;

import ltd.qubit.commons.random.Context;
import ltd.qubit.commons.random.api.Randomizer;

public interface AnnotationHandler {

  Randomizer<?> getRandomizer(Field field, Context context);

}
