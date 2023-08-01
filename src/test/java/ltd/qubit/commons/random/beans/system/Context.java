////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.random.beans.system;

import javax.annotation.Nullable;

import ltd.qubit.commons.lang.Argument;
import ltd.qubit.commons.lang.Assignable;
import ltd.qubit.commons.lang.Assignment;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.random.beans.util.Info;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * 此对象封装了系统运行时的上下文。
 *
 * @author 胡海星
 */
public class Context implements Assignable<Context> {

  /**
   * 当前调用所属App。
   */
  private Info app;

  /**
   * 当前调用所属的登录用户的会话。
   */
  @Nullable
  private Session session;

  public Context() {
    // empty
  }

  public Context(final Context other) {
    assign(other);
  }

  @Override
  public void assign(final Context other) {
    Argument.requireNonNull("other", other);
    app = Assignment.clone(other.app);
    session = Assignment.clone(other.session);
  }

  @Override
  public Context clone() {
    return new Context(this);
  }

  public final Info getApp() {
    return app;
  }

  public final void setApp(final Info app) {
    this.app = app;
  }

  @Nullable
  public final Session getSession() {
    return session;
  }

  public final void setSession(@Nullable final Session session) {
    this.session = session;
  }

  public boolean equals(@Nullable final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final Context other = (Context) o;
    return Equality.equals(app, other.app)
        && Equality.equals(session, other.session);
  }

  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, app);
    result = Hash.combine(result, multiplier, session);
    return result;
  }

  public String toString() {
    return new ToStringBuilder(this)
        .append("app", app)
        .append("session", session)
        .toString();
  }
}
