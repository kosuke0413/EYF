<%-- 科目更新JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>得点管理システム</title>
</head>
<body>
	<h2>科目情報削除</h2>
	<form action = "SubjectDeleteExecute.action" method="post">


		<label>科目番号</label>
		<input type="hidden" name="cd" value="${cd}">
	   <p>${cd}</p>
		<p>を削除しますか？</p>

		<input type="submit" value="削除">

	</form>

	<a href="Menu.action">戻る</a>
</body>
</html>