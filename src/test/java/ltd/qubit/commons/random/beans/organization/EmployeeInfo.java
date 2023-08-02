////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.beans.organization;

import javax.annotation.Nullable;

import jakarta.validation.constraints.Size;

import ltd.qubit.commons.annotation.Identifier;
import ltd.qubit.commons.annotation.Unique;
import ltd.qubit.commons.lang.Argument;
import ltd.qubit.commons.lang.Assignable;
import ltd.qubit.commons.lang.Assignment;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.random.beans.commons.CredentialInfo;
import ltd.qubit.commons.random.beans.contact.Phone;
import ltd.qubit.commons.random.beans.person.Gender;
import ltd.qubit.commons.random.beans.util.Emptyful;
import ltd.qubit.commons.random.beans.util.HasInfo;
import ltd.qubit.commons.random.beans.util.Normalizable;
import ltd.qubit.commons.random.beans.util.StatefulInfo;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * 此模型表示机构下属职工基本信息。
 *
 * @author 胡海星
 */
public class EmployeeInfo implements HasInfo, Emptyful, Normalizable,
    Assignable<EmployeeInfo> {

  private static final long serialVersionUID = -1731433473911709869L;

  /**
   * 唯一标识，系统自动生成。
   */
  @Identifier
  private Long id;

  /**
   * 编号，全局唯一；可使用所属机构编号+机构内部编号生成全局唯一编号。
   */
  @Size(min = 1, max = 64)
  @Unique
  private String code;

  /**
   * 在所属机构内部编号。
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
   * 证件。
   */
  @Nullable
  private CredentialInfo credential;

  /**
   * 手机号码。
   */
  @Nullable
  private Phone mobile;

  /**
   * 所属机构基本信息。
   */
  private StatefulInfo organization;

  /**
   * 所属部门基本信息。
   */
  @Nullable
  private StatefulInfo department;

  public static EmployeeInfo create(
          @Nullable final Long id,
          @Nullable final String code,
          @Nullable final String internalCode,
          @Nullable final String name,
          @Nullable final CredentialInfo credential,
          @Nullable final Phone mobile) {
    if (id == null
            && code == null
            && internalCode == null
            && name == null
            && credential == null
            && mobile == null) {
      return null;
    }
    final EmployeeInfo result = new EmployeeInfo();
    result.id = id;
    result.code = code;
    result.name = name;
    result.credential = credential;
    result.mobile = mobile;
    return result;
  }

  public static EmployeeInfo create(
          @Nullable final Long id,
          @Nullable final String code,
          @Nullable final String name,
          @Nullable final CredentialInfo credential,
          @Nullable final Phone mobile,
          @Nullable final StatefulInfo organization,
          @Nullable final StatefulInfo department) {
    if (id == null
            && code == null
            && name == null
            && credential == null
            && organization == null
            && department == null
            && mobile == null) {
      return null;
    }
    final EmployeeInfo result = new EmployeeInfo();
    result.id = id;
    result.code = code;
    result.name = name;
    result.credential = credential;
    result.mobile = mobile;
    result.organization = organization;
    result.department = department;
    return result;
  }

  public EmployeeInfo() {
  }

  public EmployeeInfo(final EmployeeInfo other) {
    assign(other);
  }

  public EmployeeInfo(final Employee employee) {
    assign(employee);
  }

  @Override
  public void assign(final EmployeeInfo other) {
    Argument.requireNonNull("other", other);
    id = other.id;
    code = other.code;
    internalCode = other.internalCode;
    name = other.name;
    gender = other.gender;
    credential = Assignment.clone(other.credential);
    mobile = Assignment.clone(other.mobile);
    organization = Assignment.clone(other.organization);
    department = Assignment.clone(other.department);
  }

  public void assign(final Employee employee) {
    Argument.requireNonNull("employee", employee);
    this.id = employee.getId();
    this.code = employee.getCode();
    this.internalCode = employee.getInternalCode();
    this.name = employee.getName();
    this.gender = employee.getGender();
    this.credential = employee.getCredential();
    this.mobile = (employee.getContact() != null
                   ? employee.getContact().getMobile()
                   : null);
    this.organization = employee.getOrganization();
    this.department = employee.getDepartment();
  }

  @Override
  public EmployeeInfo clone() {
    return new EmployeeInfo(this);
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

  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final EmployeeInfo other = (EmployeeInfo) o;
    return Equality.equals(id, other.id)
        && Equality.equals(code, other.code)
        && Equality.equals(internalCode, other.internalCode)
        && Equality.equals(name, other.name)
        && Equality.equals(gender, other.gender)
        && Equality.equals(credential, other.credential)
        && Equality.equals(mobile, other.mobile)
        && Equality.equals(organization, other.organization)
        && Equality.equals(department, other.department);
  }

  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, id);
    result = Hash.combine(result, multiplier, code);
    result = Hash.combine(result, multiplier, internalCode);
    result = Hash.combine(result, multiplier, name);
    result = Hash.combine(result, multiplier, gender);
    result = Hash.combine(result, multiplier, credential);
    result = Hash.combine(result, multiplier, mobile);
    result = Hash.combine(result, multiplier, organization);
    result = Hash.combine(result, multiplier, department);
    return result;
  }

  public String toString() {
    return new ToStringBuilder(this)
        .append("id", id)
        .append("code", code)
        .append("internalCode", internalCode)
        .append("name", name)
        .append("gender", gender)
        .append("credential", credential)
        .append("mobile", mobile)
        .append("organization", organization)
        .append("department", department)
        .toString();
  }
}
