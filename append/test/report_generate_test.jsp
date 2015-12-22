<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"
	import="cn.com.dhcc.adam.dailytask.datang.tools.DevTestDBManager,java.util.*,cn.com.dhcc.adam.dailytask.datang.GenerateReport,net.sf.dynamicreports.jasper.builder.JasperReportBuilder"
	isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String sql = "SELECT COUNT(*) AS runSys , now() as dt FROM tcmo WHERE fmotype LIKE 'ap%' AND fpmosn = -1";
	//List<Map<String, String>> listRe = DevTestDBManager.executeSQLInPooledDBSource(sql);
	//JdbcAbstractTemplate jat =  new JdbcAbstractTemplate(DomainManager.getDbIdByDmsn(998));
	//List<Map<String, String>> listRe = jat.getListForMap(sql);
	
	GenerateReport gr = new GenerateReport();
	String path = "/itims/release/cloudCore/workfiles/rptexporter/998/rpttaskreal/day";
	String dmsn = "998";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<TITLE>report test</TITLE>
<BODY text=#000000 bgColor=#ffffff>
	<%-- 测试数据库连接:
	<%=listRe.toString()%> --%>
	<hr />
	测试生成报表:
	<%=gr.build(path, "test-daily", dmsn,
					GenerateReport.TYPE_DAILY).toString()%>
	<%=gr.build(path, "test-weekly", dmsn,
					GenerateReport.TYPE_DAILY).toString()%>
	<%=gr.build(path, "test-monthly", dmsn,
					GenerateReport.TYPE_DAILY).toString()%>
</BODY>
</HTML>