package com.Domain;


import java.util.Date;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Aexlord
 * @date 2018年4月24日
 * @text 控制时间间隔，每24个小时确认一次数据
 */
public class Running {
	public static long timer = 1000*60*60*24l;
	
public static void main(String[] args) {
	Scanner sc = new Scanner(System.in);
	System.out.println("please inpute stock number : ");
	String stock_number = sc.nextLine();
	System.out.println("please inpute asset value : ");
	double asset_value = Double.valueOf(sc.nextLine());
	System.out.println("please inpute your email : ");
	String email = sc.nextLine();
	System.out.println("please inpute the time space : ");
	int date = sc.nextInt();
	sc.close();
	
	ControlMyText(stock_number,asset_value,email,date); //Running
	}

	public static void ControlMyText(String stock_number,double asset_value,String email,int date) {
		timer *= date;//每天的毫秒*天数
		Timer t = new Timer();
				t.schedule(new TimerTask() {

					@Override
					public void run() {
						try {
							MyText.EmailText(stock_number, asset_value, email);
						} catch (Exception e) {
							e.printStackTrace();
						}

					}
				}, new Date(System.currentTimeMillis()),timer);
	}

}
