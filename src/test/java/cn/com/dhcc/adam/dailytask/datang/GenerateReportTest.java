package cn.com.dhcc.adam.dailytask.datang;

import junit.framework.TestCase;

public class GenerateReportTest extends TestCase {
	private GenerateReport gr = new GenerateReport();
	public void testBuild() {
		gr.build("D:/","test","998",2);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
