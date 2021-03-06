package com.notebook;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import utils.JsonUtils;

import com.pardis.genericmanagedbeans.utls.SessionUtils;
import com.pardis.ssm.bean.KeyOwner;
import com.pardis.ssm.webservice.impl.SSMWSUtil;

public class PortalServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;



	private enum URLREQUEST {

		SinglePrint,
		MultiplePrint,
		PrintAllPersonInContract,
		printPermission;
		
	}
	
	protected HashMap<String, Object> getinfo(String  si,HttpServletRequest request){
		KeyOwner keyowner = new KeyOwner();
		keyowner.setTerminalId("PortletCommunication");
		keyowner.setTerminalType("PORTAL");
		keyowner.setKeyType("SimpleCodeDecode");
		HashMap<String, Object> sessionInfo = new HashMap<String, Object>();
		
	

	//	System.out.println("getinfo1:"+si);
	//	System.out.println("intest1"+ sessionId);
		String jsonSession = SSMWSUtil.decryptByAlgorithm(si, keyowner);
		sessionInfo = (HashMap<String, Object>) JsonUtils.jsonStringToObject(jsonSession);
	//	System.out.println("getinfo2"+ sessionInfo==null);
		System.out.println("getinfo from Portal"+ sessionInfo.size());
		
		for (Object name: sessionInfo.keySet()){

		     	String key =name.toString();
	            String value = sessionInfo.get(name).toString();  
	            System.out.println(key + " " + value);  
	} 
		
		HashMap<String, Object> otherparams = (HashMap<String, Object>) sessionInfo.get("otherParams");
		System.out.println("userBranch : "+ otherparams.get("userBranch") );
		System.out.println("userNationCode : "+ otherparams.get("userNationCode") );
		System.out.println("p2 : "+ otherparams.get("p2") );
		
		//SessionUtils.setSessionParam(request.getSession(), "p2", "ahhhhh"); 
		SessionUtils.setSessionParam(request.getSession(), "userBranch", otherparams.get("userBranch"));
		SessionUtils.setSessionParam(request.getSession(), "p3",  otherparams.get("p3").toString());
		SessionUtils.setSessionParam(request.getSession(), "p2", 	otherparams.get("p2").toString());
		SessionUtils.setSessionParam(request.getSession(), "userNationCode", otherparams.get("userNationCode"));
		SessionUtils.setSessionParam(request.getSession(), "userFirstName", otherparams.get("userFirstName"));
		SessionUtils.setSessionParam(request.getSession(), "userLastName", otherparams.get("userLastName"));
		
		
		return sessionInfo;
	}
	
	/*public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		System.out.println("in second do get");
		  HttpSession session = request.getSession();
		  //session.setAttribute("p2", "ahhhh");
		  SessionUtils.setSessionParam(request.getSession(), "p2", "ahhhhh"); 
		
		  //SessionUtils.setSessionParam("p2", 1545);
		response.sendRedirect( "/HealthNoteBook/searchPerson.jsf");
	}*/

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("In PortalServlet.");
		
	//	System.out.println("In PortalServlet2. :"+SessionUtils.getSessionParam(request,Symbols.SESSION_PARAM_IS_PORTLET));
	//	System.out.println("In PortalServlet3. :"+SessionUtils.getSessionParam(Symbols.SESSION_PARAM_IS_PORTLET));
	
	//System.out.println("In PortalServlet1. :"+si);
	//	System.out.println("In PortalServlet5. :"+request.getAttribute(Symbols.SESSION_PARAM_IS_PORTLET));
		
		
	/*	Boolean isPortlet=false;
		if (SessionUtils.getSessionParam(request,Symbols.SESSION_PARAM_IS_PORTLET)!=null){
			isPortlet=(Boolean) SessionUtils.getSessionParam(request,Symbols.SESSION_PARAM_IS_PORTLET);	
		}
		if (isPortlet)
		{*/
			String userName=(String) request.getSession().getAttribute("__LogonedUserName");
		//	System.out.println("__LogonedUserName in PortalServlet1:" + userName );
			System.out.println("__LogonedUserName in PortalServlet2:" + request.getParameter("portletName").toString() );
			String url=portletUrlInitialize(request.getParameter("portletName").toString());
			String si=  request.getParameter("__PortletSessionId");
			getinfo(si,request);
			response.sendRedirect( url);
		
		
		
		
	

		
		
	}
	

	private String portletUrlInitialize(String pageRequest) {
		String url = null;
		URLREQUEST requestedUrl = URLREQUEST.valueOf(pageRequest);
		switch (requestedUrl) {
		case SinglePrint:
			url = "/HealthNoteBook/searchPerson.jsf";
		//	url = "/ReportConsole/GeneralReportBean/GeneralReportBeanList.jsf";
			break;
		case MultiplePrint:
			url = "/HealthNoteBook/contractList.jsf";
			//url = "/ReportConsole/ReportFileBean/ReportFileBeanList.jsf?initialize=1";
		break;
		
		case printPermission:
			url = "/HealthNoteBook/permisionList.jsf";
			//url = "/ReportConsole/ReportFileBean/ReportFileBeanList.jsf?initialize=1";
		break;
		
		case PrintAllPersonInContract:
			url = "/HealthNoteBook/PrintAllPersonInContract.jsf";
			//url = "/ReportConsole/ReportFileBean/ReportFileBeanList.jsf?initialize=1";
		break;
		
		


	}
		return url;
	}
}
