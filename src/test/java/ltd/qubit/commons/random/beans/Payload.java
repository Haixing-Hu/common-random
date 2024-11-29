////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.beans;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nullable;

import jakarta.validation.constraints.Size;

import ltd.qubit.commons.annotation.Identifier;
import ltd.qubit.commons.annotation.Precision;
import ltd.qubit.commons.annotation.Unique;
import ltd.qubit.commons.lang.Argument;
import ltd.qubit.commons.lang.Assignable;
import ltd.qubit.commons.lang.Assignment;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * 此模型表示各类数据的负载信息。
 *
 * @author 潘凯，胡海星
 */
public class Payload implements Assignable<Payload> {

  private static final long serialVersionUID = -4130818393179381259L;

  /**
   * 唯一标识，系统自动生成。
   */
  @Identifier
  private Long id;

  /**
   * 主键。
   */
@Unique(respectTo = "owner")
  @Size(min = 1, max = 128)
  private String key;

  /**
   * 值，以字符串形式表示。
   */
  @Size(max = 256)
  @Nullable
  private String value;

  /**
   * 该对象的所有者。
   */
  private Owner owner;

  /**
   * 创建时间，以UTC时区存储。
   */
  @Precision(TimeUnit.SECONDS)
  private Instant createTime;

  /**
   * 最后一次修改时间，以UTC时区存储。
   */
  @Precision(TimeUnit.SECONDS)
  @Nullable
  private Instant modifyTime;

  /**
   * 标记删除时间，以UTC时区存储。
   */
  @Precision(TimeUnit.SECONDS)
  @Nullable
  private Instant deleteTime;

  public Payload() {
    // empty
  }

  public Payload(final Payload other) {
    assign(other);
  }

  @Override
  public void assign(final Payload other) {
    Argument.requireNonNull("other", other);
    id = other.id;
    key = other.key;
    value = other.value;
    owner = Assignment.clone(other.owner);
    createTime = other.createTime;
    modifyTime = other.modifyTime;
    deleteTime = other.deleteTime;
  }

  @Override
  public Payload cloneEx() {
    return new Payload(this);
  }

  public final Long getId() {
    return id;
  }

  public final void setId(final Long id) {
    this.id = id;
  }

  public final String getKey() {
    return key;
  }

  public final void setKey(final String key) {
    this.key = key;
  }

  @Nullable
  public final String getValue() {
    return value;
  }

  public final void setValue(@Nullable final String value) {
    this.value = value;
  }

  public final Owner getOwner() {
    return owner;
  }

  public final void setOwner(final Owner owner) {
    this.owner = owner;
  }

  public final Instant getCreateTime() {
    return createTime;
  }

  public final void setCreateTime(final Instant createTime) {
    this.createTime = createTime;
  }

  @Nullable
  public final Instant getModifyTime() {
    return modifyTime;
  }

  public final void setModifyTime(@Nullable final Instant modifyTime) {
    this.modifyTime = modifyTime;
  }

  @Nullable
  public final Instant getDeleteTime() {
    return deleteTime;
  }

  public final void setDeleteTime(@Nullable final Instant deleteTime) {
    this.deleteTime = deleteTime;
  }

  public boolean equals(@Nullable final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final Payload other = (Payload) o;
    return Equality.equals(id, other.id)
        && Equality.equals(key, other.key)
        && Equality.equals(value, other.value)
        && Equality.equals(owner, other.owner)
        && Equality.equals(createTime, other.createTime)
        && Equality.equals(modifyTime, other.modifyTime)
        && Equality.equals(deleteTime, other.deleteTime);
  }

  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, id);
    result = Hash.combine(result, multiplier, key);
    result = Hash.combine(result, multiplier, value);
    result = Hash.combine(result, multiplier, owner);
    result = Hash.combine(result, multiplier, createTime);
    result = Hash.combine(result, multiplier, modifyTime);
    result = Hash.combine(result, multiplier, deleteTime);
    return result;
  }

  public String toString() {
    return new ToStringBuilder(this)
        .append("id", id)
        .append("key", key)
        .append("value", value)
        .append("owner", owner)
        .append("createTime", createTime)
        .append("modifyTime", modifyTime)
        .append("deleteTime", deleteTime)
        .toString();
  }

  public String getIndexKey() {
    final StringBuilder builder = new StringBuilder();
    builder.append(owner.getType().toUpperCase())
        .append('-')
        .append(owner.getId())
        .append('-')
        .append(owner.getProperty().toUpperCase())
        .append('-')
        .append(key.toUpperCase());
    return builder.toString();
  }
}
