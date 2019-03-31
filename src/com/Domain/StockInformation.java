package com.Domain;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * @author Aexlord
 * @date   2018年4月24日
 * @text	获取网页上的股票信息并进行数据清洗
 */
public class StockInformation {
	private static String infor;
	private static int num;

	public static int getNum(String stock_number) {
		num = (Integer.valueOf(stock_number))/10000;
		return num;
	}

	public String getStockInformation(String stock_number) throws Exception {
		getNum(stock_number);
		if(num == 60) {
			String target_1 = "http://hq.sinajs.cn/list=sh"+stock_number;
			infor = getText(target_1);
		}else {
			String target_2 = "http://hq.sinajs.cn/list=sz"+stock_number;
			infor = getText(target_2);
		}

		return infor;
	}

	private String getText(String target) throws Exception {
		//1.封装url对象
		URL url = new URL(target);
		//2.打开链接
		URLConnection conn = url.openConnection();
		//3.获取数据流
		InputStream in = conn.getInputStream();
		//4.读取操作
		BufferedReader bufer = new BufferedReader(new InputStreamReader(in,"gb2312"));
		
		return bufer.readLine();
	}

}
