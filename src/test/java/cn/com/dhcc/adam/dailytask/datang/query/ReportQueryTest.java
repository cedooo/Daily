package cn.com.dhcc.adam.dailytask.datang.query;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.mapping.BoundSql;

import cn.com.dhcc.adam.dailytask.datang.tools.DevTestDBManager;

import junit.framework.TestCase;
/**
 * @deprecated
 * @author cedo
 *
 */
public class ReportQueryTest extends TestCase {
	/*public void testQueryAccessSQL(){
		MapperBuilder mb = new MapperBuilder();
		Map<String, BoundSql> maps= mb.getSqls(1);
		String key = "accessMostSys_accessMost_accessLeastSys_accessLeast";
		String attrsID[] = key.split("_");
		BoundSql bsql = maps.get(key);
		List<Map<String, String>>  lm = DevTestDBManager.executeSQL(bsql.getSql(), attrsID);
		System.out.println(lm);
	}*/
	/**
	 * 信息系统分类告警总数
	 */
	public void testQueryAlarmSQL(){
		MapperBuilder mb = new MapperBuilder();
		Map<String, BoundSql> maps= mb.getSqls(1);
		String key = "infoSysAlarmTotal_infoSysAlarmCommon_infoSysAlarmMinor_infoSysAlarmMain_infoSysAlarmSerious";
		String attrsID[] = key.split("_");
		BoundSql bsql = maps.get(key);
		List<Map<String, String>>  lm = DevTestDBManager.executeSQL(bsql.getSql(), attrsID);
		System.out.println(lm);
	}
}
