package cn.com.dhcc.adam.dailytask.datang;

import junit.framework.TestCase;
/**
 * 测试生成 日报 周报 月报
 * @author cedo
 *
 */
public class GenerateReportTest extends TestCase {
	private GenerateReport gr = new GenerateReport();
	String path = "D:/";    //目标文件夹必须已存在
	String dmsn = "998";
	/**
	 * 生成日报表
	 */
	public void testBuildDaily() {
		gr.build(path, "test-daily", dmsn, GenerateReport.TYPE_DAILY);
	}

	/**
	 * 生成周报表
	 */
	public void testBuildWeekly() {
		gr.build(path, "test-weekly", dmsn, GenerateReport.TYPE_WEEKLY);
	}

	/**
	 * 生成月报表
	 */
	public void testBuildMonthly() {
		gr.build(path, "test-monthly", dmsn, GenerateReport.TYPE_MONTHLY);
	}

}
