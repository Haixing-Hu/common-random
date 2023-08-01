////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.beans.upload;

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
import ltd.qubit.commons.random.beans.commons.Category;
import ltd.qubit.commons.random.beans.commons.Owner;
import ltd.qubit.commons.random.beans.commons.State;
import ltd.qubit.commons.random.beans.util.Auditable;
import ltd.qubit.commons.random.beans.util.Identifiable;
import ltd.qubit.commons.random.beans.util.InfoWithEntity;
import ltd.qubit.commons.random.beans.util.Stateful;
import ltd.qubit.commons.random.beans.util.WithCategory;
import ltd.qubit.commons.random.beans.util.WithOwner;
import ltd.qubit.commons.random.beans.util.WithVisibility;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * 此模型表示附件信息。
 *
 * @author 胡海星
 */
public class Attachment implements Identifiable, WithOwner, WithCategory,
    WithVisibility, Stateful, Auditable, Assignable<Attachment> {

  private static final long serialVersionUID = -8100282307366234737L;

  /**
   * 唯一标识，系统自动生成。
   */
  @Identifier
  private Long id;

  /**
   * 该附件的所有者。
   */
  private Owner owner;

  /**
   * 附件类型。
   */
  private AttachmentType type;

  /**
   * 附件分类。
   */
  @Reference(entity = Category.class, property = "info")
  @Nullable
  private InfoWithEntity category;

  /**
   * 该附件在所有者指定属性的附件列表中的索引。
   */
  @Unique(respectTo = "owner")
  private int index = 0;

  /**
   * 标题。
   */
  @Size(max = 512)
  @Nullable
  private String title;

  /**
   * 描述。
   */
  @Nullable
  private String description;

  /**
   * 对应的上传文件。
   */
  @Reference(entity = Upload.class)
  private Upload upload;

  /**
   * 状态。
   */
  private State state;

  /**
   * 是否可见，默认为{@code true}
   */
  private boolean visible = true;

  /**
   * 创建时间
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

  public Attachment() {
    // empty
  }

  public Attachment(final Attachment other) {
    assign(other);
  }

  @Override
  public void assign(final Attachment other) {
    Argument.requireNonNull("other", other);
    id = other.id;
    owner = Assignment.clone(other.owner);
    type = other.type;
    category = Assignment.clone(other.category);
    index = other.index;
    title = other.title;
    description = other.description;
    upload = Assignment.clone(other.upload);
    state = other.state;
    visible = other.visible;
    createTime = other.createTime;
    modifyTime = other.modifyTime;
    deleteTime = other.deleteTime;
  }

  @Override
  public Attachment clone() {
    return new Attachment(this);
  }

  public final Long getId() {
    return id;
  }

  public final void setId(final Long id) {
    this.id = id;
  }

  public final Owner getOwner() {
    return owner;
  }

  public final void setOwner(final Owner owner) {
    this.owner = owner;
  }

  public final AttachmentType getType() {
    return type;
  }

  public final void setType(final AttachmentType type) {
    this.type = type;
  }

  @Nullable
  public final InfoWithEntity getCategory() {
    return category;
  }

  public final void setCategory(@Nullable final InfoWithEntity category) {
    this.category = category;
  }

  public final int getIndex() {
    return index;
  }

  public final void setIndex(final int index) {
    this.index = index;
  }

  @Nullable
  public final String getTitle() {
    return title;
  }

  public final void setTitle(@Nullable final String title) {
    this.title = title;
  }

  @Nullable
  public final String getDescription() {
    return description;
  }

  public final void setDescription(@Nullable final String description) {
    this.description = description;
  }

  public final Upload getUpload() {
    return upload;
  }

  public final void setUpload(final Upload upload) {
    this.upload = upload;
  }

  public final State getState() {
    return state;
  }

  public final void setState(final State state) {
    this.state = state;
  }

  public final boolean isVisible() {
    return visible;
  }

  public final void setVisible(final boolean visible) {
    this.visible = visible;
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
    final Attachment other = (Attachment) o;
    return Equality.equals(id, other.id)
        && Equality.equals(owner, other.owner)
        && Equality.equals(type, other.type)
        && Equality.equals(category, other.category)
        && Equality.equals(index, other.index)
        && Equality.equals(title, other.title)
        && Equality.equals(description, other.description)
        && Equality.equals(upload, other.upload)
        && Equality.equals(state, other.state)
        && Equality.equals(visible, other.visible)
        && Equality.equals(createTime, other.createTime)
        && Equality.equals(modifyTime, other.modifyTime)
        && Equality.equals(deleteTime, other.deleteTime);
  }

  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, id);
    result = Hash.combine(result, multiplier, owner);
    result = Hash.combine(result, multiplier, type);
    result = Hash.combine(result, multiplier, category);
    result = Hash.combine(result, multiplier, index);
    result = Hash.combine(result, multiplier, title);
    result = Hash.combine(result, multiplier, description);
    result = Hash.combine(result, multiplier, upload);
    result = Hash.combine(result, multiplier, state);
    result = Hash.combine(result, multiplier, visible);
    result = Hash.combine(result, multiplier, createTime);
    result = Hash.combine(result, multiplier, modifyTime);
    result = Hash.combine(result, multiplier, deleteTime);
    return result;
  }

  public String toString() {
    return new ToStringBuilder(this)
        .append("id", id)
        .append("owner", owner)
        .append("type", type)
        .append("category", category)
        .append("index", index)
        .append("title", title)
        .append("description", description)
        .append("upload", upload)
        .append("state", state)
        .append("visible", visible)
        .append("createTime", createTime)
        .append("modifyTime", modifyTime)
        .append("deleteTime", deleteTime)
        .toString();
  }
}
