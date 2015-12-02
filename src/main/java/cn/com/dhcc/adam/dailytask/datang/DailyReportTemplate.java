package cn.com.dhcc.adam.dailytask.datang;

import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;
import static net.sf.dynamicreports.report.builder.DynamicReports.report;
import static net.sf.dynamicreports.report.builder.DynamicReports.stl;

import java.util.HashMap;
import java.util.Map;

import net.sf.dynamicreports.report.builder.component.ComponentBuilder;
import net.sf.dynamicreports.report.builder.component.TextFieldBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.constant.VerticalAlignment;
import net.sf.dynamicreports.report.exception.DRException;

/**
 * 中国大唐集团公司信息系统运行日总结报告 模版
 * @author cedo
 *
 */
public class DailyReportTemplate implements IReportTemplate {
	public DailyReportTemplate() {
		build();
	}

	private void build() {
		
		
		try {
			Map<String,String> contents = new HashMap<String, String>();
			String contentsTitleOne = "（一）信息系统报警";
			String contentsOne = "";
			
			report().title(title("中国大唐集团公司信息系统运行日总结报告"))
					.columnHeader(
							overview("11月20日00:00-24:00", "本期，集团公司信息系统总体运行状况稳定/略有提升/略有下降，系统安全状况正常/基本正常，未发生/发生 0 次重大异常事件。")
					)
					.detail(reportParagraph("二、信息系统运行情况监测", contents))
					.pageHeader(cmp.text ("This is a page header band @DHC")) 
					.pageFooter(cmp.text ("This is a page footer band @DHC"))
					.show();
		} catch (DRException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 报告标题
	 * @param title 标题
	 * @return
	 */
	public TextFieldBuilder<String>  title(String title){
		StyleBuilder titleStyle = stl.style().setAlignment(HorizontalAlignment.CENTER, VerticalAlignment.MIDDLE).setFontSize(22).bold();
		return cmp.text(title).setStyle(titleStyle);
	}
	/**
	 * 概要
	 * @param datatime 内容时间
	 * @param details 内容详细
	 * @return TextFieldBuilder 2维数组
	 */
	public TextFieldBuilder<String>[] overview(String datatime, String details){
		@SuppressWarnings("unchecked")
		TextFieldBuilder<String>[] tfba = new TextFieldBuilder[2];
		StyleBuilder datatimeStyle =stl.style().setAlignment(HorizontalAlignment.CENTER, VerticalAlignment.MIDDLE).setFontSize(16);
		tfba[0] = cmp.text(datatime).setStyle(datatimeStyle);
		
		StyleBuilder detailsStyle = stl.style().setFontSize(16).setFirstLineIndent(4*16);
		tfba[1] = cmp.text(details).setStyle(detailsStyle);
		
		return tfba;
	}
	
	public ComponentBuilder<?, ?> reportParagraph(String paTitle, Map<String, String> contents){
		return null;
	}
	public static void main(String[] args) {
		new DailyReportTemplate();
	}
}
