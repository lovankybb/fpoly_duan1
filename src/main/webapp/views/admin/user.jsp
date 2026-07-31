<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quản lý Khách hàng - Atelier</title>
    <link href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/styles/user.css" rel="stylesheet">
</head>
<body>

<%@ include file="/views/fragments/sidebar.jsp" %>

<main class="admin-main-content">

    <header class="page-header">
        <h1>Quản lý Khách hàng</h1>
        <!-- Có thể dùng để thêm khách hàng hoặc xuất file Excel tùy nghiệp vụ -->
        <a href="${pageContext.request.contextPath}/admin/user/export" class="btn-add">
            <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <path d="M21 15v4a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2v-4"></path>
                <polyline points="7 10 12 15 17 10"></polyline>
                <line x1="12" y1="15" x2="12" y2="3"></line>
            </svg>
            Xuất danh sách
        </a>
    </header>

    <c:if test="${not empty alertMsg}">
        <div class="alert">${alertMsg}</div>
    </c:if>

    <div class="card">
        <div class="toolbar">
            <div class="search-box">
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"/>
                </svg>
                <input type="text" id="searchInput" placeholder="Tìm theo tên, email hoặc SĐT...">
            </div>

            <div class="filters">
                <span style="font-size: 13px; color: #888;">Tổng: ${users.size()} khách hàng</span>
            </div>
        </div>

        <table>
            <thead>
            <tr>
                <th width="35%">Khách hàng</th>
                <th width="15%">Số điện thoại</th>
                <th width="15%">Ngày tham gia</th>
                <th width="15%">Trạng thái</th>
                <th width="20%">Thao tác</th>
            </tr>
            </thead>
            <tbody id="userTableBody">

            <c:forEach var="user" items="${users}">
                <tr class="user-row">
                    <!-- Cột Khách hàng -->
                    <td>
                        <div class="user-info">
                            <!-- Lấy chữ cái đầu của tên làm avatar nếu không có ảnh -->
                            <div class="user-avatar">
                                    ${user.fullName.substring(0, 1).toUpperCase()}
                            </div>
                            <div>
                                <div class="user-name search-target">${user.fullName}</div>
                                <div class="user-email search-target">${user.email}</div>
                            </div>
                        </div>
                    </td>

                    <!-- Cột Số điện thoại -->
                    <td>
                        <div class="user-phone search-target">${user.phone != null ? user.phone : 'Chưa cập nhật'}</div>
                    </td>

                    <!-- Cột Ngày tham gia -->
                    <td>
                        <div class="join-date">${user.createdAt}</div>
                    </td>

                    <!-- Cột Trạng thái -->
                    <td>
                        <c:choose>
                            <c:when test="${user.status == 'ACTIVE'}">
                                <span class="badge active">Hoạt động</span>
                            </c:when>
                            <c:otherwise>
                                <span class="badge locked">Bị khóa</span>
                            </c:otherwise>
                        </c:choose>
                    </td>

                    <!-- Cột Thao tác -->
                    <td>
                        <a href="${pageContext.request.contextPath}/admin/user/detail?id=${user.id}" class="btn-action btn-view">Chi tiết</a>

                        <c:choose>
                            <c:when test="${user.status == 'ACTIVE'}">
                                <button class="btn-action btn-lock" value="${user.id}" onclick="confirmLock(this)">Khóa</button>
                            </c:when>
                            <c:otherwise>
                                <button class="btn-action btn-unlock" value="${user.id}" onclick="confirmUnlock(this)">Mở khóa</button>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>

            </tbody>
        </table>

        <div id="noResultMessage" class="no-result">
            Không tìm thấy khách hàng nào phù hợp với từ khóa của bạn.
        </div>

    </div>

    <!-- Phân trang -->
    <div class="pagination-container">
        <c:if test="${offset gt 0}">
            <a href="${pageContext.request.contextPath}/admin/users?offset=${offset - 10}" title="Trang trước">
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                    <circle cx="12" cy="12" r="10"/>
                    <path d="m12 8-4 4 4 4"/>
                    <path d="M16 12H8"/>
                </svg>
            </a>
        </c:if>
        <a href="${pageContext.request.contextPath}/admin/users?offset=${offset + 10}" title="Trang sau">
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <circle cx="12" cy="12" r="10"/>
                <path d="m12 16 4-4-4-4"/>
                <path d="M8 12h8"/>
            </svg>
        </a>
    </div>
</main>

<script>
    // Xử lý Tìm kiếm (Theo Tên, Email, SĐT)
    const searchInput = document.getElementById('searchInput');
    const userRows = document.querySelectorAll('.user-row');
    const noResultMessage = document.getElementById('noResultMessage');

    searchInput.addEventListener('input', function (e) {
        const searchTerm = e.target.value.toLowerCase().trim();
        let hasVisibleRow = false;

        userRows.forEach(function (row) {
            // Gom tất cả các text có class search-target (Tên + Email + SĐT) lại để tìm kiếm
            const targets = row.querySelectorAll('.search-target');
            let match = false;

            targets.forEach(target => {
                if(target.textContent.toLowerCase().includes(searchTerm)) {
                    match = true;
                }
            });

            if (match) {
                row.style.display = '';
                hasVisibleRow = true;
            } else {
                row.style.display = 'none';
            }
        });

        if (!hasVisibleRow) {
            noResultMessage.style.display = 'block';
        } else {
            noResultMessage.style.display = 'none';
        }
    });

    // Hàm xác nhận Khóa tài khoản
    function confirmLock(btn) {
        let rs = confirm('Bạn có chắc chắn muốn khóa tài khoản này? Người dùng sẽ không thể đăng nhập.');
        if (rs) {
            window.location.href = "${pageContext.request.contextPath}/admin/user/toggle-status?id=" + btn.value + "&action=lock";
        }
    }

    // Hàm xác nhận Mở khóa tài khoản
    function confirmUnlock(btn) {
        let rs = confirm('Xác nhận mở khóa cho tài khoản này?');
        if (rs) {
            window.location.href = "${pageContext.request.contextPath}/admin/user/toggle-status?id=" + btn.value + "&action=unlock";
        }
    }
</script>
</body>
</html>