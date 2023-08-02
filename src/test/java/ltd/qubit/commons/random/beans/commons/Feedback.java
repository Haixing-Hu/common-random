////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.beans.commons;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nullable;

import jakarta.validation.constraints.Size;

import ltd.qubit.commons.annotation.Identifier;
import ltd.qubit.commons.annotation.Precision;
import ltd.qubit.commons.lang.Argument;
import ltd.qubit.commons.lang.Assignable;
import ltd.qubit.commons.lang.Assignment;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.random.beans.util.Auditable;
import ltd.qubit.commons.random.beans.util.Emptyful;
import ltd.qubit.commons.random.beans.util.Identifiable;
import ltd.qubit.commons.random.beans.util.Normalizable;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * 此模型表示用户反馈。
 *
 * @author 潘凯，胡海星
 */
public class Feedback implements Identifiable, Auditable, Emptyful,
    Normalizable, Assignable<Feedback> {

  private static final long serialVersionUID = 3984017792862959343L;

  /**
   * 唯一标识，系统自动生成。
   */
  @Identifier
  private Long id;

  /**
   * 反馈类型。
   */
  private FeedbackType type;

  /**
   * 反馈选项。
   */
  private DictEntryInfo item;

  /**
   * 反馈选项的补充说明备注。
   */
  @Nullable
  private String comment;

  /**
   * 所属实体类型。
   */
  @Size(min = 1, max = 64)
  private String ownerType;

  /**
   * 所属实体ID。
   */
  private Long ownerId;

  /**
   * 反馈用户ID。
   */
  private Long clientId;

  /**
   * 创建时间。
   */
  @Precision(TimeUnit.SECONDS)
  private Instant createTime;

  /**
   * 最后一次修改时间。
   */
  @Precision(TimeUnit.SECONDS)
  @Nullable
  private Instant modifyTime;

  /**
   * 删除时间。
   */
  @Precision(TimeUnit.SECONDS)
  @Nullable
  private Instant deleteTime;

  public Feedback() {
    // empty
  }

  public Feedback(final Feedback other) {
    assign(other);
  }

  @Override
  public void assign(final Feedback other) {
    Argument.requireNonNull("other", other);
    id = other.id;
    type = other.type;
    item = Assignment.clone(other.item);
    comment = other.comment;
    ownerType = other.ownerType;
    ownerId = other.ownerId;
    clientId = other.clientId;
    createTime = other.createTime;
    modifyTime = other.modifyTime;
    deleteTime = other.deleteTime;
  }

  @Override
  public Feedback clone() {
    return new Feedback(this);
  }

  public final Long getId() {
    return id;
  }

  public final void setId(final Long id) {
    this.id = id;
  }

  public final FeedbackType getType() {
    return type;
  }

  public final void setType(final FeedbackType type) {
    this.type = type;
  }

  public final DictEntryInfo getItem() {
    return item;
  }

  public final void setItem(final DictEntryInfo item) {
    this.item = item;
  }

  @Nullable
  public final String getComment() {
    return comment;
  }

  public final void setComment(@Nullable final String comment) {
    this.comment = comment;
  }

  public final String getOwnerType() {
    return ownerType;
  }

  public final void setOwnerType(final String ownerType) {
    this.ownerType = ownerType;
  }

  public final Long getOwnerId() {
    return ownerId;
  }

  public final void setOwnerId(final Long ownerId) {
    this.ownerId = ownerId;
  }

  public final Long getClientId() {
    return clientId;
  }

  public final void setClientId(final Long clientId) {
    this.clientId = clientId;
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

  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final Feedback other = (Feedback) o;
    return Equality.equals(id, other.id)
        && Equality.equals(type, other.type)
        && Equality.equals(item, other.item)
        && Equality.equals(comment, other.comment)
        && Equality.equals(ownerType, other.ownerType)
        && Equality.equals(ownerId, other.ownerId)
        && Equality.equals(clientId, other.clientId)
        && Equality.equals(createTime, other.createTime)
        && Equality.equals(modifyTime, other.modifyTime)
        && Equality.equals(deleteTime, other.deleteTime);
  }

  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, id);
    result = Hash.combine(result, multiplier, type);
    result = Hash.combine(result, multiplier, item);
    result = Hash.combine(result, multiplier, comment);
    result = Hash.combine(result, multiplier, ownerType);
    result = Hash.combine(result, multiplier, ownerId);
    result = Hash.combine(result, multiplier, clientId);
    result = Hash.combine(result, multiplier, createTime);
    result = Hash.combine(result, multiplier, modifyTime);
    result = Hash.combine(result, multiplier, deleteTime);
    return result;
  }

  public String toString() {
    return new ToStringBuilder(this)
        .append("id", id)
        .append("type", type)
        .append("item", item)
        .append("comment", comment)
        .append("ownerType", ownerType)
        .append("ownerId", ownerId)
        .append("clientId", clientId)
        .append("createTime", createTime)
        .append("modifyTime", modifyTime)
        .append("deleteTime", deleteTime)
        .toString();
  }
}
