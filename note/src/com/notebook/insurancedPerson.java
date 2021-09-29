package com.notebook;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;



public  class insurancedPerson  implements Serializable{
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String name;
	String nationCode;
	boolean checkedForPrint;
	boolean printed =false;
	boolean hasBrokenPagesDuringPrint =false;
	Integer printFromThis;
	Integer howManyPageToPrint;
	String insurerdType;
	String relation;
	String gender;
	String birthdate;
	String deliveryunit;// شعبه
	String printtimes;
	String company;//نام بیمه گزار
	String policyno; //شماره قرارداد
	String idno;
	String contractdate;//شروع قرارداد
	String enddate; //انقضای قرارداد
	String mainname;
	String maincode;
	String postalcode;
	String createdate;
	String serial;
	String agent;
	String insurer;
	String contractdateF;
	String enddateF;
	String birthdateF;
	String createdateF;
	
	
	
	
	
	
	public insurancedPerson() {
		// TODO Auto-generated constructor stub
	}
	
	
	public Integer getLastserial(){
		return printFromThis+ howManyPageToPrint-1;
	}

	public List<ps> psList = new ArrayList<ps>();
	
	public class ps
	{
		Integer index;
		Integer serial;
	    boolean selected= true;
	    boolean printed= true;
	
	    public ps(Integer index,Integer serial, boolean selected ) {
		this.selected= selected;
		this.serial=serial;
		this.index= index;
	    }

		public Integer getIndex() {
			return index;
		}

		public void setIndex(Integer index) {
			this.index = index;
		}

		public Integer getSerial() {
			return serial;
		}

		public void setSerial(Integer serial) {
			this.serial = serial;
		}

		public boolean isSelected() {
			return selected;
		}

		public void setSelected(boolean selected) {
			this.selected = selected;
		}
	    
	    
	    
	    
	}
	public insurancedPerson(String name,String nationCode,String relation, String insurerdType, Integer printFromThis,Integer howManyPageToPrint,boolean checkedForPrint,String maincode,String insurer,String idno,String birthdate,String gender,String mainname,String contractdate,String postalcode,String company,String enddate) {

		this.name = name;
		this.nationCode=nationCode;
		this.relation= relation;
		this.insurerdType= insurerdType;
		this.printFromThis=printFromThis;
		this.howManyPageToPrint=howManyPageToPrint;
		this.checkedForPrint=checkedForPrint;
		this.maincode= maincode;
		this.insurer= insurer;
		this.idno= idno;
		this.birthdate= birthdate;
		this.gender=gender;
		this.mainname=mainname;
		this.contractdate=contractdate;
		this.postalcode=postalcode;
		this.company=company;
		this.enddate=enddate;
		for (int i=0;i<howManyPageToPrint;i++)
		{  
			
		
			psList.add(new ps(i+1,0,false ));
		}
	
	}
	
	


	public Integer getHowManyPageToPrint() {
		return howManyPageToPrint;
	}


	public void setHowManyPageToPrint(Integer howManyPageToPrint) {
		this.howManyPageToPrint = howManyPageToPrint;
	}


	public Integer getPrintFromThis() {
		return printFromThis;
	}




	public void setPrintFromThis(Integer printFromThis) {
		this.printFromThis = printFromThis;
	}


	public List<ps> getPsList() {
		return psList;
	}





	public void setPsList(List<ps> psList) {
		this.psList = psList;
	}







	public boolean isPrinted() {
		return printed;
	}


	public void setPrinted(boolean printed) {
		this.printed = printed;
	}


	public boolean isCheckedForPrint() {
		return checkedForPrint;
	}


	public void setCheckedForPrint(boolean checkedForPrint) {
		this.checkedForPrint = checkedForPrint;
	}


	


	public String getInsurer() {
		return insurer;
	}


	public void setInsurer(String insurer) {
		this.insurer = insurer;
	}

	

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getNationCode() {
		return nationCode;
	}


	public void setNationCode(String nationCode) {
		this.nationCode = nationCode;
	}




	public boolean isHasBrokenPagesDuringPrint() {
		return hasBrokenPagesDuringPrint;
	}




	public void setHasBrokenPagesDuringPrint(boolean hasBrokenPagesDuringPrint) {
		this.hasBrokenPagesDuringPrint = hasBrokenPagesDuringPrint;
	}


	public String getInsurerdType() {
		return insurerdType;
	}


	public void setInsurerdType(String insurerdType) {
		this.insurerdType = insurerdType;
	}


	public String getRelation() {
		return relation;
	}


	public void setRelation(String relation) {
		this.relation = relation;
	}


	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}


	public String getBirthdate() {
		return birthdate;
	}


	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}


	public String getDeliveryunit() {
		return deliveryunit;
	}


	public void setDeliveryunit(String deliveryunit) {
		this.deliveryunit = deliveryunit;
	}


	public String getPrinttimes() {
		return printtimes;
	}


	public void setPrinttimes(String printtimes) {
		this.printtimes = printtimes;
	}


	public String getCompany() {
		return company;
	}


	public void setCompany(String company) {
		this.company = company;
	}


	public String getPolicyno() {
		return policyno;
	}


	public void setPolicyno(String policyno) {
		this.policyno = policyno;
	}


	public String getIdno() {
		return idno;
	}


	public void setIdno(String idno) {
		this.idno = idno;
	}


	public String getContractdate() {
		return contractdate;
	}


	public void setContractdate(String contractdate) {
		this.contractdate = contractdate;
	}


	public String getEnddate() {
		return enddate;
	}


	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}


	public String getMainname() {
		return mainname;
	}


	public void setMainname(String mainname) {
		this.mainname = mainname;
	}


	public String getMaincode() {
		return maincode;
	}


	public void setMaincode(String maincode) {
		this.maincode = maincode;
	}


	public String getPostalcode() {
		return postalcode;
	}


	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}


	public String getCreatedate() {
		return createdate;
	}


	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}


	public String getSerial() {
		return serial;
	}


	public void setSerial(String serial) {
		this.serial = serial;
	}


	public String getAgent() {
		return agent;
	}


	public void setAgent(String agent) {
		this.agent = agent;
	}


	public String getContractdateF() {
		return contractdateF;
	}


	public void setContractdateF(String contractdateF) {
		this.contractdateF = contractdateF;
	}


	public String getEnddateF() {
		return enddateF;
	}


	public void setEnddateF(String enddateF) {
		this.enddateF = enddateF;
	}


	public String getBirthdateF() {
		return birthdateF;
	}


	public void setBirthdateF(String birthdateF) {
		this.birthdateF = birthdateF;
	}


	public String getCreatedateF() {
		return createdateF;
	}


	public void setCreatedateF(String createdateF) {
		this.createdateF = createdateF;
	}


	@Override
	public String toString() {
		return "insurancedPerson [agent=" + agent + ", birthdate=" + birthdate
				+ ", birthdateF=" + birthdateF + ", checkedForPrint="
				+ checkedForPrint + ", company=" + company + ", contractdate="
				+ contractdate + ", contractdateF=" + contractdateF
				+ ", createdate=" + createdate + ", createdateF=" + createdateF
				+ ", deliveryunit=" + deliveryunit + ", enddate=" + enddate
				+ ", enddateF=" + enddateF + ", gender=" + gender
				+ ", hasBrokenPagesDuringPrint=" + hasBrokenPagesDuringPrint
				+ ", howManyPageToPrint=" + howManyPageToPrint + ", idno="
				+ idno + ", insurer=" + insurer + ", insurerdType="
				+ insurerdType + ", maincode=" + maincode + ", mainname="
				+ mainname + ", name=" + name + ", nationCode=" + nationCode
				+ ", policyno=" + policyno + ", postalcode=" + postalcode
				+ ", printFromThis=" + printFromThis + ", printed=" + printed
				+ ", printtimes=" + printtimes + ", psList=" + psList
				+ ", relation=" + relation + ", serial=" + serial + "]";
	}


	
	
	
}
