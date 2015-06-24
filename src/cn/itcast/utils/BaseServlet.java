package cn.itcast.utils;
import java.io.IOException;
import java.lang.reflect.Method;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class BaseServlet extends HttpServlet {
	@Override
	public void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTf-8");
		String methodName = req.getParameter("cmd");
		if(methodName==null || methodName.trim().equals("")){
			methodName="execute";
		}
		try{
			Method m = this.getClass().getMethod(
						methodName,
						HttpServletRequest.class,HttpServletResponse.class);
			m.invoke(this,req,resp);
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
}
