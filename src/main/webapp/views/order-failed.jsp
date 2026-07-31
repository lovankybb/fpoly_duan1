<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Đặt hàng không thành công — Atelier.</title>

  <!-- Fonts & Icons -->
  <link href="https://fonts.googleapis.com/css2?family=Be+Vietnam+Pro:wght@300;400;500;600;700&family=Playfair+Display:ital,wght@0,600;0,700;1,400&display=swap" rel="stylesheet">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

  <style>
    :root {
      --primary-color: #111111;
      --accent-color: #d32f2f; /* Chuyển sang màu đỏ báo lỗi */
      --bg-color: #f9f9f9;
      --card-bg: #ffffff;
      --text-muted: #666666;
      --border-color: #eeeeee;
    }

    body {
      font-family: 'Be Vietnam Pro', sans-serif;
      background-color: var(--bg-color);
      color: var(--primary-color);
      margin: 0;
      padding: 0;
    }

    .failed-container {
      max-width: 600px;
      margin: 60px auto;
      padding: 0 20px;
    }

    .failed-card {
      background: var(--card-bg);
      border-radius: 16px;
      padding: 40px 32px;
      text-align: center;
      box-shadow: 0 10px 30px rgba(0, 0, 0, 0.05);
      border: 1px solid var(--border-color);
    }

    .icon-wrapper {
      width: 80px;
      height: 80px;
      background-color: #ffebee; /* Nền đỏ nhạt */
      color: var(--accent-color);
      border-radius: 50%;
      display: flex;
      align-items: center;
      justify-content: center;
      font-size: 36px;
      margin: 0 auto 24px;
    }

    .failed-title {
      font-family: 'Playfair Display', serif;
      font-size: 28px;
      font-weight: 700;
      margin: 0 0 12px;
    }

    .failed-desc {
      color: var(--text-muted);
      font-size: 15px;
      line-height: 1.6;
      margin-bottom: 32px;
    }

  </style>
</head>
<body>

<%@include file="fragments/header.jsp"%>

<main class="failed-container">
  <div class="failed-card">
    <div class="icon-wrapper">
      <i class="fa-solid fa-xmark"></i>
    </div>

    <h1 class="failed-title">Đặt hàng không thành công!</h1>
    <p class="failed-desc">
      Rất tiếc, quá trình xử lý đơn hàng của bạn đã xảy ra sự cố hoặc thanh toán bị từ chối. Vui lòng kiểm tra lại thông tin hoặc thử lại sau.
    </p>
  </div>
</main>

<%@include file="fragments/footer.jsp" %>

</body>
</html>
