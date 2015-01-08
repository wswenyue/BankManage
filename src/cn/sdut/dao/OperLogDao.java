package cn.sdut.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import cn.sdut.Util.DBConn;
import cn.sdut.Util.Md5;
import cn.sdut.entity.OperLogBean;

public class OperLogDao {
	Connection con=DBConn.getConnnection();
	public int addLog(OperLogBean  log)
	{//向日志表中增加一条记录
		String sqlInsert = "insert into operlog(AccountId,TypeId,Amount,OldPassword,NewPassword) values(?,?,?,?,?)";
		PreparedStatement pst;
		int count = 0;
		try {
			pst = con.prepareStatement(sqlInsert);
			pst.setInt(1,log.getAccoutId());
			pst.setInt(2,log.getTypeId());
			pst.setFloat(3, log.getAmount());
			pst.setString(4, Md5.getMd5(log.getOldPassword()));
			pst.setString(5, Md5.getMd5(log.getNewPassword()));
			count = pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return count;
	}

	public ArrayList<OperLogBean> selectByAccountId(int accountId) {
		//把日志表中关于帐号是accountId的所有记录都查出来，形成一个链表
		ArrayList<OperLogBean> list=new ArrayList<OperLogBean>();
		String sqlInsert = "select * from operlog where accountid=?";
		PreparedStatement pst;
		
		try {
			pst = con.prepareStatement(sqlInsert);
			pst.setInt(1, accountId);
			ResultSet rs = pst.executeQuery();
			while(rs.next())
			{
				OperLogBean log=new OperLogBean();
				log.setId(rs.getInt(1));
				log.setAccoutId(accountId);
				log.setTypeId(rs.getInt(3));
				log.setAmount(rs.getFloat(4));
				//System.out.println(rs.getDate(5).getTime());
				/////////////////////////////////////////////////////////////////////////////
				//log.setOperDate(new java.util.Date(rs.getTime(5).getTime()));
				log.setOperDate(cn.sdut.Util.DateFormat.YMDDate(rs.getTimestamp(5)));
				log.setOldPassword(rs.getString(6));
				log.setNewPassword(rs.getString(7));
				list.add(log);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return list;
	}
}
