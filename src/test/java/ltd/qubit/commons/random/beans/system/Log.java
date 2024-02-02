////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.beans.system;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nullable;

import jakarta.validation.constraints.Size;

import ltd.qubit.commons.annotation.Identifier;
import ltd.qubit.commons.annotation.Precision;
import ltd.qubit.commons.error.ErrorInfo;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.random.beans.person.UserInfo;
import ltd.qubit.commons.random.beans.util.Identifiable;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * 此模型用于记录用户在系统中的操作活动。
 *
 * @author 胡海星
 */
public class Log implements Identifiable, Comparable<Log> {

  private static final long serialVersionUID = 349017511062816686L;

  /**
   * 唯一标识，系统自动生成。
   */
  @Identifier
  private Long id;

  /**
   * 时间戳，存储UTC时间戳。
   */
  @Precision(TimeUnit.SECONDS)
  private Instant timestamp;

  /**
   * 操作者基本信息，{@code null} 表示匿名用户。
   */
  @Nullable
  private UserInfo operator;

  /**
   * 操作者主机名或IP地址。
   */
  @Size(max = 128)
  @Nullable
  private String ip;

  /**
   * 具体操作。
   */
  @Size(min = 1, max = 64)
  private String operation;

  /**
   * 目标类型。
   */
  @Size(min = 1, max = 64)
  @Nullable
  private String targetType;

  /**
   * 目标 ID。
   */
  @Size(min = 1, max = 64)
  @Nullable
  private String targetId;

  /**
   * 操作是否成功。
   */
  private boolean success;

  /**
   * 操作失败时的错误信息。
   */
  @Nullable
  private ErrorInfo error;

  public Log() {
    // empty
  }

  public final Long getId() {
    return id;
  }

  public final void setId(final Long id) {
    this.id = id;
  }

  public final Instant getTimestamp() {
    return timestamp;
  }

  public final void setTimestamp(final Instant timestamp) {
    this.timestamp = timestamp;
  }

  @Nullable
  public final UserInfo getOperator() {
    return operator;
  }

  public final void setOperator(@Nullable final UserInfo operator) {
    this.operator = operator;
  }

  @Nullable
  public final String getIp() {
    return ip;
  }

  public final void setIp(@Nullable final String ip) {
    this.ip = ip;
  }

  public final String getOperation() {
    return operation;
  }

  public final void setOperation(final String operation) {
    this.operation = operation;
  }

  @Nullable
  public final String getTargetType() {
    return targetType;
  }

  public final void setTargetType(@Nullable final String targetType) {
    this.targetType = targetType;
  }

  @Nullable
  public final String getTargetId() {
    return targetId;
  }

  public final void setTargetId(@Nullable final String targetId) {
    this.targetId = targetId;
  }

  public final boolean isSuccess() {
    return success;
  }

  public final void setSuccess(final boolean success) {
    this.success = success;
  }

  @Nullable
  public final ErrorInfo getError() {
    return error;
  }

  public final void setError(@Nullable final ErrorInfo error) {
    this.error = error;
  }

  @Override
  public int compareTo(@Nullable final Log other) {
    if (other == null) {
      return +1;
    } else {
      return timestamp.compareTo(other.timestamp);
    }
  }

  public boolean equals(@Nullable final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final Log other = (Log) o;
    return Equality.equals(id, other.id)
        && Equality.equals(timestamp, other.timestamp)
        && Equality.equals(operator, other.operator)
        && Equality.equals(ip, other.ip)
        && Equality.equals(operation, other.operation)
        && Equality.equals(targetType, other.targetType)
        && Equality.equals(targetId, other.targetId)
        && Equality.equals(success, other.success)
        && Equality.equals(error, other.error);
  }

  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, id);
    result = Hash.combine(result, multiplier, timestamp);
    result = Hash.combine(result, multiplier, operator);
    result = Hash.combine(result, multiplier, ip);
    result = Hash.combine(result, multiplier, operation);
    result = Hash.combine(result, multiplier, targetType);
    result = Hash.combine(result, multiplier, targetId);
    result = Hash.combine(result, multiplier, success);
    result = Hash.combine(result, multiplier, error);
    return result;
  }

  public String toString() {
    return new ToStringBuilder(this)
        .append("id", id)
        .append("timestamp", timestamp)
        .append("operator", operator)
        .append("ip", ip)
        .append("operation", operation)
        .append("targetType", targetType)
        .append("targetId", targetId)
        .append("success", success)
        .append("error", error)
        .toString();
  }
}
