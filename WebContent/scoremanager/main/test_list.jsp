<%-- 科目一覧JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>成績参照システム</title>
</head>
<body>
	<h1>成績参照</h1>

	<h3>科目情報</h3>
	<form action = "TestListSubjectExecute.action" method="post">
		<label>入学年度</label>
		<select name="ent_year">
			<option value="0">--------</option>
			<c:forEach var="year" items="${ent_year_set}">
				<%-- 現在のyearと選択されていたent_yearが一致していた場合selectedを追記 --%>
				<option value="${year}" <c:if test="${year==ent_year}">selected</c:if>>${year}</option>
			</c:forEach>
		</select>
		<div>${errors.get("ent_year")}</div>

		<label>クラス</label>
		<select name="class_num">
			<c:forEach var="num" items="${class_num_set}">
				<%-- 現在のnumと選択されていたclass_numが一致していた場合selectedを追記 --%>
				<option value="${num}" <c:if test="${num==class_num}">selected</c:if>>${num}</option>
			</c:forEach>
		</select>

		<label>科目</label>
		<select name="subject_name">
			<c:forEach var="num" items="${subject_list_set}">
				<%-- 現在のnumと選択されていたsubject_nameが一致していた場合selectedを追記 --%>
				<option value="${num}" <c:if test="${num==subject_num}">selected</c:if>>${num}</option>
			</c:forEach>
		</select>
		<input type="submit" value="検索">
	</form>

	<h3>学生情報</h3>
	<form action = "TestListStudentExecute.action" method="post">
		<label>学生番号</label>
		<input type="text" name="no"
			placeholder="学生番号を入力してください" maxlength="10" value="${no}" required />
		<div>${errors.get("no")}</div>
		<input type="submit" value="検索">
	</form>

	<a href="Menu.action">戻る</a>

</body>
</html>