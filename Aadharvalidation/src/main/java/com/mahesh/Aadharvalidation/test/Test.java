package com.mahesh.Aadharvalidation.test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

	public static void main(String[] args) {
//		Calendar date = Calendar.getInstance();
//		long millisecondsDate = date.getTimeInMillis();
//		Long data=Long.parseLong("f125e25b-70eb-4ac5-9762-4e79630f0991");

		System.out.println("test");
		String data = "() i" + "XXXXXXXX" + "XXXXXXXXXXX" + "STH A/ Year of Birth : XXXX  [ElEipiemifE"
				+ "T%Y / MALE \"*.:%a i\r" + "A S}" + "OFETE" + "0000 1111 2222" + "L ST - 3TH STeH] B SHUBR";

//		String strPattern = "^[2-9][0-9]{11}$";
		String strPattern="\\d{4}\\s\\d{4}\\s\\d{4}";
		Pattern pattern = Pattern.compile(strPattern);
		Matcher matcher = pattern.matcher(data);
//		String val = "";
		System.out.println(matcher);

		if (matcher.find()) {
			System.out.println(matcher.group(0));

		}
//		System.out.println("" + data);
	}
}
