package cn.com.dhcc.adam.dailytask.datang.query;

import itims.extend.DomainManager;
import itims.share.db.ConnException;
import itims.share.db.DBException;
import itims.share.db.JdbcAbstractTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.log4j.Logger;

import cn.com.dhcc.adam.dailytask.datang.GenerateReport;
import cn.com.dhcc.adam.dailytask.datang.tools.DevTestDBManager;

public class ReportQuery {
	private static final Logger logger = Logger.getLogger(ReportQuery.class);
	
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
			logger.debug("生成日报");
			dailyDateTime();
			doQuery(GenerateReport.TYPE_DAILY);
			logger.debug("生成日报完成");
			break;
		case GenerateReport.TYPE_WEEKLY:
			logger.debug("生成周报");
			weeklyDateTime();
			doQuery(GenerateReport.TYPE_WEEKLY);
			logger.debug("生成周报完成");
			break;
		case GenerateReport.TYPE_MONTHLY:
			logger.debug("生成月报");
			monthlyDateTime();
			doQuery(GenerateReport.TYPE_MONTHLY);
			logger.debug("生成月报完成");
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


	private static boolean production = true;
	static {
		String proFileName = "env.properties";
		Properties pro = new Properties();
		
		InputStream is = ReportQuery.class.getClassLoader().getResourceAsStream(proFileName);
		try {
			pro.load(is);
			//System.out.println("配置文件中 不用本地数据库??:" + "0".equals(pro.getProperty("dblocal")));
			production = "0".equals(pro.getProperty("dblocal"));
		} catch (Exception e1) {
			System.err.println("env.properties不存在");
		}finally{
			if(is!=null){
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * 查询所有属性
	 * 
	 * @param type
	 *            报表类型
	 */
	@SuppressWarnings("unchecked")
	private void doQuery(int type) {
		mapperBuilder = new MapperBuilder();
		Map<String, Object> queryResultMap = new HashMap<String, Object>();
		Map<String, BoundSql> msb = mapperBuilder.getSqls(type);
		
		if(production){
			try{
				JdbcAbstractTemplate jat = null;
				jat = new JdbcAbstractTemplate(DomainManager.getDbIdByDmsn(998));
	
				List<Map<String,String>> data = null;
				
				for (String sqlKey : msb.keySet()) {
					// 从数据库查询对应指标
					BoundSql boundSql = msb.get(sqlKey);
					try {
						String attrID = sqlKey;
						data = jat.getListForMap(boundSql.getSql());
						logger.debug("execute query SQL for [ " + attrID +  " ] : " + boundSql.getSql());
						if(data!=null ){
							for (Map<String,String> resultMap : data) {
								for(String mapKey : resultMap.keySet()){
									String value = resultMap.get(mapKey);
									queryResultMap.put(mapKey, value);
								}
							}
						}else{
							throw new RuntimeException("SQL错误：可能配置出现语法错误");
						}
					} catch (DBException e) {
						e.printStackTrace();
					} catch (ConnException e) {
						e.printStackTrace();
					}
				}
			}catch (Exception e){
				logger.warn("=====================================================\n数据库连接错误\n===============================");
			}
		}else{
			for (String sqlKey : msb.keySet()) {
				BoundSql boundSql = msb.get(sqlKey);    // BoundSql bsql暂时只支持传入报表类型作为参数
				
				 List<Map<String, String>> values = DevTestDBManager.executeSQLInPooledDBSource(boundSql.getSql());
				for (Map<String, String> map : values) {
					for (String mapKey : map.keySet()) {
						queryResultMap.put(mapKey, map.get(mapKey));
					}
				}
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
