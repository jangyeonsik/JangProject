package com.sist.model;

import javax.servlet.http.HttpServletRequest;

//1.8 �̻� : default �޼ҵ�, static �޼ҵ� => ������ �޼ҵ带 ���� �� �ִ�.
/*
 *  ���
 *  	class / interface 
 *  		
 *  	class ===> class (extends)
 *  			
 *  	interface ===> interface (extends)
 *  			
 *  	interface ===> class (implements)
 *  		
 *  	class ===> interface (X �Ұ���)
 */

public interface Model {
	public String excute(HttpServletRequest req) throws Exception;
}