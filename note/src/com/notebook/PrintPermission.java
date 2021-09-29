package com.notebook;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

//import org.apache.commons.lang.NumberUtils;

import com.pardis.common.PublicUtils;
import com.pardis.genericmanagedbeans.utls.SessionUtils;
import com.pardis.jsonmessage.AnswerObject;

@ManagedBean
@RequestScoped
public class PrintPermission  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	private String policyno; 
	private String personCode;
	private String personName="";
	private String dateFrom;
	private String dateTo;


	
	




	public String getPolicyno() {
		return policyno;
	}




	public void setPolicyno(String policyno) {
		this.policyno = policyno;
	}




	public String getPersonCode() {
		return personCode;
	}




	public void setPersonCode(String personCode) {
		this.personCode = personCode;
	}




	public String getPersonName() {
		return personName ;
	}




	public void setPersonName(String personName) {

		this.personName = personName;
	}




	public String getDateFrom() {
		return dateFrom;
	}




	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}




	public String getDateTo() {
		return dateTo;
	}




	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}




	
	public void findPerson(){
		HashMap<String, Object> params = new HashMap<String, Object>();
		AnswerObject answer = TestOwragh1.searchPerson(personCode, null, null,
				params);
		System.out.println("answer : " +answer);

		if (answer == null || answer.getException() != null) {
			String messageS="";
			
			if (answer == null )
				setPersonName( " اتصال به وب سرویس ناموفق بود.");

			else {
				if (!answer.getMessage().equals(""))
				messageS = answer.getMessage();}
			
			

			setPersonName(messageS);

		}
		
		HashMap<String, Object> ans = answer.getAnswer();
		
		ArrayList<HashMap<String, Object>> ff = (ArrayList<HashMap<String, Object>>) ans
		.get("List");
		setPersonName(ff.get(0).get("name").toString());
		
	}
	
	
	public String confirm(){
		System.out.println("---------------------In Confirm printPermission");
	
		if ("".equals(policyno))
			return error("شماره قرارداد را وارد نمایید.");
		if ("".equals(personCode))
			return error("کدملی را وارد نمایید.");
		if ("".equals(dateFrom))
			return error("تاریخ شروع را وارد نمایید.");
		if ("".equals(dateTo))
			return error("تاریخ پایان را وارد نمایید.");
		
		
		System.out.println(personCode);
	
		String[] dateFromArray=dateFrom.split("/");
		if (!dateFromArray[0].startsWith("13"))
			return error("تاریخ شروع نامعتبر است.");
		
		if (Integer.parseInt(dateFromArray[1])>13 || Integer.parseInt(dateFromArray[1])<1)
			return error("تاریخ شروع نامعتبر است");
		
		if (Integer.parseInt(dateFromArray[2])>31 || Integer.parseInt(dateFromArray[2])<1)
			return error("تاریخ شروع نامعتبر است");
		   
		//----------------------------------
		String[] dateToArray=dateTo.split("/");
		if (!dateToArray[0].startsWith("13"))
			return error("تاریخ پایان نامعتبر است");
		
		if (Integer.parseInt(dateToArray[1])>13 || Integer.parseInt(dateToArray[1])<1)
			return error("تاریخ پایان نامعتبر است");
		
		if (Integer.parseInt(dateToArray[2])>31 || Integer.parseInt(dateToArray[2])<1)
			return error("تاریخ پایان نامعتبر است");
		
		//----------------------------------
		
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
		
		String dateFromGorgFromatted="";
		String dateToGorgFromatted="";
		
		
		
		
		Date dateFromGorg= PublicUtils.getGrgDate(dateFrom, "/");
		dateFromGorgFromatted =sdf.format(dateFromGorg);
		
		Date dateToGorg= PublicUtils.getGrgDate(dateTo, "/");
		dateToGorgFromatted =sdf.format(dateToGorg);
		
		String userfullname = /*"0123456789"*/SessionUtils.getSessionParam(getRequest(), "userFirstName").toString() + " "+ SessionUtils.getSessionParam(getRequest(), "userLastName").toString();
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		System.out.println("--------------------------------------------");
		System.out.println("--------------------------------------------createDefinedAccess");
		System.out.println(" personCode : "+personCode + " , policyno : "+policyno  + " , dateFromGorgFromatted : " + dateFromGorgFromatted + " , dateToGorgFromatted : "+ 
				dateToGorgFromatted + " , userfullname : " + userfullname);
		AnswerObject answer = TestOwragh1.createDefinedAccess(personCode, policyno, dateFromGorgFromatted, dateToGorgFromatted, userfullname, params);
				
				
		System.out.println("answer : "+answer);
		
		if (answer == null || answer.getException() != null) {
			String messageS="";
			
			if (answer == null )
			messageS = " اتصال به وب سرویس ناموفق بود.";

			else {
				if (!answer.getMessage().equals(""))
				messageS = answer.getMessage();}
			
			

			
			FacesMessage facem = new FacesMessage(FacesMessage.SEVERITY_ERROR, "",messageS);
			FacesContext facec = FacesContext.getCurrentInstance();
			facec.addMessage("", facem);
			return "";
			}
		
		
		FacesMessage facem = new FacesMessage(FacesMessage.SEVERITY_INFO, "","عملیات با موفقیت انجام شد.");
		FacesContext facec = FacesContext.getCurrentInstance();
		facec.addMessage("", facem);
		
		
		return "";
		
			
		
		

		
		
		
	}
	
	
	public String error(String msg){
		FacesMessage facem = new FacesMessage(FacesMessage.SEVERITY_ERROR, "",msg);
		FacesContext facec = FacesContext.getCurrentInstance();
		facec.addMessage("", facem);
		return "";
		
	}
	
	public static HttpServletRequest getRequest() {
		if (FacesContext.getCurrentInstance() == null) {
			return null;
		}
		return (HttpServletRequest) FacesContext.getCurrentInstance()
				.getExternalContext().getRequest();
	}

}
