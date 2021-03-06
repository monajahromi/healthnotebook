package com.notebook;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.constants.CommonConstants;
import com.constants.Constants;
import com.pardis.common.ConstantManager;
import com.pardis.common.PublicUtils;
import com.pardis.genericmanagedbeans.utls.SessionUtils;
import com.pardis.jsonmessage.AnswerObject;
import com.pardis.owragh.Common;
import com.pardis.report.ReportUtils;

@ManagedBean
@SessionScoped
public class SearchBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nationCode;
	private List<Passbook> pbs;
	private List<PassbookPages> pbgs;
	private Passbook selectPassbook;
	private String selectcode;
	private insurancedPerson selectperson;
	private boolean printbuttom = true;
	private String notebookSerial;
	private String serials;
	private String contarct;
	private List<Integer> removeIndex= new ArrayList<Integer>();
	private Integer StationNo;
	private boolean bachPrint;
	private List<String > bachPrintList= new ArrayList<String>();
	private List<NoPassbookPeople> noPassbookPeopleList = new ArrayList<NoPassbookPeople>();
	private String lastSerial;
	private String startContarctDate ; 
	private String endContarctDate ;
	
	

	private List<insurancedPerson> insurancedPersonList = new ArrayList<insurancedPerson>();
	
	
	insurancedPerson peresentedForPrint;
	insurancedPerson mainInsured;
	
	

	public String getNationCode() {
		return nationCode;
	}

	public void setNationCode(String nationCode) {
		this.nationCode = nationCode;
	}

	public static HttpServletRequest getRequest() {
		if (FacesContext.getCurrentInstance() == null) {
			return null;
		}
		return (HttpServletRequest) FacesContext.getCurrentInstance()
				.getExternalContext().getRequest();
	}

	public SearchBean() {}
	
	
	public void init(){
		System.out.println("*******************in init");
		//System.out.println("^^^^^^^^^^^^^^^^^^^^^:" +SessionUtils.getSessionParam("p2").toString());
		if (SessionUtils.getSessionParam( "p2") != null)
			StationNo = Integer.valueOf(SessionUtils.getSessionParam( "p2").toString());

		System.out.println("StationNo : "+StationNo);
		//System.out.println("StationNo : "+ SessionUtils.getSessionParam(getRequest(), "userBranch"));
	

	}
	public SearchBean(String code) {
		setNationCode(code);
		
		
	}
	
	public void findPerson() {
		System.out.println("---------------BachPrint: "+bachPrint);
		
		 if (StationNo==null){
			 FacesMessage message = new FacesMessage(" ?????????????? ?????????? ???????? ??????.???????? ???? ?????????? ???????? ????????"); 
			 FacesContext context =  FacesContext.getCurrentInstance(); 
			 context.addMessage("", message);
		 
		   return; }
		 
//	StationNo = 2000;

		printbuttom = true;
		serials = "";
		notebookSerial = "";
		HashMap<String, Object> params = new HashMap<String, Object>();
		Date d1= new Date();
		AnswerObject answer = TestOwragh1.searchPerson(nationCode, null, null,
				params);
	
		Date d2= new Date();
		diffTime(d1, d2,nationCode);
		System.out.println("---------------------------searchPerson Result: "+ answer);

		if (answer == null || answer.getException() != null) {
			String messageS="";
			
			if (answer == null )
			messageS = " ?????????? ???? ???? ?????????? ???????????? ??????.";

			else {
				if (!answer.getMessage().equals(""))
				messageS = answer.getMessage();}
			
			
			System.out.println("-----------------------------MESSAGE : "+ messageS);
			
			FacesMessage facem = new FacesMessage(FacesMessage.SEVERITY_ERROR, "",messageS);
			FacesContext facec = FacesContext.getCurrentInstance();
			facec.addMessage("", facem);
			return;

		}
		HashMap<String, Object> ans = answer.getAnswer();

		ArrayList<HashMap<String, Object>> ff = (ArrayList<HashMap<String, Object>>) ans
				.get("List");
		insurancedPersonList.clear();
		for (int i = 0; i < ff.size(); i++) {

			if (ff.get(i).get("ownertype").toString().equals("Main")) {
				insurancedPersonList.add(new insurancedPerson(ff.get(i)
						.get("name").toString(), ff.get(i).get("code")
						.toString(), "???????? ?????? ????????", "???????? ?????? ????????", 0, 15, true,"",ff.get(i).get("company").toString()
				,ff.get(i).get("idno").toString()	,ff.get(i).get("birthdate").toString()	,ff.get(i).get("gender").toString(),ff.get(i).get("mainname").toString()  ,ff.get(i).get("contractdate").toString(),
				ff.get(i).get("postalcode").toString(),ff.get(i).get("company").toString(),ff.get(i).get("enddate").toString()
				));
				mainInsured = new insurancedPerson(ff.get(i)
						.get("name").toString(), ff.get(i).get("code")
						.toString(), "???????? ?????? ????????", "???????? ?????? ????????", 0, 15, true,"",ff.get(i).get("company").toString()
						,ff.get(i).get("idno").toString()	,ff.get(i).get("birthdate").toString()		,ff.get(i).get("gender").toString(),ff.get(i).get("mainname").toString(),ff.get(i).get("contractdate").toString(),
						ff.get(i).get("postalcode").toString(),ff.get(i).get("company").toString(),ff.get(i).get("enddate").toString());
			}

			else
				insurancedPersonList.add(new insurancedPerson(ff.get(i)
						.get("name").toString(), ff.get(i).get("code")
						.toString(), ff.get(i).get("relation").toString(),
						"?????? ????????", 0, 15, true,nationCode,ff.get(i).get("company").toString()
						,ff.get(i).get("idno").toString(),ff.get(i).get("birthdate").toString()		,ff.get(i).get("gender").toString()	,ff.get(i).get("mainname").toString(),ff.get(i).get("contractdate").toString(),
						ff.get(i).get("postalcode").toString(),ff.get(i).get("company").toString(),ff.get(i).get("enddate").toString()));

			// insurancedPersonList.add(new insurancedPerson("mona",
			// "jahromi","0493224890",0,15 ));
			/*
			 * ip.setHowManyPageToPrint(15); ip.setPrintFromThis(0);
			 * insurancedPersonList.add(ip);
			 */

		}

		/*
		 * insurancedPersonList.add(new insurancedPerson("mona",
		 * "jahromi","0493224890",0,15 )); insurancedPersonList.add(new
		 * insurancedPerson("hana", "habibi" ,"454545",0,15));
		 * insurancedPersonList.add(new insurancedPerson("mazaher", "habibi"
		 * ,"558584584",0,15));
		 */

		HttpServletResponse response = (HttpServletResponse) FacesContext
				.getCurrentInstance().getExternalContext().getResponse();
		try {
			response.sendRedirect("insurancedPersonList.jsf");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void printNotebookCover() {

		Collection<Map<String, Object>> queryResult = new ArrayList<Map<String, Object>>();
		Map<String, Object> allRecords = new HashMap<String, Object>();
		allRecords.put("name", peresentedForPrint.getName());
		allRecords.put("relation", peresentedForPrint.getRelation());
		allRecords.put("jobStatus", "????????????");
		allRecords.put("nationCode", peresentedForPrint.getNationCode());
		allRecords.put("Insurer",peresentedForPrint.getCompany());
		//allRecords.put("Insurer", peresentedForPrint.getCompany());
		allRecords.put("geographicalLocation", "");
		allRecords.put("idno",peresentedForPrint.getIdno());
		allRecords.put("contractNo", peresentedForPrint.getPolicyno());
		allRecords.put("tajdidDate", "");
		allRecords.put("birthDate",peresentedForPrint.getBirthdateF());
		allRecords.put("exporterUnit","???????????? ??????????");
		allRecords.put("unit", "");
		allRecords.put("sex", peresentedForPrint.getGender());
		allRecords.put("issueDate", peresentedForPrint.getCreatedateF());
		allRecords.put("serialNo", peresentedForPrint.getSerial());
		allRecords.put("mainInsured",peresentedForPrint.getMainname());
		allRecords.put("startContract", peresentedForPrint.getContractdateF());
		allRecords.put("IdentificationCode", "");
		allRecords.put("mainInsuredCode",peresentedForPrint.getMaincode());
		allRecords.put("contractExpire", peresentedForPrint.getEnddateF());
		allRecords.put("tamdidDate", "");
		allRecords.put("postalCode",peresentedForPrint.getPostalcode());

		/*
		 * allRecords.put("name", "???????? ??????????");
		 * allRecords.put("relation","????????"); allRecords.put("jobStatus",
		 * "??????????");
		 * allRecords.put("nationCode",peresentedForPrint.getNationCode());
		 * allRecords.put("Insurer","?????????? ?????????? ??????");
		 * allRecords.put("geographicalLocation", "??????????");
		 * allRecords.put("idno", "2858585858"); allRecords.put("contractNo",
		 * "88521825225"); allRecords.put("tajdidDate", "1396/05/02");
		 * allRecords.put("birthDate", "1396/05/03");
		 * allRecords.put("exporterUnit", "???????????????? ??????");
		 * allRecords.put("unit","10"); allRecords.put("sex", "????");
		 * allRecords.put("issueDate", "1397/02/05"); allRecords.put("serialNo",
		 * "5512521525285"); allRecords.put("mainInsured",
		 * "?????????? ?????????? ?????? ????????????"); allRecords.put("startContract",
		 * "1397/02/07"); allRecords.put("IdentificationCode", "98532659877");
		 * allRecords.put("mainInsuredCode","???????? ??????????");
		 * allRecords.put("contractExpire", "1397/02/01");
		 * allRecords.put("tamdidDate", "1397/02/12");
		 * allRecords.put("postalCode", "562151512545");
		 */

		String pathReport = ConstantManager.getInstance().getString(
				CommonConstants.PROPERTIES_FILE,
				"com.notebook.report.notebookcover");
		String root = ConstantManager.getInstance().getString(
				CommonConstants.PROPERTIES_FILE, "com.notebook.report.root");
		try {
			HttpServletResponse rs = (HttpServletResponse) FacesContext
					.getCurrentInstance().getExternalContext().getResponse();
			queryResult.add(allRecords);
			rs.resetBuffer();
			rs.setContentType("application/pdf");
			rs.addHeader("Content-Disposition",
					"attachment; filename=notebookcover.pdf");
			OutputStream afile = rs.getOutputStream();

			/*
			 * String pathReport =
			 * "I:/notebook/HealthNoteBook/src/main/webapp/report/notebookcover.jasper"
			 * ;
			 */
			String format = ReportUtils.FORMAT_PDF;
			/*
			 * ReportUtils.report(queryResult,
			 * BankReportUtils.ReportTypes.WithdrawDepositReceipt,
			 * ReportUtils.FORMAT_PDF, afile);
			 */Map reportParameters = new HashMap();
			reportParameters.put("ROOT", root);

			ReportUtils.report(queryResult, pathReport, format, afile,
					reportParameters);
			/*
			 * // rs.sendRedirect("controlPage.jsf"); printbuttom=false;
			 */
			FacesContext.getCurrentInstance().responseComplete();
			// rs.getOutputStream().

		} catch (Exception ex) {

			ex.printStackTrace();
		}

	}

	public void printNotebookPages() {

		String[] s = serials.split(";");

		Collection<Map<String, Object>> queryResult = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> maplist = new ArrayList<Map<String, Object>>();
		System.out.println("ps list size : "+s.length);
		
		if (peresentedForPrint.hasBrokenPagesDuringPrint) {
			for (int i=0 ; i<removeIndex.size();i++){
				Map<String, Object> allRecords = new HashMap<String, Object>();

				allRecords.put("name", peresentedForPrint.getName());
				allRecords.put("nationCode", peresentedForPrint.getNationCode());
				allRecords.put("Insurer", peresentedForPrint.getCompany());
				allRecords.put("birthDate", peresentedForPrint.getBirthdateF());
				allRecords.put("unit", peresentedForPrint.getPrinttimes());
				allRecords.put("sex", peresentedForPrint.getGender());
				allRecords.put("serialNo", peresentedForPrint.getPsList().get(removeIndex.get(i)).getSerial());
				allRecords.put("mainInsured", peresentedForPrint.getMainname());
				allRecords.put("IdentificationCode", "");
				allRecords.put("relation", peresentedForPrint.getRelation());
				allRecords.put("branch", peresentedForPrint.getAgent());
				allRecords.put("page", removeIndex.get(i)+1);
				allRecords.put("contractNo", peresentedForPrint.getPolicyno());
				allRecords.put("validationDate", peresentedForPrint.getEnddateF());
				
				maplist.add(allRecords);
			}
		} else{
			for (int i = 0; i < s.length; i++) {
			Map<String, Object> allRecords = new HashMap<String, Object>();

			allRecords.put("name", peresentedForPrint.getName());
			allRecords.put("nationCode", peresentedForPrint.getNationCode());
			allRecords.put("Insurer",peresentedForPrint.getCompany());
			allRecords.put("birthDate", peresentedForPrint.getBirthdateF());
			allRecords.put("unit", peresentedForPrint.getPrinttimes());
			allRecords.put("sex", peresentedForPrint.getGender());
			allRecords.put("serialNo", peresentedForPrint.getPsList().get(i).getSerial());
			allRecords.put("mainInsured",  peresentedForPrint.getMainname());
			allRecords.put("IdentificationCode", "");
			allRecords.put("relation", peresentedForPrint.getRelation());
			allRecords.put("branch", peresentedForPrint.getAgent());
			allRecords.put("page", i+1);
			allRecords.put("contractNo",  peresentedForPrint.getPolicyno());
			allRecords.put("validationDate", peresentedForPrint.getEnddateF());
			maplist.add(allRecords);
			}
		}
		
	

		Map<String, Object> list = new HashMap<String, Object>();
		list.put("notebookpageList", maplist);

		String pathReport = ConstantManager.getInstance().getString(
				CommonConstants.PROPERTIES_FILE,
				"com.notebook.report.notebookpage");
		String root = ConstantManager.getInstance().getString(
				CommonConstants.PROPERTIES_FILE, "com.notebook.report.root");
		// pathReport
		System.out.println("pathreport:" + pathReport);
		System.out.println("root:" + pathReport);

		try {
			HttpServletResponse rs = (HttpServletResponse) FacesContext
					.getCurrentInstance().getExternalContext().getResponse();
			queryResult.add(list);

			rs.resetBuffer();
			rs.setContentType("application/pdf");
			rs.addHeader("Content-Disposition",
					"attachment; filename=notebookpage.pdf");
			OutputStream afile = rs.getOutputStream();

			String format = ReportUtils.FORMAT_PDF;
			Map reportParameters = new HashMap();
			reportParameters
					.put("ROOT", root);

			ReportUtils.report(queryResult, pathReport, format, afile,
					reportParameters);

			FacesContext.getCurrentInstance().responseComplete();
		} catch (Exception ex) {

			ex.printStackTrace();
		}

	}
	public void printSinglePages(){
		  System.out.println("------------------printSinglePages");
	 		ExternalContext context = FacesContext.getCurrentInstance()
			.getExternalContext();

			Map<String, String> params = context.getRequestParameterMap();
			String passbookSerial = params.get("passbookSerial");
			System.out.println("passbookSerial:"+passbookSerial);
			for (Passbook p :pbs) {
				if (p.getPassbookSerial().equals(passbookSerial)) {
					selectPassbook = p;
					break;
				}
			}
			for (insurancedPerson in: insurancedPersonList){
				if (selectcode.equals(in.getNationCode())){
					selectperson= in;
					break;
				}
			}


		String[] s = selectPassbook.getBargeh().split(",");

		Collection<Map<String, Object>> queryResult = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> maplist = new ArrayList<Map<String, Object>>();
		System.out.println("ps list size : "+s.length);
		
		
			for (int i = 0; i < s.length; i++) {
			Map<String, Object> allRecords = new HashMap<String, Object>();

			allRecords.put("name", selectperson.getName());
			allRecords.put("nationCode", selectperson.getNationCode());
			allRecords.put("Insurer",selectperson.getCompany());
			allRecords.put("unit", selectPassbook.getPrintTimes());
			allRecords.put("sex", selectperson.getGender());
			allRecords.put("serialNo", s[i]);
			allRecords.put("mainInsured",  selectperson.getMainname());
			allRecords.put("IdentificationCode", "");
			allRecords.put("relation", selectperson.getRelation());
			allRecords.put("branch", selectPassbook.getAgent());
			allRecords.put("page", i+1);
			allRecords.put("contractNo",  selectPassbook.getPolicyno());
			try {
				Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse( selectperson.getBirthdate().substring(0,10));
				allRecords.put("birthDate", PublicUtils.getJalaliDate(date1));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse( selectperson.getEnddate().substring(0,10));
				allRecords.put("validationDate", PublicUtils.getJalaliDate(date1));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			maplist.add(allRecords);
			}
		
		
	

		Map<String, Object> list = new HashMap<String, Object>();
		list.put("notebookpageList", maplist);

		String pathReport = ConstantManager.getInstance().getString(
				CommonConstants.PROPERTIES_FILE,
				"com.notebook.report.notebookpage");
		String root = ConstantManager.getInstance().getString(
				CommonConstants.PROPERTIES_FILE, "com.notebook.report.root");
		// pathReport
		System.out.println("pathreport:" + pathReport);
		System.out.println("root:" + pathReport);

		try {
			HttpServletResponse rs = (HttpServletResponse) FacesContext
					.getCurrentInstance().getExternalContext().getResponse();
			queryResult.add(list);

			rs.resetBuffer();
			rs.setContentType("application/pdf");
			rs.addHeader("Content-Disposition",
					"attachment; filename=notebookpage.pdf");
			OutputStream afile = rs.getOutputStream();

			String format = ReportUtils.FORMAT_PDF;
			Map reportParameters = new HashMap();
			reportParameters
					.put("ROOT", root);

			ReportUtils.report(queryResult, pathReport, format, afile,
					reportParameters);

			FacesContext.getCurrentInstance().responseComplete();
		} catch (Exception ex) {

			ex.printStackTrace();
		}

	
	}

	  public void assignSerial()
	  {
	    this.serials = "";
	    int changed = 0;
	    if (this.peresentedForPrint.hasBrokenPagesDuringPrint) {
	      for (int i = 0; i < this.removeIndex.size(); i++)
	      {
	        ((insurancedPerson.ps)this.peresentedForPrint.psList.get(((Integer)this.removeIndex.get(i)).intValue())).setSerial(Integer.valueOf(this.peresentedForPrint.getPrintFromThis().intValue() + changed));
	        this.serials = (this.serials + Integer.valueOf(this.peresentedForPrint.getPrintFromThis().intValue() + changed) + ";");
	        changed++;
	      }
	    } else {
	      for (int i = 0; i < this.peresentedForPrint.getHowManyPageToPrint().intValue(); i++)
	      {
	        ((insurancedPerson.ps)this.peresentedForPrint.psList.get(i)).setSerial(
	          Integer.valueOf(this.peresentedForPrint.getPrintFromThis().intValue() + i));
	        this.serials = 
	        
	          (this.serials + Integer.valueOf(this.peresentedForPrint.getPrintFromThis().intValue() + i) + ";");
	      }
	    }
	    HashMap<String, Object> params = new HashMap();
	    params.put(Common.MainCode_Key, peresentedForPrint.getMaincode());
	    System.out.println("----------------------------MainCode : "+ peresentedForPrint.getMaincode());
	    
	    this.serials = this.serials.substring(0, this.serials.length() - 1);
	    
	    System.out
	      .println("-------------------calling inqury for nationCode : " + 
	      this.peresentedForPrint.getNationCode() + 
	      " ,delivary Unit : " + this.StationNo + " ,PrintTimes : " + 
	      "1" + " ,serials : " + this.serials + " ,stationNo : " + 
	      this.StationNo);
	    
	    String error = null;
	    String message = "";
	    if (this.peresentedForPrint.hasBrokenPagesDuringPrint)
	    {
	      System.out.println("-------------------hasBrokenPagesDuringPrint, notebookSerial : " + this.notebookSerial + " ,Serial : " + this.serials);
	      
	      params.put("passbookserial", this.notebookSerial);
	      AnswerObject answer = TestOwragh1.inquiry(this.peresentedForPrint.getNationCode(), new Date(), this.StationNo.toString(), "1", this.serials, this.StationNo, params);
	      
	      error = answer.getException();
	      message = answer.getMessage();
	    }
	    else
	    {
	      System.out.println("-------------------inquiry new notebook ,Serial : " + this.serials);
	      
	      AnswerObject answer = TestOwragh1.inquiry(this.peresentedForPrint.getNationCode(), new Date(), this.StationNo.toString(), "1", this.serials, this.StationNo, params);
	      
	      error = answer.getException();
	      message = answer.getMessage();
	      if (error == null)
	      {
	    	
	        HashMap<String, Object> a = answer.getAnswer();
	        this.notebookSerial = a.get("serial").toString();
	        if (a.containsKey("gender")) peresentedForPrint.setGender(a.get("gender").toString());
	        if (a.containsKey("deliveryunit")) peresentedForPrint.setDeliveryunit(a.get("deliveryunit").toString());
	        if (a.containsKey("mainname")) peresentedForPrint.setMainname(a.get("mainname").toString());
	        if (a.containsKey("printtimes")) peresentedForPrint.setPrinttimes(a.get("printtimes").toString());
	        if (a.containsKey("maincode")) peresentedForPrint.setMaincode(a.get("maincode").toString());
	        if (a.containsKey("postalcode")) peresentedForPrint.setPostalcode(a.get("postalcode").toString());
	        if (a.containsKey("idno")) peresentedForPrint.setIdno(a.get("idno").toString());
	        if (a.containsKey("policyno")) peresentedForPrint.setPolicyno(a.get("policyno").toString());
	        if (a.containsKey("company")) peresentedForPrint.setCompany(a.get("company").toString());
	        if (a.containsKey("agent")) peresentedForPrint.setAgent(a.get("agent").toString());
	       
			
			try {
				if (a.containsKey("contractdate")){
					Date date1= new SimpleDateFormat("yyyy-MM-dd").parse(a.get("contractdate").toString().substring(0,10));
					peresentedForPrint.setContractdateF(PublicUtils.getJalaliDate(date1));
				}
				if (a.containsKey("enddate")){
					Date date1= new SimpleDateFormat("yyyy-MM-dd").parse(a.get("enddate").toString().substring(0,10));
					peresentedForPrint.setEnddateF(PublicUtils.getJalaliDate(date1));
				}
				if (a.containsKey("birthdate")){
					Date date1= new SimpleDateFormat("yyyy-MM-dd").parse(a.get("birthdate").toString().substring(0,10));
					peresentedForPrint.setBirthdateF(PublicUtils.getJalaliDate(date1));
				 }
				if (a.containsKey("createdate")){
					Date date1= new SimpleDateFormat("yyyy-MM-dd").parse(a.get("createdate").toString().substring(0,10));
					peresentedForPrint.setCreatedateF(PublicUtils.getJalaliDate(date1));
				 }
				
				
				
				}catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
	        
	        
	        
	      }
	    }
	    if (error != null)
	    {
	      if ((message == null) || (message.equals(""))) {
	    	  message = " ?????? ???? ???? ?????????? ";
	      }
	      FacesMessage messageF = new FacesMessage(message);
	      FacesContext context = FacesContext.getCurrentInstance();
	      context.addMessage("", messageF);
	      return;
	    }
	    String[] serialArrya=  this.serials.split(";");
    	lastSerial = serialArrya[serialArrya.length-1];
	    HttpServletResponse response = (HttpServletResponse)
	      FacesContext.getCurrentInstance().getExternalContext().getResponse();
	    try
	    {
	      response.sendRedirect("controlPage.jsf");
	    }
	    catch (IOException e)
	    {
	      e.printStackTrace();
	    }
	  }

	public void sureForRemove() {
		removeIndex=  new ArrayList<Integer>();
		HttpServletResponse response = (HttpServletResponse) FacesContext
				.getCurrentInstance().getExternalContext().getResponse();
		int deleteSum = 0;

		String ebtalSerial = "";
		for (int i = 0; i < peresentedForPrint.getPsList().size(); i++) {
			if (!peresentedForPrint.getPsList().get(i).isSelected()) {
				// doing conform job-- felan hichi
			} else {
				ebtalSerial = ebtalSerial+ peresentedForPrint.getPsList().get(i).getSerial()+ ";";
				deleteSum++;
			}
		}

		ebtalSerial = ebtalSerial.substring(0, ebtalSerial.length() - 1);
		System.out
				.println("-------------------removeBargehFromPassbook ,notebookSerial:"
						+ notebookSerial + " ,ebtalSerial:" + ebtalSerial);

		/*
		 * passbook:00000014 bargeh:1005839526 code:2285545371
		 * 
		 * passbook:00000014 bargeh:1005839526 code:2285545371
		 */
		AnswerObject answer = TestOwragh1.removeBargehFromPassbook(
				notebookSerial/* "00000026" */, ebtalSerial, null);
		
		String error = answer.getException();
		if (error != null) {
			String message = ".?????????? ???????????? ??????";

			if (answer.getMessage() != null && !answer.getMessage().equals(""))
				message = answer.getMessage();

			FacesMessage messageF = new FacesMessage(message);
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage("", messageF);
			return;
		}
		
		for (int i = 0; i < peresentedForPrint.getPsList().size(); i++) {
			if (peresentedForPrint.getPsList().get(i).isSelected()) {
				peresentedForPrint.getPsList().get(i).setSerial(-1);
				peresentedForPrint.hasBrokenPagesDuringPrint = true;
				removeIndex.add(i);
			} 
		}

		
		peresentedForPrint.setPrintFromThis(Integer.valueOf(lastSerial)+1);
		peresentedForPrint.setHowManyPageToPrint(deleteSum);
		try {
			response.sendRedirect("printNotebook.jsf");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void confirmPrint() {
		serials = "";
		notebookSerial = "";

		for (int i = 0; i < insurancedPersonList.size(); i++) {

			if (insurancedPersonList.get(i).getNationCode()
					.equals(peresentedForPrint.getNationCode())) {
				insurancedPersonList.get(i).setPrinted(true);
				insurancedPersonList.get(i).hasBrokenPagesDuringPrint = false;
				break;
			}

		}
		boolean hasSomeOneForPrint = false;
		for (int i = 0; i < insurancedPersonList.size(); i++) {

			if (insurancedPersonList.get(i).isCheckedForPrint()
					&& !insurancedPersonList.get(i).isPrinted()) {
				peresentedForPrint = insurancedPersonList.get(i);
				peresentedForPrint.setPrintFromThis(Integer.valueOf(lastSerial)+1);
				hasSomeOneForPrint = true;
				break;
			}

		}
		HttpServletResponse response = (HttpServletResponse) FacesContext
				.getCurrentInstance().getExternalContext().getResponse();
		if (hasSomeOneForPrint) {
			try {
				response.sendRedirect("printNotebook.jsf");
			} catch (IOException e) {
				e.printStackTrace();
			}

		} else {
			if (!bachPrint){
				try {
					response.sendRedirect("jobDoneSuccessfully.jsf");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			else {
				bachPrintList.remove(nationCode);
				if (bachPrintList.size()>0){
					setNationCode(bachPrintList.get(0));
					findPerson();
				}
				else{
					try {
						response.sendRedirect("jobDoneSuccessfully.jsf");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
			}
			
		}

	}
	
	public void goForPrint() {
	//	System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%in go for pirnt");
		HttpServletResponse response = (HttpServletResponse) FacesContext
				.getCurrentInstance().getExternalContext().getResponse();

		for (int i = 0; i < insurancedPersonList.size(); i++) {

			if (insurancedPersonList.get(i).isCheckedForPrint()
					&& !insurancedPersonList.get(i).isPrinted()) {

				peresentedForPrint = insurancedPersonList.get(i);
				peresentedForPrint.setPrintFromThis(Integer.valueOf(lastSerial)+1);
				break;
			}

		}

		try {
			response.sendRedirect("printNotebook.jsf");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public List<insurancedPerson> getInsurancedPersonList() {
		return insurancedPersonList;
	}

	public void setInsurancedPersonList(
			List<insurancedPerson> insurancedPersonList) {
		this.insurancedPersonList = insurancedPersonList;
	}

	public insurancedPerson getPeresentedForPrint() {
		return peresentedForPrint;
	}

	public void setPeresentedForPrint(insurancedPerson peresentedForPrint) {
		this.peresentedForPrint = peresentedForPrint;
	}

	public void viewNotebookDetail() {
		ExternalContext context = FacesContext.getCurrentInstance()
				.getExternalContext();

		Map<String, String> params = context.getRequestParameterMap();
		selectcode = params.get("nationcode");
		String name = params.get("name");
		System.out.println("nationcode : " + selectcode);
		pbs = Passbook.personPassbook(selectcode);
		HttpServletResponse response = (HttpServletResponse) FacesContext
				.getCurrentInstance().getExternalContext().getResponse();

		try {
			if (pbs.size() == 0) {
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"","???????????? ???? ???? "+name + " ?????????? ???????? ????????.");
				FacesContext context1 = FacesContext.getCurrentInstance();
				context1.addMessage("", message);
				return;
				
				
			} else
				response.sendRedirect("viewNotebookDetail.jsf?name=" + name);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void viewNotebookPageDetail() {

		ExternalContext context = FacesContext.getCurrentInstance()
				.getExternalContext();

		Map<String, String> params = context.getRequestParameterMap();
		String passbookSerial = params.get("passbookSerial");
		String name = params.get("name");
		System.out.println("passbookSerial : " + passbookSerial);
		System.out.println("name : " + name);
		for (int i = 0; i < pbs.size(); i++) {
			if (pbs.get(i).getPassbookSerial().equals(passbookSerial)) {
				selectPassbook = pbs.get(i);
				break;
			}
		}
		pbgs = PassbookPages.getPages(passbookSerial);
		HttpServletResponse response = (HttpServletResponse) FacesContext
				.getCurrentInstance().getExternalContext().getResponse();

		try {
			if (pbgs.size() == 0) {
				FacesMessage message = new FacesMessage("???????? ???? ???? ???????????? ???? ?????????? "+passbookSerial +" ?????????? ???????? ????????.");
				FacesContext context1 = FacesContext.getCurrentInstance();
				context1.addMessage("", message);
				return;
			} else
				response.sendRedirect("viewNotebookPageDetail.jsf?name=" + name
						+ "&passbookSerial=" + passbookSerial);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public List<Passbook> getPbs() {
		return pbs;
	}

	public void setPbs(List<Passbook> pbs) {
		this.pbs = pbs;
	}

	public Passbook getSelectPassbook() {
		return selectPassbook;
	}

	public void setSelectPassbook(Passbook selectPassbook) {
		this.selectPassbook = selectPassbook;
	}

	public List<PassbookPages> getPbgs() {
		return pbgs;
	}

	public void setPbgs(List<PassbookPages> pbgs) {
		this.pbgs = pbgs;
	}

	public boolean isPrintbuttom() {
		return printbuttom;
	}

	public void setPrintbuttom(boolean printbuttom) {
		this.printbuttom = printbuttom;
	}

	public String getNotebookSerial() {
		return notebookSerial;
	}

	public void setNotebookSerial(String notebookSerial) {
		this.notebookSerial = notebookSerial;
	}
	
	
	
	
	
	private List<Contract> cList = new ArrayList<Contract>();
	private Contract selectedContract;
	
	

	public List<Contract> getcList() {
		
		return cList;
	}



	public void setcList(List<Contract> cList) {
		this.cList = cList;
	}	
	
	
	public Contract getSelectedContract() {
		return selectedContract;
	}

	public void setSelectedContract(Contract selectedContract) {
		this.selectedContract = selectedContract;
	}
	public void setInitSinglePrint(String a){}
	public String getInitSinglePrint(){
        lastSerial="0";
		bachPrint= false;
		init();
		return"";
	}
	
	public void setInitContract(String a){}
	public String getInitContract() {
		init();
		lastSerial="0";           
		bachPrint= true;
		cList.clear();
		String query = "select * from owragh.T_BIMEHNAMEH " ;

		System.out.println(query);

		Connection con = null;  
		Statement stmt = null;  
		ResultSet rs = null;  

		Context ctx = null;
		Hashtable ht = new Hashtable();
		ht.put(Context.INITIAL_CONTEXT_FACTORY,  "weblogic.jndi.WLInitialContextFactory");
		ht.put(Context.PROVIDER_URL,Constants.SQL_CONNECTION.WL_DOMAIN);
		
	
		try {  
				 ctx = new InitialContext(ht);
     		     javax.sql.DataSource ds = (javax.sql.DataSource) ctx.lookup(Constants.SQL_CONNECTION.DATASOURCENAME);
			     con = ds.getConnection();

    
			
				stmt = con.createStatement();  
				rs = stmt.executeQuery(query);
				System.out.println("__________query: " + query);
				System.out.println("_______________________________");
			
					
				while (rs.next()) { 
					Contract c = new Contract();
					c.setId(rs.getString("BNM_ID"));
					c.setCompanyname(rs.getString("BNM_COMPANYNAME"));
					c.setGeneralNo(rs.getString("BNM_GENERALNO"));
					c.setOfferAgent(rs.getString("BNM_OFFERAGENT"));
					cList.add(c);
				}


					System.out.println("BimeNameh size: " + cList.size());	
		} catch (Exception e) {  
			e.printStackTrace();  
		}  
		finally {  
			if (rs != null) try { rs.close(); } catch(Exception e) {}  
			if (stmt != null) try { stmt.close(); } catch(Exception e) {}  
			if (con != null) try { con.close(); } catch(Exception e) {}  
		}	

		
		return "";
	}
	public void selectcontract(){
		
		
		if ( selectedContract== null){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "","???????????????? ???? ???????????? ????????????.");
			FacesContext context1 = FacesContext.getCurrentInstance();
			context1.addMessage("", message);
			return;
			
			
			
			 
		}
		System.out.println("contarct generalno "+ selectedContract.getGeneralNo());
		
		//setNationCode("0493224890");
	//	bachPrintList.add("0493224890");
	//	bachPrintList.add("4650495261");
		/*bachPrintList.add("0073408689");
		bachPrintList.add("0076147339");
		bachPrintList.add("0055908683");*/
		
		Integer psize = getNoPassbookPeople(selectedContract.getGeneralNo());
		if (psize==0){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "","???????? ???????? ???????? ?????????? ?????????????? "+selectedContract.getGeneralNo()+"  ???????????? ?????? ?????? ?????? ?? ???? ???????? ?????? ???? ???????? ??????????.");
			FacesContext context1 = FacesContext.getCurrentInstance();
			context1.addMessage("", message);
			return;
		}
		
		try {
			HttpServletResponse response = (HttpServletResponse) FacesContext
			.getCurrentInstance().getExternalContext().getResponse();
				response.sendRedirect("NoPassbookPeople.jsf");
		} catch (IOException e) {
			e.printStackTrace();
		}

		
	}
	
	public void printAllPersonInContractPages(){
		System.out.println("------------------printAllPersonInContractCover");
		List<insurancedPerson> contractForAllPrint  = printAllPersonInContract();
		
		if (contractForAllPrint ==null) return ; 
		
		Collection<Map<String, Object>> queryResult = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> maplist = new ArrayList<Map<String, Object>>();
		
		for (int j = 0 ; j < contractForAllPrint.size(); j++){
			 insurancedPerson ip = contractForAllPrint.get(j) ; 
			
			for (int i = 0; i <15; i++) {
				Map<String, Object> allRecords = new HashMap<String, Object>();

				allRecords.put("name", ip.getName());
				allRecords.put("nationCode", ip.getNationCode());
				allRecords.put("Insurer",ip.getCompany());
				allRecords.put("birthDate", ip.getBirthdate());
				allRecords.put("unit", ip.getPrinttimes());
				allRecords.put("sex", ip.getGender());
				allRecords.put("serialNo", ip.getPsList().get(i).getSerial());
				allRecords.put("mainInsured",  ip.getMainname());
				allRecords.put("IdentificationCode", "");
				allRecords.put("relation", ip.getRelation());
				allRecords.put("branch", ip.getAgent());
				allRecords.put("page", i+1);
				allRecords.put("contractNo",  ip.getPolicyno());
				
				allRecords.put("validationDate", endContarctDate);
				
				maplist.add(allRecords);
				}

			
		}
		
		
			Map<String, Object> list = new HashMap<String, Object>();
			list.put("notebookpageList", maplist);
		
			String pathReport = ConstantManager.getInstance().getString(
					CommonConstants.PROPERTIES_FILE,
					"com.notebook.report.notebookpage");
			String root = ConstantManager.getInstance().getString(
					CommonConstants.PROPERTIES_FILE, "com.notebook.report.root");
			
			try {
				HttpServletResponse rs = (HttpServletResponse) FacesContext
						.getCurrentInstance().getExternalContext().getResponse();
				queryResult.add(list);
		
				rs.resetBuffer();
				rs.setContentType("application/pdf");
				rs.addHeader("Content-Disposition",
						"attachment; filename=notebookpage.pdf");
				OutputStream afile = rs.getOutputStream();
		
				String format = ReportUtils.FORMAT_PDF;
				Map reportParameters = new HashMap();
				reportParameters
						.put("ROOT", root);
		
				ReportUtils.report(queryResult, pathReport, format, afile,
						reportParameters);
		
				FacesContext.getCurrentInstance().responseComplete();
			} catch (Exception ex) {
		
				ex.printStackTrace();
			}
				
				
		
		
		
	}
	public void printAllPersonInContractCover(){
		
	
		System.out.println("------------------printAllPersonInContractCover");
		List<insurancedPerson> contractForAllPrint  = printAllPersonInContract();
		
		if (contractForAllPrint ==null) return ; 
		
		Collection<Map<String, Object>> queryResult = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> maplist = new ArrayList<Map<String, Object>>();
	
		for (int i=0 ; i<contractForAllPrint.size();i++){
			Map<String, Object> allRecords = new HashMap<String, Object>();
			allRecords.put("name", contractForAllPrint.get(i).getName());
			allRecords.put("relation", contractForAllPrint.get(i).getRelation());
			allRecords.put("jobStatus", "????????????");
			allRecords.put("nationCode", contractForAllPrint.get(i).getNationCode());
			allRecords.put("Insurer",contractForAllPrint.get(i).getCompany());
			//allRecords.put("Insurer", peresentedForPrint.getCompany());
			allRecords.put("geographicalLocation", "");
			allRecords.put("idno",contractForAllPrint.get(i).getIdno());
			allRecords.put("contractNo", contractForAllPrint.get(i).getPolicyno());
			allRecords.put("tajdidDate", "");
			
			allRecords.put("exporterUnit","???????????? ??????????");
			allRecords.put("unit", "");
			allRecords.put("sex", contractForAllPrint.get(i).getGender());
			//allRecords.put("serialNo", selectPassbook.getPassbookSerial());
			allRecords.put("mainInsured",contractForAllPrint.get(i).getMainname());
			allRecords.put("IdentificationCode", "");
			allRecords.put("mainInsuredCode",contractForAllPrint.get(i).getMaincode());
		    allRecords.put("tamdidDate", "");
			allRecords.put("postalCode",contractForAllPrint.get(i).getPostalcode());
			allRecords.put("birthDate", contractForAllPrint.get(i).getBirthdate());
			
			try {
					allRecords.put("issueDate", PublicUtils.getJalaliDate(new Date()));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					
					e.printStackTrace();
				}
				try {
					//Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse( selectperson.getEnddate().substring(0,10));
					//allRecords.put("contractExpire", PublicUtils.getJalaliDate(date1));
					allRecords.put("contractExpire", endContarctDate);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				try {
					//Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse( selectperson.getContractdate().substring(0,10));
					//allRecords.put("startContract", PublicUtils.getJalaliDate(date1));
					allRecords.put("startContract", startContarctDate);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			
			maplist.add(allRecords);
			
		}
	
		
		Map<String, Object> list = new HashMap<String, Object>();
		list.put("notebookcoverList", maplist);
	
		String pathReport = ConstantManager.getInstance().getString(
				CommonConstants.PROPERTIES_FILE,
				"com.notebook.report.notebookPrintAllPersonInContarctCover");
		String root = ConstantManager.getInstance().getString(
				CommonConstants.PROPERTIES_FILE, "com.notebook.report.rootprintAllPersonInContract");
		try {
			HttpServletResponse rs = (HttpServletResponse) FacesContext
					.getCurrentInstance().getExternalContext().getResponse();
			queryResult.add(list);
			rs.resetBuffer();
			rs.setContentType("application/pdf");
			rs.addHeader("Content-Disposition",
					"attachment; filename=notebookcover.pdf");
			OutputStream afile = rs.getOutputStream();

			/*
			 * String pathReport =
			 * "I:/notebook/HealthNoteBook/src/main/webapp/report/notebookcover.jasper"
			 * ;
			 */
			String format = ReportUtils.FORMAT_PDF;
			/*
			 * ReportUtils.report(queryResult,
			 * BankReportUtils.ReportTypes.WithdrawDepositReceipt,
			 * ReportUtils.FORMAT_PDF, afile);
			 */Map reportParameters = new HashMap();
			 System.out.println("root : " + root);
			reportParameters.put("ROOT", root);

			ReportUtils.report(queryResult, pathReport, format, afile,
					reportParameters);
			/*
			 * // rs.sendRedirect("controlPage.jsf"); printbuttom=false;
			 */
			FacesContext.getCurrentInstance().responseComplete();
			// rs.getOutputStream().

		} catch (Exception ex) {

			ex.printStackTrace();
		}

	
	

		

		
	}	
	
	public  List<insurancedPerson> printAllPersonInContract(){
		
		if ( selectedContract== null){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "","???????????????? ???? ???????????? ????????????.");
			FacesContext context1 = FacesContext.getCurrentInstance();
			context1.addMessage("", message);
			return null;
			
			
			
			 
		}
		System.out.println("contarct generalno "+ selectedContract.getGeneralNo());
		
		
		AnswerObject answer = TestOwragh1.jeldList( selectedContract.getGeneralNo());
		
		System.out.println("---------------------------searchPerson Result: "+ answer);

		if (answer == null || answer.getException() != null) {
			String messageS="";
			
			if (answer == null )
			messageS = " ?????????? ???? ???? ?????????? ???????????? ??????.";

			else {
				if (!answer.getMessage().equals(""))
				messageS = answer.getMessage();}
			
			
			System.out.println("-----------------------------MESSAGE : "+ messageS);
			
			FacesMessage facem = new FacesMessage(FacesMessage.SEVERITY_ERROR, "",messageS);
			FacesContext facec = FacesContext.getCurrentInstance();
			facec.addMessage("", facem);
			return null;

		}
		
		HashMap<String, Object> ans = answer.getAnswer();

		ArrayList<HashMap<String, Object>> ff = (ArrayList<HashMap<String, Object>>) ans.get("List");
		
		System.out.println("ff : " + ff);
		 List<insurancedPerson> contractForAllPrint = new ArrayList<insurancedPerson>();
		for (int i = 0; i < ff.size(); i++) {

		
				contractForAllPrint.add(new insurancedPerson(ff.get(i)
						.get("name").toString(), ff.get(i).get("code")
						.toString(), "???????? ?????? ????????",
						ff.get(i).get("ownertype").toString().equals("Main") ? "???????? ?????? ????????" : "?????? ????????"
						, 0, 15, true,"",ff.get(i).get("company").toString()
				,ff.get(i).get("idno").toString()	,ff.get(i).get("birthdate").toString()	,ff.get(i).get("gender").toString(),ff.get(i).get("mainname").toString()  , "" /*ff.get(i).get("contractdate").toString()*/,
				ff.get(i).get("postalcode").toString(),ff.get(i).get("company").toString(), ""/*ff.get(i).get("enddate").toString()*/
				));
	
			

		
				
	
		}
		
		
		return contractForAllPrint; 
		
	}
	

	public void printAllSingleCover() {
		  System.out.println("------------------printSingleCover");
		ExternalContext context = FacesContext.getCurrentInstance()
		.getExternalContext();

		Map<String, String> params = context.getRequestParameterMap();
		String passbookSerial = params.get("passbookSerial");
		System.out.println("passbookSerial:"+passbookSerial);
		for (Passbook p :pbs) {
			if (p.getPassbookSerial().equals(passbookSerial)) {
				selectPassbook = p;
				break;
			}
		}
		for (insurancedPerson in: insurancedPersonList){
			if (selectcode.equals(in.getNationCode())){
				selectperson= in;
				break;
			}
		}

		Collection<Map<String, Object>> queryResult = new ArrayList<Map<String, Object>>();
		Map<String, Object> allRecords = new HashMap<String, Object>();
		allRecords.put("name", selectperson.getName());
		allRecords.put("relation", selectperson.getRelation());
		allRecords.put("jobStatus", "????????????");
		allRecords.put("nationCode", selectperson.getNationCode());
		allRecords.put("Insurer",selectperson.getCompany());
		//allRecords.put("Insurer", peresentedForPrint.getCompany());
		allRecords.put("geographicalLocation", "");
		allRecords.put("idno",selectperson.getIdno());
		allRecords.put("contractNo", selectPassbook.getPolicyno());
		allRecords.put("tajdidDate", "");
		
		allRecords.put("exporterUnit","???????????? ??????????");
		allRecords.put("unit", "");
		allRecords.put("sex", selectperson.getGender());
		//allRecords.put("serialNo", selectPassbook.getPassbookSerial());
		allRecords.put("mainInsured",selectperson.getMainname());
		allRecords.put("IdentificationCode", "");
		allRecords.put("mainInsuredCode",selectperson.getMaincode());
	    allRecords.put("tamdidDate", "");
		allRecords.put("postalCode",selectperson.getPostalcode());
/*
		"deliverydate":"2018-10-01 10:05:30.0"
			"contractdate":"2018-08-23 00:00:00"*/
		try {
			Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse( selectPassbook.getDelivarydate().substring(0,10));
			allRecords.put("issueDate", PublicUtils.getJalaliDate(date1));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse( selectperson.getEnddate().substring(0,10));
			allRecords.put("contractExpire", PublicUtils.getJalaliDate(date1));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		try {
			Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse( selectperson.getContractdate().substring(0,10));
			allRecords.put("startContract", PublicUtils.getJalaliDate(date1));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse( selectperson.getBirthdate().substring(0,10));
			allRecords.put("birthDate", PublicUtils.getJalaliDate(date1));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	
		String pathReport = ConstantManager.getInstance().getString(
				CommonConstants.PROPERTIES_FILE,
				"com.notebook.report.notebookcover");
		String root = ConstantManager.getInstance().getString(
				CommonConstants.PROPERTIES_FILE, "com.notebook.report.root");
		try {
			HttpServletResponse rs = (HttpServletResponse) FacesContext
					.getCurrentInstance().getExternalContext().getResponse();
			queryResult.add(allRecords);
			rs.resetBuffer();
			rs.setContentType("application/pdf");
			rs.addHeader("Content-Disposition",
					"attachment; filename=notebookcover.pdf");
			OutputStream afile = rs.getOutputStream();

			/*
			 * String pathReport =
			 * "I:/notebook/HealthNoteBook/src/main/webapp/report/notebookcover.jasper"
			 * ;
			 */
			String format = ReportUtils.FORMAT_PDF;
			/*
			 * ReportUtils.report(queryResult,
			 * BankReportUtils.ReportTypes.WithdrawDepositReceipt,
			 * ReportUtils.FORMAT_PDF, afile);
			 */Map reportParameters = new HashMap();
			reportParameters.put("ROOT", root);

			ReportUtils.report(queryResult, pathReport, format, afile,
					reportParameters);
			/*
			 * // rs.sendRedirect("controlPage.jsf"); printbuttom=false;
			 */
			FacesContext.getCurrentInstance().responseComplete();
			// rs.getOutputStream().

		} catch (Exception ex) {

			ex.printStackTrace();
		}

	}
	
	
 public Integer  getNoPassbookPeople(String generalno){
	// bachPrintList.clear();
	 noPassbookPeopleList.clear();
	 contarct=generalno;
		String query = "select * from Owragh.owragh.V_NoPassbookPeople where npp_generalno = '"+generalno + "'" ;

		System.out.println(query);

		Connection con = null;  
		Statement stmt = null;  
		ResultSet rs = null;  

		Context ctx = null;
		Hashtable ht = new Hashtable();
		ht.put(Context.INITIAL_CONTEXT_FACTORY,  "weblogic.jndi.WLInitialContextFactory");
		ht.put(Context.PROVIDER_URL,Constants.SQL_CONNECTION.WL_DOMAIN);
		
	
		try {  
				 ctx = new InitialContext(ht);
  		     javax.sql.DataSource ds = (javax.sql.DataSource) ctx.lookup(Constants.SQL_CONNECTION.DATASOURCENAME);
			     con = ds.getConnection();

 
			
				stmt = con.createStatement();  
				rs = stmt.executeQuery(query);
				System.out.println("__________query: " + query);
				System.out.println("_______________________________");
			
			    Integer i= 1;
				while (rs.next()) { 
					NoPassbookPeople npp= new NoPassbookPeople();
					npp.setName(rs.getString("npp_firstname") + " "+ rs.getString("npp_lastname"));
					npp.setNationCode(rs.getString("npp_code") );
					npp.setCheckedForPrint(true);
					npp.setIndex(i);
					noPassbookPeopleList.add(npp);
					i++;
					//bachPrintList.add(rs.getString("npp_code"));
				}


					System.out.println("noPassbookPeopleList size: " + noPassbookPeopleList.size());	
		} catch (Exception e) {  
			e.printStackTrace();  
		}  
		finally {  
			if (rs != null) try { rs.close(); } catch(Exception e) {}  
			if (stmt != null) try { stmt.close(); } catch(Exception e) {}  
			if (con != null) try { con.close(); } catch(Exception e) {}  
		}	

		 return noPassbookPeopleList.size();
	 
 }
 	public void selectednopassbook(){
		bachPrintList.clear();
		HttpServletResponse response = (HttpServletResponse) FacesContext
		.getCurrentInstance().getExternalContext().getResponse();

		for (int i = 0; i < noPassbookPeopleList.size(); i++) {
		
			if (noPassbookPeopleList.get(i).isCheckedForPrint()) {
				bachPrintList.add(noPassbookPeopleList.get(i).getNationCode()); 
			}
		
		}
		System.out.println("bachPrintList : "+bachPrintList.size());
		setNationCode(bachPrintList.get(0));
		findPerson();
		
	}
 	public void cancelnotebook() {
 		  System.out.println("-----------------cancelnotebook");
 		  Integer p2=null;
 			if (SessionUtils.getSessionParam(getRequest(), "p2") != null)
 				p2 = Integer.valueOf(SessionUtils.getSessionParam(getRequest(), "p2").toString());
 		
 			
 			if (p2== null ){
 				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "????????????", "?????? ???????????? ???? ?????????? ???????????? ????????????.");
 		        FacesContext.getCurrentInstance().addMessage(null, message);
 		        return;
 			}
 			
 			if (p2!= 9999 && p2 >5050 && p2< 5000 ){
 				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "????????????",  "?????? ???????????? ???? ?????????? ???????????? ????????????.");
 		        FacesContext.getCurrentInstance().addMessage(null, message);
 		        return;
 			}
 			
 			
 	 		ExternalContext context = FacesContext.getCurrentInstance()
 			.getExternalContext();

 			Map<String, String> params = context.getRequestParameterMap();
 			String passbookSerial = params.get("passbookSerial");
 			System.out.println("passbookSerial:"+passbookSerial);
 			
 			AnswerObject result = TestOwragh1.changePassbookStatus(passbookSerial, "Canceled", null);
 			if (result.getException()!=null){
 				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "????????????", "?????????? ???????????? ?????????? ??????.");
 		        FacesContext.getCurrentInstance().addMessage(null, message);
 		        return;
 			}
 			for (int i = 0; i < pbs.size(); i++) {
 				if (pbs.get(i).getPassbookSerial().equals(passbookSerial)) {
 					pbs.get(i).setStatus("Canceled");
 					break;
 				}
 			}
 			
 				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "????????", "???????????? ???? ?????????? ??????????  "+passbookSerial+ " ?????????? ????." );
 		        FacesContext.getCurrentInstance().addMessage(null, message);
 		      
 			
    }
     
    public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
 	public void printSingleCover() {
 		  System.out.println("------------------printSingleCover");
 		ExternalContext context = FacesContext.getCurrentInstance()
		.getExternalContext();

		Map<String, String> params = context.getRequestParameterMap();
		String passbookSerial = params.get("passbookSerial");
		System.out.println("passbookSerial:"+passbookSerial);
		for (Passbook p :pbs) {
			if (p.getPassbookSerial().equals(passbookSerial)) {
				selectPassbook = p;
				break;
			}
		}
		for (insurancedPerson in: insurancedPersonList){
			if (selectcode.equals(in.getNationCode())){
				selectperson= in;
				break;
			}
		}

		Collection<Map<String, Object>> queryResult = new ArrayList<Map<String, Object>>();
		Map<String, Object> allRecords = new HashMap<String, Object>();
		allRecords.put("name", selectperson.getName());
		allRecords.put("relation", selectperson.getRelation());
		allRecords.put("jobStatus", "????????????");
		allRecords.put("nationCode", selectperson.getNationCode());
		allRecords.put("Insurer",selectperson.getCompany());
		//allRecords.put("Insurer", peresentedForPrint.getCompany());
		allRecords.put("geographicalLocation", "");
		allRecords.put("idno",selectperson.getIdno());
		allRecords.put("contractNo", selectPassbook.getPolicyno());
		allRecords.put("tajdidDate", "");
		
		allRecords.put("exporterUnit","???????????? ??????????");
		allRecords.put("unit", "");
		allRecords.put("sex", selectperson.getGender());
		//allRecords.put("serialNo", selectPassbook.getPassbookSerial());
		allRecords.put("mainInsured",selectperson.getMainname());
		allRecords.put("IdentificationCode", "");
		allRecords.put("mainInsuredCode",selectperson.getMaincode());
	    allRecords.put("tamdidDate", "");
		allRecords.put("postalCode",selectperson.getPostalcode());
/*
		"deliverydate":"2018-10-01 10:05:30.0"
			"contractdate":"2018-08-23 00:00:00"*/
		try {
			Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse( selectPassbook.getDelivarydate().substring(0,10));
			allRecords.put("issueDate", PublicUtils.getJalaliDate(date1));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse( selectperson.getEnddate().substring(0,10));
			allRecords.put("contractExpire", PublicUtils.getJalaliDate(date1));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		try {
			Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse( selectperson.getContractdate().substring(0,10));
			allRecords.put("startContract", PublicUtils.getJalaliDate(date1));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse( selectperson.getBirthdate().substring(0,10));
			allRecords.put("birthDate", PublicUtils.getJalaliDate(date1));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	
		String pathReport = ConstantManager.getInstance().getString(
				CommonConstants.PROPERTIES_FILE,
				"com.notebook.report.notebookcover");
		String root = ConstantManager.getInstance().getString(
				CommonConstants.PROPERTIES_FILE, "com.notebook.report.root");
		try {
			HttpServletResponse rs = (HttpServletResponse) FacesContext
					.getCurrentInstance().getExternalContext().getResponse();
			queryResult.add(allRecords);
			rs.resetBuffer();
			rs.setContentType("application/pdf");
			rs.addHeader("Content-Disposition",
					"attachment; filename=notebookcover.pdf");
			OutputStream afile = rs.getOutputStream();

			/*
			 * String pathReport =
			 * "I:/notebook/HealthNoteBook/src/main/webapp/report/notebookcover.jasper"
			 * ;
			 */
			String format = ReportUtils.FORMAT_PDF;
			/*
			 * ReportUtils.report(queryResult,
			 * BankReportUtils.ReportTypes.WithdrawDepositReceipt,
			 * ReportUtils.FORMAT_PDF, afile);
			 */Map reportParameters = new HashMap();
			reportParameters.put("ROOT", root);

			ReportUtils.report(queryResult, pathReport, format, afile,
					reportParameters);
			/*
			 * // rs.sendRedirect("controlPage.jsf"); printbuttom=false;
			 */
			FacesContext.getCurrentInstance().responseComplete();
			// rs.getOutputStream().

		} catch (Exception ex) {

			ex.printStackTrace();
		}

	}
 
	public boolean isBachPrint() {
		return bachPrint;
	}

	public void setBachPrint(boolean bachPrint) {
		this.bachPrint = bachPrint;
	}

	public List<NoPassbookPeople> getNoPassbookPeopleList() {
		return noPassbookPeopleList;
	}

	public void setNoPassbookPeopleList(List<NoPassbookPeople> noPassbookPeopleList) {
		this.noPassbookPeopleList = noPassbookPeopleList;
	}

	public String getContarct() {
		return contarct;
	}

	public void setContarct(String contarct) {
		this.contarct = contarct;
	}

	public String getLastSerial() {
		return lastSerial;
	}

	public void setLastSerial(String lastSerial) {
		this.lastSerial = lastSerial;
	}

	
	
  public String getStartContarctDate() {
		return startContarctDate;
	}

	public void setStartContarctDate(String startContarctDate) {
		this.startContarctDate = startContarctDate;
	}
	
	
	

public String getEndContarctDate() {
		return endContarctDate;
	}

	public void setEndContarctDate(String endContarctDate) {
		this.endContarctDate = endContarctDate;
	}

public void diffTime(Date d1, Date d2,String nationCode){
	

		try {
			
			//in milliseconds
			long diff = d2.getTime() - d1.getTime();

			long diffSeconds = diff / 1000 % 60;
			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;
			long diffDays = diff / (24 * 60 * 60 * 1000);
			System.out.println("------- " + nationCode + " ----------");
			System.out.print(diffDays + " days, ");
			System.out.print(diffHours + " hours, ");
			System.out.print(diffMinutes + " minutes, ");
			System.out.print(diffSeconds + " seconds.");

		} catch (Exception e) {
			e.printStackTrace();
		}

  }
}