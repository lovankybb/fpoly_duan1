<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Permissions</title>
</head>
<body>
<br/>
<h1>Permissions</h1>
<table border="1">
    <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Description</th>
        </tr>
    </thead>
    <tbody>
      <c:forEach var="permission" items="${permissions}">
        <tr>
            <td>${permission.id}</td>
            <td>${permission.name}</td>
            <td>${permission.description}</td>
        </tr>
      </c:forEach>
    </tbody>
</table>
</body>
</html>