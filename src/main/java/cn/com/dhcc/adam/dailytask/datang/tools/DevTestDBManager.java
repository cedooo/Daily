package cn.com.dhcc.adam.dailytask.datang.tools;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.mchange.v2.c3p0.ComboPooledDataSource;


public class DevTestDBManager {
	private static final Logger logger = Logger.getLogger(DevTestDBManager.class);

	private static ComboPooledDataSource cpds;
	static{
		cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass( "com.mysql.jdbc.Driver" );
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}          
		cpds.setJdbcUrl("jdbc:mysql://localhost:3306/dmsn_998" );
		cpds.setUser("root");                                  
		cpds.setPassword("root");                                  
			
		// the settings below are optional -- c3p0 can work with defaults
		/*cpds.setMinPoolSize(5);                                     
		cpds.setAcquireIncrement(5);
		cpds.setMaxPoolSize(20);*/
	}
	
	public static List<Map<String, String>> executeSQLInPooledDBSource(String sql){
	
		List<Map<String, String>> listMaps = new ArrayList<Map<String, String>>();
		try {
			Connection conn = cpds.getConnection();
			Statement sta = conn.createStatement();
			ResultSet result = sta.executeQuery(sql);
			ResultSetMetaData metaData = result.getMetaData();
			String[] columnsName = new String[metaData.getColumnCount()];
			for (int i=0; i< columnsName.length; i++) {
				columnsName[i] = metaData.getColumnLabel(i+1);
			}
			//System.out.println(Arrays.toString(columnsName));
			while(result.next()){
				Map<String, String> maps = new HashMap<String, String>();
				for (int i = 0; i < columnsName.length; i++) {
					String value = result.getString(columnsName[i]);
					maps.put(columnsName[i], value);
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
		/*String attr[] = new String[2];
		attr[0] = "tot";
		attr[1] = "dy";
		System.out.println(DevTestDBManager.executeSQLInPooledDBSource("select count(*) as tot ,now() as dy  from v_azy_temp_hum", attr));*/
		String ssql = "SELECT mat.accessSys as accessMostSys, mat.access as accessMost, " + 
				 "mit.accessSys as accessLeastSys, mit.access as accessLeast " + 
			 "FROM " + 
			 "( " + 
			 "	SELECT  accessSys,access " + 
			 "FROM( " + 
			 "SELECT cm.m_name as accessSys, sum(in_flows+out_flows) as access " + 
			 "FROM data_monitor_traffic_m tm  LEFT JOIN cfg_monitor cm   ON  tm.m_id = cm.m_id  " + 
			 "group by  cm.m_name   " + 
			 "	ORDER BY  sum(in_flows+out_flows) DESC " + 
			 ")t " + 
			 "WHERE t.accessSys IS NOT NULL " + 
			 "LIMIT 1  " + 
			 ")mat, " + 
			 "( " + 
			 "SELECT  accessSys,access " + 
			 "FROM( " + 
			 "SELECT cm.m_name as accessSys, sum(in_flows+out_flows) as access " + 
			 "FROM data_monitor_traffic_m tm  LEFT JOIN cfg_monitor cm   ON  tm.m_id = cm.m_id  " + 
			 "group by  cm.m_name   " + 
			 "ORDER BY  sum(in_flows+out_flows) ASC " + 
			 ")t " + 
			 "WHERE t.accessSys IS NOT NULL " + 
			 "LIMIT 1  " + 
			 ")mit";
		logger.info("exclude attr array: " + DevTestDBManager.executeSQLInPooledDBSource(ssql));
	}
	
}
