////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.beans.commons;

import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;
import ltd.qubit.commons.util.range.InstantRange;

/**
 * 此模型用于表示查询{@link Dict}对象时用到的过滤条件。
 *
 * @author 胡海星
 */
public class DictFilter {

  private String code;
  private String name;
  private String standardDoc;
  private String standardCode;
  private Boolean deleted;
  private InstantRange createTime;
  private InstantRange modifyTime;
  private InstantRange deleteTime;

  public String getCode() {
    return code;
  }

  public DictFilter setCode(final String code) {
    this.code = code;
    return this;
  }

  public final String getName() {
    return name;
  }

  public final DictFilter setName(final String name) {
    this.name = name;
    return this;
  }

  public final String getStandardDoc() {
    return standardDoc;
  }

  public final DictFilter setStandardDoc(final String standardDoc) {
    this.standardDoc = standardDoc;
    return this;
  }

  public final String getStandardCode() {
    return standardCode;
  }

  public final DictFilter setStandardCode(final String standardCode) {
    this.standardCode = standardCode;
    return this;
  }

  public final Boolean getDeleted() {
    return deleted;
  }

  public final DictFilter setDeleted(final Boolean deleted) {
    this.deleted = deleted;
    return this;
  }

  public final InstantRange getCreateTime() {
    return createTime;
  }

  public final DictFilter setCreateTime(final InstantRange createTime) {
    this.createTime = createTime;
    return this;
  }

  public final InstantRange getModifyTime() {
    return modifyTime;
  }

  public final DictFilter setModifyTime(final InstantRange modifyTime) {
    this.modifyTime = modifyTime;
    return this;
  }

  public final InstantRange getDeleteTime() {
    return deleteTime;
  }

  public final DictFilter setDeleteTime(final InstantRange deleteTime) {
    this.deleteTime = deleteTime;
    return this;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final DictFilter other = (DictFilter) o;
    return Equality.equals(code, other.code)
            && Equality.equals(name, other.name)
            && Equality.equals(standardDoc, other.standardDoc)
            && Equality.equals(standardCode, other.standardCode)
            && Equality.equals(deleted, other.deleted)
            && Equality.equals(createTime, other.createTime)
            && Equality.equals(modifyTime, other.modifyTime)
            && Equality.equals(deleteTime, other.deleteTime);
  }

  @Override
  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, code);
    result = Hash.combine(result, multiplier, name);
    result = Hash.combine(result, multiplier, standardDoc);
    result = Hash.combine(result, multiplier, standardCode);
    result = Hash.combine(result, multiplier, deleted);
    result = Hash.combine(result, multiplier, createTime);
    result = Hash.combine(result, multiplier, modifyTime);
    result = Hash.combine(result, multiplier, deleteTime);
    return result;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
            .append("code", code)
            .append("name", name)
            .append("standardDoc", standardDoc)
            .append("standardCode", standardCode)
            .append("deleted", deleted)
            .append("createTime", createTime)
            .append("modifyTime", modifyTime)
            .append("deleteTime", deleteTime)
            .toString();
  }
}
