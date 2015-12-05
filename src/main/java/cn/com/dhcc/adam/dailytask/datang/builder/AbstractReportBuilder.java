package cn.com.dhcc.adam.dailytask.datang.builder;

import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;
import static net.sf.dynamicreports.report.builder.DynamicReports.stl;

import java.awt.Color;
import java.util.LinkedHashMap;

import cn.com.dhcc.adam.dailytask.datang.query.ReportQuery;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.component.ComponentBuilder;
import net.sf.dynamicreports.report.builder.component.TextFieldBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.constant.VerticalAlignment;

/**
 * 中国大唐集团公司信息系统运行日总结报告 模版
 * 
 * @author cedo
 * 
 */
public class AbstractReportBuilder implements IReportBuilder {
	protected ReportQuery reportQuery;
	protected String title;
	protected String dateTime;
	protected String overView;
	protected LinkedHashMap<String, String> contents;


	@Override
	public JasperReportBuilder build() {
		JasperReportBuilder report = DynamicReports.report();
			report.title(title(title)).title(overview(dateTime, overView))
					.title(subReport(contents)).pageHeader(pageHeader());
		return report;
	}

	/**
	 * 子报表
	 * 
	 * @param contents
	 * @return
	 */
	protected ComponentBuilder<?, ?>[] subReport(
			LinkedHashMap<String, String> contents) {
		ComponentBuilder<?, ?>[] reports = new ComponentBuilder<?, ?>[contents
				.size()];
		StyleBuilder paTitleStyle = stl
				.style()
				.setAlignment(HorizontalAlignment.LEFT,
						VerticalAlignment.MIDDLE).setFontSize(14).bold();

		StyleBuilder detailsStyle = stl.style().setFontSize(16)
				.setPadding(2 * 14).setFirstLineIndent(2 * 14);
		int counter = 0;
		/**
		 * 子报表内容设置
		 */
		for (String key : contents.keySet()) {
			JasperReportBuilder report = DynamicReports.report();
			report.title(cmp.text(key).setStyle(paTitleStyle)).columnHeader(
					cmp.text(contents.get(key)).setStyle(detailsStyle));
			reports[counter++] = cmp.subreport(report);

		}
		return reports;
	}

	/**
	 * 报告标题
	 * 
	 * @param title
	 *            标题
	 * @return
	 */
	protected TextFieldBuilder<String> title(String title) {
		StyleBuilder titleStyle = stl
				.style()
				.setAlignment(HorizontalAlignment.CENTER,
						VerticalAlignment.MIDDLE).setFontSize(22).bold()
				.setPadding(16);
		return cmp.text(title).setStyle(titleStyle);
	}

	/**
	 * 概要
	 * 
	 * @param datatime
	 *            内容时间
	 * @param details
	 *            内容详细
	 * @return TextFieldBuilder 2维数组
	 */
	protected TextFieldBuilder<String>[] overview(String datatime, String details) {
		@SuppressWarnings("unchecked")
		TextFieldBuilder<String>[] tfba = new TextFieldBuilder[2];
		StyleBuilder datatimeStyle = stl
				.style()
				.setAlignment(HorizontalAlignment.CENTER,
						VerticalAlignment.MIDDLE).setFontSize(16)
				.setPadding(16);
		tfba[0] = cmp.text(datatime).setStyle(datatimeStyle);

		StyleBuilder detailsStyle = stl.style().setFontSize(16)
				.setFirstLineIndent(2 * 16);
		tfba[1] = cmp.text(details).setStyle(detailsStyle);

		return tfba;
	}

	/**
	 * 页头
	 * 
	 * @return
	 */
	protected ComponentBuilder<?, ?> pageHeader() {
		StyleBuilder headerStyle = stl
				.style()
				.setAlignment(HorizontalAlignment.CENTER,
						VerticalAlignment.MIDDLE).setFontSize(10)
				.setPadding(10).setForegroundColor(Color.LIGHT_GRAY);
		return cmp.text("©东华软件").setStyle(headerStyle);
	}

	/**
	 * 页尾
	 * 
	 * @return
	 */
	protected ComponentBuilder<?, ?> pageFooter() {
		StyleBuilder footerStyle = stl
				.style()
				.setAlignment(HorizontalAlignment.CENTER,
						VerticalAlignment.MIDDLE).setFontSize(10)
				.setForegroundColor(Color.LIGHT_GRAY);
		return cmp.text("©东华软件").setStyle(footerStyle);
	}

}
