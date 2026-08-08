<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đổi mật khẩu</title>
    <style>
        /* Reset cơ bản */
        * {
            box-sizing: border-box;
            margin: 0;
            padding: 0;
            font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Helvetica, Arial, sans-serif;
        }

        .main-content {
            background-color: #f7f9fc;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 40vh;
            width: 100%;
            margin: 30px 0 30px 0;
        }

        /* Container form */
        .password-card {
            background-color: #ffffff;
            width: 100%;
            max-width: 400px;
            padding: 40px 32px;
            border-radius: 12px;
            box-shadow: 0 8px 24px rgba(149, 157, 165, 0.1);
        }

        .password-card h2 {
            font-size: 24px;
            color: #111827;
            margin-bottom: 24px;
            font-weight: 600;
            text-align: center;
        }

        /* Nhóm form */
        .form-group {
            margin-bottom: 20px;
        }

        .form-group label {
            display: block;
            font-size: 14px;
            color: #4b5563;
            margin-bottom: 8px;
            font-weight: 500;
        }

        .input-wrapper {
            position: relative;
        }

        .input-wrapper input {
            width: 100%;
            padding: 12px 40px 12px 16px;
            font-size: 15px;
            color: #111827;
            background-color: #f9fafb;
            border: 1px solid #d1d5db;
            border-radius: 8px;
            transition: all 0.2s ease;
            outline: none;
        }

        .input-wrapper input:focus {
            background-color: #ffffff;
            border-color: #111827;
            box-shadow: 0 0 0 3px rgba(17, 24, 39, 0.1);
        }

        /* Nút ẩn/hiện mật khẩu */
        .toggle-btn {
            position: absolute;
            right: 12px;
            top: 50%;
            transform: translateY(-50%);
            background: none;
            border: none;
            color: #6b7280;
            font-size: 13px;
            cursor: pointer;
            font-weight: 500;
            padding: 4px;
        }

        .toggle-btn:hover {
            color: #111827;
        }

        /* Nút submit */
        .submit-btn {
            width: 100%;
            padding: 14px;
            background-color: #111827;
            color: #ffffff;
            border: none;
            border-radius: 8px;
            font-size: 15px;
            font-weight: 500;
            cursor: pointer;
            transition: background-color 0.2s ease;
            margin-top: 8px;
        }

        .submit-btn:hover {
            background-color: #374151;
        }

        /* Thông báo */
        .message {
            margin-top: 16px;
            font-size: 14px;
            text-align: center;
            display: none;
            padding: 10px;
            border-radius: 6px;
        }

        .message.error {
            display: block;
            background-color: #fef2f2;
            color: #dc2626;
            border: 1px solid #f87171;
        }

        .message.success {
            display: block;
            background-color: #f0fdf4;
            color: #16a34a;
            border: 1px solid #86efac;
        }
    </style>
</head>
<body>
<%@ include file="fragments/header.jsp" %>
<main class="main-content">

    <div class="password-card">
        <h2>Đổi mật khẩu</h2>
        <form action="${pageContext.request.contextPath}/user/change-pwd" method="POST">

            <!-- Mật khẩu cũ -->
            <div class="form-group">
                <label for="oldPassword">Mật khẩu hiện tại</label>
                <div class="input-wrapper">
                    <input name="password" type="password" id="oldPassword" required
                           placeholder="Nhập mật khẩu hiện tại">
                    <button type="button" class="toggle-btn" onclick="togglePassword('oldPassword', this)">Hiện</button>
                </div>
            </div>

            <!-- Mật khẩu mới -->
            <div class="form-group">
                <label for="newPassword">Mật khẩu mới</label>
                <div class="input-wrapper">
                    <input name="newPassword" type="password" id="newPassword" required placeholder="Tối thiểu 6 ký tự">
                    <button type="button" class="toggle-btn" onclick="togglePassword('newPassword', this)">Hiện</button>
                </div>
            </div>

            <!-- Xác nhận mật khẩu mới -->
            <div class="form-group">
                <label for="confirmPassword">Xác nhận mật khẩu mới</label>
                <div class="input-wrapper">
                    <input name="confirmPassword" type="password" id="confirmPassword" required
                           placeholder="Nhập lại mật khẩu mới">
                    <button type="button" class="toggle-btn" onclick="togglePassword('confirmPassword', this)">Hiện
                    </button>
                </div>
            </div>

            <button type="submit" class="submit-btn">Cập nhật mật khẩu</button>
            <c:if test="${not empty message}">

                <c:choose>
                    <c:when test="${message == 'SUCCESS'}">
                        <div class="message success">Đổi mật khẩu thành công</div>
                    </c:when>
                    <c:when test="${message == 'EMPTY'}">
                        <div class="message error">Vui lòng điền đầy đủ các trường</div>
                    </c:when>
                    <c:otherwise>
                        <div class="message error">Mật khẩu sai</div>
                    </c:otherwise>

                </c:choose>

            </c:if>
        </form>
    </div>
</main>
<%@ include file="fragments/footer.jsp" %>
<script>
    // Hàm ẩn/hiện mật khẩu
    function togglePassword(inputId, btnElement) {
        const input = document.getElementById(inputId);
        if (input.type === 'password') {
            input.type = 'text';
            btnElement.textContent = 'Ẩn';
        } else {
            input.type = 'password';
            btnElement.textContent = 'Hiện';
        }
    }
</script>

</body>
</html>