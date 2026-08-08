<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quản lý Người dùng - Admin</title>
    <link href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/styles/user.css" rel="stylesheet">
</head>
<body>

<%@ include file="/views/fragments/sidebar.jsp" %>

<main class="admin-main-content">
    <header class="page-header">
        <h1>Quản lý Người dùng</h1>
    </header>

    <c:if test="${not empty errorMsg}">
        <div class="alert alert-error">${errorMsg}</div>
    </c:if>
    <c:if test="${not empty successMsg}">
        <div class="alert alert-success">${successMsg}</div>
    </c:if>

    <div class="crud-layout">
        <div class="card">
            <h2 class="card-title">Danh sách người dùng</h2>

            <div class="toolbar">
                <div class="search-box">
                    <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                              d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"/>
                    </svg>
                    <input type="text" id="searchInput" placeholder="Tìm theo tên đăng nhập, email, SĐT...">
                </div>
                <span class="user-count">Tổng: ${users.size()} người dùng</span>
            </div>

            <table>
                <thead>
                <tr>
                    <th>Tên đăng nhập</th>
                    <th>Email</th>
                    <th>Số điện thoại</th>
                    <th>Ngày tạo</th>
                    <th>Thao tác</th>
                </tr>
                </thead>
                <tbody id="userTableBody">
                <c:forEach var="user" items="${users}">
                    <tr class="user-row">
                        <td>
                            <div class="user-info">
                                <div class="user-avatar search-target">
                                    ${user.username.substring(0, 1).toUpperCase()}
                                </div>
                                <div class="user-name search-target">${user.username}</div>
                            </div>
                        </td>
                        <td><span class="search-target">${user.email != null ? user.email : '—'}</span></td>
                        <td><span class="search-target">${user.phone != null ? user.phone : '—'}</span></td>
                        <td>
                            <c:choose>
                                <c:when test="${not empty user.createdAt}">${user.createdAt}</c:when>
                                <c:otherwise>—</c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <a href="${pageContext.request.contextPath}/admin/users?editId=${user.id}"
                               class="btn-action btn-edit">Sửa</a>
                            <a href="${pageContext.request.contextPath}/admin/user/delete?id=${user.id}"
                               class="btn-action btn-delete"
                               onclick="return confirm('Xóa người dùng này? Hành động không thể hoàn tác.');">Xóa</a>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty users}">
                    <tr>
                        <td colspan="5" class="empty-cell">Chưa có người dùng nào.</td>
                    </tr>
                </c:if>
                </tbody>
            </table>
            <div id="noResultMessage" class="no-result">
                Không tìm thấy người dùng phù hợp.
            </div>
        </div>

        <div class="card form-card">
            <c:choose>
                <c:when test="${not empty editUser}">
                    <h2 class="card-title">Cập nhật người dùng</h2>
                    <c:set var="formAction" value="${pageContext.request.contextPath}/admin/user/update"/>
                </c:when>
                <c:otherwise>
                    <h2 class="card-title">Thêm người dùng</h2>
                    <c:set var="formAction" value="${pageContext.request.contextPath}/admin/user/add"/>
                </c:otherwise>
            </c:choose>

            <form action="${formAction}" method="post">
                <c:if test="${not empty editUser}">
                    <input type="hidden" name="id" value="${editUser.id}">
                </c:if>

                <div class="form-group">
                    <label for="username">Tên đăng nhập <span class="required">*</span></label>
                    <input type="text" id="username" name="username"
                           value="${editUser.username}" required
                           autocomplete="off">
                </div>

                <div class="form-group">
                    <label for="password">
                        Mật khẩu
                        <c:if test="${empty editUser}"><span class="required">*</span></c:if>
                    </label>
                    <input type="password" id="password" name="password"
                           minlength="6"
                           placeholder="${not empty editUser ? 'Để trống nếu không đổi mật khẩu' : ''}"
                           ${empty editUser ? 'required' : ''}
                           autocomplete="new-password">
                </div>

                <div class="form-group">
                    <label for="email">Email</label>
                    <input type="email" id="email" name="email" value="${editUser.email}">
                </div>

                <div class="form-group">
                    <label for="phone">Số điện thoại</label>
                    <input type="text" id="phone" name="phone" value="${editUser.phone}">
                </div>

                <div class="form-group">
                    <label for="address">Địa chỉ</label>
                    <textarea id="address" name="address" rows="3">${editUser.address}</textarea>
                </div>

                <button type="submit" class="btn btn-save">
                    ${not empty editUser ? 'Lưu thay đổi' : 'Thêm mới'}
                </button>
                <c:if test="${not empty editUser}">
                    <a href="${pageContext.request.contextPath}/admin/users" class="btn btn-cancel">Hủy</a>
                </c:if>
            </form>
        </div>
    </div>
</main>

<script>
    const searchInput = document.getElementById('searchInput');
    const userRows = document.querySelectorAll('.user-row');
    const noResultMessage = document.getElementById('noResultMessage');

    if (searchInput) {
        searchInput.addEventListener('input', function (e) {
            const searchTerm = e.target.value.toLowerCase().trim();
            let hasVisibleRow = false;

            userRows.forEach(function (row) {
                const targets = row.querySelectorAll('.search-target');
                let match = false;
                targets.forEach(target => {
                    if (target.textContent.toLowerCase().includes(searchTerm)) {
                        match = true;
                    }
                });
                row.style.display = match ? '' : 'none';
                if (match) hasVisibleRow = true;
            });

            if (userRows.length > 0) {
                noResultMessage.style.display = hasVisibleRow ? 'none' : 'block';
            }
        });
    }
</script>
</body>
</html>
