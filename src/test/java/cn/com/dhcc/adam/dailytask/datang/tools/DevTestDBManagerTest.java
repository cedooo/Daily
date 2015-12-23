package cn.com.dhcc.adam.dailytask.datang.tools;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.mapping.BoundSql;

import cn.com.dhcc.adam.dailytask.datang.GenerateReport;
import cn.com.dhcc.adam.dailytask.datang.query.MapperBuilder;
import cn.com.dhcc.adam.dailytask.datang.query.ReportQueryTest;
import junit.framework.TestCase;

public class DevTestDBManagerTest extends TestCase {
	private static final Log logger = LogFactory.getLog(ReportQueryTest.class);

	private String[] attar = {
			"runSys",
			"accessMostSys_accessMost_accessLeastSys_accessLeast",
			"alarmNetworkUnreach_alarmURL_alarmHeaderOther",
			"infoSysAlarmTotal_infoSysAlarmCommon_infoSysAlarmMinor_infoSysAlarmMain_infoSysAlarmSerious" ,
			"infoSysInaccess_sysNetworkUnreach_sysURLUnreach_otherUnreach"};
	public void testExecuteSQLInPooledDBSource() {
		String key = attar[0];
		Integer itype = GenerateReport.TYPE_WEEKLY;
		
		Map<String, BoundSql> maps = new MapperBuilder().getSqls(itype);
		BoundSql bsql = maps.get(key);
		logger.info("the SQL to be execut:" + bsql.getSql());
		List<Map<String, String>> lm = DevTestDBManager.executeSQLInPooledDBSource(
				bsql.getSql());
		logger.info("execute SQL result: " + lm);
		assertEquals(true, lm!=null);
	}

}
