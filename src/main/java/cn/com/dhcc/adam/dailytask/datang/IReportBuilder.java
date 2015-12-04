package cn.com.dhcc.adam.dailytask.datang;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;

/**
 * 报表模版接口
 * @author cedo
 *
 */
public interface IReportBuilder  {
	/**
	 * 构建报表
	 * @return JasperReportBuilder
	 */
	public JasperReportBuilder build();
}
