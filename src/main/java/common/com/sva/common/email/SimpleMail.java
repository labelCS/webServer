package com.sva.common.email;

import java.util.List;

public class SimpleMail {

	/**
	 * 邮箱用户名
	 */
	private String userName  = GetProperty.getPropertyByName("sva", "userName");
	
	/**
	 * 邮箱密码
	 */
	private String password = GetProperty.getPropertyByName("sva", "password");
	
	/**
	 * 邮箱服务器名称
	 */
	private String smtpHostName = GetProperty.getPropertyByName("sva", "smtpHostName");
	
	/**
	 * 邮件主题
	 */
	private String subject;
	
	/**
	 * 邮件内容
	 */
	private String content;
	
	/**
	 * 发件人
	 */
	private String from = GetProperty.getPropertyByName("sva", "userName");

	/**
	 * 收件人
	 */
	private	List<String> toList;
	
	/**
	 * 抄送
	 */
	private List<String> ccList;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSmtpHostName() {
		return smtpHostName;
	}

	public void setSmtpHostName(String smtpHostName) {
		this.smtpHostName = smtpHostName;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public List<String> getToList() {
		return toList;
	}

	public void setToList(List<String> toList) {
		this.toList = toList;
	}

	public List<String> getCcList() {
		return ccList;
	}

	public void setCcList(List<String> ccList) {
		this.ccList = ccList;
	}

}
