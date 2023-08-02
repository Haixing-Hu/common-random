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

import javax.annotation.Nullable;

import ltd.qubit.commons.annotation.Identifier;
import ltd.qubit.commons.lang.Argument;
import ltd.qubit.commons.lang.Assignable;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.random.beans.util.Identifiable;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * 此模型表示消息队列中的任务。
 *
 * @author 潘凯
 */
public final class MqFailedTask implements Identifiable,
    Assignable<MqFailedTask> {

  private static final long serialVersionUID = 8798044007752865924L;

  /**
   * 内部ID，全局唯一。
   */
  @Identifier
  private Long id;

  /**
   * 消息主题。
   */
  private String topic;

  /**
   * 消息tag。
   */
  private String tag;

  /**
   * 消息类型。
   */
  private MqType type;

  /**
   * 消息的唯一ID。
   */
  private String messageId;

  /**
   * 消息的key。
   */
  private String messageKey;

  /**
   * 消息的内容。
   */
  private String messageValue;

  /**
   * 创建时间，即提交时间。
   */
  private Instant createTime;

  /**
   * 删除时间。
   */
  @Nullable
  private Instant deleteTime;

  public MqFailedTask() {
    // empty
  }

  public MqFailedTask(final MqFailedTask other) {
    assign(other);
  }

  @Override
  public void assign(final MqFailedTask other) {
    Argument.requireNonNull("other", other);
    id = other.id;
    topic = other.topic;
    tag = other.tag;
    type = other.type;
    messageId = other.messageId;
    messageKey = other.messageKey;
    messageValue = other.messageValue;
    createTime = other.createTime;
    deleteTime = other.deleteTime;
  }

  @Override
  public MqFailedTask clone() {
    return new MqFailedTask(this);
  }

  public final Long getId() {
    return id;
  }

  public final void setId(final Long id) {
    this.id = id;
  }

  public final String getTopic() {
    return topic;
  }

  public final void setTopic(final String topic) {
    this.topic = topic;
  }

  public final String getTag() {
    return tag;
  }

  public final void setTag(final String tag) {
    this.tag = tag;
  }

  public final MqType getType() {
    return type;
  }

  public final void setType(final MqType type) {
    this.type = type;
  }

  public final String getMessageId() {
    return messageId;
  }

  public final void setMessageId(final String messageId) {
    this.messageId = messageId;
  }

  public final String getMessageKey() {
    return messageKey;
  }

  public final void setMessageKey(final String messageKey) {
    this.messageKey = messageKey;
  }

  public final String getMessageValue() {
    return messageValue;
  }

  public final void setMessageValue(final String messageValue) {
    this.messageValue = messageValue;
  }

  public final Instant getCreateTime() {
    return createTime;
  }

  public final void setCreateTime(final Instant createTime) {
    this.createTime = createTime;
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
    final MqFailedTask other = (MqFailedTask) o;
    return Equality.equals(id, other.id)
        && Equality.equals(topic, other.topic)
        && Equality.equals(tag, other.tag)
        && Equality.equals(type, other.type)
        && Equality.equals(messageId, other.messageId)
        && Equality.equals(messageKey, other.messageKey)
        && Equality.equals(messageValue, other.messageValue)
        && Equality.equals(createTime, other.createTime)
        && Equality.equals(deleteTime, other.deleteTime);
  }

  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, id);
    result = Hash.combine(result, multiplier, topic);
    result = Hash.combine(result, multiplier, tag);
    result = Hash.combine(result, multiplier, type);
    result = Hash.combine(result, multiplier, messageId);
    result = Hash.combine(result, multiplier, messageKey);
    result = Hash.combine(result, multiplier, messageValue);
    result = Hash.combine(result, multiplier, createTime);
    result = Hash.combine(result, multiplier, deleteTime);
    return result;
  }

  public String toString() {
    return new ToStringBuilder(this)
        .append("id", id)
        .append("topic", topic)
        .append("tag", tag)
        .append("type", type)
        .append("messageId", messageId)
        .append("messageKey", messageKey)
        .append("messageValue", messageValue)
        .append("createTime", createTime)
        .append("deleteTime", deleteTime)
        .toString();
  }
}
