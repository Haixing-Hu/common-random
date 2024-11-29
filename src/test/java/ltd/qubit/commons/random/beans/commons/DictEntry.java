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

import ltd.qubit.commons.annotation.Computed;
import ltd.qubit.commons.annotation.Identifier;
import ltd.qubit.commons.annotation.Precision;
import ltd.qubit.commons.annotation.Reference;
import ltd.qubit.commons.annotation.Unique;
import ltd.qubit.commons.lang.Argument;
import ltd.qubit.commons.lang.Assignable;
import ltd.qubit.commons.lang.Assignment;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.random.beans.util.Auditable;
import ltd.qubit.commons.random.beans.util.Emptyful;
import ltd.qubit.commons.random.beans.util.Identifiable;
import ltd.qubit.commons.random.beans.util.Normalizable;
import ltd.qubit.commons.random.beans.util.StatefulInfo;
import ltd.qubit.commons.random.beans.util.WithComment;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * 此模型表示数据字典中的条目。
 *
 * @author 胡海星
 * @see Dict
 */
public class DictEntry implements Identifiable, WithComment, Auditable,
    Emptyful, Normalizable, Assignable<DictEntry> {

  private static final long serialVersionUID = 1189064957011240489L;

  /**
   * 唯一标识，系统自动生成。
   */
  @Identifier
  private Long id;

  /**
   * 所属字典的基本信息。
   */
  @Reference(entity = Dict.class, property = "info")
  private StatefulInfo dict;

  /**
   * 代码，同一个所属字典下不可重复。
   */
  @Size(min = 1, max = 64)
  @Unique(respectTo = "dict")
  private String code;

  /**
   * 名称。
   */
  @Size(min = 1, max = 128)
  private String name;

  /**
   * 详细描述。
   */
  @Nullable
  private String description;

  /**
   * 备注。
   */
  @Nullable
  private String comment;

  /**
   * 父项条目。
   */
  @Reference(entity = DictEntry.class, property = "info")
  @Nullable
  private DictEntryInfo parent;

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

  public DictEntry() {
    // empty
  }

  public DictEntry(final DictEntry other) {
    assign(other);
  }

  @Override
  public void assign(final DictEntry other) {
    Argument.requireNonNull("other", other);
    id = other.id;
    dict = Assignment.clone(other.dict);
    code = other.code;
    name = other.name;
    description = other.description;
    comment = other.comment;
    parent = Assignment.clone(other.parent);
    createTime = other.createTime;
    modifyTime = other.modifyTime;
    deleteTime = other.deleteTime;
  }

  @Override
  public DictEntry cloneEx() {
    return new DictEntry(this);
  }

  public final Long getId() {
    return id;
  }

  public final void setId(final Long id) {
    this.id = id;
  }

  public final StatefulInfo getDict() {
    return dict;
  }

  public final void setDict(final StatefulInfo dict) {
    this.dict = dict;
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
  public final DictEntryInfo getParent() {
    return parent;
  }

  public final void setParent(@Nullable final DictEntryInfo parent) {
    this.parent = parent;
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
    final DictEntry other = (DictEntry) o;
    return Equality.equals(id, other.id)
        && Equality.equals(dict, other.dict)
        && Equality.equals(code, other.code)
        && Equality.equals(name, other.name)
        && Equality.equals(description, other.description)
        && Equality.equals(comment, other.comment)
        && Equality.equals(parent, other.parent)
        && Equality.equals(createTime, other.createTime)
        && Equality.equals(modifyTime, other.modifyTime)
        && Equality.equals(deleteTime, other.deleteTime);
  }

  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, id);
    result = Hash.combine(result, multiplier, dict);
    result = Hash.combine(result, multiplier, code);
    result = Hash.combine(result, multiplier, name);
    result = Hash.combine(result, multiplier, description);
    result = Hash.combine(result, multiplier, comment);
    result = Hash.combine(result, multiplier, parent);
    result = Hash.combine(result, multiplier, createTime);
    result = Hash.combine(result, multiplier, modifyTime);
    result = Hash.combine(result, multiplier, deleteTime);
    return result;
  }

  public String toString() {
    return new ToStringBuilder(this)
        .append("id", id)
        .append("dict", dict)
        .append("code", code)
        .append("name", name)
        .append("description", description)
        .append("comment", comment)
        .append("parent", parent)
        .append("createTime", createTime)
        .append("modifyTime", modifyTime)
        .append("deleteTime", deleteTime)
        .toString();
  }

  @Computed({"id", "code", "name"})
  public DictEntryInfo getInfo() {
    return new DictEntryInfo(this);
  }
}
