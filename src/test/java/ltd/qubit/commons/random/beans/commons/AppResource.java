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
import ltd.qubit.commons.annotation.Reference;
import ltd.qubit.commons.lang.Argument;
import ltd.qubit.commons.lang.Assignable;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.random.beans.util.Identifiable;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * 此模型表示App和资源的关联信息.
 *
 * @author 潘凯
 */
public class AppResource implements Identifiable, Assignable<AppResource> {

  private static final long serialVersionUID = 3890125292507947521L;

  /**
   * 唯一标识，系统自动生成。
   */
  @Identifier
  private Long id;

  /**
   * 应用ID。
   */
  @Reference(entity = App.class, property = "id")
  private Long appId;

  /**
   * 资源类型。
   */
  @Size(min = 1, max = 64)
  private String resourceType;

  /**
   * 资源的ID。
   */
  private Long resourceId;

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

  public AppResource() {
    // empty
  }

  public AppResource(final AppResource other) {
    assign(other);
  }

  @Override
  public void assign(final AppResource other) {
    Argument.requireNonNull("other", other);
    id = other.id;
    appId = other.appId;
    resourceType = other.resourceType;
    resourceId = other.resourceId;
    createTime = other.createTime;
    modifyTime = other.modifyTime;
    deleteTime = other.deleteTime;
  }

  @Override
  public AppResource clone() {
    return new AppResource(this);
  }

  public final Long getId() {
    return id;
  }

  public final void setId(final Long id) {
    this.id = id;
  }

  public final Long getAppId() {
    return appId;
  }

  public final void setAppId(final Long appId) {
    this.appId = appId;
  }

  public final String getResourceType() {
    return resourceType;
  }

  public final void setResourceType(final String resourceType) {
    this.resourceType = resourceType;
  }

  public final Long getResourceId() {
    return resourceId;
  }

  public final void setResourceId(final Long resourceId) {
    this.resourceId = resourceId;
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
    final AppResource other = (AppResource) o;
    return Equality.equals(id, other.id)
        && Equality.equals(appId, other.appId)
        && Equality.equals(resourceType, other.resourceType)
        && Equality.equals(resourceId, other.resourceId)
        && Equality.equals(createTime, other.createTime)
        && Equality.equals(modifyTime, other.modifyTime)
        && Equality.equals(deleteTime, other.deleteTime);
  }

  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, id);
    result = Hash.combine(result, multiplier, appId);
    result = Hash.combine(result, multiplier, resourceType);
    result = Hash.combine(result, multiplier, resourceId);
    result = Hash.combine(result, multiplier, createTime);
    result = Hash.combine(result, multiplier, modifyTime);
    result = Hash.combine(result, multiplier, deleteTime);
    return result;
  }

  public String toString() {
    return new ToStringBuilder(this)
        .append("id", id)
        .append("appId", appId)
        .append("resourceType", resourceType)
        .append("resourceId", resourceId)
        .append("createTime", createTime)
        .append("modifyTime", modifyTime)
        .append("deleteTime", deleteTime)
        .toString();
  }
}
