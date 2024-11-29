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

import ltd.qubit.commons.annotation.Identifier;
import ltd.qubit.commons.annotation.Precision;
import ltd.qubit.commons.annotation.Reference;
import ltd.qubit.commons.lang.Argument;
import ltd.qubit.commons.lang.Assignable;
import ltd.qubit.commons.lang.Assignment;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.random.beans.util.Auditable;
import ltd.qubit.commons.random.beans.util.Emptyful;
import ltd.qubit.commons.random.beans.util.Identifiable;
import ltd.qubit.commons.random.beans.util.Info;
import ltd.qubit.commons.random.beans.util.InfoWithEntity;
import ltd.qubit.commons.random.beans.util.Normalizable;
import ltd.qubit.commons.random.beans.util.Stateful;
import ltd.qubit.commons.random.beans.util.WithCategory;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * 此模型表示常见问答。
 *
 * @author 潘凯，胡海星
 */
public class Faq implements Identifiable, WithCategory, Stateful, Auditable,
    Emptyful, Normalizable, Assignable<Faq> {

  private static final long serialVersionUID = -5402819884948980583L;

  /**
   * 唯一标识，系统自动生成。
   */
  @Identifier
  private Long id;

  /**
   * 所属应用。
   */
  private Info app;

  /**
   * 所属类别的基本信息。
   */
  @Reference(entity = Category.class, property = "info")
  @Nullable
  private InfoWithEntity category;

  /**
   * 所属产品。
   */
  private Info product;

  /**
   * 问题描述。
   */
  private String question;

  /**
   * 回答描述。
   */
  private String answer;

  /**
   * 问题被查看频次。
   */
  private Long frequency = 0L;

  /**
   * 状态。
   */
  private State state = State.NORMAL;

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

  public Faq() {
    // empty
  }

  public Faq(final Faq other) {
    assign(other);
  }

  @Override
  public void assign(final Faq other) {
    Argument.requireNonNull("other", other);
    id = other.id;
    app = Assignment.clone(other.app);
    category = Assignment.clone(other.category);
    product = Assignment.clone(other.product);
    question = other.question;
    answer = other.answer;
    frequency = other.frequency;
    state = other.state;
    createTime = other.createTime;
    modifyTime = other.modifyTime;
    deleteTime = other.deleteTime;
  }

  @Override
  public Faq cloneEx() {
    return new Faq(this);
  }

  public final Long getId() {
    return id;
  }

  public final void setId(final Long id) {
    this.id = id;
  }

  public final Info getApp() {
    return app;
  }

  public final void setApp(final Info app) {
    this.app = app;
  }

  @Nullable
  public final InfoWithEntity getCategory() {
    return category;
  }

  public final void setCategory(@Nullable final InfoWithEntity category) {
    this.category = category;
  }

  public final Info getProduct() {
    return product;
  }

  public final void setProduct(final Info product) {
    this.product = product;
  }

  public final String getQuestion() {
    return question;
  }

  public final void setQuestion(final String question) {
    this.question = question;
  }

  public final String getAnswer() {
    return answer;
  }

  public final void setAnswer(final String answer) {
    this.answer = answer;
  }

  public final Long getFrequency() {
    return frequency;
  }

  public final void setFrequency(final Long frequency) {
    this.frequency = frequency;
  }

  public final State getState() {
    return state;
  }

  public final void setState(final State state) {
    this.state = state;
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
    final Faq other = (Faq) o;
    return Equality.equals(id, other.id)
        && Equality.equals(app, other.app)
        && Equality.equals(category, other.category)
        && Equality.equals(product, other.product)
        && Equality.equals(question, other.question)
        && Equality.equals(answer, other.answer)
        && Equality.equals(frequency, other.frequency)
        && Equality.equals(state, other.state)
        && Equality.equals(createTime, other.createTime)
        && Equality.equals(modifyTime, other.modifyTime)
        && Equality.equals(deleteTime, other.deleteTime);
  }

  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, id);
    result = Hash.combine(result, multiplier, app);
    result = Hash.combine(result, multiplier, category);
    result = Hash.combine(result, multiplier, product);
    result = Hash.combine(result, multiplier, question);
    result = Hash.combine(result, multiplier, answer);
    result = Hash.combine(result, multiplier, frequency);
    result = Hash.combine(result, multiplier, state);
    result = Hash.combine(result, multiplier, createTime);
    result = Hash.combine(result, multiplier, modifyTime);
    result = Hash.combine(result, multiplier, deleteTime);
    return result;
  }

  public String toString() {
    return new ToStringBuilder(this)
        .append("id", id)
        .append("app", app)
        .append("category", category)
        .append("product", product)
        .append("question", question)
        .append("answer", answer)
        .append("frequency", frequency)
        .append("state", state)
        .append("createTime", createTime)
        .append("modifyTime", modifyTime)
        .append("deleteTime", deleteTime)
        .toString();
  }
}
