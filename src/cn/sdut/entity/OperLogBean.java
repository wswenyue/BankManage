package cn.sdut.entity;
public class OperLogBean {
private int id;
private int accountId;
private int typeId;
private float amount;
private String operDate;
private String oldPassword;
private String newPassword;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public int getAccoutId() {
	return accountId;
}
public void setAccoutId(int accountId) {
	this.accountId = accountId;
}
public int getTypeId() {
	return typeId;
}
public void setTypeId(int typeId) {
	this.typeId = typeId;
}
public float getAmount() {
	return amount;
}
public void setAmount(float amount) {
	this.amount = amount;
}

public String getOldPassword() {
	return oldPassword;
}
public void setOldPassword(String oldPassword) {
	this.oldPassword = oldPassword;
}
public String getNewPassword() {
	return newPassword;
}
public void setNewPassword(String newPassword) {
	this.newPassword = newPassword;
}
public String getOperDate() {
	return operDate;
}
public void setOperDate(String operDate) {
	this.operDate = operDate;
}
}
