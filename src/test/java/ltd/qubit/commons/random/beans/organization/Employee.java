////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.beans.organization;

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
import ltd.qubit.commons.random.beans.commons.Credential;
import ltd.qubit.commons.random.beans.commons.CredentialInfo;
import ltd.qubit.commons.random.beans.commons.State;
import ltd.qubit.commons.random.beans.contact.Contact;
import ltd.qubit.commons.random.beans.person.Gender;
import ltd.qubit.commons.random.beans.person.Person;
import ltd.qubit.commons.random.beans.person.User;
import ltd.qubit.commons.random.beans.util.Auditable;
import ltd.qubit.commons.random.beans.util.Emptyful;
import ltd.qubit.commons.random.beans.util.Identifiable;
import ltd.qubit.commons.random.beans.util.InfoWithEntity;
import ltd.qubit.commons.random.beans.util.Normalizable;
import ltd.qubit.commons.random.beans.util.Stateful;
import ltd.qubit.commons.random.beans.util.StatefulInfo;
import ltd.qubit.commons.random.beans.util.WithCategory;
import ltd.qubit.commons.random.beans.util.WithCode;
import ltd.qubit.commons.random.beans.util.WithComment;
import ltd.qubit.commons.random.beans.util.WithContact;
import ltd.qubit.commons.random.beans.util.WithCredential;
import ltd.qubit.commons.random.beans.util.WithName;
import ltd.qubit.commons.random.beans.util.WithOrganization;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * 此模型表示机构下属职工信息详情。
 *
 * @author 胡海星
 */
public class Employee implements Identifiable, WithCode, WithName, WithCategory,
    WithOrganization, WithContact, WithCredential, WithComment, Stateful,
    Auditable, Emptyful, Normalizable, Assignable<Employee> {

  private static final long serialVersionUID = 4249048050215639904L;

  /**
   * 唯一标识，系统自动生成。
   */
  @Identifier
  private Long id;

  /**
   * 所属用户ID，对应于 {@link User} 类的 id 属性。
   *
   * <p>注意若该员工没有在系统中注册，此属性可以为{@code null}
   */
  @Reference(entity = User.class, property = "id")
  @Nullable
  private Long userId;

  /**
   * 个人档案ID，对应于 {@link Person} 类的 id 属性。
   *
   * <p>注意若系统中不存在该员工的个人信息，此属性可以为{@code null}。
   */
  @Reference(entity = Person.class, property = "id")
  @Nullable
  private Long personId;

  /**
   * 编码，全局唯一。
   *
   * <p>可使用 [所属机构编码] + "-" + [机构内部编码] 生成全局唯一编码。
   */
  @Size(min = 1, max = 64)
  @Unique
  private String code;

  /**
   * 在所属机构内部编码，在所属机构内部不可重复。
   */
  @Size(min = 1, max = 64)
  @Unique(respectTo = "organization")
  @Nullable
  private String internalCode;

  /**
   * 姓名。
   */
  @Size(min = 1, max = 128)
  private String name;

  /**
   * 性别。
   */
  private Gender gender;

  /**
   * 所属类别。
   */
  @Reference(entity = Category.class, property = "info")
  @Nullable
  private InfoWithEntity category;

  /**
   * 所属机构。
   */
  @Reference(entity = Organization.class, property = "info")
  private StatefulInfo organization;

  /**
   * 所属部门。
   */
  @Reference(entity = Department.class, property = "info")
  @Nullable
  private StatefulInfo department;

  /**
   * 工作联系方式。
   */
  @Reference
  @Nullable
  private Contact contact;

  /**
   * 照片，存储相对路径或者URL。
   */
  @Size(max = 512)
  @Nullable
  private String photo;

  /**
   * 描述。
   */
  @Nullable
  private String description;

  /**
   * 身份证明证件，例如身份证、护照等。
   */
  @Reference(entity = Credential.class, property = "info")
  @Nullable
  private CredentialInfo credential;

  /**
   * 执业资格证。
   */
  @Reference(entity = Credential.class, property = "info")
  @Nullable
  private CredentialInfo practisingCertificate;

  /**
   * 职称资格证。
   */
  @Reference(entity = Credential.class, property = "info")
  @Nullable
  private CredentialInfo titleCertificate;

  /**
   * 执业类别，字典条目。
   */
  @Size(min = 1, max = 256)
  @Nullable
  private String practisingType;

  /**
   * 执业范围，字典条目。
   */
  @Size(min = 1, max = 256)
  @Nullable
  private String practisingScope;

  /**
   * 职称。
   */
  @Size(min = 1, max = 256)
  @Nullable
  private String jobTitle;

  /**
   * 备注。
   */
  @Nullable
  private String comment;

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

  public Employee() {
    // empty
  }

  public Employee(final Employee other) {
    assign(other);
  }

  @Override
  public void assign(final Employee other) {
    Argument.requireNonNull("other", other);
    id = other.id;
    userId = other.userId;
    personId = other.personId;
    code = other.code;
    internalCode = other.internalCode;
    name = other.name;
    gender = other.gender;
    category = Assignment.clone(other.category);
    organization = Assignment.clone(other.organization);
    department = Assignment.clone(other.department);
    contact = Assignment.clone(other.contact);
    photo = other.photo;
    description = other.description;
    credential = Assignment.clone(other.credential);
    practisingCertificate = Assignment.clone(other.practisingCertificate);
    titleCertificate = Assignment.clone(other.titleCertificate);
    practisingType = other.practisingType;
    practisingScope = other.practisingScope;
    jobTitle = other.jobTitle;
    comment = other.comment;
    state = other.state;
    createTime = other.createTime;
    modifyTime = other.modifyTime;
    deleteTime = other.deleteTime;
  }

  @Override
  public Employee clone() {
    return new Employee(this);
  }

  public final Long getId() {
    return id;
  }

  public final void setId(final Long id) {
    this.id = id;
  }

  @Nullable
  public final Long getUserId() {
    return userId;
  }

  public final void setUserId(@Nullable final Long userId) {
    this.userId = userId;
  }

  @Nullable
  public final Long getPersonId() {
    return personId;
  }

  public final void setPersonId(@Nullable final Long personId) {
    this.personId = personId;
  }

  public final String getCode() {
    return code;
  }

  public final void setCode(final String code) {
    this.code = code;
  }

  @Nullable
  public final String getInternalCode() {
    return internalCode;
  }

  public final void setInternalCode(@Nullable final String internalCode) {
    this.internalCode = internalCode;
  }

  public final String getName() {
    return name;
  }

  public final void setName(final String name) {
    this.name = name;
  }

  public final Gender getGender() {
    return gender;
  }

  public final void setGender(final Gender gender) {
    this.gender = gender;
  }

  @Nullable
  public final InfoWithEntity getCategory() {
    return category;
  }

  public final void setCategory(@Nullable final InfoWithEntity category) {
    this.category = category;
  }

  public final StatefulInfo getOrganization() {
    return organization;
  }

  public final void setOrganization(final StatefulInfo organization) {
    this.organization = organization;
  }

  @Nullable
  public final StatefulInfo getDepartment() {
    return department;
  }

  public final void setDepartment(@Nullable final StatefulInfo department) {
    this.department = department;
  }

  @Nullable
  public final Contact getContact() {
    return contact;
  }

  public final void setContact(@Nullable final Contact contact) {
    this.contact = contact;
  }

  @Nullable
  public final String getPhoto() {
    return photo;
  }

  public final void setPhoto(@Nullable final String photo) {
    this.photo = photo;
  }

  @Nullable
  public final String getDescription() {
    return description;
  }

  public final void setDescription(@Nullable final String description) {
    this.description = description;
  }

  @Nullable
  public final CredentialInfo getCredential() {
    return credential;
  }

  public final void setCredential(@Nullable final CredentialInfo credential) {
    this.credential = credential;
  }

  @Nullable
  public final CredentialInfo getPractisingCertificate() {
    return practisingCertificate;
  }

  public final void setPractisingCertificate(@Nullable final CredentialInfo practisingCertificate) {
    this.practisingCertificate = practisingCertificate;
  }

  @Nullable
  public final CredentialInfo getTitleCertificate() {
    return titleCertificate;
  }

  public final void setTitleCertificate(@Nullable final CredentialInfo titleCertificate) {
    this.titleCertificate = titleCertificate;
  }

  @Nullable
  public final String getPractisingType() {
    return practisingType;
  }

  public final void setPractisingType(@Nullable final String practisingType) {
    this.practisingType = practisingType;
  }

  @Nullable
  public final String getPractisingScope() {
    return practisingScope;
  }

  public final void setPractisingScope(@Nullable final String practisingScope) {
    this.practisingScope = practisingScope;
  }

  @Nullable
  public final String getJobTitle() {
    return jobTitle;
  }

  public final void setJobTitle(@Nullable final String jobTitle) {
    this.jobTitle = jobTitle;
  }

  @Nullable
  public final String getComment() {
    return comment;
  }

  public final void setComment(@Nullable final String comment) {
    this.comment = comment;
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

  public boolean equals(@Nullable final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final Employee other = (Employee) o;
    return Equality.equals(id, other.id)
        && Equality.equals(userId, other.userId)
        && Equality.equals(personId, other.personId)
        && Equality.equals(code, other.code)
        && Equality.equals(internalCode, other.internalCode)
        && Equality.equals(name, other.name)
        && Equality.equals(gender, other.gender)
        && Equality.equals(category, other.category)
        && Equality.equals(organization, other.organization)
        && Equality.equals(department, other.department)
        && Equality.equals(contact, other.contact)
        && Equality.equals(photo, other.photo)
        && Equality.equals(description, other.description)
        && Equality.equals(credential, other.credential)
        && Equality.equals(practisingCertificate, other.practisingCertificate)
        && Equality.equals(titleCertificate, other.titleCertificate)
        && Equality.equals(practisingType, other.practisingType)
        && Equality.equals(practisingScope, other.practisingScope)
        && Equality.equals(jobTitle, other.jobTitle)
        && Equality.equals(comment, other.comment)
        && Equality.equals(state, other.state)
        && Equality.equals(createTime, other.createTime)
        && Equality.equals(modifyTime, other.modifyTime)
        && Equality.equals(deleteTime, other.deleteTime);
  }

  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, id);
    result = Hash.combine(result, multiplier, userId);
    result = Hash.combine(result, multiplier, personId);
    result = Hash.combine(result, multiplier, code);
    result = Hash.combine(result, multiplier, internalCode);
    result = Hash.combine(result, multiplier, name);
    result = Hash.combine(result, multiplier, gender);
    result = Hash.combine(result, multiplier, category);
    result = Hash.combine(result, multiplier, organization);
    result = Hash.combine(result, multiplier, department);
    result = Hash.combine(result, multiplier, contact);
    result = Hash.combine(result, multiplier, photo);
    result = Hash.combine(result, multiplier, description);
    result = Hash.combine(result, multiplier, credential);
    result = Hash.combine(result, multiplier, practisingCertificate);
    result = Hash.combine(result, multiplier, titleCertificate);
    result = Hash.combine(result, multiplier, practisingType);
    result = Hash.combine(result, multiplier, practisingScope);
    result = Hash.combine(result, multiplier, jobTitle);
    result = Hash.combine(result, multiplier, comment);
    result = Hash.combine(result, multiplier, state);
    result = Hash.combine(result, multiplier, createTime);
    result = Hash.combine(result, multiplier, modifyTime);
    result = Hash.combine(result, multiplier, deleteTime);
    return result;
  }

  public String toString() {
    return new ToStringBuilder(this)
        .append("id", id)
        .append("userId", userId)
        .append("personId", personId)
        .append("code", code)
        .append("internalCode", internalCode)
        .append("name", name)
        .append("gender", gender)
        .append("category", category)
        .append("organization", organization)
        .append("department", department)
        .append("contact", contact)
        .append("photo", photo)
        .append("description", description)
        .append("credential", credential)
        .append("practisingCertificate", practisingCertificate)
        .append("titleCertificate", titleCertificate)
        .append("practisingType", practisingType)
        .append("practisingScope", practisingScope)
        .append("jobTitle", jobTitle)
        .append("comment", comment)
        .append("state", state)
        .append("createTime", createTime)
        .append("modifyTime", modifyTime)
        .append("deleteTime", deleteTime)
        .toString();
  }

  public EmployeeInfo getInfo() {
    return new EmployeeInfo(this);
  }
}
