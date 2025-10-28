<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>home page</title>
</head>
<body>
    
    <c:if test="${not empty sessionScope.currentUserName}">
        <h1>hello ${sessionScope.currentUserName}!</h1>
        <a href="logout">Logout</a>
    </c:if>

    <c:if test="${empty sessionScope.currentUserName}">
        <h2>Kindly Login to Goto the page.</h2>
        <a href="login">Login</a>
    </c:if>
</body>
</html>