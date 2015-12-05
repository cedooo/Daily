package cn.com.dhcc.adam.dailytask.datang.builder;

import java.util.ArrayList;
import java.util.List;

import net.sf.dynamicreports.report.exception.DRException;
import cn.com.dhcc.adam.dailytask.datang.query.ReportQuery;
import cn.com.dhcc.adam.dailytask.datang.tools.TxtReportTemplate;

/**
 * 中国大唐集团公司信息系统运行月总结报告
 * 
 * @author cedo
 * 
 */
public class MonthlyReportBuilder extends AbstractReportBuilder{
	private final static String TXT_TEMPLATE = "src/main/java/cn/com/dhcc/adam/dailytask/datang/tools/template.txt";
	
	public MonthlyReportBuilder() {
		reportQuery = new ReportQuery(MonthlyReportBuilder.class);

		List<String> template = new TxtReportTemplate().getReportString(TXT_TEMPLATE);
		title = reportPart(template.get(0));
		dateTime = reportPart(template.get(1));
		overView = reportPart(template.get(2));
		List<String> listContent = template.subList(3, template.size());
		String contentsTitle = null;
		List<String> strBuilder = new ArrayList<String>();
		for (String string : listContent) {
			if(string.matches("^[一二三四五六七八九十]、.*$")){
				if(contentsTitle!=null&& strBuilder!=null){
					List<String> listReportPart = new ArrayList<String>();
					for (String paragraph : strBuilder) {
						String contentStr = reportPart(paragraph);
						listReportPart.add(contentStr);
					}
					contents.put(contentsTitle, listReportPart);
				}
				contentsTitle = string;
				strBuilder.clear();
			}else{
				strBuilder.add(string);
			}
		}
	}

	public static void main(String[] args) {
		try {
			new MonthlyReportBuilder().build().show();
		} catch (DRException e) {
			e.printStackTrace();
		}
	}
}
