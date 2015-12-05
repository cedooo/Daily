package cn.com.dhcc.adam.dailytask.datang.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TxtReportTemplate {
	public List<String> getReportString(String filePath) {
		List<String> listStr = new ArrayList<String>();
		File file = new File(filePath);
		try {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line;
			try {
				while ((line = br.readLine()) != null) {
					if (!"".equals(line)) {
						listStr.add(line);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (br != null) {
						br.close();
					}
					if (fr != null) {
						fr.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return listStr;
	}

	public static void main(String[] args) {
		List<String> strb = new TxtReportTemplate()
				.getReportString("src/main/java/cn/com/dhcc/adam/dailytask/datang/tools/template.txt");
		System.out.println(strb);
	}
}
