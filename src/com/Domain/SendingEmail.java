package com.Domain;

import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


/**
 * @author Aexlord
 * @date 2018年4月23日
 * @text 邮件发送系统
 */

public class SendingEmail {
	// 发件人的账户和密码
	private static String myEmailAccount;
	private static String myEmailPassword;
	// 发件人的SMTP地址
	private static String myEmailSMTPHost;


	//获取配置信息
	static {
		Properties pro = new Properties();
		try {
			InputStream in = SendingEmail.class.getClassLoader()
							.getResourceAsStream("account.properties");
			pro.load(in);
		} catch (Exception e) {
			System.out.println("配置文件读取错误");
		}
		myEmailAccount = pro.getProperty("myEmailAccount");
		myEmailPassword = pro.getProperty("myEmailPassword");
		myEmailSMTPHost = pro.getProperty("myEmailSMTPHost");
	}
	
	public  void sendingEmail(String subject,String content,String receiveMail) throws Exception {
		// 1.创建参数配置，用于连接邮件服务器
		Properties props = new Properties(); // 参数配置
		props.setProperty("mail.transport.protocol", "smtp"); // 使用Javamaile的协议规范
		props.setProperty("mail.smtp.host", myEmailSMTPHost); // 发件人的smpt服务
		props.setProperty("mail.smtp.auth", "true"); // 需要请求认证
		
		final String smtpPort = "465";	//由于阿里云禁止了25端口，所以需要修改为465端口，并同时要改为ssl加密传输
        props.setProperty("mail.smtp.port", smtpPort);
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.socketFactory.port", smtpPort);

		Session session = Session.getInstance(props);
//		session.setDebug(true);

		// 3.创建一封邮件
		MimeMessage message = creatMimeMessage(session, myEmailAccount, receiveMail,subject,content);

		// 4.根据Session获取邮件传输对象
		Transport transport = session.getTransport();

		// 5.使用邮箱账号和密码链接邮件服务器
		transport.connect(myEmailAccount, myEmailPassword);
		

		// 6.发送邮件
		transport.sendMessage(message, message.getAllRecipients());

		// 7.关闭连接
		transport.close();
	}

	/*
	 * 创建邮件
	 */
	private static MimeMessage creatMimeMessage(Session session, String sendMail, String receiveMail
												,String subject,String content)
			throws Exception {
		// 创建一封邮件
		MimeMessage message = new MimeMessage(session);
		// From 发件人
		message.setFrom(new InternetAddress(sendMail, "我的小助手", "UTF-8"));
		// TO 收件人
		message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, "主人", "UTF-8"));
		// 邮件主题
		message.setSubject(subject, "UTF-8");
		// 邮件正文
		message.setContent(content, "text/html;charset=UTF-8");
		// 设置发送时间
		message.setSentDate(new Date());
		// 保存设置
		message.saveChanges();
		return message;
	}

}
