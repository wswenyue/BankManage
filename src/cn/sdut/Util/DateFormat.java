package cn.sdut.Util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormat {
	public static String YMDDate(Date date) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
		return sdf.format(date);
	}
}
