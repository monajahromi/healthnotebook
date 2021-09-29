package com.notebook;

import java.util.ArrayList;
import java.util.List;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<String> a= new ArrayList<String>();
		a.add("aaaa");
		a.add("bbb");
		
		a.remove("aaaa");
		System.out.println(a+ "--------"+a.size());
		
		a.remove("bbb");
		System.out.println(a+ "--------"+a.size());
		
	}

}
