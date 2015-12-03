package cn.com.dhcc.adam.dailytask.datang;

import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;
import static net.sf.dynamicreports.report.builder.DynamicReports.stl;

import java.awt.Color;
import java.util.LinkedHashMap;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.component.ComponentBuilder;
import net.sf.dynamicreports.report.builder.component.TextFieldBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.constant.VerticalAlignment;
import net.sf.dynamicreports.report.exception.DRException;

/**
 * 中国大唐集团公司信息系统运行月总结报告
 * 
 * @author cedo
 * 
 */
public class MonthlyReportTemplate implements IReportTemplate {
	private ReportQuery reportQuery;
	private String title;
	private String dateTime;
	private String overView;
	private LinkedHashMap<String, String> contents;

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public void setOverView(String overView) {
		this.overView = overView;
	}

	public void setContents(LinkedHashMap<String, String> contents) {
		this.contents = contents;
	}

	public MonthlyReportTemplate() {
		reportQuery = new ReportQuery(MonthlyReportTemplate.class);
		title = "中国大唐集团公司信息系统运月总结报告";
		dateTime = reportQuery.query("dateTime");
		overView = "本期，集团公司信息系统总体运行状况稳定/略有提升/略有下降，系统安全状况正常/基本正常，未发生/发生 0 次重大异常事件。";
		contents = new LinkedHashMap<String, String>();
		String contentsTitleOne = "一、信息系统基本情况";
		String contentsOne = "集团公司系统运行信息系统721个，与昨日相比增加 0 个/减少0个/持平。\n"
				+ "其中，集团公司总部 58 个系统；分子公司本部 116 个系统；497 个系统。\n"
				+ "按等级保护级别划分，三级系统 8 个，二级系统 292 个，一级系统 22 个，未分级399个。\n"
				+ "按系统类别划分，重要资源管控类140个，共享交换平台类 10 个，基础设施类 46 个，主营业务管控类 52 个，公用应用类 231 个，其它类 11 个。";
		contents.put(contentsTitleOne, contentsOne);
		String contentsTitleTwo = "二、信息系统运行情况监测";
		String contentsTwo = "（一）信息系统报警（月）\n"
				+ "本期，信息系统报警总量 1034 条，一般报警 31 条，次要告警 1054 ，主要报警 53 条，严重报警573 条。\n"
				+ "集团公司总部系统报警 274 条其中网络不通报警 105条，URL报警 7 条，其他报警 14 条。截止到目前总部系统报警处理共0 条。\n"
				+ "（二）系统不可访问\n"
				+ "截至目前，信息系统不可访问共 74 个，其中系统网络不通_71 个，系统URL 1 个，其他原因 2 个。\n"
				+ "（四）系统访问量（月）\n"
				+ "重点监测访问量的信息系统共16个，系统访问量最高的为全面计划，访问量   4851764次。系统访问量最低的为中国大唐集团公司三会管理信息系统，系统访问量 32次。";
		contents.put(contentsTitleTwo, contentsTwo);

		String contentsTitleThr = "三、广域网链路与设备运行监测";
		String contentsThr = "（一）广域网链路情况  \n"
				+ "集团公司骨干广域网链路共 27 条， 目前运行稳定/基本稳定，本期未发生链路中断事件/发生 1 次。其中流量最大的链路为 广西公司 ，其设计带宽为 6 _M，平均流量为0.8 M，峰值流量为 6 M。\n"
				+ "（二）网络设备运行情况\n"
				+ "1.集团公司骨干广域网路由器共30台（二级分子公司总量）；设备运行率100%。目前重点监测路由器20台，CPU平均使用率最大的路由器为_10.67.252.118_名称__ R_B_JL_CCRL _；使用率_8.5%_ 值;与上级交互平均流量最大的路由器为    流量_2_M\n"
				+ "2.集团公司总部主核心交换机运行基本正常， CPU平均使用率为_7%_；（通常应在10%以下）；到骨干广域网流量平均值为（145.95Kbps）；最大值为（1.08Mbps）。\n"
				+ "集团公司总部备用核心交换机CPU使用率）到骨干广域网流量（最大流量、平均流量）\n"
				+ "3.目前监测网络设备_75台，共发生报警_889 _条;其中严重报警_82条一般报警_0条；次要报警_346_条；主要报警_48条。\n";

		contents.put(contentsTitleThr, contentsThr);

		String contentsTitleFour = "四、服务器运行监测";
		String contentsFour = "1.集团公司系统服务器共计 14 台，目前重点监测服务器_0 台，设备运行率_100%_；发生故障 92 次；主要原因为 43个；已处理 20 个正在处理 23  个。\n"
				+ "2.重点监测的服务器中，CPU使用率最高为_在线检测系统，CPU使用率为_25%_；内存平均使用率_77%_；磁盘平均使用率_67%_；\n"
				+ "3.本期共发生报警_21119__条;其中严重报警_20265_条一般报警_25_条；次要报警_730_条；主要报警_91_条。\n";

		contents.put(contentsTitleFour, contentsFour);
		build();
	}

	@Override
	public JasperReportBuilder build() {
		JasperReportBuilder report = DynamicReports.report();
		try {
			report.title(title(title)).title(overview(dateTime, overView))
					.title(subReport(contents)).pageHeader(pageHeader())
					// .pageFooter(pageFooter())
					.show();
		} catch (DRException e) {
			e.printStackTrace();
		}
		return report;
	}

	/**
	 * 自报表
	 * 
	 * @param contents
	 * @return
	 */
	private ComponentBuilder<?, ?>[] subReport(
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
	public TextFieldBuilder<String> title(String title) {
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
	public TextFieldBuilder<String>[] overview(String datatime, String details) {
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
	public ComponentBuilder<?, ?> pageHeader() {
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
	public ComponentBuilder<?, ?> pageFooter() {
		StyleBuilder footerStyle = stl
				.style()
				.setAlignment(HorizontalAlignment.CENTER,
						VerticalAlignment.MIDDLE).setFontSize(10)
				.setForegroundColor(Color.LIGHT_GRAY);
		return cmp.text("©东华软件").setStyle(footerStyle);
	}

	public static void main(String[] args) {
		new MonthlyReportTemplate();
	}
}
