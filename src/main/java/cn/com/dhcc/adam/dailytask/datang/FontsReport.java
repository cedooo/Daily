package cn.com.dhcc.adam.dailytask.datang;

import static net.sf.dynamicreports.report.builder.DynamicReports.*;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.exception.DRException;

public class FontsReport {
	public FontsReport() {
		build();
	}

	private void build() {
		StyleBuilder plainStyle = stl.style().setFontName("Meiryo");
		StyleBuilder boldStyle = stl.style(plainStyle).bold();
		StyleBuilder italicStyle = stl.style(plainStyle).italic();
		StyleBuilder boldItalicStyle = stl.style(plainStyle).boldItalic();
		try {
			report().title(
					cmp.text("FreeUniversal font - plain").setStyle(plainStyle),
					cmp.text("FreeUniversal font - bold").setStyle(boldStyle),
					cmp.text("FreeUniversal font - italic").setStyle(
							italicStyle),
					cmp.text("FreeUniversal font - bolditalic").setStyle(
							boldItalicStyle))
					.pageHeader(
							cmp.text("FreeUniversal font - plain").setStyle(plainStyle)
					)
					.show();
		} catch (DRException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new FontsReport();
	}

}
