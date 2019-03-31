
package com.Domain;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Aexlord
 * @date 2018年4月24日
 * @text 用以填充邮件内容
 */
public class MyText {
	private static String subject;
	private static String content;
	private static String flag;

	public static void EmailText(String shock_number,double asset_value,String email) throws Exception {
		StockInformation s = new StockInformation();
		String[] str = s.getStockInformation(shock_number).split(",");
		

		if (str.length > 1) {
			String regex = "[\\u4E00-\\u9FA5]+";
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(str[0]);
			String name = null;
			if (m.find()) {
				name = m.group(); // 股票名称
			}
			double current_price = Double.valueOf(str[3]); // 股票现价
			double current_pb = current_price / asset_value; // 当前市净率
			current_pb = new BigDecimal(current_pb).setScale(2,  BigDecimal.ROUND_HALF_UP).doubleValue();
			double a = Double.valueOf(str[9]);
			int sum_volum = (int) (a / 100000000); // 当日交易总额（亿元）
			String current_time = str[30]; // 时间
			
			if(s.getNum(shock_number) == 60) {
				flag = "sh"+shock_number;
			}else {
				flag = "sz"+shock_number;
			}
			if (current_pb > 1.20) {
				subject = "小助手高价格警示！";
				content = "您关注的<b>" + name + "</b>股票，在<b>" + current_time + "</b>这天创下了<b>" + current_price
						+ "</b>元/股的高价，" + "其PB值为<b>" + current_pb + "</b>,截至收市，其成交总额已经达到<b>" + sum_volum
						+ "</b>亿元。小助手提醒您可以<font color=\"red\"><b>适量减持</b></font>以减少过高价格泡沫所带来的风险"
						+ "<img src='http://image.sinajs.cn/newchart/daily/n/"+flag+".gif'/>";
			} else if (current_pb < 0.90) {
				subject = "小助手低价格提醒";
				content = "您关注的<b>" + name + "</b>股票，在<b>" + current_time + "</b>这天达到了<b>" + current_price
						+ "</b>元/股的低价，" + "其PB值为<b>" + current_pb + "</b>,截至收市，其成交总额已经达到<b>" + sum_volum
						+ "</b>亿元。小助手提醒您可以<font color=\"red\"><b>适量增持</b></font>以增加自己的筹码数量获得更高的收益";
			} else if (current_pb < 0.80) {
				subject = "小助手抄底推荐！";
				content = "您关注的<b>" + name + "</b>股票，在<b>" + current_time + "</b>这天达到了<b>" + current_price
						+ "</b>元/股的底价，" + "其PB值为<b>" + current_pb + "</b>,截至收市，其成交总额已经达到<b>" + sum_volum
						+ "</b>亿元。小助手提醒您可以<font color=\"red\"><b>大幅增持</b></font>以增加自己的筹码数量获得更高的收益";
			} else if (sum_volum > 50) {
				subject = "小助手高成交量提醒";
				content = "您关注的<b>" + name + "</b>股票，在<b>" + current_time + "</b>这天达到了<b>" + current_price
						+ "</b>元/股的价格，" + "其PB值为<b>" + current_pb + "</b>,截至收市，其成交总额已经达到<b>" + sum_volum
						+ "</b>亿元。小助手提醒您可以<font color=\"red\"><b>关注</b></font>最近的走势，可能会有新的行情出现";
			}
			// System.out.println(subject);
			// System.out.println(content);
			if (subject != null && content != null) {
				// System.out.println("发送邮件");
				// System.out.println(current_pb);
				SendingEmail se = new SendingEmail();
				se.sendingEmail(subject, content,email);
			} else {				
				//创建日志输出流
				File file = new File( File.separator+"home"  + File.separator + "LOG" + File.separator +current_time+".txt");
				if (!file.getParentFile().exists()) {
					file.getParentFile().mkdirs();
				}
				// 准备输出流
				FileWriter out = new FileWriter(file);
				out.write(current_time+"  "+name+"  没有监测到异常，是否报告主人：否");
				out.close();
			}
		}
	}
}
