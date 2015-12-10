package cn.com.dhcc.adam.dailytask.datang.query;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MapperBuilder {
	/**
	 * mapper配置路径
	 */
	private static final String MAPPER_CONFIG_PATH = "cn/com/dhcc/adam/dailytask/datang/query/mapper-config.xml";
	private Configuration config;
	
	public MapperBuilder(){
		InputStream inputStream = null;
		try {
			inputStream = Resources.getResourceAsStream(MAPPER_CONFIG_PATH);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			config = sqlSessionFactory.getConfiguration();
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
			BoundSql bsql = sqlSource.getBoundSql(type);    // BoundSql bsql暂时只支持传入报表类型作为参数
			sqlMap.put(attrID, bsql);
		}
		return sqlMap;
	}

}
