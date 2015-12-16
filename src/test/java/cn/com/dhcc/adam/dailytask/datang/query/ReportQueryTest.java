package cn.com.dhcc.adam.dailytask.datang.query;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.mapping.BoundSql;

import cn.com.dhcc.adam.dailytask.datang.GenerateReport;
import cn.com.dhcc.adam.dailytask.datang.tools.DevTestDBManager;

import junit.framework.TestCase;

/**
 * 测试本地获取数据
 * @author cedo
 * 
 */
public class ReportQueryTest extends TestCase {
	private static final Log logger = LogFactory.getLog(ReportQueryTest.class);

	private String[] attar = {
			"accessMostSys_accessMost_accessLeastSys_accessLeast",
			"alarmNetworkUnreach_alarmURL_alarmHeaderOther",
			"infoSysAlarmTotal_infoSysAlarmCommon_infoSysAlarmMinor_infoSysAlarmMain_infoSysAlarmSerious" ,
			"infoSysInaccess_sysNetworkUnreach_sysURLUnreach_otherUnreach"};

	/**
	 * 信息系统分类告警总数
	 */
	public void testQueryAlarmSQL() {
		String key = attar[0];
		Integer itype = GenerateReport.TYPE_WEEKLY;
		
		Map<String, BoundSql> maps = new MapperBuilder().getSqls(itype);
		BoundSql bsql = maps.get(key);
		logger.debug("the SQL to be execut:" + bsql.getSql());
		List<Map<String, String>> lm = DevTestDBManager.executeSQLInPooledDBSource(
				bsql.getSql());
		System.out.println(lm);
		assertEquals(true, lm!=null&&lm.size()>0);
	}
}
