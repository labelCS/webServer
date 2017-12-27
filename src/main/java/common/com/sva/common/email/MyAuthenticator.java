package com.sva.common.email;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * �����������¼��֤
 * 
 * @author MZULE
 * 
 */
public class MyAuthenticator extends Authenticator {

	/**
	 * �û�������¼���䣩
	 */
	private String username;
	/**
	 * ����
	 */
	private String password;

	/**
	 * ��ʼ�����������
	 * 
	 * @param username
	 *            ����
	 * @param password
	 *            ����
	 */
	public MyAuthenticator(String username, String password) {
		this.username = username;
		this.password = password;
	}

	@Override
	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(username, password);
	}

	public void setPassword(String password) {
		this.password = password;
	}

	String getPassword() {
		return password;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	String getUsername() {
		return username;
	}

}
