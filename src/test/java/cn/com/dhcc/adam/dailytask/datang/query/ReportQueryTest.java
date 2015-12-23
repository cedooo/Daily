package cn.com.dhcc.adam.dailytask.datang.query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.com.dhcc.adam.dailytask.datang.GenerateReport;
import junit.framework.TestCase;

/**
 * 测试本地获取数据
 * @author cedo
 * 
 */
public class ReportQueryTest extends TestCase {
	private static final Log logger = LogFactory.getLog(ReportQueryTest.class);

	public void testQuery(){
		ReportQuery rq = new ReportQuery(GenerateReport.TYPE_DAILY);
		String runSys = "runSys";
		String rqResult = rq.query(runSys );
		logger.info(runSys + " = " + rqResult);
		assertEquals(true, rqResult!=null);
	}
}
