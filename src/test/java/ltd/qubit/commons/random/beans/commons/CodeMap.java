////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
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
import ltd.qubit.commons.random.beans.util.WithEntity;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * 此模型表示编码转换表中的一项。
 *
 * @author 胡海星
 */
public class CodeMap implements Identifiable, WithEntity, Auditable, Emptyful,
    Normalizable, Assignable<CodeMap> {

  private static final long serialVersionUID = -2130837931984798576L;

  /**
   * 唯一标识，系统自动生成。
   */
  @Identifier
  private Long id;

  /**
   * 所属实体。
   */
  @Size(min = 1, max = 64)
  private String entity;

  /**
   * 来源编码。
   */
  private Code source;

  /**
   * 平台编码。
   */
  private String platformCode;

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
   * 标记删除时间。
   */
  @Precision(TimeUnit.SECONDS)
  @Nullable
  private Instant deleteTime;

  public CodeMap() {
    // empty
  }

  public CodeMap(final CodeMap other) {
    assign(other);
  }

  @Override
  public void assign(final CodeMap other) {
    Argument.requireNonNull("other", other);
    id = other.id;
    entity = other.entity;
    source = Assignment.clone(other.source);
    platformCode = other.platformCode;
    createTime = other.createTime;
    modifyTime = other.modifyTime;
    deleteTime = other.deleteTime;
  }

  @Override
  public CodeMap cloneEx() {
    return new CodeMap(this);
  }

  public final Long getId() {
    return id;
  }

  public final void setId(final Long id) {
    this.id = id;
  }

  public final String getEntity() {
    return entity;
  }

  public final void setEntity(final String entity) {
    this.entity = entity;
  }

  public final Code getSource() {
    return source;
  }

  public final void setSource(final Code source) {
    this.source = source;
  }

  public final String getPlatformCode() {
    return platformCode;
  }

  public final void setPlatformCode(final String platformCode) {
    this.platformCode = platformCode;
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
    final CodeMap other = (CodeMap) o;
    return Equality.equals(id, other.id)
        && Equality.equals(entity, other.entity)
        && Equality.equals(source, other.source)
        && Equality.equals(platformCode, other.platformCode)
        && Equality.equals(createTime, other.createTime)
        && Equality.equals(modifyTime, other.modifyTime)
        && Equality.equals(deleteTime, other.deleteTime);
  }

  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, id);
    result = Hash.combine(result, multiplier, entity);
    result = Hash.combine(result, multiplier, source);
    result = Hash.combine(result, multiplier, platformCode);
    result = Hash.combine(result, multiplier, createTime);
    result = Hash.combine(result, multiplier, modifyTime);
    result = Hash.combine(result, multiplier, deleteTime);
    return result;
  }

  public String toString() {
    return new ToStringBuilder(this)
        .append("id", id)
        .append("entity", entity)
        .append("source", source)
        .append("platformCode", platformCode)
        .append("createTime", createTime)
        .append("modifyTime", modifyTime)
        .append("deleteTime", deleteTime)
        .toString();
  }
}
