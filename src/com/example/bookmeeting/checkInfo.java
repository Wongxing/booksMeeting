package com.example.bookmeeting;

public class checkInfo {
	String userName;
	int days;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getDays() {
		return days;
	}
	public void setDays(int days) {
		this.days = days;
	}
	
	public checkInfo(String u, int d) {
		userName = u;
		days = d;
	}
	
	public checkInfo() {
		userName = null;
		days = 0;
	}
}
