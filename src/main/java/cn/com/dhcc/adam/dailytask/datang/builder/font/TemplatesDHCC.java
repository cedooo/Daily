/**
 * DynamicReports - Free Java reporting library for creating reports dynamically
 *
 * Copyright (C) 2010 - 2011 Ricardo Mariaca
 * http://dynamicreports.sourceforge.net
 *
 * This file is part of DynamicReports.
 *
 * DynamicReports is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * DynamicReports is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with DynamicReports. If not, see <http://www.gnu.org/licenses/>.
 */

package cn.com.dhcc.adam.dailytask.datang.builder.font;

import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;
import static net.sf.dynamicreports.report.builder.DynamicReports.stl;
import static net.sf.dynamicreports.report.builder.DynamicReports.tableOfContentsCustomizer;
import static net.sf.dynamicreports.report.builder.DynamicReports.template;

import java.awt.Color;
import java.util.Locale;

import net.sf.dynamicreports.report.base.expression.AbstractValueFormatter;
import net.sf.dynamicreports.report.builder.ReportTemplateBuilder;
import net.sf.dynamicreports.report.builder.component.ComponentBuilder;
import net.sf.dynamicreports.report.builder.datatype.BigDecimalType;
import net.sf.dynamicreports.report.builder.style.FontBuilder;
import net.sf.dynamicreports.report.builder.style.ReportStyleBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.builder.tableofcontents.TableOfContentsCustomizerBuilder;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.constant.PdfEncoding;
import net.sf.dynamicreports.report.constant.VerticalAlignment;
import net.sf.dynamicreports.report.definition.ReportParameters;

/**
 * @author Ricardo Mariaca (dynamicreports@gmail.com)
 */
@SuppressWarnings("deprecation")
public class TemplatesDHCC {
	public static final StyleBuilder rootStyle;
	public static final StyleBuilder boldStyle;
	public static final StyleBuilder italicStyle;
	public static final StyleBuilder boldCenteredStyle;
	public static final StyleBuilder titleCenteredStyle;
	
	public static final StyleBuilder bold12CenteredStyle;
	public static final StyleBuilder bold18CenteredStyle;
	public static final StyleBuilder bold22CenteredStyle;
	public static final StyleBuilder columnStyle;
	public static final StyleBuilder cellStyle;
	
	public static final StyleBuilder columnZhuStyle;
	
	public static final StyleBuilder columnTitleStyle;
	public static final StyleBuilder groupStyle;
	public static final StyleBuilder subtotalStyle;

	public static final ReportTemplateBuilder reportTemplate;
	public static final CurrencyType currencyType;
	public static final ComponentBuilder<?, ?> dynamicReportsComponent;
	public static final ComponentBuilder<?, ?> footerComponent;

	static {
		rootStyle           = stl.style().setPadding(2);
		//boldStyle           = stl.style(rootStyle).bold();
		//ReportStyleBuilder root1 =  (ReportStyleBuilder)rootStyle;
		
		boldStyle           = stl.style(rootStyle).bold();
		italicStyle         = stl.style(rootStyle).italic();
		boldCenteredStyle   = stl.style(boldStyle)
		                         .setAlignment(HorizontalAlignment.CENTER, VerticalAlignment.MIDDLE);
		
		FontBuilder fontBuilder = stl.font();
		
		bold12CenteredStyle = stl.style(boldCenteredStyle)
		                         .setFontSize(12)
		                         .setFont(fontBuilder.setPdfFontName("STSong-Light").setPdfEncoding(PdfEncoding.UniGB_UCS2_H_Chinese_Simplified));
		                         ;
		
		bold18CenteredStyle = stl.style(boldCenteredStyle)
		//.setFont(fontBuilder.setPdfFontName("STSong-Light").setPdfEncoding(PdfEncoding.UniGB_UCS2_H_Chinese_Simplified))
		                         .setFontSize(18);
		
		FontBuilder fontBuilderTitle = stl.font();
		titleCenteredStyle = stl.style(rootStyle).bold()
					.setFont(fontBuilderTitle.setPdfFontName("STSong-Light").setPdfEncoding(PdfEncoding.UniGB_UCS2_H_Chinese_Simplified))
					.setFontSize(18).bold();
		
		//stl.style().setFont(Font.)
		//DRFont drfont = stl.style().setFont(font)
		
		
		//UniCNS_UCS2_H_Chinese_traditional MSung-Light
		bold22CenteredStyle = stl.style(boldCenteredStyle)
                             .setFontSize(18)
                             .setFont(fontBuilder.setPdfFontName("STSong-Light").setPdfEncoding(PdfEncoding.UniGB_UCS2_H_Chinese_Simplified));
                            //.setPdfEncoding(PdfEncoding.UniCNS_UCS2_H_Chinese_traditional);
		//columnStyle         = stl.style(rootStyle).setVerticalAlignment(VerticalAlignment.MIDDLE);
		
		
//		columnTitleStyle    = stl.style(columnStyle)
//		                         .setBorder(stl.pen1Point())
//		                         .setHorizontalAlignment(HorizontalAlignment.CENTER)
//		                         .setBackgroundColor(Color.LIGHT_GRAY)
//		                         .bold();
		
		columnStyle         = stl.style(rootStyle)
		//.setVerticalAlignment(VerticalAlignment.MIDDLE)
		.setAlignment(HorizontalAlignment.CENTER, VerticalAlignment.JUSTIFIED);
		;
		FontBuilder cellStyleFont = stl.font();
		
		cellStyle           = stl.style(rootStyle).setBorder(stl.pen1Point().setLineColor(new Color(Integer.parseInt("d2d2fa", 16))))
        .setHorizontalAlignment(HorizontalAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE)
        .setFont(cellStyleFont.setPdfFontName("STSong-Light").setPdfEncoding(PdfEncoding.UniGB_UCS2_H_Chinese_Simplified))
        //UniGB_UCS2_H_Chinese_Simplified
        //STSong-Light STSongStd-Light
        
        //.setBold(false);
//        .setBackgroundColor(Color.LIGHT_GRAY).bold()
        ;
		
		columnZhuStyle = stl.style(rootStyle).setBorder(stl.pen1Point().setLineColor(new Color(Integer.parseInt("d2d2fa", 16))))
	        .setHorizontalAlignment(HorizontalAlignment.CENTER)
	        .setFont(cellStyleFont.setPdfFontName("STSong-Light").setPdfEncoding(PdfEncoding.UniGB_UCS2_H_Chinese_Simplified))
	        ;
		//FontBuilder columnTitleFont = stl.font();
		
		columnTitleStyle    = stl.style(columnStyle)
		 						 .setHorizontalAlignment(HorizontalAlignment.CENTER)
		                         .setBorder(stl.pen1Point().setLineColor(Color.LIGHT_GRAY))
		                        .setVerticalAlignment(VerticalAlignment.MIDDLE)
		                         .setBackgroundColor(new Color(Integer.parseInt("e3eaf1",16)))
		                         //.setFont(cellStyleFont.setPdfFontName("MSung-Light").setPdfEncoding(PdfEncoding.UniCNS_UCS2_H_Chinese_traditional))
		                         .setFont(fontBuilder.setPdfFontName("STSong-Light").setPdfEncoding(PdfEncoding.UniGB_UCS2_H_Chinese_Simplified))
		                         .bold();
		
		
		groupStyle          = stl.style(boldStyle)
		                         .setHorizontalAlignment(HorizontalAlignment.LEFT);
		subtotalStyle       = stl.style(boldStyle)
		                         .setTopBorder(stl.pen1Point());

		ReportStyleBuilder crosstabGroupStyle      = stl.style(columnTitleStyle);
		ReportStyleBuilder crosstabGroupTotalStyle = stl.style(columnTitleStyle)
		                                          .setBackgroundColor(new Color(170, 170, 170));
		ReportStyleBuilder crosstabGrandTotalStyle = stl.style(columnTitleStyle)
		                                          .setBackgroundColor(new Color(140, 140, 140));
		ReportStyleBuilder crosstabCellStyle       = stl.style(columnStyle)
		                                          .setBorder(stl.pen1Point());

		TableOfContentsCustomizerBuilder tableOfContentsCustomizer = tableOfContentsCustomizer()
			.setHeadingStyle(0, stl.style(rootStyle).bold());

		reportTemplate = template()
		                   .setLocale(Locale.ENGLISH)
		                   .setColumnStyle(columnStyle)
		                   .setColumnTitleStyle(columnTitleStyle)
		                   .setGroupStyle(groupStyle)
		                   .setGroupTitleStyle(groupStyle)
		                   .setSubtotalStyle(subtotalStyle)
		                   .highlightDetailEvenRows()
		                   .crosstabHighlightEvenRows()
		                   .setCrosstabGroupStyle(crosstabGroupStyle)
		                   .setCrosstabGroupTotalStyle(crosstabGroupTotalStyle)
		                   .setCrosstabGrandTotalStyle(crosstabGrandTotalStyle)
		                   .setCrosstabCellStyle(crosstabCellStyle)
		                   .setTableOfContentsCustomizer(tableOfContentsCustomizer);

		currencyType = new CurrencyType();

		//HyperLinkBuilder link = hyperLink("www.dhcc.com.cn");
		String root = "";//RptFilePath.getDefaultLogo()+java.io.File.separator+"logo.jpg";
		root = "";
		//System.out.println("root==="+root);
		//D:\\apache-tomcat-6.0.24\\webapps\\itims-web-rpt\\logo.jpg
		dynamicReportsComponent =
		  cmp.horizontalList(
				  cmp.image(root).setFixedDimension(690, 40)
		  	);

//		cmp.horizontalList(
////		  		cmp.text("东华软件").setStyle(bold22CenteredStyle).setHorizontalAlignment(HorizontalAlignment.LEFT),
//		  		cmp.text("www.dhcc.com.cn").setStyle(bold12CenteredStyle).setHyperLink(link))
		footerComponent = cmp.pageXofY()
		                     .setStyle(
		                     	stl.style(boldCenteredStyle)
		                     	   .setTopBorder(stl.pen1Point()));
	}

	/**
	 * Creates custom component which is possible to add to any report band component
	 */
	public static ComponentBuilder<?, ?> createTitleComponent(String label) {
		return cmp.horizontalList()
		
//		        .add(dynamicReportsComponent)
		        	//,cmp.text("东华软件").setStyle(bold18CenteredStyle).setHorizontalAlignment(HorizontalAlignment.RIGHT))
		        .newRow()
		        .add(cmp.line())
		        .newRow()
		        //titleCenteredStyle  bold18CenteredStyle
		        .add(cmp.text(label).setStyle(titleCenteredStyle).setHorizontalAlignment(HorizontalAlignment.CENTER))
		        .newRow()
		        .add(cmp.verticalGap(5));
	}
	
	public static ComponentBuilder<?, ?> createSubTitleComponent(String label) {
		return cmp.horizontalList()
		
		        .add(
		        	
		        	cmp.text(label).setStyle(bold12CenteredStyle).setHorizontalAlignment(HorizontalAlignment.RIGHT))
		        .newRow()
		        
		        .add(cmp.verticalGap(5));
	}
	
	public static ComponentBuilder<?, ?> createLayoutSubTitleComponent(String label) {
		return cmp.horizontalList()
		
		        .add(
		        	
		        	cmp.text(label).setStyle(bold12CenteredStyle).setHorizontalAlignment(HorizontalAlignment.LEFT))
		        	
		        .newRow()
		        
		        .add(cmp.verticalGap(5));
	}

	public static CurrencyValueFormatter createCurrencyValueFormatter(String label) {
		return new CurrencyValueFormatter(label);
	}

	public static class CurrencyType extends BigDecimalType {
		private static final long serialVersionUID = 1L;

		@Override
		public String getPattern() {
			return "$ #,###.00";
		}
	}

	private static class CurrencyValueFormatter extends AbstractValueFormatter<String, Number> {
		private static final long serialVersionUID = 1L;

		private String label;

		public CurrencyValueFormatter(String label) {
			this.label = label;
		}

		public String format(Number value, ReportParameters reportParameters) {
			return label + currencyType.valueToString(value, reportParameters.getLocale());
		}
	}
}