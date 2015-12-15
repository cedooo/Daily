package cn.com.dhcc.adam.dailytask.datang.tools;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mchange.v2.c3p0.ComboPooledDataSource;


public class DevTestDBManager {

	
	public static List<Map<String, String>> executeSQL(String sql, String[] attribute){
		List<Map<String, String>> listMaps = new ArrayList<Map<String, String>>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dmsn_998","root","root");
			Statement sta = conn.createStatement();
			ResultSet result = sta.executeQuery(sql);
			while(result.next()){
				System.err.println(result.getCursorName());
				Map<String, String> maps = new HashMap<String, String>();
				for (int i = 0; i < attribute.length; i++) {
					String attr = attribute[i];
					String value = result.getString(attr);
					maps.put(attribute[i], value);
				}
				listMaps.add(maps);
			}
			conn.close();
			return listMaps;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	private static ComboPooledDataSource cpds;
	static{
		cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass( "com.mysql.jdbc.Driver" );
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		} //loads the jdbc driver            
		cpds.setJdbcUrl( "jdbc:mysql://localhost:3306/dmsn_998" );
		cpds.setUser("root");                                  
		cpds.setPassword("root");                                  
			
		// the settings below are optional -- c3p0 can work with defaults
		cpds.setMinPoolSize(5);                                     
		cpds.setAcquireIncrement(5);
		cpds.setMaxPoolSize(20);
	}
	
	public static List<Map<String, String>> executeSQLInPooledDBSource(String sql, String[] attribute){
	
		
		
		List<Map<String, String>> listMaps = new ArrayList<Map<String, String>>();
		try {
			Connection conn = cpds.getConnection();
			Statement sta = conn.createStatement();
			ResultSet result = sta.executeQuery(sql);
			ResultSetMetaData metaData = result.getMetaData();
			String[] columnsName = new String[metaData.getColumnCount()];
			for (int i=0; i< columnsName.length; i++) {
				columnsName[i] = metaData.getColumnName(i+1);
			}
			System.out.println(Arrays.toString(columnsName));
			while(result.next()){
				Map<String, String> maps = new HashMap<String, String>();
				for (int i = 0; i < columnsName.length; i++) {
					String attr = attribute[i];
					String value = result.getString(attr);
					maps.put(attribute[i], value);
				}
				listMaps.add(maps);
			}
			conn.close();
			return listMaps;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static void main(String[] args) {
		String attr[] = new String[2];
		attr[0] = "tot";
		attr[1] = "dy";
		System.out
				.println(DevTestDBManager
						.executeSQLInPooledDBSource("select count(*) as tot ,now() as dy  from v_azy_temp_hum", attr)
						);
	}
	
}
