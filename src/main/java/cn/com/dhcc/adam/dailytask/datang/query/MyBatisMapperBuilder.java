package cn.com.dhcc.adam.dailytask.datang.query;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MyBatisMapperBuilder {
	public static void main(String[] args) {
		try {
			testMybatis();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void testMybatis() throws IOException{
		InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		Configuration config = sqlSessionFactory.getConfiguration();
		MappedStatement ms = config.getMappedStatement("cn.com.dhcc.adam.dailytask.datang.query.daily.infoSysNum");
		sqlSessionFactory.openSession().selectList("", 12);
		BoundSql bsql = ms.getSqlSource().getBoundSql(21);
		System.out.println(bsql.getSql());
		
		
		List<ParameterMapping> li = bsql.getParameterMappings();
		for (ParameterMapping parameterMapping : li) {
			System.out.println(parameterMapping);
		}
		/*Collection<MappedStatement> col = config.getMappedStatements();
		for (MappedStatement mappedStatement : col) {
			System.out.println(mappedStatement.getSqlSource().getBoundSql(null).getSql());
		}*/
	}
}
