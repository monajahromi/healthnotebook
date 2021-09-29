package com.notebook;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.pardis.common.PublicUtils;
import com.pardis.jsonmessage.AnswerObject;
import com.pardis.owragh.Common;


@ManagedBean
@SessionScoped
public class PermisionList implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<Permision> cList = new ArrayList<Permision>();
	
	public void setInit(String a){}
	
	
	
	public String getInit(){
		HashMap<String, Object> params = new HashMap<String, Object>();
		AnswerObject answer = TestOwragh1.searchDefineAccess(null, params);

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
				HashMap<String, Object> ans = answer.getAnswer();
				
				ArrayList<HashMap<String, Object>> ff = (ArrayList<HashMap<String, Object>>) ans
				.get("List");
				cList.clear();
				for (int i = 0; i < ff.size(); i++) {
		
				String todate = "";
				String fromdate = ""; 
				try {
						if (!ff.get(i).get("todate").equals("null") &&  !ff.get(i).get("todate").equals("") ){
							Date	date1 = new SimpleDateFormat("yyyy-MM-dd").parse(ff.get(i).get("todate").toString().substring(0,10));
							todate= PublicUtils.getJalaliDate(date1);	
						}
						
						if (!ff.get(i).get("fromdate").equals("null") &&  !ff.get(i).get("fromdate").equals("") ){
							Date	date2 = new SimpleDateFormat("yyyy-MM-dd").parse(ff.get(i).get("fromdate").toString().substring(0,10));
							fromdate= PublicUtils.getJalaliDate(date2);
								
						}
						
						
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
				
				cList.add(new Permision(Integer.valueOf(ff.get(i).get("id").toString()), ff.get(i).get("policyno").toString(), ff.get(i).get("nationalcode").toString(),
						  ff.get(i).get("firstname").toString(),"", null, todate,null,fromdate, ff.get(i).get("status").toString(), 
						  ff.get(i).get("username").toString()));
				}

		
		return "";
	}
	public void newPermision() {
		System.out.println("newPermision");
		
	}
	public void deletePermision() {
		System.out.println("deletePermision");
		ExternalContext context = FacesContext.getCurrentInstance()
			.getExternalContext();

			Map<String, String> params = context.getRequestParameterMap();
			String id = params.get("id");
			System.out.println("id:"+id);
		
		AnswerObject answer = TestOwragh1.changeDefinedAccessStatus(id, Common.Lookup_DefineAccessStatus_Deleted, null);

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
					return ;
				}
				
				

		FacesMessage facem = new FacesMessage(FacesMessage.SEVERITY_INFO, "","عملیات با موفقیت انجام شد");
		FacesContext facec = FacesContext.getCurrentInstance();
		facec.addMessage("", facem);	
		getInit();
		
		
		
	}
	/*public String getInit1() {
		
		cList.clear();
		String query = "select * from owragh.T_DEFINEDACCESS " ;

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
					Permision c = new Permision();
					
					
					c.setId(rs.getString("DAS_ID"));
					c.setCommitdate(rs.getDate("DAS_COMMITDATE"));
					c.setFirstname(rs.getString("DAS_FIRSTNAME"));
					c.setLastname(rs.getString("DAS_LASTNAME"));
					c.setNationalcode(rs.getString("DAS_NATIONALCODE"));
					c.setPolicy_no(rs.getString("DAS_POLICY_NO"));
					c.setPremature(rs.getString("DAS_PREMATURE"));
					c.setStatus(rs.getString("DAS_LK_ID_STATUS"));
					c.setTodate(rs.getDate("DAS_TODATE"));
				    c.setFromdate(rs.getDate("DAS_FROMDATE"));
					c.setUser(rs.getString("DAS_USER"));
					
					c.setTodateFarsi(DateFormatUtils.getJalaliDate(rs.getDate("DAS_TODATE")));
					c.setFromdateFarsi(DateFormatUtils.getJalaliDate(rs.getDate("DAS_FROMDATE")));

					cList.add(c);
				}


					System.out.println("DEFINEDACCESS size: " + cList.size());	
		} catch (Exception e) {  
			e.printStackTrace();  
		}  
		finally {  
			if (rs != null) try { rs.close(); } catch(Exception e) {}  
			if (stmt != null) try { stmt.close(); } catch(Exception e) {}  
			if (con != null) try { con.close(); } catch(Exception e) {}  
		}	

		
		return "";
	}*/
	public List<Permision> getcList() {
	
		return cList;
	}
	public void setcList(List<Permision> cList) {
		this.cList = cList;
	}

	
	
	
}
