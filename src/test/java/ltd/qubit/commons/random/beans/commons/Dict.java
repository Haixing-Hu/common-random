////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.beans.commons;

import java.time.Instant;
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
import ltd.qubit.commons.random.beans.util.Auditable;
import ltd.qubit.commons.random.beans.util.Emptyful;
import ltd.qubit.commons.random.beans.util.HasStatefulInfo;
import ltd.qubit.commons.random.beans.util.InfoWithEntity;
import ltd.qubit.commons.random.beans.util.Normalizable;
import ltd.qubit.commons.random.beans.util.StatefulInfo;
import ltd.qubit.commons.random.beans.util.WithApp;
import ltd.qubit.commons.random.beans.util.WithCategory;
import ltd.qubit.commons.random.beans.util.WithComment;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * 此模型表示数据字典。
 *
 * @author 胡海星
 * @see DictEntry
 */
public class Dict implements HasStatefulInfo, WithApp, WithCategory, WithComment,
    Auditable, Emptyful, Normalizable, Assignable<Dict> {

  private static final long serialVersionUID = 6810466251438620015L;

  /**
   * 唯一标识，系统自动生成。
   */
  @Identifier
  private Long id;

  /**
   * 代码，全局不可重复，一旦设置不能更改。
   */
  @Unique
  @Size(min = 1, max = 64)
  private String code;

  /**
   * 名称。
   */
  @Size(min = 1, max = 128)
  private String name;

  /**
   * 所遵循的标准规范。
   */
  @Size(max = 128)
  @Nullable
  private String standardDoc;

  /**
   * 在所遵循的标准规范中的编码。
   */
  @Size(max = 64)
  @Nullable
  private String standardCode;

  /**
   * 网址 URL。
   */
  @Size(max = 512)
  @Nullable
  private String url;

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
   * 所属App的基本信息。
   */
  @Reference(entity = App.class, property = "info")
  @Nullable
  private StatefulInfo app;

  /**
   * 所属类别的基本信息。
   */
  @Reference(entity = Category.class, property = "info")
  @Nullable
  private InfoWithEntity category;

  /**
   * 状态。
   */
  private State state = State.NORMAL;

  /**
   * 是否是预定义的数据。
   */
  private boolean predefined;

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

  public Dict() {
    // empty
  }

  public Dict(final Dict other) {
    assign(other);
  }

  @Override
  public void assign(final Dict other) {
    Argument.requireNonNull("other", other);
    id = other.id;
    code = other.code;
    name = other.name;
    standardDoc = other.standardDoc;
    standardCode = other.standardCode;
    url = other.url;
    description = other.description;
    comment = other.comment;
    app = Assignment.clone(other.app);
    category = Assignment.clone(other.category);
    state = other.state;
    predefined = other.predefined;
    createTime = other.createTime;
    modifyTime = other.modifyTime;
    deleteTime = other.deleteTime;
  }

  @Override
  public Dict clone() {
    return new Dict(this);
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
  public final String getStandardDoc() {
    return standardDoc;
  }

  public final void setStandardDoc(@Nullable final String standardDoc) {
    this.standardDoc = standardDoc;
  }

  @Nullable
  public final String getStandardCode() {
    return standardCode;
  }

  public final void setStandardCode(@Nullable final String standardCode) {
    this.standardCode = standardCode;
  }

  @Nullable
  public final String getUrl() {
    return url;
  }

  public final void setUrl(@Nullable final String url) {
    this.url = url;
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
  public final StatefulInfo getApp() {
    return app;
  }

  public final void setApp(@Nullable final StatefulInfo app) {
    this.app = app;
  }

  @Nullable
  public final InfoWithEntity getCategory() {
    return category;
  }

  public final void setCategory(@Nullable final InfoWithEntity category) {
    this.category = category;
  }

  public final State getState() {
    return state;
  }

  public final void setState(final State state) {
    this.state = state;
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
    final Dict other = (Dict) o;
    return Equality.equals(id, other.id)
        && Equality.equals(code, other.code)
        && Equality.equals(name, other.name)
        && Equality.equals(standardDoc, other.standardDoc)
        && Equality.equals(standardCode, other.standardCode)
        && Equality.equals(url, other.url)
        && Equality.equals(description, other.description)
        && Equality.equals(comment, other.comment)
        && Equality.equals(app, other.app)
        && Equality.equals(category, other.category)
        && Equality.equals(state, other.state)
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
    result = Hash.combine(result, multiplier, standardDoc);
    result = Hash.combine(result, multiplier, standardCode);
    result = Hash.combine(result, multiplier, url);
    result = Hash.combine(result, multiplier, description);
    result = Hash.combine(result, multiplier, comment);
    result = Hash.combine(result, multiplier, app);
    result = Hash.combine(result, multiplier, category);
    result = Hash.combine(result, multiplier, state);
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
        .append("standardDoc", standardDoc)
        .append("standardCode", standardCode)
        .append("url", url)
        .append("description", description)
        .append("comment", comment)
        .append("app", app)
        .append("category", category)
        .append("state", state)
        .append("predefined", predefined)
        .append("createTime", createTime)
        .append("modifyTime", modifyTime)
        .append("deleteTime", deleteTime)
        .toString();
  }
}
