package cn.itcast.utils;
import java.sql.Connection;
import javax.sql.DataSource;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DataSourceUtils {
	// 声明线程局部的容器
	private static ThreadLocal<Connection> tl = new ThreadLocal<Connection>();
	private static DataSource ds;
	static {
		ds = // 默认的读取c3p0-config.xml中默认配置
		new ComboPooledDataSource("itcast");
	}

	public static DataSource getDatasSource() {
		return ds;
	}
	//当使用多个dao进就调用这个方法控制多个dao中的事务
	public static Connection getConn() {
		// 先从tl这个容器中获取一次数据，如果当前线程已经保存过connection则直接返回这个connecton
		Connection con = tl.get();
		if (con == null) {
			try {
				con = ds.getConnection();// 每一次从ds中获取一个新的连接
				// 将这个con放到tl中
				tl.set(con);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return con;
	}
	//如果不需要事务，直接使用connection可以直接获取一个Connection
	public static Connection openConn() {
		Connection con = null;
		try {
			con = ds.getConnection();// 每一次从ds中获取一个新的连接
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return con;
	}

	public static void remove() {
		tl.remove();
	}
	public static void main(String[] args) {
		getConn();
	}
}
