package com.example.bookmeeting;

public class stateInfo {
	String username;
	String state;
	String date;
	String time;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public stateInfo(String s1, String s2, String s3, String s4) {
		// TODO Auto-generated constructor stub
		username = s1;
		state = s2;
		date= s3;
		time = s4;
	}
}
