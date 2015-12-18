package cn.com.dhcc.adam.dailytask.datang;

import java.io.File;

import org.apache.log4j.Logger;

import cn.com.dhcc.adam.dailytask.datang.builder.IReportBuilder;
import cn.com.dhcc.adam.dailytask.datang.builder.TemplateReportBuilder;
import static net.sf.dynamicreports.report.builder.DynamicReports.export;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.jasper.builder.export.JasperDocxExporterBuilder;
import net.sf.dynamicreports.jasper.builder.export.JasperPdfExporterBuilder;
import net.sf.dynamicreports.jasper.builder.export.JasperRtfExporterBuilder;
import net.sf.dynamicreports.jasper.builder.export.JasperXlsxExporterBuilder;
import net.sf.dynamicreports.report.exception.DRException;

public class GenerateReport {
	private static final Logger logger = Logger.getLogger(GenerateReport.class);
	// 日周月年
	public static final int TYPE_DAILY = 1;
	public static final int TYPE_WEEKLY = 2;
	public static final int TYPE_MONTHLY = 3;
	public static final int TYPE_YEARLY = 4;

	
	public final static String TEMPLATE_DAILY = "/template/daily-template.txt";
	public final static String TEMPLATE_WEEKLY = "/template/weekly-template.txt";
	public final static String TEMPLATE_MONTHLY = "/template/monthly-template.txt";
	/**
	 * 产生报表
	 * @param path
	 * 		报表文件存储目录
	 * @param fileName 文件名称
	 * @param dmsn
	 * 		域
	 * @param type
	 *      报表类型(GenerateReport.TYPE_DAILY-日报，GenerateReport.TYPE_WEEKLY-周报，GenerateReport.TYPE_MONTHLY-月报)
	 * @return JasperReportBuilder对象
	 */
	public JasperReportBuilder build(String path, String fileName, String dmsn,
			int type) {
		logger.info("中国大唐集团公司信息系统运行报告  - 调用  :  path=" + path + ", fileName =  " + fileName + ", dmsn=" +  dmsn 
				+ ", type=" + type);
		IReportBuilder reportBuilder = null;
		
		switch (type) {
		case TYPE_DAILY:
			reportBuilder = new TemplateReportBuilder(TEMPLATE_DAILY, TYPE_DAILY);
			break;
		case TYPE_WEEKLY:
			reportBuilder = new TemplateReportBuilder(TEMPLATE_WEEKLY, TYPE_WEEKLY);
			break;
		case TYPE_MONTHLY:
			reportBuilder = new TemplateReportBuilder(TEMPLATE_MONTHLY, TYPE_MONTHLY);
			break;
		case TYPE_YEARLY:
		default:
		}
		if (reportBuilder != null) {
			JasperReportBuilder report = reportBuilder.build();
			if (report != null) {
				export(report, path, fileName, dmsn);
			}else{
				throw new RuntimeException("生成报表失败，无法生成报表文件");
			}
			logger.info("中国大唐集团公司信息系统运行报告  - 调用完成");
			return report;
		} else {
			logger.info("中国大唐集团公司信息系统运行报告  - 调用完成， 但未生成模版");
			return null;
		}
	}
	/**
	 * 将报表导出成报表文件
	 * @param report JasperReportBuilder对象
	 * @param path 存储路径
	 * @param fileName 报表文件名称
	 * @param dmsn 域
	 * @return 
	 */
	private int export(JasperReportBuilder report, String path, String fileName,
			String dmsn) {
		logger.info("中国大唐集团公司信息系统运行报告  - 生成报表文件开始");
		String pdfName = path + File.separator + fileName + ".pdf";
		String excelName = path + File.separator + fileName + ".xlsx";
		String wordDocxName = path + File.separator + fileName + ".docx";

		String rtfName = path + File.separator + fileName + ".rtf";

		JasperRtfExporterBuilder rtfExporter = export.rtfExporter(rtfName);
		JasperPdfExporterBuilder pdfExporter = export.pdfExporter(pdfName);

		JasperXlsxExporterBuilder xlsExporter = export.xlsxExporter(excelName)
				.setDetectCellType(true).setIgnorePageMargins(true)
				.setWhitePageBackground(false)
				.setRemoveEmptySpaceBetweenColumns(true);

		JasperDocxExporterBuilder wordDocxExporter = export
				.docxExporter(wordDocxName);
		try {
			report.toDocx(wordDocxExporter);
		} catch (DRException e) {
			logger.info(" 生成报表文件" + wordDocxName + "失败");
		}
		try {
			report.toPdf(pdfExporter);
		} catch (DRException e) {
			logger.info(" 生成报表文件" + pdfName + "失败");
		}
		try {
			report.toXlsx(xlsExporter);
		} catch (DRException e) {
			logger.info(" 生成报表文件" + excelName + "失败");
		}
		try {
			report.toRtf(rtfExporter);
		} catch (DRException e) {
			logger.info(" 生成报表文件" + rtfName + "失败");
		}
		logger.info("中国大唐集团公司信息系统运行报告  - 生成报表文件成功: [" + pdfName + "],[" + excelName + "],[" + wordDocxName + "]");
		return -1;
	}
}
