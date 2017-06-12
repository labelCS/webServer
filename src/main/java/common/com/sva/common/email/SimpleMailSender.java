package com.sva.common.email;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

public class SimpleMailSender {
	
	private static Logger Log = Logger.getLogger(SimpleMailSender.class);

	/**
	 * 邮箱session
	 */
	private transient Session session;

	/**
	 * 初始化邮件发送器
	 * 
	 * @param smtpHostName
	 *            SMTP邮件服务器地址
	 * @param username
	 *            发送邮件的用户名(地址)
	 * @param password
	 *            发送邮件的密码
	 */
	public SimpleMailSender(SimpleMail simpleMail) {
		init(simpleMail.getUserName(), simpleMail.getPassword(), simpleMail.getSmtpHostName());
	}

	/**
	 * 初始化
	 * 
	 * @param username
	 *            发送邮件的用户名(地址)
	 * @param password
	 *            密码
	 * @param smtpHostName
	 *            SMTP主机地址
	 */
	private void init(String username, String password, String smtpHostName) {
		// 初始化props
		Properties props = System.getProperties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.host", smtpHostName);
		// 验证
		MyAuthenticator authenticator = new MyAuthenticator(username, password);
		// 创建session
		session = Session.getInstance(props, authenticator);
	}

	/**
	 * 群发邮件
	 * 
	 * @param recipients
	 *            收件人们
	 * @param subject
	 *            主题
	 * @param content
	 *            内容
	 * @throws AddressException
	 * @throws MessagingException
	 */
	public void send(List<String> toList, List<String> ccList, String subject, String content, String from)
			throws AddressException, MessagingException {
		// 创建mime类型邮件
		final MimeMessage message = new MimeMessage(session);
		InternetAddress address = new InternetAddress(from);
		try {
			address.setPersonal(MimeUtility.encodeText("SVADEMOAPP<" + from + ">"));
			// 设置发信人
			message.setFrom(address);
			// 设置收件人们
			message.setRecipients(RecipientType.TO, getAddress(toList));
			// 设置抄送人
			if(ccList != null && !ccList.isEmpty()){
				message.setRecipients(RecipientType.CC, getAddress(ccList));
			}
		} catch (UnsupportedEncodingException e) {
			Log.error("send error",e);
		} catch (Exception e) {
			Log.error("send error",e);
		}
		// 设置主题
		message.setSubject(subject, "utf-8");
		// 设置邮件内容
		message.setContent(content, "text/html;charset=utf-8");
		// 发送
		Transport.send(message);
	}
	
	private InternetAddress[] getAddress(List<String> list) throws AddressException{
		final int num = list.size();
		InternetAddress[] addresses = new InternetAddress[num];
		for (int i = 0; i < num; i++) {
			addresses[i] = new InternetAddress(list.get(i));
		}
		return addresses;
	}

	/**
	 * 群发邮件
	 * 
	 * @param mail
	 *            邮件对象
	 * @throws AddressException
	 * @throws MessagingException
	 */
	public void send(SimpleMail simpleMail) throws AddressException, MessagingException {
		send(simpleMail.getToList(),simpleMail.getCcList(), simpleMail.getSubject(), simpleMail.getContent(), simpleMail.getFrom());
	}

	public static void main(String[] args) {
		String os = System.getProperty("os.name");
		
		System.out.println(os);

		SimpleMail simpleMail = new SimpleMail();
		/*simpleMail.setSmtpHostName("smtp.163.com");
		simpleMail.setUserName("fanbinbin--001@163.com");
		simpleMail.setPassword("Sva123456");
		simpleMail.setFrom("fanbinbin--001@163.com");*/
		simpleMail.setSubject("SVA异常告警");
		simpleMail.setContent("您维护的SVA配置（10.10.10.10）数据出现异常，请及时处理！");
		simpleMail.setToList(Arrays.asList("kjzxgaolu@126.com"));
		// simpleMail.setCcList(Arrays.asList("1072455155@qq.com"));
		SimpleMailSender simpleMailSender = new SimpleMailSender(simpleMail);
		try {
			simpleMailSender.send(simpleMail);
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
