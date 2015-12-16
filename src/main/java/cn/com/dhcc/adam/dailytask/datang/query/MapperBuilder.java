package cn.com.dhcc.adam.dailytask.datang.query;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.session.Configuration;

public class MapperBuilder {
	/**
	 * mapper配置路径
	 */
	private static final String MAPPER_CONFIG_PATH = "mapper-config.xml";
	private Configuration config;
	
	public MapperBuilder(){
		InputStream inputStream = null;
		try {
			inputStream = Resources.getResourceAsStream(MAPPER_CONFIG_PATH);
			/*
			 *  Mybatis XMLConfigBuilder http://mybatis.org/mybatis-3/zh/java-api.html
			 */
			XMLConfigBuilder parser = new XMLConfigBuilder(inputStream);
			config = parser.parse();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if(inputStream!=null){
					inputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public Map<String, BoundSql> getSqls(int type){
		Collection<MappedStatement> mappedStatements = config
				.getMappedStatements();
		Map<String, BoundSql> sqlMap = new HashMap<String,BoundSql>();
		for (MappedStatement mappedStatement : mappedStatements) {
			String attrID = mappedStatement.getId().split("\\.")[1];
			SqlSource sqlSource = mappedStatement.getSqlSource();
			BoundSql bsql = sqlSource.getBoundSql(type);
			sqlMap.put(attrID, bsql);
		}
		return sqlMap;
	}

}
