package com.notebook;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.pardis.jsonmessage.AnswerObject;



public class PassbookPages implements Serializable {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String passbookPagesSerial;
	String status;
	Integer index; 
	boolean printed =false;

	
	public static 	List<PassbookPages>  getPages(String passbookSerial) {
		HashMap<String, Object> params = new HashMap<String, Object>();
	//	params.put("bargehstatus", "Assigned");
		AnswerObject result = TestOwragh1.searchBargeh(null, null, passbookSerial, params);
		HashMap<String, Object> createresult=result.getAnswer();
		
		ArrayList<HashMap<String, Object>> ff  =  (ArrayList<HashMap<String, Object>>) createresult.get("List");
		List<PassbookPages> pbgs=  new ArrayList<PassbookPages>(); 
		
		for (int i = 0 ; i< ff.size(); i++){
			PassbookPages pbg= new PassbookPages();
			pbg.setStatus(ff.get(i).get("bargehstatuscaption").toString());
			pbg.setPassbookPagesSerial(ff.get(i).get("bargehserial").toString());
			pbg.setIndex(i+1);
			pbgs.add(pbg);	
		}
		
		
		return pbgs;
		
	}
	
	
	public String getPassbookPagesSerial() {
		return passbookPagesSerial;
	}

	public void setPassbookPagesSerial(String passbookPagesSerial) {
		this.passbookPagesSerial = passbookPagesSerial;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public Integer getIndex() {
		return index;
	}


	public void setIndex(Integer index) {
		this.index = index;
	}


	public boolean isPrinted() {
		return printed;
	}


	public void setPrinted(boolean printed) {
		this.printed = printed;
	}
	
	
	
	

}
