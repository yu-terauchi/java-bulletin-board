<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
 <h2>掲示板アプリケーション</h2>
<!-- 	記事内容入力フォーム	 -->
 	<form:form modelAttribute="articleForm" action="${pageContext.request.contextPath}/article/insertArticle" method="POST">
 		<form:errors path="name" cssStyle="color:red" element="div"/>
 		<label for="name">投稿者名：</label>
 		<form:input path="name" id="name"/><br>
 	
 	 	<form:errors path="content" cssStyle="color:red" element="div"/>
 		<label for="content">投稿内容：</label>
 		<form:textarea path="content" id="content"/><br>
 	
 		<input type="submit" value="記事投稿"> 
	 </form:form>
	 
<!--	投稿内容を表示	 -->
	 <c:forEach var="article" items="${articleList}">
	 	<hr>
	 	<p>ID:
	 	<c:out value="${article.id}"></c:out></p>
	 	<p>投稿者名:
	 	<c:out value="${article.name}"></c:out></p>
	 	<p>記事内容:
	 	<c:out value="${article.content}"></c:out></p>
	 	
<!-- 	削除フォーム	 -->
	 	<form action="${pageContext.request.contextPath}/article/deleteArticle" method="POST">
	 			<input type="hidden" name="articleId" value="${article.id}">
	 		<input type="submit" value="投稿を削除">
	 	</form>
	 	
<!--  コメント内容表示 	 -->
	 		<c:forEach var="comment" items="${article.commentList}">
	 		
	 			<p>コメントID:
	 				<c:out value="${comment.id}"></c:out><br>
	 			コメント者名:
	 				<c:out value="${comment.name}"></c:out><br>
	 			コメント内容:
	 				<c:out value="${comment.content}"></c:out></p>
	 		</c:forEach>
<!--	コメント入力フォーム 	-->
	 		<form:form modelAttribute="commentForm" action="${pageContext.request.contextPath}/article/insertComment"  method="POST">
	 			<form:hidden path="articleId" value="${article.id}" />
	 			<c:if test="${article.id == commentForm.articleId}">
	 			<form:errors path="name" cssStyle="color:red" element="div"/>
	 			</c:if>
	 			<label for="name">名前：</label>
 				<form:input path="name" id="name"/><br>
 				
 				<c:if test="${article.id == commentForm.articleId}">
	 			<form:errors path="content" cssStyle="color:red" element="div"/>
	 			</c:if>
 				<label for="content">コメント：</label>
 				<form:textarea path="content" id="content"/><br>
 	
 				<input type="submit" value="コメントを投稿"> 
	 		</form:form>
	 	
	 </c:forEach>
	 
</body>
</html>