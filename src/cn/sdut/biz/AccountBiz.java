package cn.sdut.biz;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import cn.sdut.Util.DateFormat;
import cn.sdut.Util.OperTypeConvert;
import cn.sdut.Util.SecurityCode;
import cn.sdut.dao.AccountDao;
import cn.sdut.dao.OperLogDao;
import cn.sdut.entity.AccountBean;
import cn.sdut.entity.OperLogBean;

/**
 * 银行管理业务类：
 * 含开户、存款、取款、转账、查询、修改密码、销户等方法
 */
public class AccountBiz {
	AccountDao accountDao = new AccountDao();// 数据库表 “账户”的操作对象
	OperLogDao operlogDao = new OperLogDao();// 数据库表 “操作日志” 的操作对象

	/*
	 * 开户 
	 * （1）创建一帐户account，设置其姓名，创建日期（当前时间），金额，初始密码（666666）； 
	 * （2）向Account表中增加记录；
	 * （3）向操作日志表中增加一条记录； 
	 * （4）返回字符串对象（开户后的账号，初始密码（提示信息尽快修改长度为6位），开户金额，开户日期）。
	 */
	public String createAccount(String name, float initAmount, String phoneNum, String IDcard) {
		AccountBean account = new AccountBean();
		account.setName(name);
		account.setAmount(initAmount);

		account.setPhoneNum(phoneNum);
		account.setIDcard(IDcard);
		
		
		//account.setCreateDate(date);
		String initPassword = "666666";
		//initPassword = Md5.getMd5(initPassword);/////////////////////////////
		account.setPassword(initPassword);
		int accountId = accountDao.addAcount(account);

		OperLogBean log = new OperLogBean();
		log.setAccoutId(accountId);
		log.setTypeId(1);
		log.setAmount(initAmount);
		java.util.Date date = new java.util.Date();
		//log.setOperDate(date);
		log.setOldPassword(initPassword);
		log.setNewPassword(initPassword);

		String strDate = DateFormat.YMDDate(date);
		@SuppressWarnings("unused")
		int count2 = operlogDao.addLog(log);
		String strReturn = "开户后的账号:" + accountId + "\n初始密码为：" + initPassword
				+ ",请尽快修改长度为6位的密码\n" + "开户金额:" + initAmount + "\n开户日期："
				+ strDate;
		return strReturn;
	}

	// 存款
	/*
	 * （1）在账户表中根据输入的账号找到账户对象 
	 * （2）取出该对象的金额并加上money的值 
	 * （3）更新帐户记录 
	 * （4）增加一条操作日志记录
	 * （5）返回字符串对象（存储的金额，账号中现有金额，操作时间）
	 */

	public String saveMoney(int accountId, float money) {
		AccountBean account = accountDao.selectById(accountId);
		String strReturn = null;
		if (account != null) {
			if(account.getIsLock().equals("n")){
				float initMoney = account.getAmount();
				float newMoney=initMoney + money;
				accountDao.modMoney(accountId,newMoney);
	
				OperLogBean log = new OperLogBean();
				log.setAccoutId(accountId);
				log.setAmount(money);
				log.setNewPassword(account.getPassword());
				log.setOldPassword(account.getPassword());
				Date date = new java.util.Date();
				//log.setOperDate(date);
				log.setTypeId(2);
	
				operlogDao.addLog(log);
				strReturn = "储户姓名：" + account.getName() + "\n原有金额:" + initMoney
						+ "\n存储金额:" + money + "\n" + "账号中现有金额:"
						+ newMoney+ "\n操作时间:"
						+ DateFormat.YMDDate(date);
			}else{
				
				strReturn = "对不起，您的账号没有激活，请修改密码激活！";
			}
			
		} else {
			strReturn = "对不起，您输入的账号不正确，查无此人！";
		}
		return strReturn;
	}

	// 取款
	/*
	 * （1）在账户表中根据输入的账号和密码找到账户对象 
	 * （2）取出该对象的金额并减去money的值 
	 * （3）更新帐户记录 
	 * （4）增加一条操作日志记录
	 * （5）返回字符串对象（提取的金额，账号中现有的金额，操作时间）。
	 */

	public String getMoney(int accountId, String password, float money) {
		AccountBean account = accountDao.selectByIdAndPassword(accountId,
				password);
		String strReturn = null;
		
		float newMoney;
		
		if (account != null) {
			float initMoney = account.getAmount();
			
			if(account.getIsLock().equals("n")){
			
					if (money<=initMoney){
						newMoney=initMoney - money;
						accountDao.modMoney(accountId, newMoney);
			
						OperLogBean log = new OperLogBean();
						log.setAccoutId(accountId);
						log.setAmount(money);
						log.setNewPassword(account.getPassword());
						log.setOldPassword(account.getPassword());
						Date date = new java.util.Date();
						//log.setOperDate(date);
						log.setTypeId(3);
			
						operlogDao.addLog(log);
						strReturn = "储户姓名：" + account.getName() + "\n原有金额:" + initMoney
								+ "\n取款金额:" + money + "\n" + "账号中现有金额:"
								+newMoney + "\n操作时间:"
								+ DateFormat.YMDDate(date);
						}else{
							strReturn = "对不起，您输入的金额有误，请重新输入！";			
						}
					
			}else{
				strReturn = "对不起，您的账号没有激活，请修改密码激活！";	
			}
			
		} else {
			strReturn = "对不起，您输入的账号和密码不正确，查无此人！";
		}
		return strReturn;
	}

	// 转帐
	/*
	 * （1）在账户表中根据输入的账号和密码找到转出账户对象 
	 * （2）根据输入的账号找到转入账户对象 
	 * （3）转出账号中的金额减去转帐金额
	 * （4）转入账号中的金额加上转帐金额 
	 * （5） 增加2条操作日志记录（分别记录转出和转入操作）
	 * （6）返回字符串对象（转帐的金额，转出账号现有的金额，本次操作的时间）
	 */

	public String turnAccount(int outAccountId, String password,
			int inAccountId, float money) {
		AccountBean outAccount = accountDao.selectByIdAndPassword(outAccountId,
				password);
		AccountBean inAccount = accountDao.selectById(inAccountId);
		String strReturn = null;
		
		if (outAccount != null && inAccount != null){
				float outInitMoney = outAccount.getAmount();
				float inInitMoney = inAccount.getAmount();//修正错误
				
				if(outAccount.getIsLock().equals("n")){
					if (money <= outInitMoney){
							outAccount.setAmount(outInitMoney - money);
							inAccount.setAmount(inInitMoney + money);
							accountDao.modMoney(outAccountId, outAccount.getAmount());
							accountDao.modMoney(inAccountId, inAccount.getAmount());
	
							OperLogBean log = new OperLogBean();
							log.setAccoutId(outAccountId);
							log.setAmount(money * (-1));
							log.setNewPassword(outAccount.getPassword());
							log.setOldPassword(outAccount.getPassword());
							Date date = new java.util.Date();
							//log.setOperDate(date);//不用写时间，数据库自动添加
							log.setTypeId(4);
	
							operlogDao.addLog(log);
	
							log.setAccoutId(inAccountId);
							log.setAmount(money);
							log.setNewPassword(inAccount.getPassword());
							log.setOldPassword(inAccount.getPassword());
	
							log.setTypeId(4);
							operlogDao.addLog(log);
	
						    strReturn = "转出账号:" + outAccountId + " 账户姓名:"
									+ outAccount.getName() + "\n原有金额：" + outInitMoney + " 转出的金额:"
									+ money + " 现有金额:" + outAccount.getAmount() + "\n转入账号:"
									+ inAccountId + " 账户姓名:" + inAccount.getName() + "\n原有金额："
									+ inInitMoney + " 转入的金额:" + money + " 现有金额:"
									+ inAccount.getAmount() + "\n本次操作的时间:"
									+ DateFormat.YMDDate(date);
					}else {
						strReturn = "对不起，您输入的金额有误，请重新输入！";	
					}
				}else{
					strReturn = "对不起，您的账号没有激活，请修改密码激活！";
				}
				
		} else {
			strReturn = "对不起，您输入的账号和密码不正确，查无此人！";
		}
		return strReturn;
	}


	// 查询
	/*
	 * （1）在账户表中根据输入的账号和密码查询账户对象，能查到的话，继续；否则，返回null 
	 * （2）根据账号查询操作日志表，读取链表信息
	 * （3）返回字符串对象（账户信息和日志信息——账号，姓名，开户日期，现有金额，{操作类型，操作金额，操作时间，旧密码，新密码}）
	 */
	public String selectAccount(int accountId, String password) {
		AccountBean account = accountDao.selectByIdAndPassword(accountId,
				password);
		if (account != null) {
			if(account.getIsLock().equals("n")){
			
					ArrayList<OperLogBean> list = operlogDao
							.selectByAccountId(accountId);
					StringBuffer sbuf = new StringBuffer();
					sbuf.append("账号:" + accountId + "   姓名:" + account.getName() + "\n");
					sbuf.append("开户日期：" + account.getCreateDate()
							+ "\n");
					sbuf.append(" 现有金额：" + account.getAmount() + "\n操作记录：\n");
					sbuf.append("操作类型  金额\t\t日期\t        备注\n");
					sbuf.append("-----------------------------------------------------------------------------------------------\n");
					Iterator<OperLogBean> it = list.iterator();
					while (it.hasNext()) {
						OperLogBean log = it.next();
						///////////////////
						sbuf.append(OperTypeConvert.typeIdToName(log.getTypeId())
								+ "        ");
						sbuf.append(log.getAmount() + "   ");
						sbuf.append(log.getOperDate() + "  ");
						//sbuf.append(log.getOldPassword() + "  ");
						//sbuf.append(log.getNewPassword() + "\n");
						if(!log.getNewPassword().equals(log.getOldPassword())){
							sbuf.append("   修改过密码\n");
						}else{
							sbuf.append("   ************\n");
						}
					}
					return sbuf.toString();
			}else{
				return "对不起，您的账号没有激活，请修改密码激活！";
			}
					
		} else {
			return "用户名和密码不正确";
		}
	}

	// 修改密码
	/*
	 * （1）在账户表中根据输入的账号和密码查询账户对象，能查到的话，继续；否则，返回false 
	 * （2）更新账户对象的密码
	 * （3）在操作日志表中增加一条记录 
	 * （4）返回字符串对象，显示操作结果true或false
	 */
	public String updatePassword(int accountId, String oldPassword,
			String newPassword) {
		AccountBean account = accountDao.selectByIdAndPassword(accountId,
				oldPassword);
		String strReturn = null;
		if (account != null) {
			int count = accountDao.modPassword(accountId, newPassword);
			if (count > 0) {
				OperLogBean log = new OperLogBean();
				log.setAccoutId(accountId);
				log.setTypeId(6);
				log.setOldPassword(oldPassword);
				log.setNewPassword(newPassword);
				//log.setOperDate(new java.util.Date());///////////////不用写时间，数据库自动写入///////////////////////////
				log.setAmount(0);
				operlogDao.addLog(log);
				strReturn = "密码修改成功！";
			} else {
				strReturn = "密码修改不成功！";
			}
		} else {
			strReturn = "用户名或密码不正确！";
		}
		return strReturn;
	}

	// 销户
	/*
	 * （1）在账户表中根据输入的账号和密码查询账户对象，能查到的话，继续；否则，返回null 
	 * （2）将账户的钱全部取出； 
	 * （3）将该账户从表中删除
	 * （4）操作日志表增加2条记录 
	 * （5）返回操作的结果
	 */

	public String destroyAccount(int accountId, String password) {
		AccountBean account = accountDao.selectByIdAndPassword(accountId,
				password);
		String strReturn = null;
		if (account != null) {
			float amount = account.getAmount(); // 得到账户中的金额
			accountDao.delAccount(accountId); // 删除账户
			OperLogBean log = new OperLogBean();
			log.setAccoutId(accountId);
			log.setTypeId(3);
			log.setAmount(amount);
			log.setNewPassword(account.getPassword());
			log.setOldPassword(account.getPassword());
			//log.setOperDate(new java.util.Date());///////////不用写时间，数据库自动添加////////////////////////////
			operlogDao.addLog(log);

			log.setTypeId(7);
			log.setAmount(0);
			operlogDao.addLog(log);

			strReturn = "帐号：" + accountId + "取出现金：" + amount + "，销户成功！";
		} else {
			strReturn = "用户名和密码不正确！";
		}
		return strReturn;
	}
	
	public String getScodeById(int accountId){
		
		AccountBean account = accountDao.selectById(accountId);
		String strReturn = null;
		if (account != null) {
			String phone = account.getPhoneNum();
			try {
				strReturn = SecurityCode.sendGet(phone, "银行提示您，您的验证码是：");
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			
		}else{
			strReturn = "用户不存在！";
		}
		
		return strReturn;
	}
	
}
