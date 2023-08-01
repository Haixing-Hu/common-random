////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.beans.person;

import java.time.LocalDate;

import javax.annotation.Nullable;
import javax.validation.constraints.Size;

import ltd.qubit.commons.annotation.Identifier;
import ltd.qubit.commons.lang.Argument;
import ltd.qubit.commons.lang.Assignable;
import ltd.qubit.commons.lang.Assignment;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.random.beans.commons.CredentialInfo;
import ltd.qubit.commons.random.beans.contact.Contact;
import ltd.qubit.commons.random.beans.contact.Phone;
import ltd.qubit.commons.random.beans.util.Emptyful;
import ltd.qubit.commons.random.beans.util.Identifiable;
import ltd.qubit.commons.random.beans.util.Normalizable;
import ltd.qubit.commons.random.beans.util.WithBirthday;
import ltd.qubit.commons.random.beans.util.WithName;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * 此模型表示一个人的基本信息。
 *
 * @author 胡海星
 */
public class PersonInfo implements Identifiable, WithName, WithBirthday,
    Emptyful, Normalizable, Assignable<PersonInfo> {

  private static final long serialVersionUID = -1051312542127306317L;

  /**
   * 唯一标识，系统自动生成。
   */
  @Identifier
  private Long id;

  /**
   * 姓名。
   */
  @Size(min = 1, max = 128)
  private String name;

  /**
   * 性别。
   */
  @Nullable
  private Gender gender;

  /**
   * 出生日期。
   */
  @Nullable
  private LocalDate birthday;

  /**
   * 身份证件。
   */
  @Nullable
  private CredentialInfo credential;

  /**
   * 手机号码。
   */
  @Nullable
  private Phone mobile;

  /**
   * 电子邮件地址。
   */
  @Nullable
  private String email;

  public PersonInfo() {
    // empty
  }

  public PersonInfo(final Person person) {
    Argument.requireNonNull("person", person);
    id = person.getId();
    name = person.getName();
    gender = person.getGender();
    birthday = person.getBirthday();
    credential = person.getCredential();
    final Contact contact = person.getContact();
    if (contact != null) {
      mobile = contact.getMobile();
      email = contact.getEmail();
    }
  }

  public PersonInfo(final PersonInfo other) {
    assign(other);
  }

  @Override
  public void assign(final PersonInfo other) {
    Argument.requireNonNull("other", other);
    id = other.id;
    name = other.name;
    gender = other.gender;
    birthday = other.birthday;
    credential = Assignment.clone(other.credential);
    mobile = Assignment.clone(other.mobile);
    email = other.email;
  }

  @Override
  public PersonInfo clone() {
    return new PersonInfo(this);
  }

  public final Long getId() {
    return id;
  }

  public final void setId(final Long id) {
    this.id = id;
  }

  public final String getName() {
    return name;
  }

  public final void setName(final String name) {
    this.name = name;
  }

  @Nullable
  public final Gender getGender() {
    return gender;
  }

  public final void setGender(@Nullable final Gender gender) {
    this.gender = gender;
  }

  @Nullable
  public final LocalDate getBirthday() {
    return birthday;
  }

  public final void setBirthday(@Nullable final LocalDate birthday) {
    this.birthday = birthday;
  }

  @Nullable
  public final CredentialInfo getCredential() {
    return credential;
  }

  public final void setCredential(@Nullable final CredentialInfo credential) {
    this.credential = credential;
  }

  @Nullable
  public final Phone getMobile() {
    return mobile;
  }

  public final void setMobile(@Nullable final Phone mobile) {
    this.mobile = mobile;
  }

  @Nullable
  public final String getEmail() {
    return email;
  }

  public final void setEmail(@Nullable final String email) {
    this.email = email;
  }

  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final PersonInfo other = (PersonInfo) o;
    return Equality.equals(id, other.id)
        && Equality.equals(name, other.name)
        && Equality.equals(gender, other.gender)
        && Equality.equals(birthday, other.birthday)
        && Equality.equals(credential, other.credential)
        && Equality.equals(mobile, other.mobile)
        && Equality.equals(email, other.email);
  }

  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, id);
    result = Hash.combine(result, multiplier, name);
    result = Hash.combine(result, multiplier, gender);
    result = Hash.combine(result, multiplier, birthday);
    result = Hash.combine(result, multiplier, credential);
    result = Hash.combine(result, multiplier, mobile);
    result = Hash.combine(result, multiplier, email);
    return result;
  }

  public String toString() {
    return new ToStringBuilder(this)
        .append("id", id)
        .append("name", name)
        .append("gender", gender)
        .append("birthday", birthday)
        .append("credential", credential)
        .append("mobile", mobile)
        .append("email", email)
        .toString();
  }
}
