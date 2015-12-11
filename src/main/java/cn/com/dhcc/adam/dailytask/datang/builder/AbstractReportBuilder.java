package cn.com.dhcc.adam.dailytask.datang.builder;

import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;
import static net.sf.dynamicreports.report.builder.DynamicReports.stl;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.com.dhcc.adam.dailytask.datang.query.ReportQuery;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.component.ComponentBuilder;
import net.sf.dynamicreports.report.builder.component.TextFieldBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.constant.PdfEncoding;
import net.sf.dynamicreports.report.constant.VerticalAlignment;

/**
 * 中国大唐集团公司信息系统运行总结报告 抽象 模版
 * 
 * @author cedo
 * 
 */
public class AbstractReportBuilder implements IReportBuilder {
	protected ReportQuery reportQuery;
	protected String title;
	protected String dateTime;
	protected String overView;
	protected LinkedHashMap<String, List<String>> contents;

	private StyleBuilder plainStyle;

	@SuppressWarnings("deprecation")
	public AbstractReportBuilder() {
		contents = new LinkedHashMap<String,List<String>>();
		plainStyle = stl.style().setPdfFontName("STSong-Light")
				.setPdfEncoding(PdfEncoding.UniGB_UCS2_H_Chinese_Simplified) ;//.setFontName("Arial");
	}

	@Override
	public JasperReportBuilder build() {
		JasperReportBuilder report = DynamicReports.report();
		report.title(title(title)).title(overview(dateTime, overView))
				.title(subReport(contents));
				//.pageFooter(pageFooter())
				//.pageHeader(pageHeader());
		return report;
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
				.style(plainStyle)
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
	protected TextFieldBuilder<String>[] overview(String datatime,
			String details) {
		@SuppressWarnings("unchecked")
		TextFieldBuilder<String>[] tfba = new TextFieldBuilder[2];
		StyleBuilder datatimeStyle = stl
				.style(plainStyle)
				.setAlignment(HorizontalAlignment.CENTER,
						VerticalAlignment.MIDDLE).setFontSize(16)
				.setPadding(16);
		tfba[0] = cmp.text(datatime).setStyle(datatimeStyle);

		StyleBuilder detailsStyle = stl
				.style(plainStyle).setFontSize(16)
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
				.style(plainStyle)
				.setAlignment(HorizontalAlignment.CENTER,
						VerticalAlignment.MIDDLE).setFontSize(10)
				.setPadding(10).setForegroundColor(Color.LIGHT_GRAY);
		String generateTime = new SimpleDateFormat("MM-dd HH:mm").format(new Date());
		return cmp.text(generateTime + " ©东华软件").setStyle(headerStyle);
	}

	/**
	 * 页尾
	 * 
	 * @return
	 */
	protected ComponentBuilder<?, ?> pageFooter() {
		StyleBuilder footerStyle = stl
				.style(plainStyle)
				.setAlignment(HorizontalAlignment.CENTER,
						VerticalAlignment.MIDDLE).setFontSize(10)
				.setForegroundColor(Color.LIGHT_GRAY);
		String generateTime = new SimpleDateFormat("MM-dd HH:mm").format(new Date());
		return cmp.text(generateTime + " ©东华软件").setStyle(footerStyle);
	}

	public final static String REGEX_VARIABLE = "^(.*?\\{([a-zA-Z_0-9]+)\\})+?.*$";
	/**
	 * 将表达式替换为查询得到的值
	 * @param line 字符串
	 * @return 替换为数据后的字符串
	 */
	public String reportPart(String line) {
		Pattern pattern = Pattern.compile(REGEX_VARIABLE);
		Matcher matcher = pattern.matcher(line);
		while (matcher.matches()) {
			String matchedAttribute = matcher.group(2);
			line = line.replaceAll("\\{" + matchedAttribute + "\\}", " " + reportQuery.query(matchedAttribute) + " " );
			matcher = pattern.matcher(line);
		}
		return line;
	}

	/**
	 * 子报表
	 * 
	 * @param contents
	 * @return
	 */
	protected ComponentBuilder<?, ?>[] subReport(
			LinkedHashMap<String, List<String>> contents) {
		ComponentBuilder<?, ?>[] reports = new ComponentBuilder<?, ?>[contents
				.size()];
		StyleBuilder paTitleStyle = stl
				.style(plainStyle)
				.setAlignment(HorizontalAlignment.LEFT,
						VerticalAlignment.MIDDLE).setFontSize(14).bold();

		StyleBuilder detailsStyle = stl
				.style(plainStyle).setFontSize(16)
				.setPadding(2 * 14);//.setFirstLineIndent(2 * 14);
		//StyleBuilder dataStyle = stl.style(plainStyle).underline().setFontSize(16);
		int counter = 0;
		/**
		 * 子报表内容设置
		 */
		for (String key : contents.keySet()) {
			JasperReportBuilder report = DynamicReports.report();
			List<String> listString = contents.get(key);
			
			report.title(cmp.text(key).setStyle(paTitleStyle));;
			
			StringBuilder stb = new StringBuilder();
			for (String string : listString) {
				stb.append(string+"\n");
			}
			report.title(cmp.text(stb.toString()).setStyle(detailsStyle));
			reports[counter++] = cmp.subreport(report);

		}
		return reports;
	}
}
