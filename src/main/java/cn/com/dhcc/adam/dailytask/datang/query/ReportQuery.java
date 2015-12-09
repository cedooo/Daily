package cn.com.dhcc.adam.dailytask.datang.query;

import itims.extend.DomainManager;
import itims.share.db.ConnException;
import itims.share.db.DBException;
import itims.share.db.JdbcAbstractTemplate;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.session.Configuration;

import cn.com.dhcc.adam.dailytask.datang.GenerateReport;
import cn.com.dhcc.adam.dailytask.datang.tools.DevTestDBManager;

public class ReportQuery {
	private Map<String, Object> resultMap =  new HashMap<String, Object>();
	private SimpleDateFormat dailyDateTimeFormat = new SimpleDateFormat(
			"MM月dd日");
	private SimpleDateFormat weeklyDateTimeFormat = new SimpleDateFormat(
			"MM月dd日(E)");
	private SimpleDateFormat monthlyDateTimeFormat = new SimpleDateFormat(
			"MM月dd日");
	private MapperBuilder mapperBuilder;


	public ReportQuery(int type){
		switch (type) {
		case GenerateReport.TYPE_DAILY:
			dailyDateTime();
			doQuery(GenerateReport.TYPE_DAILY);
			break;
		case GenerateReport.TYPE_WEEKLY:
			weeklyDateTime();
			doQuery(GenerateReport.TYPE_WEEKLY);
			break;
		case GenerateReport.TYPE_MONTHLY:
			monthlyDateTime();
			doQuery(GenerateReport.TYPE_MONTHLY);
			break;
		case GenerateReport.TYPE_YEARLY:
		default:
		}
	}
	/**
	 * 查询结果
	 * 
	 * @param attribute
	 *            属性名称
	 * @return 结果
	 */
	public String query(String attribute) {
		Object value = resultMap.get(attribute);
		boolean valueValid = value!=null&&!("".equals(value.toString()));
		return valueValid?value.toString():"____";
	}


	private final boolean production = false;
	/**
	 * 查询所有属性
	 * 
	 * @param type
	 *            报表类型
	 */
	@SuppressWarnings("unchecked")
	private void doQuery(int type) {
		mapperBuilder = new MapperBuilder();
		Configuration config = mapperBuilder.getConfig();
		Collection<MappedStatement> mappedStatements = config
				.getMappedStatements();
		Map<String, Object> queryResultMap = new HashMap<String, Object>();
		
		if(production){
			JdbcAbstractTemplate jat = null;
			jat = new JdbcAbstractTemplate(DomainManager.getDbIdByDmsn(998));

			List<Map<String,String>> data = null;
			Map<String,String> item = null;
			
			for (MappedStatement mappedStatement : mappedStatements) {
				SqlSource sqlSource = mappedStatement.getSqlSource();
				BoundSql bsql = sqlSource.getBoundSql(type);    // BoundSql bsql暂时只支持传入报表类型作为参数
				// 从数据库查询对应指标
				try {
					String attrID = mappedStatement.getId().split("\\.")[1];
					//String value = jat.getString(attrID, bsql.getSql());
					data = jat.getListForMap(bsql.getSql());
					if(data!=null && data.size()==1 && item.size()==1){
						item = data.get(0);
						for(String key : item.keySet()){
							String value = item.get(key);
							queryResultMap.put(attrID, value);
						}
						item.clear();
					}else{
						throw new RuntimeException("SQL 配置错误：可能语法错误或者 语句返回多个结果");
					}
				} catch (DBException e) {
					e.printStackTrace();
				} catch (ConnException e) {
					e.printStackTrace();
				}
			}
		}else{
			for (MappedStatement mappedStatement : mappedStatements) {
				SqlSource sqlSource = mappedStatement.getSqlSource();
				
				BoundSql bsql = sqlSource.getBoundSql(type);    // BoundSql bsql暂时只支持传入报表类型作为参数

					String attrID = mappedStatement.getId().split("\\.")[1];
					String value = DevTestDBManager
							.executeSQL(bsql.getSql(), attrID);
//System.out.println(attrID + ":" + value);
					queryResultMap.put(attrID, value);
			}
		}
		resultMap.putAll(queryResultMap);
	}

	private void dailyDateTime() {
		Calendar preDay = Calendar.getInstance();
		preDay.setTime(new Date());
		preDay.add(Calendar.DAY_OF_YEAR, -1);
		resultMap.put("dateTime", dailyDateTimeFormat.format(preDay.getTime()));
	}

	private void weeklyDateTime() {
		Calendar preWeek = Calendar.getInstance();
		preWeek.setTime(new Date());
		preWeek.add(Calendar.WEEK_OF_MONTH, -1);
		preWeek.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		String preWeekFirstDay = weeklyDateTimeFormat.format(preWeek.getTime());
		preWeek.add(Calendar.DAY_OF_YEAR,
				preWeek.getActualMaximum(Calendar.DAY_OF_WEEK) - 1);
		String preWeekLastDay = weeklyDateTimeFormat.format(preWeek.getTime());
		resultMap.put("dateTime", preWeekFirstDay + "-" + preWeekLastDay);
	}

	private void monthlyDateTime() {
		Calendar preMonth = Calendar.getInstance();
		preMonth.setTime(new Date());
		preMonth.add(Calendar.MONTH, -1);
		preMonth.set(Calendar.DAY_OF_MONTH, 1);
		String preMonthFirstDay = monthlyDateTimeFormat.format(preMonth
				.getTime());
		preMonth.add(Calendar.DAY_OF_MONTH,
				preMonth.getActualMaximum(Calendar.DAY_OF_MONTH) - 1);
		String preMonthLastDay = monthlyDateTimeFormat.format(preMonth
				.getTime());
		resultMap.put("dateTime", preMonthFirstDay + "-" + preMonthLastDay);
	}
	
}
