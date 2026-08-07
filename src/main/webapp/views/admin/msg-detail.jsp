<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Chi tiết Tin nhắn - Atelier</title>
    <link href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@300;400;500;600;700&display=swap" rel="stylesheet">

    <!-- Trỏ vào file CSS mới -->
    <link href="${pageContext.request.contextPath}/styles/message-management.css" rel="stylesheet">
</head>
<body>

<%@ include file="/views/fragments/sidebar.jsp" %>

<main class="admin-main-content">

    <header class="page-header">
        <h1>Chi tiết Tin nhắn</h1>
        <a href="${pageContext.request.contextPath}/admin/messages" class="btn-add btn-back">
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor" width="18" height="18">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 19l-7-7m0 0l7-7m-7 7h18"/>
            </svg>
            Quay lại
        </a>
    </header>

    <div class="card">
        <div class="msg-detail-header">
            <h2 class="msg-title-display">${msg.title}</h2>
            <div class="msg-meta">
                <div class="msg-meta-item">
                    Người gửi: <strong>${msg.sender}</strong>
                </div>
                <div class="msg-meta-item">
                    Email: <strong><a href="mailto:${msg.email}" style="color: #3498db; text-decoration: none;">${msg.email}</a></strong>
                </div>
                <div class="msg-meta-item">
                    Ngày gửi: <strong><fmt:formatDate value="${msg.getCreatedDateForJsp()}" pattern="dd/MM/yyyy HH:mm" /></strong>
                </div>
                <div class="msg-meta-item">
                    Trạng thái:
                    <c:if test="${msg.status.name() == 'READ'}">
                        <span class="badge instock">Đã đọc</span>
                    </c:if>
                    <c:if test="${msg.status.name() == 'UNREAD'}">
                        <span class="badge outstock">Chưa đọc</span>
                    </c:if>
                </div>
            </div>
        </div>

        <div class="msg-body">
            ${msg.message}
        </div>

        <div class="actions-footer">
            <button onclick="confirmDelete(${msg.id})" class="btn-add" style="background-color: #e74c3c;">
                Xóa tin nhắn
            </button>
        </div>
    </div>
</main>

<script>
    function confirmDelete(id) {
        let rs = confirm('Bạn có chắc muốn xóa tin nhắn này? Hành động này không thể hoàn tác.');
        if (rs) {
            window.location.href = "${pageContext.request.contextPath}/admin/message/delete?id=" + id;
        }
    }
</script>
</body>
</html>