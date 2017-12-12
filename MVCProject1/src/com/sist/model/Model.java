package com.sist.model;

import javax.servlet.http.HttpServletRequest;

//1.8 이상 : default 메소드, static 메소드 => 구현된 메소드를 만들 수 있다.
/*
 *  상속
 *  	class / interface 
 *  		
 *  	class ===> class (extends)
 *  			
 *  	interface ===> interface (extends)
 *  			
 *  	interface ===> class (implements)
 *  		
 *  	class ===> interface (X 불가능)
 */

public interface Model {
	public String excute(HttpServletRequest req) throws Exception;
}