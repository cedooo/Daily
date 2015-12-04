package cn.com.dhcc.adam.dailytask.datang;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;

public class GenerateReport {
	// 日周月年
	public static final int TYPE_DAILY = 1;
	public static final int TYPE_WEEKLY = 2;
	public static final int TYPE_MONTHLY = 3;
	public static final int TYPE_YEARLY = 4; 
	/**
	 * 根据type返回对应的 定期报表
	 * @param type 报表类型
	 * @return JasperReport
	 */
	public JasperReportBuilder build(int type) {
		IReportBuilder reportBuilder = null;
		switch (type) {
		case TYPE_DAILY:
			reportBuilder = new DailyReportBuilder();
			break;
		case TYPE_WEEKLY:
			reportBuilder = new WeeklyReportBuilder();
			break;
		case TYPE_MONTHLY:
			reportBuilder = new MonthlyReportBuilder();
			break;
		case TYPE_YEARLY:
		default:
		}
		if (reportBuilder != null) {
			return reportBuilder.build();
		} else {
			return null;
		}
	}
}
