<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <style>
        /* Container chính của Footer */
        .site-footer {
            background-color: #fafdff; /* Màu nền trắng xám nhẹ nhàng, thanh lịch */
            border-top: 1px solid #eaeaea;
            padding: 60px 0 30px 0;
            font-family: 'Plus Jakarta Sans', sans-serif;
            margin-top: auto; /* Đẩy footer xuống đáy nếu trang ít nội dung */
        }

        .footer-container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 0 25px;
            display: grid;
            grid-template-columns: 2fr 1fr 1fr 1.5fr; /* Chia tỉ lệ các cột */
            gap: 40px;
        }

        /* Cột 1: Giới thiệu thương hiệu */
        .footer-brand .brand-logo {
            font-family: 'Playfair Display', serif;
            font-size: 24px;
            font-weight: 700;
            color: #1a1a1a;
            margin-bottom: 15px;
            letter-spacing: 1px;
        }
        .footer-brand .brand-logo span {
            color: #9b59b6;
        }
        .footer-brand .brand-desc {
            font-size: 14px;
            color: #777777;
            line-height: 1.6;
            max-width: 280px;
        }

        /* Các cột Links */
        .footer-column h4 {
            font-size: 13px;
            font-weight: 600;
            text-transform: uppercase;
            letter-spacing: 1.5px;
            color: #1a1a1a;
            margin-bottom: 20px;
        }

        .footer-column ul {
            list-style: none;
            padding: 0;
            margin: 0;
        }

        .footer-column ul li {
            margin-bottom: 12px;
        }

        .footer-column ul a {
            font-size: 14px;
            color: #666666;
            text-decoration: none;
            transition: color 0.3s ease, padding-left 0.3s ease;
        }

        /* Hiệu ứng di chuột tinh tế */
        .footer-column ul a:hover {
            color: #1a1a1a;
            padding-left: 4px;
        }

        /* Cột nhận bản tin (Newsletter) */
        .newsletter-text {
            font-size: 14px;
            color: #777777;
            margin-bottom: 15px;
            line-height: 1.5;
        }
        .newsletter-form {
            display: flex;
            border-bottom: 1px solid #1a1a1a;
            padding-bottom: 5px;
        }
        .newsletter-input {
            border: none;
            background: transparent;
            font-size: 13px;
            width: 100%;
            padding: 5px 0;
            outline: none;
            font-family: inherit;
        }
        .newsletter-btn {
            border: none;
            background: transparent;
            font-size: 12px;
            font-weight: 600;
            text-transform: uppercase;
            letter-spacing: 1px;
            cursor: pointer;
            color: #1a1a1a;
            padding: 0 5px;
        }

        /* Dòng bản quyền bên dưới cùng */
        .footer-bottom {
            max-width: 1200px;
            margin: 50px auto 0 auto;
            padding: 25px 25px 0 25px;
            border-top: 1px solid #f0f0f0;
            display: flex;
            justify-content: space-between;
            align-items: center;
            font-size: 12px;
            color: #999999;
        }
        .footer-bottom-links a {
            color: #999999;
            text-decoration: none;
            margin-left: 20px;
            transition: color 0.3s ease;
        }
        .footer-bottom-links a:hover {
            color: #1a1a1a;
        }

        /* Responsive cho màn hình điện thoại */
        @media (max-width: 768px) {
            .footer-container {
                grid-template-columns: 1fr;
                gap: 30px;
            }
            .footer-bottom {
                flex-direction: column;
                gap: 15px;
                text-align: center;
            }
            .footer-bottom-links a {
                margin: 0 10px;
            }
        }
    </style>
</head>
<body>

<footer class="site-footer">
    <div class="footer-container">

        <!-- Cột 1: Thương hiệu -->
        <div class="footer-brand">
            <div class="brand-logo">Atelier<span>.</span></div>
            <p class="brand-desc">Mang đến trải nghiệm không gian số tối giản, tinh tế và hiện đại trong từng chi tiết sản phẩm.</p>
        </div>

        <!-- Cột 2: Điều hướng nhanh -->
        <div class="footer-column">
            <h4>Khám phá</h4>
            <ul>
                <li><a href="${pageContext.request.contextPath}/collections">Bộ sưu tập</a></li>
                <li><a href="${pageContext.request.contextPath}/about">Về chúng tôi</a></li>
                <li><a href="${pageContext.request.contextPath}/blog">Tạp chí</a></li>
            </ul>
        </div>

        <!-- Cột 3: Hỗ trợ khách hàng -->
        <div class="footer-column">
            <h4>Dịch vụ</h4>
            <ul>
                <li><a href="${pageContext.request.contextPath}/contact">Liên hệ</a></li>
                <li><a href="${pageContext.request.contextPath}/faq">Câu hỏi thường gặp</a></li>
                <li><a href="${pageContext.request.contextPath}/privacy">Chính sách bảo mật</a></li>
            </ul>
        </div>

        <!-- Cột 4: Đăng ký nhận tin tức -->
        <div class="footer-column">
            <h4>Bản tin</h4>
            <p class="newsletter-text">Đăng ký để nhận thông tin về các bộ sưu tập mới nhất và ưu đãi đặc quyền.</p>
            <form class="newsletter-form" action="#" method="post">
                <input type="email" class="newsletter-input" placeholder="Địa chỉ email của bạn" required />
                <button type="submit" class="newsletter-btn">Gửi</button>
            </form>
        </div>

    </div>

    <!-- Bản quyền & Điều khoản -->
    <div class="footer-bottom">
        <div>
            &copy; 2026 Atelier. All rights reserved.
        </div>
        <div class="footer-bottom-links">
            <a href="#">Điều khoản sử dụng</a>
            <a href="#">Cookies</a>
        </div>
    </div>
</footer>

</body>
</html>