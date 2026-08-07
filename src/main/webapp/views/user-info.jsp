<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thông tin tài khoản</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/user-info.css">
</head>
<body>

<div class="profile-card">
    <!-- Form cập nhật (Method POST trỏ tới Servlet xử lý) -->
    <form action="${pageContext.request.contextPath}/update-profile" method="POST">

        <!-- Hidden ID -->
        <input type="hidden" name="id" value="${user.id}">

        <div class="avatar-section">
            <!-- Hiển thị Avatar, nếu không có dùng ảnh mặc định -->
            <img src="${not empty user.avatar ? user.avatar : 'https://ui-avatars.com/api/?name=' += user.username += '&background=f3f4f6&color=111827'}" alt="Avatar">
            <h2>${user.username}</h2>
            <p>Thành viên từ: ${user.createdAt}</p>
        </div>

        <div class="form-grid">
            <!-- Tên đăng nhập (Không cho sửa) -->
            <div class="form-group">
                <label for="username">Tên đăng nhập</label>
                <input type="text" id="username" name="username" value="${user.username}" readonly>
            </div>

            <!-- Số điện thoại -->
            <div class="form-group">
                <label for="phone">Số điện thoại</label>
                <input type="text" id="phone" name="phone" value="${user.phone}" placeholder="Nhập số điện thoại">
            </div>

            <!-- Email -->
            <div class="form-group full-width">
                <label for="email">Địa chỉ Email</label>
                <input type="email" id="email" name="email" value="${user.email}" placeholder="Nhập email">
            </div>

            <!-- Địa chỉ -->
            <div class="form-group full-width">
                <label for="address">Địa chỉ liên hệ</label>
                <input type="text" id="address" name="address" value="${user.address}" placeholder="Nhập địa chỉ của bạn">
            </div>
        </div>

        <div class="actions">
            <a href="${pageContext.request.contextPath}/change-password.jsp" class="btn btn-secondary">Đổi mật khẩu</a>
            <button type="submit" class="btn btn-primary">Lưu thay đổi</button>
        </div>

    </form>
</div>

</body>
</html>