<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Liên hệ với chúng tôi - Bảo Bình Mobile</title>
    <link href="https://fonts.googleapis.com/css2?family=Playfair+Display:ital,wght=0,400..700;1,400..700&family=Plus+Jakarta+Sans:wght@300;400;500;600;700&display=swap"
          rel="stylesheet">
    <link href="/styles/contact.css" rel="stylesheet">
</head>
<body>

 <%@ include file="/views/fragments/header.jsp" %>

<main class="container">

    <section class="contact-header">
        <span>Kết nối</span>
        <h1>Liên hệ với Atelier Mobile</h1>
    </section>

    <div class="contact-layout">

        <div class="contact-info">
            <h2>Thông tin liên hệ</h2>

            <div class="info-item">
                <div class="icon">📍</div>
                <div>
                    <h3>Địa chỉ cửa hàng</h3>
                    <p>Số 118, đường Cát Bi, Hải Phòng, Việt Nam</p>
                </div>
            </div>

            <div class="info-item">
                <div class="icon">📞</div>
                <div>
                    <h3>Điện thoại hỗ trợ</h3>
                    <p><a href="tel:+0865305996">+84 865 305 996</a> (8:00 - 21:30)</p>
                </div>
            </div>

            <div class="info-item">
                <div class="icon">✉️</div>
                <div>
                    <h3>Email liên hệ</h3>
                    <p><a href="mailto:support@ateliermobile.com">support@ateliermobile.com</a></p>
                </div>
            </div>

            <div class="map-container">
                <iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3729.112332467512!2d106.7014601758609!3d20.827169894670824!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x314a700bea9d39c1%3A0xa08cc3f2f9c78ca5!2zMTE4IFAuIEPDoXQgQmksIEjhuqNpIEFuLCBI4bqjaSBQaMOybmcgMTgwMDAwLCBWaeG7h3QgTmFt!5e0!3m2!1svi!2s!4v1783284724821!5m2!1svi!2s"
                        width="400" height="300" style="border:0;" allowfullscreen="" loading="lazy"
                        referrerpolicy="strict-origin-when-cross-origin"></iframe>
            </div>
        </div>

        <div class="contact-form-card">
            <h2>Gửi lời nhắn cho chúng tôi</h2>

            <%--
            <c:if test="${not empty successMessage}">
                <div class="success-msg">${successMessage}</div>
            </c:if>
            --%>

            <form action="${pageContext.request.contextPath}/contact" method="post">
                <div class="form-group">
                    <label for="name">Họ và tên</label>
                    <input type="text" id="name" name="name" placeholder="Nguyễn Văn A" required>
                </div>

                <div class="form-group">
                    <label for="email">Địa chỉ Email</label>
                    <input type="email" id="email" name="email" placeholder="name@example.com" required>
                </div>

                <div class="form-group">
                    <label for="subject">Tiêu đề</label>
                    <input type="text" id="subject" name="subject" placeholder="Cần tư vấn về sản phẩm..." required>
                </div>

                <div class="form-group">
                    <label for="message">Nội dung lời nhắn</label>
                    <textarea id="message" name="message" rows="5" placeholder="Viết lời nhắn của bạn tại đây..."
                              required></textarea>
                </div>

                <button type="submit" class="btn-submit">Gửi thông điệp</button>
            </form>
        </div>

    </div>

</main>

 <%@ include file="/views/fragments/footer.jsp" %>

</body>
</html>