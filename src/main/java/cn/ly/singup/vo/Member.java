package cn.ly.singup.vo;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class Member implements Serializable{
	private String mid ;
	private String password ;
	private String name ;
	private Integer sflag ;
	private Date regdate ;
	private Integer locked ;
	private String rid ;
	public void setRid(String rid) {
		this.rid = rid;
	}
	public String getRid() {
		return rid;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getSflag() {
		return sflag;
	}
	public void setSflag(Integer sflag) {
		this.sflag = sflag;
	}
	public Date getRegdate() {
		return regdate;
	}
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	public Integer getLocked() {
		return locked;
	}
	public void setLocked(Integer locked) {
		this.locked = locked;
	}
	@Override
	public String toString() {
		return "Member [mid=" + mid + ", password=" + password + ", name=" + name + ", sflag=" + sflag + ", regdate="
				+ regdate + ", locked=" + locked + "]";
	}		
}
