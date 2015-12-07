package cn.com.dhcc.adam.dailytask.datang;

import junit.framework.TestCase;

public class GenerateReportTest extends TestCase {
	private GenerateReport gr = new GenerateReport();
	String path = "D:/";
	String dmsn = "998";

	public void testBuildDaily() {
		gr.build(path, "test-daily", dmsn, GenerateReport.TYPE_DAILY);
	}

	public void testBuildWeekly() {
		gr.build(path, "test-weekly", dmsn, GenerateReport.TYPE_WEEKLY);
	}

	public void testBuildMonthly() {
		gr.build(path, "test-monthly", dmsn, GenerateReport.TYPE_MONTHLY);
	}

}
