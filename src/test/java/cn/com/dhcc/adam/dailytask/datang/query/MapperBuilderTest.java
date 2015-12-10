package cn.com.dhcc.adam.dailytask.datang.query;

import java.util.Map;

import org.apache.ibatis.mapping.BoundSql;

import junit.framework.TestCase;

public class MapperBuilderTest extends TestCase {
	private MapperBuilder mb = new MapperBuilder();
	public void testGetSqls() {
		Map<String, BoundSql> msb = mb.getSqls(1);
		System.out.println(msb.size());
		for (String key : msb.keySet()) {
			System.out.println(key + ":" + msb.get(key));
		}
	}

}
