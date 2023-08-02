////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.beans.person;

import java.time.Instant;
import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nullable;

import jakarta.validation.constraints.Size;

import ltd.qubit.commons.annotation.Computed;
import ltd.qubit.commons.annotation.Identifier;
import ltd.qubit.commons.annotation.Precision;
import ltd.qubit.commons.annotation.Reference;
import ltd.qubit.commons.lang.Argument;
import ltd.qubit.commons.lang.Assignable;
import ltd.qubit.commons.lang.Assignment;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.random.beans.commons.Credential;
import ltd.qubit.commons.random.beans.commons.CredentialInfo;
import ltd.qubit.commons.random.beans.commons.Source;
import ltd.qubit.commons.random.beans.contact.City;
import ltd.qubit.commons.random.beans.contact.Contact;
import ltd.qubit.commons.random.beans.organization.Organization;
import ltd.qubit.commons.random.beans.util.Auditable;
import ltd.qubit.commons.random.beans.util.Emptyful;
import ltd.qubit.commons.random.beans.util.Identifiable;
import ltd.qubit.commons.random.beans.util.Info;
import ltd.qubit.commons.random.beans.util.Normalizable;
import ltd.qubit.commons.random.beans.util.WithBirthday;
import ltd.qubit.commons.random.beans.util.WithComment;
import ltd.qubit.commons.random.beans.util.WithContact;
import ltd.qubit.commons.random.beans.util.WithCredential;
import ltd.qubit.commons.random.beans.util.WithName;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * 此模型表示人的具体信息。
 *
 * @author 胡海星
 */
public class Person implements Identifiable, WithName, WithCredential,
    WithBirthday, WithContact, WithComment, Auditable, Emptyful, Normalizable,
    Assignable<Person> {

  private static final long serialVersionUID = -8219332411445840617L;

  /**
   * 成年人年龄。
   */
  public static final int DEFAULT_ADULT_AGE = 18;

  /**
   * 唯一标识，系统自动生成。
   */
  @Identifier
  private Long id;

  /**
   * 数据来源基本信息。
   */
  @Reference(entity = Source.class, property = "info")
  @Nullable
  private Info source;

  /**
   * 真实姓名。
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
  @Reference(entity = Credential.class, property = "info", existing = false)
  @Nullable
  private CredentialInfo credential;

  /**
   * 是否有医保。
   */
  @Nullable
  private Boolean hasMedicare;

  /**
   * 医保类别。
   */
  @Nullable
  private MedicareType medicareType;

  /**
   * 医保卡。
   */
  @Reference(entity = Credential.class, property = "info", existing = false)
  @Nullable
  private CredentialInfo medicareCard;

  /**
   * 医保所在城市。
   */
  @Reference(entity = City.class, property = "info")
  @Nullable
  private Info medicareCity;

  /**
   * 是否有社保。
   */
  @Nullable
  private Boolean hasSocialSecurity;

  /**
   * 社保卡。
   */
  @Reference(entity = Credential.class, property = "info", existing = false)
  @Nullable
  private CredentialInfo socialSecurityCard;

  /**
   * 社保所在城市。
   */
  @Reference(entity = City.class, property = "info")
  @Nullable
  private Info socialSecurityCity;

  /**
   * 联系方式，包括电话、手机、电子邮件、通讯地址等。
   */
  @Reference
  @Nullable
  private Contact contact;

  /**
   * 监护人信息。
   *
   * <p>未满18周岁未成年人可以有监护人，监护人本身必须年满18周岁。
   */
  @Reference(entity = Person.class, property = "info")
  @Nullable
  private PersonInfo guardian;

  /**
   * 文化程度，枚举值。
   */
  @Nullable
  private Education education;

  /**
   * 民族，枚举值。
   */
  @Nullable
  private Ethnic ethnic;

  /**
   * 血型，枚举值。
   */
  @Nullable
  private Blood blood;

  /**
   * 婚姻状况，枚举值。
   */
  @Nullable
  private Marriage marriage;

  /**
   * 有无子女。
   */
  @Nullable
  private Boolean hasChild;

  /**
   * 性取向，枚举值。
   */
  @Nullable
  private SexOrientation sexOrientation;

  /**
   * 宗教信仰，枚举值。
   */
  @Nullable
  private Religion religion;

  /**
   * 政治面貌，枚举项。
   */
  @Nullable
  private Politics politics;

  /**
   * 行业，枚举值。
   */
  @Nullable
  private Industry industry;

  /**
   * 职业。
   */
  @Size(min = 1, max = 64)
  @Nullable
  private String job;

  /**
   * 职称。
   */
  @Nullable
  private String jobTitle;

  /**
   * 收入档次，枚举值。
   */
  @Nullable
  private Incoming incoming;

  /**
   * 工作单位。
   *
   * <p>注意：如果工作单位没有注册在系统中，可以只填写工作单位名称不填其ID或代码。
   */
  @Reference(entity = Organization.class, property = "info")
  @Nullable
  private Info organization;

  /**
   * 身高，单位厘米。
   */
  @Nullable
  private Integer height;

  /**
   * 体重，单位千克。
   */
  @Nullable
  private Integer weight;

  /**
   * 个人过敏史。
   */
  @Nullable
  private String allergicHistory;

  /**
   * 备注。
   */
  @Nullable
  private String comment;

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

  public Person() {
    // empty
  }

  public Person(final Person other) {
    assign(other);
  }

  public Person(final PersonInfo info) {
    assign(info);
  }

  @Override
  public void assign(final Person other) {
    Argument.requireNonNull("other", other);
    id = other.id;
    source = Assignment.clone(other.source);
    name = other.name;
    gender = other.gender;
    birthday = other.birthday;
    credential = Assignment.clone(other.credential);
    hasMedicare = other.hasMedicare;
    medicareType = other.medicareType;
    medicareCard = Assignment.clone(other.medicareCard);
    medicareCity = Assignment.clone(other.medicareCity);
    hasSocialSecurity = other.hasSocialSecurity;
    socialSecurityCard = Assignment.clone(other.socialSecurityCard);
    socialSecurityCity = Assignment.clone(other.socialSecurityCity);
    contact = Assignment.clone(other.contact);
    guardian = Assignment.clone(other.guardian);
    education = other.education;
    ethnic = other.ethnic;
    blood = other.blood;
    marriage = other.marriage;
    hasChild = other.hasChild;
    sexOrientation = other.sexOrientation;
    religion = other.religion;
    politics = other.politics;
    industry = other.industry;
    job = other.job;
    jobTitle = other.jobTitle;
    incoming = other.incoming;
    organization = Assignment.clone(other.organization);
    height = other.height;
    weight = other.weight;
    allergicHistory = other.allergicHistory;
    comment = other.comment;
    createTime = other.createTime;
    modifyTime = other.modifyTime;
    deleteTime = other.deleteTime;
  }

  @Override
  public Person clone() {
    return new Person(this);
  }

  public void assign(final PersonInfo info) {
    this.id = info.getId();
    this.name = info.getName();
    this.gender = info.getGender();
    this.birthday = info.getBirthday();
    this.credential = Assignment.clone(info.getCredential());
    this.contact = Contact.create(null, info.getMobile(), info.getEmail(), null, null);
  }

  public final Long getId() {
    return id;
  }

  public final void setId(final Long id) {
    this.id = id;
  }

  @Nullable
  public final Info getSource() {
    return source;
  }

  public final void setSource(@Nullable final Info source) {
    this.source = source;
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
  public final Boolean getHasMedicare() {
    return hasMedicare;
  }

  public final void setHasMedicare(@Nullable final Boolean hasMedicare) {
    this.hasMedicare = hasMedicare;
  }

  @Nullable
  public final MedicareType getMedicareType() {
    return medicareType;
  }

  public final void setMedicareType(@Nullable final MedicareType medicareType) {
    this.medicareType = medicareType;
  }

  @Nullable
  public final CredentialInfo getMedicareCard() {
    return medicareCard;
  }

  public final void setMedicareCard(@Nullable final CredentialInfo medicareCard) {
    this.medicareCard = medicareCard;
  }

  @Nullable
  public final Info getMedicareCity() {
    return medicareCity;
  }

  public final void setMedicareCity(@Nullable final Info medicareCity) {
    this.medicareCity = medicareCity;
  }

  @Nullable
  public final Boolean getHasSocialSecurity() {
    return hasSocialSecurity;
  }

  public final void setHasSocialSecurity(@Nullable final Boolean hasSocialSecurity) {
    this.hasSocialSecurity = hasSocialSecurity;
  }

  @Nullable
  public final CredentialInfo getSocialSecurityCard() {
    return socialSecurityCard;
  }

  public final void setSocialSecurityCard(@Nullable final CredentialInfo socialSecurityCard) {
    this.socialSecurityCard = socialSecurityCard;
  }

  @Nullable
  public final Info getSocialSecurityCity() {
    return socialSecurityCity;
  }

  public final void setSocialSecurityCity(@Nullable final Info socialSecurityCity) {
    this.socialSecurityCity = socialSecurityCity;
  }

  @Nullable
  public final Contact getContact() {
    return contact;
  }

  public final void setContact(@Nullable final Contact contact) {
    this.contact = contact;
  }

  @Nullable
  public final PersonInfo getGuardian() {
    return guardian;
  }

  public final void setGuardian(@Nullable final PersonInfo guardian) {
    this.guardian = guardian;
  }

  @Nullable
  public final Education getEducation() {
    return education;
  }

  public final void setEducation(@Nullable final Education education) {
    this.education = education;
  }

  @Nullable
  public final Ethnic getEthnic() {
    return ethnic;
  }

  public final void setEthnic(@Nullable final Ethnic ethnic) {
    this.ethnic = ethnic;
  }

  @Nullable
  public final Integer getHeight() {
    return height;
  }

  public final void setHeight(@Nullable final Integer height) {
    this.height = height;
  }

  @Nullable
  public final Integer getWeight() {
    return weight;
  }

  public final void setWeight(@Nullable final Integer weight) {
    this.weight = weight;
  }

  @Nullable
  public final Blood getBlood() {
    return blood;
  }

  public final void setBlood(@Nullable final Blood blood) {
    this.blood = blood;
  }

  @Nullable
  public final Marriage getMarriage() {
    return marriage;
  }

  public final void setMarriage(@Nullable final Marriage marriage) {
    this.marriage = marriage;
  }

  @Nullable
  public final Boolean getHasChild() {
    return hasChild;
  }

  public final void setHasChild(@Nullable final Boolean hasChild) {
    this.hasChild = hasChild;
  }

  @Nullable
  public final SexOrientation getSexOrientation() {
    return sexOrientation;
  }

  public final void setSexOrientation(@Nullable final SexOrientation sexOrientation) {
    this.sexOrientation = sexOrientation;
  }

  @Nullable
  public final Religion getReligion() {
    return religion;
  }

  public final void setReligion(@Nullable final Religion religion) {
    this.religion = religion;
  }

  @Nullable
  public final Politics getPolitics() {
    return politics;
  }

  public final void setPolitics(@Nullable final Politics politics) {
    this.politics = politics;
  }

  @Nullable
  public final Industry getIndustry() {
    return industry;
  }

  public final void setIndustry(@Nullable final Industry industry) {
    this.industry = industry;
  }

  @Nullable
  public final String getJob() {
    return job;
  }

  public final void setJob(@Nullable final String job) {
    this.job = job;
  }

  @Nullable
  public final String getJobTitle() {
    return jobTitle;
  }

  public final void setJobTitle(@Nullable final String jobTitle) {
    this.jobTitle = jobTitle;
  }

  @Nullable
  public final Incoming getIncoming() {
    return incoming;
  }

  public final void setIncoming(@Nullable final Incoming incoming) {
    this.incoming = incoming;
  }

  @Nullable
  public final Info getOrganization() {
    return organization;
  }

  public final void setOrganization(@Nullable final Info organization) {
    this.organization = organization;
  }

  @Nullable
  public final String getAllergicHistory() {
    return allergicHistory;
  }

  public final void setAllergicHistory(@Nullable final String allergicHistory) {
    this.allergicHistory = allergicHistory;
  }

  @Nullable
  public final String getComment() {
    return comment;
  }

  public final void setComment(@Nullable final String comment) {
    this.comment = comment;
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

  /**
   * 判定此人和另一个人是否是同一个人。
   *
   * @param <T>
   *     另一个用来比较的人的类型。
   * @param person
   *     另一个人。
   * @return
   *     若此人和另一个人是同一个人，及他们的ID相同或者证件相同，则返回{@code true}；否则返
   *     回{@code false}。
   */
  public final <T extends Identifiable & WithCredential>
  boolean isSame(@Nullable final T person) {
    if (person == null) {
      return false;
    } else if (id != null && person.getId() != null) {
      return id.longValue() == person.getId().longValue();
    } else if (credential == null) {
      return false;
    } else {
      return credential.isSame(person.getCredential());
    }
  }

  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final Person other = (Person) o;
    return Equality.equals(id, other.id)
        && Equality.equals(source, other.source)
        && Equality.equals(name, other.name)
        && Equality.equals(gender, other.gender)
        && Equality.equals(birthday, other.birthday)
        && Equality.equals(credential, other.credential)
        && Equality.equals(hasMedicare, other.hasMedicare)
        && Equality.equals(medicareType, other.medicareType)
        && Equality.equals(medicareCard, other.medicareCard)
        && Equality.equals(medicareCity, other.medicareCity)
        && Equality.equals(hasSocialSecurity, other.hasSocialSecurity)
        && Equality.equals(socialSecurityCard, other.socialSecurityCard)
        && Equality.equals(socialSecurityCity, other.socialSecurityCity)
        && Equality.equals(contact, other.contact)
        && Equality.equals(guardian, other.guardian)
        && Equality.equals(education, other.education)
        && Equality.equals(ethnic, other.ethnic)
        && Equality.equals(blood, other.blood)
        && Equality.equals(marriage, other.marriage)
        && Equality.equals(hasChild, other.hasChild)
        && Equality.equals(sexOrientation, other.sexOrientation)
        && Equality.equals(religion, other.religion)
        && Equality.equals(politics, other.politics)
        && Equality.equals(industry, other.industry)
        && Equality.equals(job, other.job)
        && Equality.equals(jobTitle, other.jobTitle)
        && Equality.equals(incoming, other.incoming)
        && Equality.equals(organization, other.organization)
        && Equality.equals(height, other.height)
        && Equality.equals(weight, other.weight)
        && Equality.equals(allergicHistory, other.allergicHistory)
        && Equality.equals(comment, other.comment)
        && Equality.equals(createTime, other.createTime)
        && Equality.equals(modifyTime, other.modifyTime)
        && Equality.equals(deleteTime, other.deleteTime);
  }

  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, id);
    result = Hash.combine(result, multiplier, source);
    result = Hash.combine(result, multiplier, name);
    result = Hash.combine(result, multiplier, gender);
    result = Hash.combine(result, multiplier, birthday);
    result = Hash.combine(result, multiplier, credential);
    result = Hash.combine(result, multiplier, hasMedicare);
    result = Hash.combine(result, multiplier, medicareType);
    result = Hash.combine(result, multiplier, medicareCard);
    result = Hash.combine(result, multiplier, medicareCity);
    result = Hash.combine(result, multiplier, hasSocialSecurity);
    result = Hash.combine(result, multiplier, socialSecurityCard);
    result = Hash.combine(result, multiplier, socialSecurityCity);
    result = Hash.combine(result, multiplier, contact);
    result = Hash.combine(result, multiplier, guardian);
    result = Hash.combine(result, multiplier, education);
    result = Hash.combine(result, multiplier, ethnic);
    result = Hash.combine(result, multiplier, blood);
    result = Hash.combine(result, multiplier, marriage);
    result = Hash.combine(result, multiplier, hasChild);
    result = Hash.combine(result, multiplier, sexOrientation);
    result = Hash.combine(result, multiplier, religion);
    result = Hash.combine(result, multiplier, politics);
    result = Hash.combine(result, multiplier, industry);
    result = Hash.combine(result, multiplier, job);
    result = Hash.combine(result, multiplier, jobTitle);
    result = Hash.combine(result, multiplier, incoming);
    result = Hash.combine(result, multiplier, organization);
    result = Hash.combine(result, multiplier, height);
    result = Hash.combine(result, multiplier, weight);
    result = Hash.combine(result, multiplier, allergicHistory);
    result = Hash.combine(result, multiplier, comment);
    result = Hash.combine(result, multiplier, createTime);
    result = Hash.combine(result, multiplier, modifyTime);
    result = Hash.combine(result, multiplier, deleteTime);
    return result;
  }

  public String toString() {
    return new ToStringBuilder(this)
        .append("id", id)
        .append("source", source)
        .append("name", name)
        .append("gender", gender)
        .append("birthday", birthday)
        .append("credential", credential)
        .append("hasMedicare", hasMedicare)
        .append("medicareType", medicareType)
        .append("medicareCard", medicareCard)
        .append("medicareCity", medicareCity)
        .append("hasSocialSecurity", hasSocialSecurity)
        .append("socialSecurityCard", socialSecurityCard)
        .append("socialSecurityCity", socialSecurityCity)
        .append("contact", contact)
        .append("guardian", guardian)
        .append("education", education)
        .append("ethnic", ethnic)
        .append("blood", blood)
        .append("marriage", marriage)
        .append("hasChild", hasChild)
        .append("sexOrientation", sexOrientation)
        .append("religion", religion)
        .append("politics", politics)
        .append("industry", industry)
        .append("job", job)
        .append("jobTitle", jobTitle)
        .append("incoming", incoming)
        .append("organization", organization)
        .append("height", height)
        .append("weight", weight)
        .append("allergicHistory", allergicHistory)
        .append("comment", comment)
        .append("createTime", createTime)
        .append("modifyTime", modifyTime)
        .append("deleteTime", deleteTime)
        .toString();
  }

  @Computed({"id", "name", "gender", "birthday", "credential", "contact"})
  public PersonInfo getInfo() {
    return new PersonInfo(this);
  }
}
