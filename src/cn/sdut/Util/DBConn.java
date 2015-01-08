package cn.sdut.Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConn {
	public static Connection getConnnection() {
		  Connection conn = null;
		  @SuppressWarnings("unused")
		Statement stmt = null;
		  @SuppressWarnings("unused")
		ResultSet rs = null;
		  String url = null;
		  String user = null;
		  String password = null;
		  @SuppressWarnings("unused")
		String sql = null;
		try {
	
			Class.forName("com.mysql.jdbc.Driver"); //加载mysq驱动
			  } catch (ClassNotFoundException e) {
			   System.out.println("驱动加载错误");
			   e.printStackTrace();//打印出错详细信息
			  }
			  try {
			   url = 
			    "jdbc:mysql://192.168.3.110/bank?user=bank&password=admin&useUnicode=true&&characterEncoding=gb2312&autoReconnect=true&relaxAutoCommit=true&zeroDateTimeBehavior=convertToNull";//简单写法：url = "jdbc:myqsl://localhost/test(数据库名)? user=root(用户)&password=yqs2602555(密码)";
			   user = "bank";
			   password = "admin";
			   conn = DriverManager.getConnection(url,user,password);
			   //System.out.println("数据库已连接！！！");
			  } catch (SQLException e) {
			   System.out.println("数据库链接错误");
			   e.printStackTrace();
			  }
		
		return conn;
	}
}
