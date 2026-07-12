<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Đăng nhập</title>

    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600&display=swap" rel="stylesheet">

    <style>
        /* Toàn bộ trang */
        body {
            font-family: 'Inter', sans-serif;
            background-color: #f4f7f6; /* Nền xám nhạt thanh lịch */
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            margin: 0;
            color: #333;
        }

        /* Container bao quanh form */
        .login-container {
            background-color: #ffffff;
            padding: 40px;
            border-radius: 12px;
            box-shadow: 0 10px 25px rgba(0, 0, 0, 0.05); /* Đổ bóng mịn */
            width: 100%;
            max-width: 400px;
            box-sizing: border-box;
        }

        /* Tiêu đề */
        h1 {
            font-size: 1.75rem;
            font-weight: 600;
            margin-top: 0;
            margin-bottom: 24px;
            color: #111;
            text-align: center;
            letter-spacing: -0.5px;
        }

        /* Từng cụm input */
        .form-group {
            margin-bottom: 20px;
        }

        /* Nhãn (Label) */
        label {
            display: block;
            font-size: 0.85rem;
            font-weight: 500;
            margin-bottom: 6px;
            color: #555;
        }

        /* Ô nhập liệu (Input) */
        input[type="text"],
        input[type="password"] {
            width: 100%;
            padding: 12px 14px;
            font-size: 0.95rem;
            border: 1px solid #e2e8f0; /* Viền mờ tinh tế */
            border-radius: 8px;
            background-color: #f8fafc;
            box-sizing: border-box;
            transition: all 0.2s ease;
            outline: none;
        }

        /* Hiệu ứng khi nhấn vào ô input */
        input[type="text"]:focus,
        input[type="password"]:focus {
            border-color: #4f46e5; /* Màu indigo hiện đại */
            background-color: #fff;
            box-shadow: 0 0 0 4px rgba(79, 70, 229, 0.1);
        }

        /* Nút Đăng ký */
        button[type="submit"] {
            width: 100%;
            padding: 12px;
            font-size: 1rem;
            font-weight: 500;
            color: #ffffff;
            background-color: #111111; /* Đen tối giản thanh lịch */
            border: none;
            border-radius: 8px;
            cursor: pointer;
            transition: background-color 0.2s ease;
            margin-top: 10px;
            margin-bottom: 10px;
        }

        /* Hiệu ứng rê chuột vào nút */
        button[type="submit"]:hover {
            background-color: #2d2d2d;
        }

        /* Thông báo lỗi */
        .error {
            color: #dc2626; /* Đỏ dịu mắt hơn */
            font-size: 0.8rem;
            display: block;
            margin-top: 4px;
        }

        .alert {
            padding: 14px 16px;
            border-radius: 5px;
            font-size: 14px;
            margin-bottom: 20px;
            font-weight: 500;
            background-color: #edbcbc;
            color: #b30202;
            border: 1px solid #875959;
        }

    </style>
</head>
<body>
    <div class="login-container">
        <h1>Đăng nhập</h1>
        <c:if test="${not empty alertMsg}">
            <div class="alert">${alertMsg}
            </div>
        </c:if>
        <form action="sign-in" method="post">

            <div class="form-group">
                <label>Tên đăng nhập</label>
                <input type="text" name="username" value="${oldUsername}" placeholder="Nhập tên đăng nhập"/>
                <span class="error">${usernameError}</span>
            </div>

            <div class="form-group">
                <label>Mật khẩu</label>
                <input type="password" name="password" placeholder="••••••••"/>
                <span class="error">${passwordError}</span>
            </div>

            <button type="submit">Đăng nhập</button>
            <a href="/sign-up">Chưa có tài khoản? Đăng ký ngay</a>
        </form>
    </div>

</body>
</html>