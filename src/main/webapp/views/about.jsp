<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Về chúng tôi - Bảo Bình Mobile</title>
    <link href="https://fonts.googleapis.com/css2?family=Playfair+Display:ital,wght@0,400..700;1,400..700&family=Plus+Jakarta+Sans:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link href="/styles/about.css" rel="stylesheet">
</head>
<body>

 <%@ include file="fragments/header.jsp" %>

<main>

    <section class="about-hero container">
        <span>Câu chuyện thương hiệu</span>
        <h1>Định hình phong cách công nghệ tối giản & tinh tế</h1>
        <p>Tại Bảo Bình Mobile, chúng tôi tin rằng một thiết bị công nghệ không chỉ phục vụ cuộc sống, mà còn là tuyên ngôn về phong cách sống của bạn.</p>
    </section>

    <section class="story-section container">

        <div class="story-row">
            <div class="story-col-text">
                <h2>Hành trình của chúng tôi</h2>
                <p>Được thành lập từ niềm đam mê bất tận với những sản phẩm công nghệ cao cấp, Bảo Bình Mobile bắt đầu như một không gian nhỏ dành cho những người tìm kiếm sự hoàn mỹ trong từng chi tiết.</p>
                <p>Chúng tôi không chạy theo số lượng, mỗi sản phẩm xuất hiện tại cửa hàng đều phải trải qua quy trình tuyển chọn và kiểm định nghiêm ngặt, đảm bảo mang lại trải nghiệm nguyên bản và xứng tầm nhất.</p>
            </div>
            <div class="story-col-img">
                <img src="${pageContext.request.contextPath}/resources/imgs/about-store.jpg" alt="Không gian cửa hàng">
            </div>
        </div>

        <div class="story-row reverse">
            <div class="story-col-text">
                <h2>Tầm nhìn & Sứ mệnh</h2>
                <p>Bảo Bình Mobile định vị mình là cầu nối đưa những tuyệt tác công nghệ di động thế giới đến tay người dùng Việt Nam theo một cách duy mỹ và thanh lịch nhất.</p>
                <p>Sứ mệnh của chúng tôi là tạo ra một điểm chạm an tâm tuyệt đối, nơi dịch vụ hậu mãi chăm sóc khách hàng được đặt lên hàng đầu, tinh tế và chu đáo như cách bạn thưởng thức một tác phẩm nghệ thuật.</p>
            </div>
            <div class="story-col-img">
                <img src="${pageContext.request.contextPath}/resources/imgs/about-vision.jpg" alt="Tầm nhìn công nghệ">
            </div>
        </div>

    </section>

    <section class="values-section">
        <div class="container">
            <h2 class="values-title">Giá trị cốt lõi</h2>

            <div class="values-grid">
                <div class="value-card">
                    <div class="icon">✦</div>
                    <h3>Chất lượng Nguyên Bản</h3>
                    <p>Cam kết tuyệt đối về nguồn gốc chính hãng và độ hoàn thiện cao nhất của từng thiết bị trước khi trao tay khách hàng.</p>
                </div>

                <div class="value-card">
                    <div class="icon">✦</div>
                    <h3>Thẩm mỹ Tối Giản</h3>
                    <p>Từ không gian trải nghiệm đến thiết kế giao diện trực tuyến, chúng tôi luôn hướng tới sự thanh lịch, sang trọng và tinh giản.</p>
                </div>

                <div class="value-card">
                    <div class="icon">✦</div>
                    <h3>Dịch vụ Tận Tâm</h3>
                    <p>Đồng hành cùng khách hàng trong suốt vòng đời sản phẩm bằng sự chân thành, thấu hiểu và chuyên nghiệp nhất.</p>
                </div>
            </div>

        </div>
    </section>

</main>

 <%@ include file="fragments/footer.jsp" %>

</body>
</html>