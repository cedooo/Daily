package cn.com.dhcc.adam.dailytask.datang.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DevTestDBManager {

	
	public static String executeSQL(String sql, String attribute){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.0.104:3306/dmsn_998","root","root");
			Statement sta = conn.createStatement();
			ResultSet result = sta.executeQuery(sql);
			while(result.next()){
				return result.getString(attribute);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "";
		
	}
	
	public static void main(String[] args) {
		System.out
				.println(DevTestDBManager
						.executeSQL("select count(*) as value from v_azy_temp_hum", "value"));
	}
	
}
