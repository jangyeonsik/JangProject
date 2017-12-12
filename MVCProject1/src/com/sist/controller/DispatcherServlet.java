package com.sist.controller;

import java.io.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.sist.model.*;
import java.util.*; // Map(��ó => Ŭ����(��) ��Ī

public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private String[] strCls = { //��Ȯ�� ��ΰ�
			"com.sist.model.MovieList",
			"com.sist.model.MovieDetail"
	};
	private String[] strCmd= { //�� ��ΰ��� Ű������ ǥ���� ��
			"list",
			"detail"
	};
	// ������ => <bean id="list" class="com.sist.model.MovieList" />
	// csv => list, com.sist.model.MovieList
	/*
	 * 	key		value
	 * 	list new MovieList() Class.forName()
	 * 	detail new MovieDetail()
	 */
	public Map clsMap = new HashMap();
	// HashMap, Hashtable
	public void init(ServletConfig config) throws ServletException { //������ ����!!! init
		try {
			
			for(int i=0; i<strCls.length; i++) {
				Class clsName = Class.forName(strCls[i]); 
				Object obj = clsName.newInstance();
				clsMap.put(strCmd[i], obj); // Singleton : �ּ� ���� ���� �ٲ��� �ʴ´�.
			}
			//clsMap.put("list", new MovieList()); 
			//clsMap.put("detail", new MovieDetail()); ->���� ���� ��������� for������ ������ �ѹ濡 ������ �ְ� �Ѵ�.
			
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	// ����ڰ� ��û�ϴ� ȭ������ �Ѱ��ִ� ����
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
		try {
			// list.do, detail.do => movie.do?cmd=list
			String cmd = request.getRequestURI(); // URI : ����ڰ� �ּ��Է¶��� ��û�� ����
			//http://localhost:8080/MVCProject/list.do
			//						===================URI
			//					   /MVCProject/list.do
			//					   ====================ContextPath.length()+1, cmd.lastIndexOf(".") = list
			cmd = cmd.substring(request.getContextPath().length()+1, cmd.lastIndexOf("."));
			// ��û�� ó�� => �� Ŭ���� (Ŭ���� �޼ҵ�)
			Model model = (Model)clsMap.get(cmd);
			// model => ������ �� �Ŀ� ����� request�� ��� �޶�
			// Call By Reference => �ּҸ� �Ѱ��ְ� �ּҿ� ���� ä���.
			String jsp = model.excute(request);
			//JSP�� request,session���� ����
			RequestDispatcher rd = request.getRequestDispatcher(jsp);
			rd.forward(request, response);
			// jsp�� _jspService() �� ȣ���Ѵ�.
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
