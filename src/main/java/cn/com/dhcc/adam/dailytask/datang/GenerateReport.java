package cn.com.dhcc.adam.dailytask.datang;

import java.io.File;

import static net.sf.dynamicreports.report.builder.DynamicReports.export;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.jasper.builder.export.JasperDocxExporterBuilder;
import net.sf.dynamicreports.jasper.builder.export.JasperPdfExporterBuilder;
import net.sf.dynamicreports.jasper.builder.export.JasperRtfExporterBuilder;
import net.sf.dynamicreports.jasper.builder.export.JasperXlsxExporterBuilder;
import net.sf.dynamicreports.report.constant.PdfEncoding;
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
			JasperReportBuilder report = reportBuilder.build();
			export(report, path, fileName, dmsn);
			return report;
		} else {
			return null;
		}
	}

	public int export(JasperReportBuilder report, String path, String fileName,
			String dmsn) {
		String pdfName = path + File.separator + fileName + ".pdf";// reportWeb.getReportName
		String excelName = path + File.separator + fileName + ".xlsx";
		String wordDocxName = path + File.separator + fileName + ".docx";

		String rtfName = path + File.separator + fileName + ".rtf";

		JasperRtfExporterBuilder rtfExporter = export.rtfExporter(rtfName);
		JasperPdfExporterBuilder pdfExporter = export.pdfExporter(pdfName)
				.setCharacterEncoding(
						PdfEncoding.UniGB_UCS2_H_Chinese_Simplified);

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
