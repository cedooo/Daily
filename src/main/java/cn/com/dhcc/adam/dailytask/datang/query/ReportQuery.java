package cn.com.dhcc.adam.dailytask.datang.query;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.com.dhcc.adam.dailytask.datang.DailyReportBuilder;
import cn.com.dhcc.adam.dailytask.datang.IReportBuilder;
import cn.com.dhcc.adam.dailytask.datang.MonthlyReportBuilder;
import cn.com.dhcc.adam.dailytask.datang.WeeklyReportBuilder;

public class ReportQuery {
	private Map<String, Object> resultMap= null;
	private SimpleDateFormat dailyDateTimeFormat = new SimpleDateFormat("MM月dd日");
	private SimpleDateFormat weeklyDateTimeFormat = new SimpleDateFormat("MM月dd日(E)");
	private SimpleDateFormat monthlyDateTimeFormat = new SimpleDateFormat("MM月dd日");
	public ReportQuery(Class<? extends IReportBuilder> irt){
		resultMap = new HashMap<String, Object>();
		if(irt == DailyReportBuilder.class){
			daily();
		}else if(irt  == WeeklyReportBuilder.class){
			weekly();
		}else if(irt  == MonthlyReportBuilder.class){
			monthly();
		}
	}
	/**
	 * 查询结果
	 * @param attribute 属性名称
	 * @return 结果
	 */
	public String query(String attribute){
		return resultMap.get(attribute).toString();
	}
	private void daily(){
		Calendar preDay =  Calendar.getInstance();
		preDay.setTime(new Date());
		preDay.add(Calendar.DAY_OF_YEAR, -1);
		resultMap.put("dateTime", dailyDateTimeFormat.format(preDay.getTime()));
	}
	private void weekly(){
		Calendar preWeek =  Calendar.getInstance();
		preWeek.setTime(new Date());
		preWeek.add(Calendar.WEEK_OF_MONTH, -1);
		preWeek.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		String preWeekFirstDay = weeklyDateTimeFormat.format(preWeek.getTime());
		preWeek.add(Calendar.DAY_OF_YEAR, preWeek.getActualMaximum(Calendar.DAY_OF_WEEK)-1);
		String preWeekLastDay = weeklyDateTimeFormat.format(preWeek.getTime());
		resultMap.put("dateTime",  preWeekFirstDay + "-" + preWeekLastDay);
	}
	private void monthly(){
		Calendar preMonth =  Calendar.getInstance();
		preMonth.setTime(new Date());
		preMonth.add(Calendar.MONTH, -1);
		preMonth.set(Calendar.DAY_OF_MONTH, 1);
		String preMonthFirstDay = monthlyDateTimeFormat.format(preMonth.getTime());
		preMonth.add(Calendar.DAY_OF_MONTH, preMonth.getActualMaximum(Calendar.DAY_OF_MONTH)-1);
		String preMonthLastDay = monthlyDateTimeFormat.format(preMonth.getTime());
		resultMap.put("dateTime",  preMonthFirstDay + "-" + preMonthLastDay);
	}
}
