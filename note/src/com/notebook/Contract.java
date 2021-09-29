package com.notebook;

public class Contract {
	private String id;
	private String generalNo;//شماره قرارداد
	private String companyname;//نام بیمه گزار
	private String offerAgent;//کد نماینده
	boolean contarctdSelected; 
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getGeneralNo() {
		return generalNo;
	}
	public void setGeneralNo(String generalNo) {
		this.generalNo = generalNo;
	}
	public String getCompanyname() {
		return companyname;
	}
	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}
	
	public String getOfferAgent() {
		return offerAgent;
	}
	public void setOfferAgent(String offerAgent) {
		this.offerAgent = offerAgent;
	}
	public boolean isContarctdSelected() {
		return contarctdSelected;
	}
	public void setContarctdSelected(boolean contarctdSelected) {
		this.contarctdSelected = contarctdSelected;
	}
	
	
	
}
