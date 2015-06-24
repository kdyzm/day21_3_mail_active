package cn.itcast;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import cn.itcast.domain.User;
import cn.itcast.utils.DataSourceUtils;

public class LoginSerlvet extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		request.setCharacterEncoding("UTF-8");
		String name = request.getParameter("name");
		String pwd  = request.getParameter("pwd");
		//如果
		try{
			String sql = "SELECT id,name,pwd,email,code"+
						 " FROM users LEFT JOIN active ON users.id=active.uid"+
					     " WHERE name=? and pwd=?";
			QueryRunner run = new QueryRunner(DataSourceUtils.getDatasSource());
			User u = run.query(sql,new BeanHandler<User>(User.class),name,pwd);
			if(u==null){
				out.print("你的用户名或是密码错误..");
			}else{
				if(u.getCode()==null){
					out.print("你登录成功、。。。");
				}else{
					out.print("你还没有激活...");
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
