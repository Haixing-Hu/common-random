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
import ltd.qubit.commons.random.beans.util.HasInfoWithEntity;
import ltd.qubit.commons.random.beans.util.InfoWithEntity;
import ltd.qubit.commons.random.beans.util.Normalizable;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * 此模型表示实体的类别。
 *
 * @author 胡海星
 */
public class Category implements HasInfoWithEntity, Auditable, Emptyful,
    Normalizable, Assignable<Category> {

  private static final long serialVersionUID = 8069770708740141238L;

  /**
   * 用于连接类别显示名称的字符串。
   */
  public static final String TITLE_JOINER = " - ";

  /**
   * 唯一标识，系统自动生成。
   */
  @Identifier
  private Long id;

  /**
   * 该类别所属实体。
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
   * 名称，同一实体下类别名称不可重复。
   */
  @Size(min = 1, max = 128)
  @Unique(respectTo = "entity")
  private String name;

  /**
   * 图标。
   */
  @Size(min = 1, max = 512)
  @Nullable
  private String icon;

  /**
   * 描述。
   */
  @Nullable
  private String description;

  /**
   * 显示标题。
   *
   * <p>若当前类别没有父类别，则显示标题为当前类别的名称；否则显示标题定义为：
   * <pre>
   * [父类别标题] "-" [当前类别名称]
   * </pre>
   *
   * <p>即用连字符连接父类别标题和当前类别名称，其中父类别标题用同样的方法递归定义。
   *
   * <p>例如，<code>"主营业务收入 - 在线商城 - 会员卡"</code>
   *
   * <p>此字段通过查询构造生成
   */
  @Size(min = 1, max = 4096)
  @Computed(value = {"name", "parent"})
  @Nullable
  private String title;

  /**
   * 父类别基本信息。
   *
   * <p>父类别必须与子类别属于同一个App同一个实体；若不存在父类别则此属性为{@code null}。
   */
  @Reference(entity = Category.class, property = "info")
  @Nullable
  private InfoWithEntity parent;

  /**
   * 是否是预定义的数据。
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

  public Category() {
    // empty
  }

  public Category(final Category other) {
    assign(other);
  }

  @Override
  public void assign(final Category other) {
    Argument.requireNonNull("other", other);
    id = other.id;
    entity = other.entity;
    code = other.code;
    name = other.name;
    icon = other.icon;
    description = other.description;
    title = other.title;
    parent = Assignment.clone(other.parent);
    predefined = other.predefined;
    createTime = other.createTime;
    modifyTime = other.modifyTime;
    deleteTime = other.deleteTime;
  }

  @Override
  public Category cloneEx() {
    return new Category(this);
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
  public final String getTitle() {
    return title;
  }

  public final void setTitle(@Nullable final String title) {
    this.title = title;
  }

  @Nullable
  public final InfoWithEntity getParent() {
    return parent;
  }

  public final void setParent(@Nullable final InfoWithEntity parent) {
    this.parent = parent;
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
    final Category other = (Category) o;
    return Equality.equals(id, other.id)
        && Equality.equals(entity, other.entity)
        && Equality.equals(code, other.code)
        && Equality.equals(name, other.name)
        && Equality.equals(icon, other.icon)
        && Equality.equals(description, other.description)
        && Equality.equals(title, other.title)
        && Equality.equals(parent, other.parent)
        && Equality.equals(predefined, other.predefined)
        && Equality.equals(createTime, other.createTime)
        && Equality.equals(modifyTime, other.modifyTime)
        && Equality.equals(deleteTime, other.deleteTime);
  }

  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, id);
    result = Hash.combine(result, multiplier, entity);
    result = Hash.combine(result, multiplier, code);
    result = Hash.combine(result, multiplier, name);
    result = Hash.combine(result, multiplier, icon);
    result = Hash.combine(result, multiplier, description);
    result = Hash.combine(result, multiplier, title);
    result = Hash.combine(result, multiplier, parent);
    result = Hash.combine(result, multiplier, predefined);
    result = Hash.combine(result, multiplier, createTime);
    result = Hash.combine(result, multiplier, modifyTime);
    result = Hash.combine(result, multiplier, deleteTime);
    return result;
  }

  public String toString() {
    return new ToStringBuilder(this)
        .append("id", id)
        .append("entity", entity)
        .append("code", code)
        .append("name", name)
        .append("icon", icon)
        .append("description", description)
        .append("title", title)
        .append("parent", parent)
        .append("predefined", predefined)
        .append("createTime", createTime)
        .append("modifyTime", modifyTime)
        .append("deleteTime", deleteTime)
        .toString();
  }
}
