<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
                            <a href="${pageContext.request.contextPath}/admin/users?userId=${user.id}"
                               class="btn-action btn-edit">Chi tiết</a>
                            <c:if test="${user.username != 'admin'}">
                                <a href="${pageContext.request.contextPath}/admin/user/delete?id=${user.id}"
                                   class="btn-action btn-delete"
                                   onclick="return confirm('Xóa người dùng này? Hành động không thể hoàn tác.');">Xóa</a>
                            </c:if>
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
                    <h2 class="card-title">Chi tiết khách hàng</h2>

            <div>
                <div class="form-group">
                   <strong style="font-size: 20px">${userDetail.username}</strong>
                </div>

                <div class="form-group">
                    <strong>Email: ${userDetail.email != null ? userDetail.email : '—'}</strong>
                </div>
                <div class="form-group">
                    <strong>Số điện thọai: ${userDetail.phone != null ? userDetail.phone : '—'}</strong>
                </div>

                <div class="form-group">
                    <strong>Địa chỉ: ${userDetail.address != null ? userDetail.address : '—'}</strong>
                </div>

                <div class="form-group">
                    <strong>Tổng đơn hàng: ${totalOrder}</strong>
                </div>
                <div class="form-group">
                    <strong style="color: #0cbc0c">Hoàn thành: ${userSpend.completedOrder}</strong>
                </div>
                <div class="form-group">
                    <p>Tổng chi tiêu: </p>
                    <c:choose>
                        <c:when test="${userSpend.totalSpend gt 0}">
                            <p class="total-spend js-format-price" data-price="${userSpend.totalSpend}"></p>
                        </c:when>
                        <c:otherwise>
                            <p class="total-spend">0.00đ</p>
                        </c:otherwise>
                    </c:choose>

                </div>
            </div>
        </div>
    </div>
    <c:if test="${offset gt 0}">
        <a href="${pageContext.request.contextPath}/admin/users?offset=${offset - 10}">
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"
                 stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
                 class="lucide lucide-circle-arrow-left-icon lucide-circle-arrow-left">
                <circle cx="12" cy="12" r="10"/>
                <path d="m12 8-4 4 4 4"/>
                <path d="M16 12H8"/>
            </svg>
        </a>
    </c:if>
    <a href="${pageContext.request.contextPath}/admin/users?offset=${offset + 10}">
        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"
             stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
             class="lucide lucide-circle-arrow-right-icon lucide-circle-arrow-right">
            <circle cx="12" cy="12" r="10"/>
            <path d="m12 16 4-4-4-4"/>
            <path d="M8 12h8"/>
        </svg>
    </a>
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


    document.addEventListener("DOMContentLoaded", function () {
        // Tìm tất cả các thẻ có class js-format-price
        const priceElements = document.querySelectorAll('.js-format-price');

        priceElements.forEach(function (el) {
            // Lấy con số từ data-price
            const rawPrice = Number(el.getAttribute('data-price'));

            // Nếu có giá trị hợp lệ thì format và gán lại
            if (!isNaN(rawPrice) && rawPrice > 0) {
                el.innerText = rawPrice.toLocaleString('vi-VN') + ' ₫';
            }
        });
    });
</script>
</body>
</html>
