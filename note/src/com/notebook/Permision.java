package com.notebook;

import java.util.Date;

public class Permision {
	private Integer  id;
	private String policy_no;//شماره قرارداد
	private String nationalcode;//نام بیمه گزار
	private String firstname;//کد نماینده
	private String lastname;//
	private String premature;//کد نماینده
	private Date todate;
	private String todateFarsi;
	private Date fromdate;//
	private String fromdateFarsi;
	private Date commitdate;//کد نماینده
	private String status;//کد نماینده
	private String user;//
	
	 
	
	public Permision(Integer  id ,String policyNo, String nationalcode, String firstname,
			String lastname, Date todate, String todateFarsi, Date fromdate,
			String fromdateFarsi, String status, String user) {
		super();
		this.id = id;
		this.policy_no = policyNo;
		this.nationalcode = nationalcode;
		this.firstname = firstname;
		this.lastname = lastname;
		this.todate = todate;
		this.todateFarsi = todateFarsi;
		this.fromdate = fromdate;
		this.fromdateFarsi = fromdateFarsi;
		this.status = status;
		this.user = user;
	}
	
	
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getPolicy_no() {
		return policy_no;
	}
	public void setPolicy_no(String policyNo) {
		policy_no = policyNo;
	}
	public String getNationalcode() {
		return nationalcode;
	}
	public void setNationalcode(String nationalcode) {
		this.nationalcode = nationalcode;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getPremature() {
		return premature;
	}
	public void setPremature(String premature) {
		this.premature = premature;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public Date getTodate() {
		return todate;
	}
	public void setTodate(Date todate) {
		this.todate = todate;
	}
	public Date getFromdate() {
		return fromdate;
	}
	public void setFromdate(Date fromdate) {
		this.fromdate = fromdate;
	}
	public Date getCommitdate() {
		return commitdate;
	}
	public void setCommitdate(Date commitdate) {
		this.commitdate = commitdate;
	}
	public String getTodateFarsi() {
		return todateFarsi;
	}
	public void setTodateFarsi(String todateFarsi) {
		this.todateFarsi = todateFarsi;
	}
	public String getFromdateFarsi() {
		return fromdateFarsi;
	}
	public void setFromdateFarsi(String fromdateFarsi) {
		this.fromdateFarsi = fromdateFarsi;
	}
	 
	
	
	
	
}
