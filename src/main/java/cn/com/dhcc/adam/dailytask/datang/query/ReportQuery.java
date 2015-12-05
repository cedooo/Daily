package cn.com.dhcc.adam.dailytask.datang.query;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.session.Configuration;

import cn.com.dhcc.adam.dailytask.datang.GenerateReport;
import cn.com.dhcc.adam.dailytask.datang.builder.DailyReportBuilder;
import cn.com.dhcc.adam.dailytask.datang.builder.IReportBuilder;
import cn.com.dhcc.adam.dailytask.datang.builder.MonthlyReportBuilder;
import cn.com.dhcc.adam.dailytask.datang.builder.WeeklyReportBuilder;

public class ReportQuery {
	private Map<String, Object> resultMap= null;
	private SimpleDateFormat dailyDateTimeFormat = new SimpleDateFormat("MM月dd日");
	private SimpleDateFormat weeklyDateTimeFormat = new SimpleDateFormat("MM月dd日(E)");
	private SimpleDateFormat monthlyDateTimeFormat = new SimpleDateFormat("MM月dd日");
	private MapperBuilder mapperBuilder;
	public ReportQuery(Class<? extends IReportBuilder> irt){
		resultMap = new HashMap<String, Object>();
		if(irt == DailyReportBuilder.class){
			daily();
			doQuery(GenerateReport.TYPE_DAILY);
		}else if(irt  == WeeklyReportBuilder.class){
			weekly();
			doQuery(GenerateReport.TYPE_WEEKLY);
		}else if(irt  == MonthlyReportBuilder.class){
			monthly();
			doQuery(GenerateReport.TYPE_MONTHLY);
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
	/**
	 * 查询所有属性
	 * @param type 报表类型
	 */
	private void doQuery(int type){
		mapperBuilder = new MapperBuilder();
		Configuration config = mapperBuilder.getConfig();
		Collection<MappedStatement> mappedStatements = config.getMappedStatements();
		Integer parameter = type; 
		Map<? extends String, ? extends Object> queryResultMap = new HashMap<String, Object>();
		for (MappedStatement mappedStatement : mappedStatements) {
			SqlSource sqlSource = mappedStatement.getSqlSource();
			BoundSql bsql = sqlSource.getBoundSql(parameter);
			
			System.out.println(mappedStatement.getId() + "\n" + bsql.getSql());
			
		}
		resultMap.putAll(queryResultMap);
	}
}
