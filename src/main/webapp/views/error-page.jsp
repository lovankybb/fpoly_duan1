<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thông Báo Lỗi</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f4f6f9;
            color: #333;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }

        .error-container {
            text-align: center;
            background-color: #ffffff;
            padding: 40px 60px;
            border-radius: 12px;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
            max-width: 500px;
            width: 100%;
        }

        /* Phần 1: Mã lỗi */
        .error-code {
            font-size: 96px;
            font-weight: 800;
            color: #e74c3c;
            margin: 0;
            line-height: 1;
        }

        /* Phần 2: Tin nhắn thân thiện */
        .error-message {
            margin-top: 20px;
        }

        .error-title {
            font-size: 24px;
            font-weight: 600;
            color: #2c3e50;
            margin-bottom: 10px;
        }

        .error-description {
            font-size: 16px;
            color: #7f8c8d;
            line-height: 1.6;
            margin-bottom: 30px;
        }

        /* Nút quay lại */
        .btn-home {
            display: inline-block;
            padding: 12px 30px;
            background-color: #3498db;
            color: #ffffff;
            text-decoration: none;
            border-radius: 25px;
            font-weight: 600;
            transition: background-color 0.3s ease;
        }

        .btn-home:hover {
            background-color: #2980b9;
        }
    </style>
</head>
<body>


<%@include file="fragments/header.jsp" %>

<main>
    <div class="error-container">
        <div class="error-code">${errorCode}</div>

        <div class="error-message">
            <div class="error-title">${errorTitle}</div>
            <div class="error-description">
                ${errorDesc}
            </div>
        </div>

        <a href="${pageContext.request.contextPath}/home" class="btn-home">Quay về Trang chủ</a>
    </div>
</main>

</body>
</html>