package com.notebook;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//import org.python.core.exceptions;

//import weblogic.wsee.wsdl.WsdlException;

import com.pardis.jsonmessage.AnswerObject;
import com.pardis.owragh.Common;
import com.pardis.owragh.client.OwraghModuleClient;


public class TestOwragh1 {

	public static void main(String[] args) {
		HashMap<String, Object> params = new HashMap<String, Object>();
//		 params.put("port", "7007");

//		("0059671580", params);
//		("2296907393", params);
//		("2285545371", params);
//		("2286285721", params);
//		relatePerson("0059671580", "2296907393", "Husband", params);
//		relatePerson("0059671580", "2285545371", "Child", params);
//		relatePerson("0059671580", "2286285721", "Child", params);
//		inquiry("0059671580", new Date(), "Sadra", "1", "1005839515", 2168, "sadra", params);
		searchPerson("0059671580","1005839515","00000005", params);
//		searchBargeh("0059671580","1005839515","000000001", params);
//		searchPassbook("0059671580","1005839515","000000001", params);
		
		
	}
	
	static void assignOwraghToPassbook(String code, Integer capacity, String caption,
			HashMap<String, Object> params) {
		if (params == null)
			params = new HashMap<String, Object>();

		AnswerObject result = OwraghModuleClient.assignBargehToPassbook(params);

		System.out.println(result);
	}

	static void changeOwraghStatus(String code, Integer capacity, String caption,
			HashMap<String, Object> params) {
		if (params == null)
			params = new HashMap<String, Object>();

		AnswerObject result = OwraghModuleClient.changeBargehStatus(params);

		System.out.println(result);
	}

	static void changePassbookStatus(String code, Integer capacity, String caption,
			HashMap<String, Object> params) {
		if (params == null)
			params = new HashMap<String, Object>();

		AnswerObject result = OwraghModuleClient.changePassbookStatus(params);

		System.out.println(result);
	}

	static AnswerObject inquiry(String code, Date deliveryDate, String deliveryUnit, 
			String PrintTimes, 
			String serials, Integer StationNo, 
			HashMap<String, Object> params) {
		if (params == null)
			params = new HashMap<String, Object>();

		params.put(Common.DeliveryDate_Key,deliveryDate);
		params.put(Common.DeliveryUnit_Key,deliveryUnit);
		params.put(Common.PrintTimes_Key,PrintTimes);
		params.put(Common.Code_Key,code);
		params.put(Common.BargehSerial_Key, serials);
		params.put(Common.StationNo_Key, StationNo);
		
		
		AnswerObject result = OwraghModuleClient.inquiry(params);

		System.out.println(result);
		return result;
	}

	static void deletePerson(String code, Integer capacity, String caption,
			HashMap<String, Object> params) {
		if (params == null)
			params = new HashMap<String, Object>();

		AnswerObject result = OwraghModuleClient.deletePerson(params);

		System.out.println(result);
	}

	static void relatePerson(String code1, String code2, String relation, HashMap<String, Object> params) {
		if (params == null)
			params = new HashMap<String, Object>();

		params.put(Common.Code_Key+1, code1);
		params.put(Common.Code_Key+2, code2);
		params.put(Common.Type_Key, relation);
		
		AnswerObject result = OwraghModuleClient.relatePerson(params);

		System.out.println(result);
	}


	static 	AnswerObject   searchPassbook(String code, String bargehserial,	String passbookserial,
			HashMap<String, Object> params) {
		if (params == null)
			params = new HashMap<String, Object>();

		params.put(Common.Code_Key, code);
		params.put(Common.BargehSerial_Key, bargehserial);
		params.put(Common.PassbookSerial_Key, passbookserial);
		AnswerObject result = OwraghModuleClient.searchPassbook(params);
		System.out.println(result);
        return result;
	}
	
	
	static 	AnswerObject   jeldList( String policyno) {

		HashMap<String, Object>	params = new HashMap<String, Object>();

		params.put("policyno",policyno);
		AnswerObject result = OwraghModuleClient.searchJeld(params);
		
		
		
		System.out.println(result);
        return result;
	}
	
	

	static 	AnswerObject   searchDefineAccess(String code,HashMap<String, Object> params) {
		if (params == null)
			params = new HashMap<String, Object>();
		params.put(Common.Code_Key, code);
		AnswerObject result = OwraghModuleClient.searchDefineAccess(params);
		System.out.println(result);
        return result;
	}
	

	static 	AnswerObject   changeDefinedAccessStatus(String id,String status,HashMap<String, Object> params) {
		if (params == null)
			params = new HashMap<String, Object>();
	
		params.put(Common.Id_Key, id);
		params.put(Common.Status_Key, status);

		AnswerObject result = OwraghModuleClient.changeDefinedAccessStatus(params);
		System.out.println(result);
        return result;
	}
	
	static 	AnswerObject   changePassbookStatus(String serial, String status,HashMap<String, Object> params) {
		if (params == null)
			params = new HashMap<String, Object>();

		params.put(Common.PassbookSerial_Key, serial);
		params.put(Common.Status_Key, status/*"Canceled"*/);
		AnswerObject result = OwraghModuleClient.changePassbookStatus(params);
		System.out.println(result);
        return result;
	}
	
	

	static AnswerObject searchBargeh(String code, String bargehserial,	String passbookserial,	HashMap<String, Object> params) {
		if (params == null)
			params = new HashMap<String, Object>();

		params.put(Common.Code_Key, code);
		params.put(Common.BargehSerial_Key, bargehserial);
		params.put(Common.PassbookSerial_Key, passbookserial);
		
		
		AnswerObject result = OwraghModuleClient.searchBargeh(params);

		System.out.println(result);
		return result; 
	}

	
	static AnswerObject removeBargehFromPassbook(String passbookserial, String bargehserial,
			HashMap<String, Object> params) {
		if (params == null)
			params = new HashMap<String, Object>();
		params.put(Common.BargehSerial_Key, bargehserial);
		params.put(Common.PassbookSerial_Key, passbookserial);

		AnswerObject result = OwraghModuleClient.removeBargehFromPassbook(params);

		System.out.println(result);
		return result;
	}

	

	static AnswerObject searchPerson(String code, String bargehserial,	String passbookserial, HashMap<String, Object> params) {
		if (params == null)
			params = new HashMap<String, Object>();

		params.put(Common.Code_Key, code);
		params.put(Common.BargehSerial_Key, bargehserial);
		params.put(Common.PassbookSerial_Key, passbookserial);
		AnswerObject result = null;
		try {
			 result = OwraghModuleClient.searchPerson(params);
		} catch (Exception  e) {
		e.printStackTrace();
		}
		

		System.out.println(result);
		return result; 
	}

	static AnswerObject createDefinedAccess(String code, String policyno,	String fromdate, String todate, String user , HashMap<String, Object> params) {
		if (params == null)
			params = new HashMap<String, Object>();

		params.put(Common.Code_Key, code);
		params.put(Common.PolicyNo_Key, policyno);
		params.put(Common.FromDate_Key, fromdate);
		params.put(Common.ToDate_Key, todate);
		params.put(Common.UserName_Key, user);
		AnswerObject result = null;
		try {
			 result =OwraghModuleClient.createDefinedAccess(params);
		} catch (Exception  e) {
		e.printStackTrace();
		}
		

		System.out.println(result);
		return result; 
	}
	
	


	
	
}
