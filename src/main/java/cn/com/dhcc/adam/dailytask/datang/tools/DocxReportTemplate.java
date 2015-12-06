package cn.com.dhcc.adam.dailytask.datang.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

public class DocxReportTemplate {
	public List<String> reportTemplate(String filePath){
		List<String> listStr = new ArrayList<String>();
		FileInputStream fileIS = null;
		try {
			File file = new File(filePath);
			fileIS = new FileInputStream(file);
			XWPFDocument  word = new XWPFDocument(fileIS);
			List<IBodyElement> list = word.getBodyElements();
			for (IBodyElement iBodyElement : list) {
				if(iBodyElement instanceof XWPFParagraph){
					String paragraph = ((XWPFParagraph) iBodyElement).getParagraphText();
					listStr.add(paragraph);
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
		return listStr;
	}
	public static void main(String[] args) {
		String filePath = "D:/ddd.docx";
		System.out
				.println(new DocxReportTemplate().reportTemplate(filePath));
	}
}
