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
import ltd.qubit.commons.random.beans.organization.Organization;
import ltd.qubit.commons.random.beans.util.Auditable;
import ltd.qubit.commons.random.beans.util.Emptyful;
import ltd.qubit.commons.random.beans.util.HasInfo;
import ltd.qubit.commons.random.beans.util.InfoWithEntity;
import ltd.qubit.commons.random.beans.util.Normalizable;
import ltd.qubit.commons.random.beans.util.StatefulInfo;
import ltd.qubit.commons.random.beans.util.WithApp;
import ltd.qubit.commons.random.beans.util.WithEntity;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * 此模型表示渠道来源，例如可以表示产品订单的渠道，也可以表示新用户的来源等。
 *
 * @author 胡海星
 */
public class Source implements HasInfo, WithApp, WithEntity, Auditable,
    Emptyful, Normalizable, Assignable<Source> {

  private static final long serialVersionUID = -2153621088317243035L;

  /**
   * 内部唯一标识。
   */
  @Identifier
  private Long id;

  /**
   * 所属 App 的基本信息。
   */
  @Reference(entity = App.class, property = "info")
  private StatefulInfo app;

  /**
   * 所属实体名称。
   */
  @Size(min = 1, max = 64)
  private String entity;

  /**
   * 编码，全局不可重复。
   */
  @Size(min = 1, max = 64)
  @Unique
  private String code;

  /**
   * 名称，同一个App的同一类实体下来源名称不可重复。
   */
  @Size(min = 1, max = 128)
  @Unique(respectTo = {"app", "entity"})
  private String name;

  /**
   * 描述。
   */
  @Nullable
  private String description;

  /**
   * 所属类别的基本信息。
   */
  @Reference(entity = Category.class, property = "info")
  @Nullable
  private InfoWithEntity category;

  /**
   * 供应商的App的基本信息。
   */
  @Reference(entity = App.class, property = "info")
  @Nullable
  private StatefulInfo providerApp;

  /**
   * 供应商机构的基本信息。
   */
  @Reference(entity = Organization.class, property = "info")
  @Nullable
  private StatefulInfo providerOrg;

  /**
   * 是否是预定义的数据。
   */
  private boolean predefined;

  /**
   * 此对象创建时间。
   */
  @Precision(TimeUnit.SECONDS)
  @Nullable
  private Instant createTime;

  /**
   * 此对象最后一次修改时间。
   */
  @Precision(TimeUnit.SECONDS)
  @Nullable
  private Instant modifyTime;

  /**
   * 此对象标记删除时间。
   */
  @Precision(TimeUnit.SECONDS)
  @Nullable
  private Instant deleteTime;

  public Source() {
    // empty
  }

  public Source(final Source other) {
    assign(other);
  }

  @Override
  public void assign(final Source other) {
    Argument.requireNonNull("other", other);
    id = other.id;
    app = Assignment.clone(other.app);
    entity = other.entity;
    code = other.code;
    name = other.name;
    description = other.description;
    category = Assignment.clone(other.category);
    providerApp = Assignment.clone(other.providerApp);
    providerOrg = Assignment.clone(other.providerOrg);
    predefined = other.predefined;
    createTime = other.createTime;
    modifyTime = other.modifyTime;
    deleteTime = other.deleteTime;
  }

  @Override
  public Source clone() {
    return new Source(this);
  }

  public final Long getId() {
    return id;
  }

  public final void setId(final Long id) {
    this.id = id;
  }

  public final StatefulInfo getApp() {
    return app;
  }

  public final void setApp(final StatefulInfo app) {
    this.app = app;
  }

  public final String getEntity() {
    return entity;
  }

  public final void setEntity(final String entity) {
    this.entity = entity;
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
  public final InfoWithEntity getCategory() {
    return category;
  }

  public final void setCategory(@Nullable final InfoWithEntity category) {
    this.category = category;
  }

  @Nullable
  public final StatefulInfo getProviderApp() {
    return providerApp;
  }

  public final void setProviderApp(@Nullable final StatefulInfo providerApp) {
    this.providerApp = providerApp;
  }

  @Nullable
  public final StatefulInfo getProviderOrg() {
    return providerOrg;
  }

  public final void setProviderOrg(@Nullable final StatefulInfo providerOrg) {
    this.providerOrg = providerOrg;
  }

  public final boolean isPredefined() {
    return predefined;
  }

  public final void setPredefined(final boolean predefined) {
    this.predefined = predefined;
  }

  @Nullable
  public final Instant getCreateTime() {
    return createTime;
  }

  public final void setCreateTime(@Nullable final Instant createTime) {
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
    final Source other = (Source) o;
    return Equality.equals(id, other.id)
        && Equality.equals(app, other.app)
        && Equality.equals(entity, other.entity)
        && Equality.equals(code, other.code)
        && Equality.equals(name, other.name)
        && Equality.equals(description, other.description)
        && Equality.equals(category, other.category)
        && Equality.equals(providerApp, other.providerApp)
        && Equality.equals(providerOrg, other.providerOrg)
        && Equality.equals(predefined, other.predefined)
        && Equality.equals(createTime, other.createTime)
        && Equality.equals(modifyTime, other.modifyTime)
        && Equality.equals(deleteTime, other.deleteTime);
  }

  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, id);
    result = Hash.combine(result, multiplier, app);
    result = Hash.combine(result, multiplier, entity);
    result = Hash.combine(result, multiplier, code);
    result = Hash.combine(result, multiplier, name);
    result = Hash.combine(result, multiplier, description);
    result = Hash.combine(result, multiplier, category);
    result = Hash.combine(result, multiplier, providerApp);
    result = Hash.combine(result, multiplier, providerOrg);
    result = Hash.combine(result, multiplier, predefined);
    result = Hash.combine(result, multiplier, createTime);
    result = Hash.combine(result, multiplier, modifyTime);
    result = Hash.combine(result, multiplier, deleteTime);
    return result;
  }

  public String toString() {
    return new ToStringBuilder(this)
        .append("id", id)
        .append("app", app)
        .append("entity", entity)
        .append("code", code)
        .append("name", name)
        .append("description", description)
        .append("category", category)
        .append("providerApp", providerApp)
        .append("providerOrg", providerOrg)
        .append("predefined", predefined)
        .append("createTime", createTime)
        .append("modifyTime", modifyTime)
        .append("deleteTime", deleteTime)
        .toString();
  }
}
