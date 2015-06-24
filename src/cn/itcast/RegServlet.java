package cn.itcast;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import java.util.UUID;

import javax.mail.Authenticator;
import javax.mail.Message.RecipientType;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbutils.QueryRunner;

import cn.itcast.domain.User;
import cn.itcast.utils.DataSourceUtils;

public class RegServlet extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String name = request.getParameter("name");
		String pwd = request.getParameter("pwd");
		String mail = request.getParameter("email");
		//声明对象
		User u = new User();
		u.setName(name);
		u.setPwd(pwd);
		u.setEmail(mail);
		//调用后台的serice保存
		Connection con = null;
		try{
			con = DataSourceUtils.getDatasSource().getConnection();
			u.setId(UUID.randomUUID().toString().replace("-", ""));
			u.setCode(UUID.randomUUID().toString().replace("-", "")
					+UUID.randomUUID().toString().replace("-", ""));
			con.setAutoCommit(false);
			//先保存user表
			String sql = "insert into users values(?,?,?,?)";
			QueryRunner run = new QueryRunner();
			run.update(con, sql,u.getId(),u.getName(),u.getPwd(),u.getEmail());
			//
			sql = "insert into active values(?,?)";
			run.update(con,sql,u.getId(),u.getCode());
			con.commit();
		}catch(Exception e){
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			throw new RuntimeException(e);
		}finally{
			try {
				con.close();
				DataSourceUtils.remove();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print("你已经注册成功，请去激活，如果没有收到邮件，请等一会再收.");
		new SendThread(u).start();
	}
}
//声明一个线程专门发邮件
class SendThread extends Thread{
	private User user;
	public SendThread(User u) {
		this.user=u;
	}
	@Override
	public void run() {
		try{
			Properties p = new Properties();
			p.setProperty("mail.host","127.0.0.1");
			p.setProperty("mail.smtp.auth", "true");
			Session s = Session.getDefaultInstance(p,new Authenticator() {
				public javax.mail.PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("one", "1234");
				};
			});
			s.setDebug(true);
			MimeMessage mm = new MimeMessage(s);
			mm.setFrom(new InternetAddress("one@wj.com"));
			mm.setRecipient(RecipientType.TO,new InternetAddress(user.getEmail()));
			mm.setSubject("请去激活");
			//声明url
			String url = "http://192.168.1.100:6666/day21_active/ActiveServlet?id="+user.getCode();
			String html ="你好:"+user.getName()+"<br/>请激活:<a href='"+url+"'>激活</a>,你可以Copy这个连接:"+url;
			mm.setContent(html,"text/html;charset=UTf-8");
			
			Transport.send(mm);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
