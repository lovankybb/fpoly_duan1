<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${empty authentication}">
    <c:redirect url="/sign-in"/>
</c:if>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thông tin cá nhân - Atelier Mobile</title>
    <link href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/styles/profile.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/styles/user-info.css" rel="stylesheet">
</head>
<body>

<%@ include file="fragments/header.jsp" %>

<main class="container">
    <div class="page-header">
        <h1>Thông tin cá nhân</h1>
    </div>

    <div class="profile-layout">
        <%@ include file="fragments/profile-aside.jsp" %>

        <div class="info-panels">
            <c:if test="${not empty successMsg}">
                <div class="alert alert-success">${successMsg}</div>
            </c:if>
            <c:if test="${not empty errorMsg}">
                <div class="alert alert-error">${errorMsg}</div>
            </c:if>

            <section class="card">
                <h3 class="section-title">Thông tin đang lưu</h3>
                <table class="info-table">
                    <tbody>
                    <tr>
                        <th>Tên đăng nhập</th>
                        <td>${user.username}</td>
                    </tr>
                    <tr>
                        <th>Email</th>
                        <td>${not empty user.email ? user.email : '—'}</td>
                    </tr>
                    <tr>
                        <th>Số điện thoại</th>
                        <td>${not empty user.phone ? user.phone : '—'}</td>
                    </tr>
                    <tr>
                        <th>Địa chỉ</th>
                        <td>${not empty user.address ? user.address : '—'}</td>
                    </tr>
                    <tr>
                        <th>Ngày tạo tài khoản</th>
                        <td>${not empty user.createdAt ? user.createdAt : '—'}</td>
                    </tr>
                    <tr>
                        <th>Cập nhật lần cuối</th>
                        <td>${not empty user.updatedAt ? user.updatedAt : '—'}</td>
                    </tr>
                    </tbody>
                </table>
            </section>

            <section class="card">
                <h3 class="section-title">
                    <c:choose>
                        <c:when test="${empty user.email && empty user.phone && empty user.address}">
                            Thêm thông tin liên hệ
                        </c:when>
                        <c:otherwise>
                            Cập nhật thông tin liên hệ
                        </c:otherwise>
                    </c:choose>
                </h3>

                <form action="${pageContext.request.contextPath}/user/info/save" method="post" class="info-form">
                    <input type="hidden" name="id" value="${user.id}">

                    <div class="form-grid">
                        <div class="form-group full-width">
                            <label for="username">Tên đăng nhập</label>
                            <input type="text" id="username" value="${user.username}" readonly>
                        </div>

                        <div class="form-group">
                            <label for="phone">Số điện thoại</label>
                            <input type="text" id="phone" name="phone" value="${user.phone}"
                                   placeholder="Nhập số điện thoại">
                        </div>

                        <div class="form-group">
                            <label for="email">Email</label>
                            <input type="email" id="email" name="email" value="${user.email}"
                                   placeholder="Nhập email">
                        </div>

                        <div class="form-group full-width">
                            <label for="address">Địa chỉ liên hệ</label>
                            <input type="text" id="address" name="address" value="${user.address}"
                                   placeholder="Nhập địa chỉ">
                        </div>
                    </div>

                    <div class="actions">
                        <button type="submit" class="btn btn-primary">
                            <c:choose>
                                <c:when test="${empty user.email && empty user.phone && empty user.address}">
                                    Lưu thông tin
                                </c:when>
                                <c:otherwise>
                                    Cập nhật
                                </c:otherwise>
                            </c:choose>
                        </button>
                    </div>
                </form>
            </section>

            <section class="card card-danger">
                <h3 class="section-title">Xóa thông tin liên hệ</h3>
                <p class="danger-hint">Xóa email, số điện thoại và địa chỉ đã lưu. Tài khoản đăng nhập vẫn được giữ nguyên.</p>
                <form action="${pageContext.request.contextPath}/user/info/delete" method="post"
                      onsubmit="return confirm('Bạn có chắc muốn xóa toàn bộ thông tin liên hệ?');">
                    <button type="submit" class="btn btn-danger">Xóa thông tin liên hệ</button>
                </form>
            </section>
        </div>
    </div>
</main>

<%@ include file="fragments/footer.jsp" %>
</body>
</html>
