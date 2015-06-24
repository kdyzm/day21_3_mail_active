package cn.itcast;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbutils.QueryRunner;

import cn.itcast.utils.DataSourceUtils;

public class ActiveServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String code = request.getParameter("id");
		try{
			String sql = "delete from active where code=?";
			QueryRunner run = new QueryRunner(DataSourceUtils.getDatasSource());
			int effect = run.update(sql,code);
			if(effect==0){
				response.getWriter().print("Active Failed...");
			}else{
				response.getWriter().print("Active Successfully");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

}
