package com.sist.controller;

import java.io.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.sist.model.*;
import java.util.*; // Map(요처 => 클래스(모델) 매칭

public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private String[] strCls = { //정확한 경로값
			"com.sist.model.MovieList",
			"com.sist.model.MovieDetail"
	};
	private String[] strCmd= { //위 경로값을 키값으로 표현한 것
			"list",
			"detail"
	};
	// 스프링 => <bean id="list" class="com.sist.model.MovieList" />
	// csv => list, com.sist.model.MovieList
	/*
	 * 	key		value
	 * 	list new MovieList() Class.forName()
	 * 	detail new MovieDetail()
	 */
	public Map clsMap = new HashMap();
	// HashMap, Hashtable
	public void init(ServletConfig config) throws ServletException { //생성자 역할!!! init
		try {
			
			for(int i=0; i<strCls.length; i++) {
				Class clsName = Class.forName(strCls[i]); 
				Object obj = clsName.newInstance();
				clsMap.put(strCmd[i], obj); // Singleton : 주소 값이 절대 바뀌지 않는다.
			}
			//clsMap.put("list", new MovieList()); 
			//clsMap.put("detail", new MovieDetail()); ->위와 같은 방법이지만 for문으로 돌려서 한방에 끝낼수 있게 한다.
			
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	// 사용자가 요청하는 화면으로 넘겨주는 역할
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
		try {
			// list.do, detail.do => movie.do?cmd=list
			String cmd = request.getRequestURI(); // URI : 사용자가 주소입력란에 요청한 파일
			//http://localhost:8080/MVCProject/list.do
			//						===================URI
			//					   /MVCProject/list.do
			//					   ====================ContextPath.length()+1, cmd.lastIndexOf(".") = list
			cmd = cmd.substring(request.getContextPath().length()+1, cmd.lastIndexOf("."));
			// 요청을 처리 => 모델 클래스 (클래스 메소드)
			Model model = (Model)clsMap.get(cmd);
			// model => 실행을 한 후에 결과를 request에 담아 달라
			// Call By Reference => 주소를 넘겨주고 주소에 값을 채운다.
			String jsp = model.excute(request);
			//JSP에 request,session값을 전송
			RequestDispatcher rd = request.getRequestDispatcher(jsp);
			rd.forward(request, response);
			// jsp의 _jspService() 를 호출한다.
			/*
			 * service(request, response) {
			 * 	a_jsp._jspService(request);
			 * }
			 * 
			 */
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
