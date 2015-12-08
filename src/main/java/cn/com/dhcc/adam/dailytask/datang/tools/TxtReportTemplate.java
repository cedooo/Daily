package cn.com.dhcc.adam.dailytask.datang.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
/**
 * 报表txt模版
 * @author cedo
 *
 */
public class TxtReportTemplate {
	public List<String> getReportString(String filePath) {
		List<String> listStr = null;
		try {
			InputStream is = TxtReportTemplate.class.getResourceAsStream(filePath);
			InputStreamReader isr = new InputStreamReader(is, "UTF-8");
			BufferedReader br = new BufferedReader(isr);
			String line;
			listStr = new ArrayList<String>();
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
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		return listStr;
	}

}
