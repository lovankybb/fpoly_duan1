<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Permissions</title>
</head>
<body>
<br/>
<h1>Roles</h1>
<table border="1">
    <thead>
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Description</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="role" items="${roles}">
        <tr>
            <td>${role.id}</td>
            <td>${role.name}</td>
            <td>${role.description}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>