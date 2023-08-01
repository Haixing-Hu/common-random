////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.beans;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nullable;
import javax.validation.constraints.Size;

import ltd.qubit.commons.annotation.Identifier;
import ltd.qubit.commons.annotation.Precision;
import ltd.qubit.commons.annotation.Reference;
import ltd.qubit.commons.annotation.Unique;
import ltd.qubit.commons.lang.Argument;
import ltd.qubit.commons.lang.Assignable;
import ltd.qubit.commons.lang.Assignment;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * 此模型表示组织机构，可用于表示公司、医院、药店、学校、政府机构等。
 *
 * <p>参考标准：《WS 445.17-2014 电子病历基本数据集 第17部分：医疗机构信息》
 *
 * @author 胡海星
 */
public class Organization implements Assignable<Organization> {

  private static final long serialVersionUID = 5527359674601589225L;

  /**
   * 唯一标识，系统自动生成。
   */
  @Identifier
  private Long id;

  /**
   * 编码，全局唯一。
   */
  @Size(min = 1, max = 64)
  @Unique
  private String code;

  /**
   * 名称，全局唯一。
   */
  @Size(min = 1, max = 128)
  @Unique
  private String name;

  /**
   * 图标，存储相对路径或者URL。
   */
  @Size(max = 512)
  @Nullable
  private String icon;

  /**
   * 描述。
   */
  @Nullable
  private String description;

  /**
   * 备注。
   *
   * <p>备注(comment)是由系统管理员填写，描述(description)可以由用户自己填写。</p>
   */
  @Nullable
  private String comment;

  /**
   * 额外参数
   */
  @Size(min = 1, max = 10)
  @Reference(entity = Payload.class, existing = false)
  @Nullable
  private List<Payload> payloads;

  /**
   * 是否是预定义的数据
   */
  private boolean predefined;

  /**
   * 创建时间，存储UTC时间戳。
   */
  @Precision(TimeUnit.SECONDS)
  private Instant createTime;

  /**
   * 最后一次修改时间，存储UTC时间戳。
   */
  @Precision(TimeUnit.SECONDS)
  @Nullable
  private Instant modifyTime;

  /**
   * 标记删除时间，存储UTC时间戳。
   */
  @Precision(TimeUnit.SECONDS)
  @Nullable
  private Instant deleteTime;

  public Organization() {
    // empty
  }

  public Organization(final Organization other) {
    assign(other);
  }

  @Override
  public void assign(final Organization other) {
    Argument.requireNonNull("other", other);
    id = other.id;
    code = other.code;
    name = other.name;
    icon = other.icon;
    description = other.description;
    comment = other.comment;
    payloads = Assignment.deepClone(other.payloads);
    predefined = other.predefined;
    createTime = other.createTime;
    modifyTime = other.modifyTime;
    deleteTime = other.deleteTime;
  }

  @Override
  public Organization clone() {
    return new Organization(this);
  }

  public final Long getId() {
    return id;
  }

  public final void setId(final Long id) {
    this.id = id;
  }

  public final String getCode() {
    return code;
  }

  public final void setCode(final String code) {
    this.code = code;
  }

  public final String getName() {
    return name;
  }

  public final void setName(final String name) {
    this.name = name;
  }

  @Nullable
  public final String getIcon() {
    return icon;
  }

  public final void setIcon(@Nullable final String icon) {
    this.icon = icon;
  }

  @Nullable
  public final String getDescription() {
    return description;
  }

  public final void setDescription(@Nullable final String description) {
    this.description = description;
  }

  @Nullable
  public final String getComment() {
    return comment;
  }

  public final void setComment(@Nullable final String comment) {
    this.comment = comment;
  }

  @Nullable
  public final List<Payload> getPayloads() {
    return payloads;
  }

  public final void setPayloads(@Nullable final List<Payload> payloads) {
    this.payloads = payloads;
  }

  public final boolean isPredefined() {
    return predefined;
  }

  public final void setPredefined(final boolean predefined) {
    this.predefined = predefined;
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
    final Organization other = (Organization) o;
    return Equality.equals(id, other.id)
        && Equality.equals(code, other.code)
        && Equality.equals(name, other.name)
        && Equality.equals(icon, other.icon)
        && Equality.equals(description, other.description)
        && Equality.equals(comment, other.comment)
        && Equality.equals(payloads, other.payloads)
        && Equality.equals(predefined, other.predefined)
        && Equality.equals(createTime, other.createTime)
        && Equality.equals(modifyTime, other.modifyTime)
        && Equality.equals(deleteTime, other.deleteTime);
  }

  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, id);
    result = Hash.combine(result, multiplier, code);
    result = Hash.combine(result, multiplier, name);
    result = Hash.combine(result, multiplier, icon);
    result = Hash.combine(result, multiplier, description);
    result = Hash.combine(result, multiplier, comment);
    result = Hash.combine(result, multiplier, payloads);
    result = Hash.combine(result, multiplier, predefined);
    result = Hash.combine(result, multiplier, createTime);
    result = Hash.combine(result, multiplier, modifyTime);
    result = Hash.combine(result, multiplier, deleteTime);
    return result;
  }

  public String toString() {
    return new ToStringBuilder(this)
        .append("id", id)
        .append("code", code)
        .append("name", name)
        .append("icon", icon)
        .append("description", description)
        .append("comment", comment)
        .append("payloads", payloads)
        .append("predefined", predefined)
        .append("createTime", createTime)
        .append("modifyTime", modifyTime)
        .append("deleteTime", deleteTime)
        .toString();
  }
}
