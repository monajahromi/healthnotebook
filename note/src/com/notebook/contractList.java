package com.notebook;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import utils.system;

import com.constants.Constants;
import com.pardis.genericmanagedbeans.utls.SessionUtils;
import com.pardis.jsonmessage.AnswerObject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


@ManagedBean(name="contract")
@ViewScoped
public  class contractList  implements Serializable{
	
	
	
	private List<Contract> cList = new ArrayList<Contract>();
	private Contract selectedContract;
	 private String color; 
	private List<String> nationcodes= new ArrayList<String>(); 


	public String getInit() {
		
		cList.clear();
		String StationNo= ""/*"2527"*/;
		if (SessionUtils.getSessionParam(getRequest(), "p3")!= null)
		 StationNo = (SessionUtils.getSessionParam(getRequest(), "p3").toString()).toString();
		
		String query = "select * from owragh.T_BIMEHNAMEH where BNM_OFFERAGENT= '"+ StationNo + "'" ;

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


					System.out.println("size: " + cList.size());	
		} catch (Exception e) {  
			e.printStackTrace();  
		}  
		/*finally {  
			if (rs != null) try { rs.close(); } catch(Exception e) {}  
			if (stmt != null) try { stmt.close(); } catch(Exception e) {}  
			if (con != null) try { con.close(); } catch(Exception e) {}  
		}	
*/
		
		return "";
	}



	public List<Contract> getcList() {
		getInit();
		return cList;
	}



	public void setcList(List<Contract> cList) {
		this.cList = cList;
	}	
	


	public static HttpServletRequest getRequest() {
		if (FacesContext.getCurrentInstance() == null) {
			return null;
		}
		return (HttpServletRequest) FacesContext.getCurrentInstance()
				.getExternalContext().getRequest();
	}
	
	public void selectcontract(){
		System.out.println("color"+ selectedContract.getGeneralNo());
		
		nationcodes.add("0493224890");
		nationcodes.add("4650495261");
		

		
	}

	
	public Contract getSelectedContract() {
		return selectedContract;
	}



	public void setSelectedContract(Contract selectedContract) {
		this.selectedContract = selectedContract;
	}



	public String getColor() {
		return color;
	}



	public void setColor(String color) {
		this.color = color;
	}
	
	
	
}
