<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <!-- Thêm Google Fonts để chữ nhìn sang và thanh lịch hơn -->
    <link href="https://fonts.googleapis.com/css2?family=Playfair+Display:ital,wght@0,400..700;1,400..700&family=Plus+Jakarta+Sans:wght@300;400;500;600&display=swap"
          rel="stylesheet">

    <style>
        /* Reset cơ bản */
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Plus Jakarta Sans', sans-serif;
        }

        /* Container của Header */
        .site-header {
            background-color: #ffffff;
            border-bottom: 1px solid #eaeaea;
            position: sticky;
            top: 0;
            z-index: 1000;
            box-shadow: 0 2px 20px rgba(0, 0, 0, 0.02);
        }

        .header-container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 20px 25px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        /* Phần 1: Logo mang phong cách quý phái */
        .header-logo a {
            font-family: 'Playfair Display', serif;
            font-size: 26px;
            font-weight: 700;
            color: #1a1a1a;
            text-decoration: none;
            letter-spacing: 1px;
        }

        .header-logo span {
            color: #9b59b6; /* Điểm xuyết một chút màu tím hoàng gia/thanh lịch */
        }

        /* Phần 2: Thanh điều hướng (Menu) */
        .header-nav ul {
            list-style: none;
            display: flex;
            gap: 35px;
        }

        .header-nav a {
            font-size: 14px;
            font-weight: 500;
            color: #666666;
            text-decoration: none;
            text-transform: uppercase;
            letter-spacing: 1.5px;
            position: relative;
            padding: 8px 0;
            transition: color 0.3s ease;
        }

        /* Hiệu ứng gạch chân thanh mảnh khi hover */
        .header-nav a::after {
            content: '';
            position: absolute;
            bottom: 0;
            left: 0;
            width: 0;
            height: 1px;
            background-color: #1a1a1a;
            transition: width 0.3s ease;
        }

        .header-nav a:hover {
            color: #1a1a1a;
        }

        .header-nav a:hover::after {
            width: 100%;
        }

        /* Phần 3: Nút hành động hoặc User Profile */
        .header-actions {
            display: flex;
            align-items: center;
            gap: 20px;
        }

        .btn-login {
            font-size: 14px;
            color: #1a1a1a;
            text-decoration: none;
            font-weight: 500;
        }

    </style>
</head>
<body>

<header class="site-header">
    <div class="header-container">

        <!-- LOGO -->
        <div class="header-logo">
            <a href="${pageContext.request.contextPath}/">Atelier<span>.</span></a>
        </div>

        <!-- MENU ĐIỀU HƯỚNG -->
        <nav class="header-nav">
            <ul>
                <li><a href="${pageContext.request.contextPath}/">Trang chủ</a></li>
                <li><a href="${pageContext.request.contextPath}/shop">Cửa hàng</a></li>
                <li><a href="${pageContext.request.contextPath}/about">Về chúng tôi</a></li>
                <c:if test="${authentication.roles.contains('ADMIN')}">
                    <li><a href="${pageContext.request.contextPath}/admin">Quản lý</a></li>
                </c:if>
            </ul>
        </nav>

        <!-- HÀNH ĐỘNG (LOGIN / CTA) -->
        <div class="header-actions">
            <a href="${pageContext.request.contextPath}/cart">
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"
                     stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
                     class="lucide lucide-shopping-cart-icon lucide-shopping-cart">
                    <circle cx="8" cy="21" r="1"/>
                    <circle cx="19" cy="21" r="1"/>
                    <path d="M2.05 2.05h2l2.66 12.42a2 2 0 0 0 2 1.58h9.78a2 2 0 0 0 1.95-1.57l1.65-7.43H5.12"/>
                </svg>
            </a>

            <c:if test="${authentication.username != null}">

                <a href="${pageContext.request.contextPath}/profile">
                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"
                         stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
                         class="lucide lucide-user-round-icon lucide-user-round">
                        <circle cx="12" cy="8" r="5"/>
                        <path d="M20 21a8 8 0 0 0-16 0"/>
                    </svg>
                </a>
<%--                <a href="${pageContext.request.contextPath}/logout">--%>
<%--                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"--%>
<%--                         stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"--%>
<%--                         class="lucide lucide-log-out-icon lucide-log-out">--%>
<%--                        <path d="m16 17 5-5-5-5"/>--%>
<%--                        <path d="M21 12H9"/>--%>
<%--                        <path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4"/>--%>
<%--                    </svg>--%>
<%--                </a>--%>
            </c:if>
            <c:if test="${authentication.username == null}">
                <a href="${pageContext.request.contextPath}/sign-in" class="btn-login">Đăng nhập</a>
            </c:if>
        </div>

    </div>
</header>

</body>
</html>