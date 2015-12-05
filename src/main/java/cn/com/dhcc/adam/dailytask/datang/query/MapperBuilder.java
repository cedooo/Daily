package cn.com.dhcc.adam.dailytask.datang.query;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MapperBuilder {
	/**
	 * mapper配置路劲
	 */
	private static final String MAPPER_CONFIG_PATH = "cn/com/dhcc/adam/dailytask/datang/query/mapper-config.xml";
	private Configuration config;
	
	public Configuration getConfig() {
		return config;
	}

	public MapperBuilder(){
		try {
			InputStream inputStream = Resources.getResourceAsStream(MAPPER_CONFIG_PATH);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			config = sqlSessionFactory.getConfiguration();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	public static void main(String[] args) {
		try {
			new MapperBuilder().testMybatis();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void testMybatis() throws IOException{
		
		Collection<MappedStatement> col = config.getMappedStatements();
		System.out.println(col.size());
		for (MappedStatement mappedStatement : col) {
			Integer parameter = 1; 
			SqlSource sqlSource = mappedStatement.getSqlSource();
			BoundSql bsql = sqlSource.getBoundSql(parameter);
			System.out.println(mappedStatement.getId() + "\n" + bsql.getSql());
		}

	}
}
