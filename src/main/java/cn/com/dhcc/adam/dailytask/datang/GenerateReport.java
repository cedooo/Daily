package cn.com.dhcc.adam.dailytask.datang;

import java.io.File;

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
	// 日周月年
	public static final int TYPE_DAILY = 1;
	public static final int TYPE_WEEKLY = 2;
	public static final int TYPE_MONTHLY = 3;
	public static final int TYPE_YEARLY = 4;

	/**
	 * 产生报表
	 * 
	 * @param path
	 * @param filename
	 * @param dmsn
	 * @param type
	 *            报表类型
	 * @return JasperReport
	 */
	public JasperReportBuilder build(String path, String fileName, String dmsn,
			int type) {
		IReportBuilder reportBuilder = null;
		
		switch (type) {
		case TYPE_DAILY:
			reportBuilder = new TemplateReportBuilder("/daily-template.txt",TYPE_DAILY);
			break;
		case TYPE_WEEKLY:
			reportBuilder = new TemplateReportBuilder("/weekly-template.txt",TYPE_WEEKLY);
			break;
		case TYPE_MONTHLY:
			reportBuilder = new TemplateReportBuilder("/monthly-template.txt",TYPE_MONTHLY);
			break;
		case TYPE_YEARLY:
		default:
		}
		if (reportBuilder != null) {
			JasperReportBuilder report = reportBuilder.build();
			if (report != null) {
				export(report, path, fileName, dmsn);
			}
			return report;
		} else {
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
			report.toDocx(wordDocxExporter).toPdf(pdfExporter)
					.toXlsx(xlsExporter).toRtf(rtfExporter);
		} catch (DRException e) {
			e.printStackTrace();
		}
		return -1;
	}
}
