package com.notebook;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;



public  class NoPassbookPeople  implements Serializable{
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String name;
	String nationCode;
	boolean checkedForPrint;
	Integer index;
	
	
	
	public NoPassbookPeople() {
		// TODO Auto-generated constructor stub
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



	public boolean isCheckedForPrint() {
		return checkedForPrint;
	}



	public void setCheckedForPrint(boolean checkedForPrint) {
		this.checkedForPrint = checkedForPrint;
	}



	public Integer getIndex() {
		return index;
	}



	public void setIndex(Integer index) {
		this.index = index;
	}
	
	
	
}
