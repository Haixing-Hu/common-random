////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class ObjenesisObjectFactoryTest {

  @Mock(answer = Answers.RETURNS_DEEP_STUBS)
  private Context context;

  private ObjenesisObjectFactory factory;

  @BeforeEach
  void setUp() {
    factory = new ObjenesisObjectFactory();
  }

  @Test
  void concreteClassesShouldBeCreatedAsExpected() {
    final String string = factory.createInstance(String.class, context);
    assertThat(string).isNotNull();
  }

  @Test
  void whenNoConcreteTypeIsFound_thenShouldThrowAnInstantiationError() {
    Mockito.when(context.getParameters().isScanClasspathForConcreteTypes())
           .thenReturn(true);
    assertThatThrownBy(() -> factory.createInstance(AbstractFoo.class, context))
            .isInstanceOf(InstantiationError.class);
  }

  private abstract class AbstractFoo {

  }
}
