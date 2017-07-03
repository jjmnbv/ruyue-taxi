package com.szyciov.util;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.szyciov.util.MailService;

public class SendMailService {
	
	/** 主题 */
	private String subject;
	/** 正文内容 */
	private String text;
	/** 发送人 */
	private String[] to;
	
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String[] getTo() {
		return to;
	}

	public void setTo(String[] to) {
		this.to = to;
	}
	private static SendMailService instance = null;

	public SendMailService() {

	}

	public static SendMailService getInstance() {
		if (instance == null) {
			instance = new SendMailService();
		}
		return instance;
	}
	
	
	public void send() {
		try {
//			String to[] = { "2726988402@qq.com", "670089245@qq.com" };
			Properties p = new Properties(); // Properties p =
												// System.getProperties();
			p.put("mail.smtp.auth", "true");
			p.put("mail.transport.protocol", "smtp");
			p.put("mail.smtp.host", "smtp.163.com");
			p.put("mail.smtp.port", "25");
			// 建立会话
			Session session = Session.getInstance(p);
			Message msg = new MimeMessage(session); // 建立信息

			msg.setFrom(new InternetAddress("szyciov@163.com")); // 发件人

			String toList = getMailList(to);
			InternetAddress[] iaToList = new InternetAddress().parse(toList);

			msg.setRecipients(Message.RecipientType.TO, iaToList); // 收件人

			msg.setSentDate(new Date()); // 发送日期
//			msg.setSubject("javamail测试邮件"); // 主题
//			msg.setText("注意，这是测试程序发的，请不要回复！"); // 内容
			msg.setSubject(subject); // 主题
			msg.setText(text); // 内容
			// 邮件服务器进行验证
			Transport tran = session.getTransport("smtp");
//			tran.connect("smtp.163.com", "tiwson", "9041160");
			tran.connect("smtp.163.com", "szyciov@163.com", "a123456");
			tran.sendMessage(msg, msg.getAllRecipients()); // 发送
			System.out.println("邮件发送成功");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String getMailList(String[] mailArray) {

		StringBuffer toList = new StringBuffer();
		int length = mailArray.length;
		if (mailArray != null && length < 2) {
			toList.append(mailArray[0]);
		} else {
			for (int i = 0; i < length; i++) {
				toList.append(mailArray[i]);
				if (i != (length - 1)) {
					toList.append(",");
				}

			}
		}
		return toList.toString();

	}
	public static void main(String[] args){
		SendMailService s = new SendMailService();
		String to[] = { "2726988402@qq.com", "670089245@qq.com" };
		s.setSubject("邮件测试");
		s.setText("一封测试邮件！");
		s.setTo(to);
		s.send();
	}

}
