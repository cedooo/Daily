package cn.com.dhcc.adam.dailytask.datang;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;

public class DocxReport {
	public JasperReportBuilder reportTemplate(String file){
		FileInputStream fileIS = null;
		try {
			fileIS = new FileInputStream("D:/ttt.docx");
			XWPFDocument  word = new XWPFDocument(fileIS) ;
			List<IBodyElement> list = word.getBodyElements();
			for (IBodyElement iBodyElement : list) {
				if(iBodyElement instanceof XWPFParagraph){
					System.out.println(((XWPFParagraph) iBodyElement).getParagraphText());
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(fileIS!=null){
				try {
					fileIS.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
}
