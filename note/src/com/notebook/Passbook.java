package com.notebook;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.pardis.common.DateUtils;
import com.pardis.common.GregorianJalaliBridge;
import com.pardis.common.PublicUtils;
import com.pardis.jsonmessage.AnswerObject;



public class Passbook implements Serializable {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String printTimes;
	String pagescount;
	String delivarydate;
	String delivaryUnit;
	String status;
	String expirarydate;
	String expirarydatePersian;
	String delivarydatePersian;
	String passbookSerial;
	String policyno;
	String bargeh; 
	String agent;
	
	public static 	List<Passbook>  personPassbook(String nationalCode) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		AnswerObject result = TestOwragh1.searchPassbook(nationalCode,null,null, params);
		HashMap<String, Object> createresult=result.getAnswer();
		
		ArrayList<HashMap<String, Object>> ff  =  (ArrayList<HashMap<String, Object>>) createresult.get("List");
		List<Passbook> pbs=  new ArrayList<Passbook>(); 
	
		for (int i = 0 ; i< ff.size(); i++){
			Passbook pb= new Passbook();
			pb.setDelivarydate( ff.get(i).get("deliverydate").toString());
			pb.setDelivaryUnit(ff.get(i).get("deliveryunit").toString());
			pb.setExpirarydate(ff.get(i).get("expiratoindate").toString());
			pb.setPagescount(ff.get(i).get("pagescount").toString());
			pb.setPrintTimes(ff.get(i).get("printtimes").toString());
			pb.setStatus(ff.get(i).get("passbookstatus").toString());
			pb.setPassbookSerial(ff.get(i).get("passbookserial").toString());
			pb.setPolicyno(ff.get(i).get("policyno").toString());
			pb.setBargeh(ff.get(i).get("bargeh").toString());
			pb.setAgent(ff.get(i).get("agent").toString());
			
			
			
			
			
			 
			try {
				if (!pb.getExpirarydate().equals("null") &&  !pb.getExpirarydate().equals("") ){
					Date	date1 = new SimpleDateFormat("yyyy-MM-dd").parse(pb.getExpirarydate().substring(0,10));
					pb.setExpirarydatePersian(PublicUtils.getJalaliDate(date1));	
				}
				
				if (!pb.getDelivarydate().equals("null")  &&  !pb.getDelivarydate().equals("")){
					Date	date2 = new SimpleDateFormat("yyyy-MM-dd").parse(pb.getDelivarydate().substring(0,10));
					pb.setDelivarydatePersian(PublicUtils.getJalaliDate(date2));
						
				}
				
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			pbs.add(pb);
			 
		}
		// String sDate1="31/12/1998";  
	/*	 2018-04-28 09:00:53.0*/
	/*	    Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(sDate1.substring(0,10));  
		    System.out.println(sDate1+"\t"+date1);  
	GregorianJalaliBridge a=	new GregorianJalaliBridge;
	a.fromGregToJalali(gregorianYear, gregorianMonth, gregorianDate)
DateUtils.*/
	//PublicUtils.
		return pbs;
		
	}
	
	public Passbook() {
		// TODO Auto-generated constructor stub
	}
	
	public String getPrintTimes() {
		return printTimes;
	}
	public void setPrintTimes(String printTimes) {
		this.printTimes = printTimes;
	}
	
	
	public String getDelivarydate() {
		return delivarydate;
	}

	public void setDelivarydate(String delivarydate) {
		this.delivarydate = delivarydate;
	}

	public String getExpirarydatePersian() {
		return expirarydatePersian;
	}

	public void setExpirarydatePersian(String expirarydatePersian) {
		this.expirarydatePersian = expirarydatePersian;
	}

	public String getDelivarydatePersian() {
		return delivarydatePersian;
	}

	public void setDelivarydatePersian(String delivarydatePersian) {
		this.delivarydatePersian = delivarydatePersian;
	}

	public String getDelivaryUnit() {
		return delivaryUnit;
	}
	public void setDelivaryUnit(String delivaryUnit) {
		this.delivaryUnit = delivaryUnit;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getExpirarydate() {
		return expirarydate;
	}
	public void setExpirarydate(String expirarydate) {
		this.expirarydate = expirarydate;
	}

	public String getPagescount() {
		return pagescount;
	}

	public void setPagescount(String pagescount) {
		this.pagescount = pagescount;
	}

	public String getPassbookSerial() {
		return passbookSerial;
	}

	public void setPassbookSerial(String passbookSerial) {
		this.passbookSerial = passbookSerial;
	}

	public String getPolicyno() {
		return policyno;
	}

	public void setPolicyno(String policyno) {
		this.policyno = policyno;
	}

	public String getBargeh() {
		return bargeh;
	}

	public void setBargeh(String bargeh) {
		this.bargeh = bargeh;
	}

	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}
	
	
	

}
