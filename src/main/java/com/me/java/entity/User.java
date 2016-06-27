package com.me.java.entity;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable{
	private static final long serialVersionUID = 1L;

	private String id;

    private String loginid;

    private String passwd;

    private Byte level;

    private Boolean lockstate;

    private Date regdate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getLoginid() {
        return loginid;
    }

    public void setLoginid(String loginid) {
        this.loginid = loginid == null ? null : loginid.trim();
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd == null ? null : passwd.trim();
    }

    public Byte getLevel() {
        return level;
    }

    public void setLevel(Byte level) {
        this.level = level;
    }

    public Boolean getLockstate() {
        return lockstate;
    }

    public void setLockstate(Boolean lockstate) {
        this.lockstate = lockstate;
    }

    public Date getRegdate() {
        return regdate;
    }

    public void setRegdate(Date regdate) {
        this.regdate = regdate;
    }

	@Override
	public String toString() {
		return "User [id=" + id + ", loginid=" + loginid + ", passwd=" + passwd
				+ ", level=" + level + ", lockstate=" + lockstate
				+ ", regdate=" + regdate + "]";
	}
}