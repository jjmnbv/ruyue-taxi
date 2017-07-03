package com.szyciov.util;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * @ClassName MailService 
 * @author Efy Shu
 * @Description 
 * 这是一个邮件发送系统 默认使用QQ邮箱服务器 
 * 设置其他邮箱服务器需要同时指定端口号 
 * 目前只支持smtp协议
 * @date 2016年7月21日 下午1:44:33
 */
public class MailService {

	/** 发信人 */
	private String from;
	/** 收信人 */
	private String to;
	/** 主题 */
	private String subject;
	/** 正文 */
	private String body;
	/** 密码 */
	private String passwd;
	/** 邮箱服务器 */
	private String host;
	/** 邮箱服务器端口 */
	private String port;

	public MailService() {

	}

	public MailService(String from, String to, String passwd) {
		super();
		this.from = from;
		this.to = to;
		this.passwd = passwd;
	}

	/**
	 * 发送邮件.
	 * 
	 * @return boolean - 发送结果
	 * @throws Exception 
	 */
	public boolean sendMail() throws Exception {
		if (getBody() == null || getTo() == null || getFrom() == null || getSubject() == null) {
			return false;
		}
		try {
			Properties props = new Properties();
			props.put("username", getFrom()); // 用户名
			props.put("password", getPasswd()); // 密码
			props.put("mail.debug", "false");
			//SSL
//			props.put("mail.smtp.ssl.enable", "true"); // QQ邮箱必须使用SSL链接
//			props.put("mail.smtp.starttls.enable", "true"); // 文本链接转SSL
//			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); // 指定SSLSocket
			
			props.put("mail.transport.protocol", "smtp"); // 设置邮箱服务器协议
			props.put("mail.smtp.auth", "true"); // 设置需要验证用户名,密码(否则服务器不允许通过)
			props.put("mail.smtp.host", "smtp.163.com"); // 设置邮箱服务器地址
			props.put("mail.smtp.port", "25"); // 设置邮箱服务器端口

			if (getHost() != null || getPort() != null) {
				props.put("mail.smtp.host", getHost()); // 重新设置邮箱服务器地址
				props.put("mail.smtp.port", getPort()); // 重新设置邮箱服务器端口
			}
			Session mailSession = Session.getInstance(props);
			Message msg = new MimeMessage(mailSession);

			msg.setFrom(new InternetAddress(getFrom()));
			msg.addRecipients(Message.RecipientType.TO, InternetAddress.parse(getTo()));
			msg.setSentDate(new Date());
			msg.setSubject(getSubject());

			msg.setText(getBody());
			msg.saveChanges();
			System.out.println("正在连接邮件服务器。。。。");
			Transport transport = mailSession.getTransport("smtp");
			transport.connect(
					props.getProperty("mail.smtp.host"), 
					props.getProperty("username"),
					props.getProperty("password")
			);
			System.out.println("正在发送邮件。。。。");
			transport.sendMessage(msg, msg.getAllRecipients());
			transport.close();
			System.out.println("发送完毕。。。。");

		} catch (Exception e) {
			throw new Exception(e);
		}
		return true;
	}

	/**
	 * @return Returns the body.
	 */
	public String getBody() {
		return body;
	}

	/**
	 * @param body
	 *            设置正文(暂只支持文本形式)
	 */
	public void setBody(String body) {
		this.body = body;
	}

	/**
	 * @return Returns the from.
	 */
	public String getFrom() {
		return from;
	}

	/**
	 * @param from
	 *            设置发件人
	 */
	public void setFrom(String from) {
		this.from = from;
	}

	/**
	 * @return Returns the subject.
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject
	 *            设置邮件主题
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * @return Returns the to.
	 */
	public String getTo() {
		return to;
	}

	/**
	 * @param to
	 *            设置收件人
	 */
	public void setTo(String to) {
		this.to = to;
	}

	/**
	 * @return Returns the passwd.
	 */
	public String getPasswd() {
		return passwd;
	}

	/**
	 * @param passwd
	 *            The passwd to set.
	 */
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public static void main(String[] args) throws Exception {
		// 声明邮件实例
		MailService sender = new MailService();
		// 发件人|用户名
		sender.setFrom("szyciov@163.com");
		// 密码
		sender.setPasswd("a123456");
		// 收件人
		sender.setTo("2726988402@qq.com");
		// 主题
		sender.setSubject("测试邮件");
		// 正文
		sender.setBody("这是一封测试邮件!");
		// 发送邮件
		sender.sendMail();
		
		//发送多人
//		sender.setBody("2222222222");
//		sender.setTo("liangx8rx@qq.com");
//		sender.sendMail();
	}
}