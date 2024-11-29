////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.beans.util;

import javax.annotation.Nullable;

import ltd.qubit.commons.random.beans.contact.Address;

/**
 * 此接口表示实体类具有地址属性。
 *
 * @author 胡海星
 */
public interface WithAddress {

  /**
   * 获取当前对象的地址。
   *
   * @return
   *     当前对象的地址，或{@code null}如果当前对象没有设置地址。
   */
  @Nullable
  Address getAddress();

  /**
   * 设置当前对象的地址。
   *
   * @param contact
   *     新的地址，可以为{@code null}。
   */
  void setAddress(@Nullable Address contact);

  /**
   * 清除空的地址数据。
   *
   * <p>如果{@code contact.address.isEmpty()}，将此对象
   * 加入数据库时不会设置{@code address}对应的字段；因此会导致从数据库再读出此对象时，
   * 其{@code address}属性为{@code null}。</p>
   *
   * <p>为了自动化单元测试时方便，我们需要保持数据库的读写一致性，因此需要对待添加的
   * {@code WithAddress}对象做一些修正，清除空的{@code address}属性。</p>
   */
  default void clearEmptyAddress() {
    final Address address = getAddress();
    if (address != null) {
      if (address.isEmpty()) {
        setAddress(null);
      }
    }
  }
}
