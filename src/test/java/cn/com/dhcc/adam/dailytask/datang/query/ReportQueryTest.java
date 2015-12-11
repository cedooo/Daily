package cn.com.dhcc.adam.dailytask.datang.query;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.mapping.BoundSql;

import cn.com.dhcc.adam.dailytask.datang.GenerateReport;
import cn.com.dhcc.adam.dailytask.datang.tools.DevTestDBManager;

import junit.framework.TestCase;

/**
 * @deprecated
 * @author cedo
 * 
 */
public class ReportQueryTest extends TestCase {

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
		Integer i = GenerateReport.TYPE_WEEKLY;
		
		MapperBuilder mb = new MapperBuilder();
		Map<String, BoundSql> maps = mb.getSqls(i);
		String attrsID[] = key.split("_");
		BoundSql bsql = maps.get(key);
		List<Map<String, String>> lm = DevTestDBManager.executeSQLInPooledDBSource(
				bsql.getSql(), attrsID);
		System.out.println(lm);
		assertEquals(true, lm!=null&&lm.size()>0);
	}
}
