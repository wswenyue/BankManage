package cn.sdut.entity;

public class AccountBean {
private int id;
private String name;
private String createDate;
private float amount;
private String phoneNum;//电话号码
private String IDcard;//身份证号
private String password;
private String isLock;//锁定账户，如果初始密码未修改则锁定，直到修改后解锁，默认新建用户为锁定状态，在此状态下，
					//只有修改账户密码可以使用，其余功能均不可以。
private String frozen;//账户冻结，默认为n，只有当用户账户出现异常，比如输入密码错误超过三次，账户就会被冻结，只有管理员可以解锁
					//管理员也可对某个账户进行冻结与解冻。
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}

public float getAmount() {
	return amount;
}
public void setAmount(float amount) {
	this.amount = amount;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public String getPhoneNum() {
	return phoneNum;
}
public void setPhoneNum(String phoneNum) {
	this.phoneNum = phoneNum;
}
public String getIDcard() {
	return IDcard;
}
public void setIDcard(String iDcard) {
	this.IDcard = iDcard;
}
public String getIsLock() {
	return isLock;
}
public void setIsLock(String isLock) {
	this.isLock = isLock;
}
public String getFrozen() {
	return frozen;
}
public void setFrozen(String frozen) {
	this.frozen = frozen;
}
public String getCreateDate() {
	return createDate;
}
public void setCreateDate(String createDate) {
	this.createDate = createDate;
}



}
