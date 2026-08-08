<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quản lý Tin nhắn - Atelier</title>
    <link href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@300;400;500;600;700&display=swap"
          rel="stylesheet">

    <!-- Trỏ vào file CSS mới -->
    <link href="${pageContext.request.contextPath}/styles/message-management.css" rel="stylesheet">
</head>
<body>

<%@ include file="/views/fragments/sidebar.jsp" %>

<main class="admin-main-content">

    <header class="page-header">
        <h1>Quản lý Tin nhắn</h1>
    </header>

    <c:if test="${not empty alertMsg}">
       <c:choose>
           <c:when test="${alertMsg == 'DELETE_FAILED'}">
               <div class="alert alert-error">Xóa tin nhắn thất bại!</div>
           </c:when>
           <c:when test="${alertMsg == 'DELETE_SUCCESS'}">
               <div class="alert alert-success">Xóa tin nhắn thành công!</div>
           </c:when>
       </c:choose>
    </c:if>

    <div class="card">
        <div class="toolbar">
            <div class="search-box">
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                          d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"/>
                </svg>
                <input type="text" id="searchInput" placeholder="Nhập tên người gửi hoặc email để tìm...">
            </div>

            <div class="filters">
                <span style="font-size: 13px; color: #888;">Tổng: ${messages.size()} tin nhắn</span>
            </div>
        </div>

        <table>
            <thead>
            <tr>
                <th width="20%">Người gửi</th>
                <th width="20%">Email</th>
                <th width="25%">Tiêu đề</th>
                <th width="15%">Ngày gửi</th>
                <th width="10%">Trạng thái</th>
                <th width="10%">Thao tác</th>
            </tr>
            </thead>
            <tbody id="messageTableBody">

            <c:forEach var="msg" items="${messages}">
                <tr class="message-row">
                    <td>
                        <div class="msg-sender">${msg.sender}</div>
                    </td>
                    <td class="msg-email">${msg.email}</td>
                    <td>
                        <div style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis; max-width: 250px;"
                             title="${msg.title}">
                                ${msg.title}
                        </div>
                    </td>
                    <td>
                        ${msg.getCreateAt()}
                    </td>
                    <td>
                        <c:if test="${msg.status.name() == 'READ'}">
                            <span class="badge instock">Đã đọc</span>
                        </c:if>
                        <c:if test="${msg.status.name() == 'UNREAD'}">
                            <span class="badge outstock">Chưa đọc</span>
                        </c:if>
                    </td>
                    <td>
                        <a href="${pageContext.request.contextPath}/admin/message/detail?id=${msg.id}"
                           class="btn-action btn-manage">Xem</a>
                        <button class="btn-action btn-delete" value="${msg.id}">Xóa</button>
                    </td>
                </tr>
            </c:forEach>

            </tbody>
        </table>

        <div id="noResultMessage" class="no-result">
            Không tìm thấy tin nhắn nào phù hợp với từ khóa của bạn.
        </div>
    </div>

    <!-- Phân trang -->
    <c:if test="${offset gt 0}">
        <a href="${pageContext.request.contextPath}/admin/messages?offset=${offset - 10}">
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"
                 stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <circle cx="12" cy="12" r="10"/>
                <path d="m12 8-4 4 4 4"/>
                <path d="M16 12H8"/>
            </svg>
        </a>
    </c:if>
    <a href="${pageContext.request.contextPath}/admin/messages?offset=${offset + 10}">
        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"
             stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <circle cx="12" cy="12" r="10"/>
            <path d="m12 16 4-4-4-4"/>
            <path d="M8 12h8"/>
        </svg>
    </a>
</main>

<script>
    const searchInput = document.getElementById('searchInput');
    const messageRows = document.querySelectorAll('.message-row');
    const noResultMessage = document.getElementById('noResultMessage');

    searchInput.addEventListener('input', function (e) {
        const searchTerm = e.target.value.toLowerCase().trim();
        let hasVisibleRow = false;

        messageRows.forEach(function (row) {
            const senderName = row.querySelector('.msg-sender').textContent.toLowerCase();
            const email = row.querySelector('.msg-email').textContent.toLowerCase();

            if (senderName.includes(searchTerm) || email.includes(searchTerm)) {
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

    const deleteButtons = document.querySelectorAll('.btn-delete');
    deleteButtons.forEach(deleteButton => {
        deleteButton.addEventListener('click', (e) => {
            e.preventDefault();
            let rs = confirm('Bạn có chắc muốn xóa tin nhắn này?');
            if (rs) {
                window.location.href = "${pageContext.request.contextPath}/admin/message/delete?id=" + e.target.value;
            }
        })
    });
</script>
</body>
</html>