package cn.sdut.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import cn.sdut.Util.DBConn;
import cn.sdut.Util.Md5;
import cn.sdut.entity.AccountBean;

public class AccountDao {
	Connection con = DBConn.getConnnection();

	/***************数据库插入*********************/
	@SuppressWarnings("resource")
	public int addAcount(AccountBean account) {
		// 向账户表中增加一条记录，每个字段的值取自传进来的封装参数
		//返回帐号
		//String sqlInsert = "insert into account values(?,?,?,?)";
		String sqlInsert = "insert into account(name,amount,password,phoneNum,IDcard) values(?,?,?,?,?)";
		PreparedStatement pst=null;
		ResultSet rs=null;
		int countId = 0;
		try {
			pst = con.prepareStatement(sqlInsert); /*实现了PreparedStatement接口的实现类的实例*/
			pst.setString(1, account.getName());

			//pst.setDate(2, new java.sql.Date(account.getCreateDate().getTime()));
			//时间不用获取直接由数据库自动添加
			pst.setFloat(2, account.getAmount());
			pst.setString(3, Md5.getMd5(account.getPassword()));//密码MD5加密
			pst.setString(4, account.getPhoneNum());
			pst.setString(5, account.getIDcard());
			pst.executeUpdate();
			
			//获得刚加入的那条记录的主键
			pst=con.prepareStatement("select id from account",ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			rs = pst.executeQuery();
			rs.last();
			countId=rs.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{			
			try {
				rs.close();
				pst.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return countId;
	}

	/***************数据库删除***************************************************/
	public int delAccount(int accountId) {// 根据账号删除记录
		String sqlInsert = "delete from account where id=?";
		PreparedStatement pst;
		int count = 0;
		try {
			pst = con.prepareStatement(sqlInsert);
			pst.setInt(1, accountId);
			count = pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	/***************数据库更新******************************************/
	public int modPassword(int accountId,String newPassword) {
		// 把帐号为accountId的记录的密码更新为newPassword
		String sqlInsert = "update account set isLock='n',password=? where id=?";
		PreparedStatement pst;
		int count = 0;
		try {
			pst = con.prepareStatement(sqlInsert);
			pst.setString(1, Md5.getMd5(newPassword));
			pst.setInt(2, accountId);
			count = pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	public int modMoney(int accountId,float newAmount) {
		// 把帐号为accountId的记录的金额更新为newPassword
		String sqlInsert = "update account set amount=? where id=?";
		PreparedStatement pst;
		int count = 0;
		try {
			pst = con.prepareStatement(sqlInsert);
			pst.setFloat(1, newAmount);
			pst.setInt(2, accountId);
			count = pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	
	public int setFrozen_Y(int accountId) {
		// 把帐号为accountId的账号冻结状态更改为y即冻结该用户
		String sqlInsert = "update account set frozen='y' where id=?";
		PreparedStatement pst;
		int count = 0;
		try{
			pst = con.prepareStatement(sqlInsert);
			pst.setInt(1, accountId);
			count = pst.executeUpdate();
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
		
	}
	
	public int setFrozen_N(int accountId) {
		// 把帐号为accountId的账号冻结状态更改为n即解冻该用户
		String sqlInsert = "update account set frozen='n' where id=?";
		PreparedStatement pst;
		int count = 0;
		try{
			pst = con.prepareStatement(sqlInsert);
			pst.setInt(1, accountId);
			count = pst.executeUpdate();
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
		
	}

	/***************数据库查询*******************************************/
	public AccountBean selectById(int accountId) {
		//根据账号id得到账户详细信息
		String sqlInsert = "select * from  account where id=?";
		AccountBean account =null;
		PreparedStatement pst;
		try {
			pst = con.prepareStatement(sqlInsert);

			pst.setInt(1, accountId);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				account= new AccountBean();
				account.setId(rs.getInt(1));
				account.setName(rs.getString(2));
				//account.setCreateDate(new java.util.Date(rs.getDate(3)
				//		.getTime()));//////////////////////////////////////////这里时间的获取需要修改
				//account.setCreateDate( DateToUtil.getUtilDate(rs.getTimestamp(3)) );
				account.setCreateDate(cn.sdut.Util.DateFormat.YMDDate(rs.getTimestamp(3)) );
				account.setAmount(rs.getFloat(4));
				account.setPassword(rs.getString(5));
				account.setPhoneNum(rs.getString(6));
				account.setIDcard(rs.getString(7));
				account.setIsLock(rs.getString(8));
				account.setFrozen(rs.getString(9));
				

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return account;
	}

	public AccountBean selectByIdAndPassword(int accountId, String password) {
		//根据账号id和密码得到账户详细信息
		
		String sqlInsert = "select * from  account where id=? and password=?";
		AccountBean account = null;
		PreparedStatement pst;
		try {
			pst = con.prepareStatement(sqlInsert);
			pst.setInt(1, accountId);
			pst.setString(2, Md5.getMd5(password));
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				account = new AccountBean();
				account.setId(rs.getInt(1));
				account.setName(rs.getString(2));
				account.setCreateDate(cn.sdut.Util.DateFormat.YMDDate(rs.getTimestamp(3)));
				account.setAmount(rs.getFloat(4));
				account.setPassword(rs.getString(5));
				account.setPhoneNum(rs.getString(6));
				account.setIDcard(rs.getString(7));
				account.setIsLock(rs.getString(8));
				account.setFrozen(rs.getString(9));
				
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		if (account.getPassword().equals(Md5.getMd5("666666"))){
//			account = null;
//		}
		return account;
	}

}
