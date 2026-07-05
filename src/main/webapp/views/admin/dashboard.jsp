<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard - Atelier</title>
    <link href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@300;400;500;600;700&family=Playfair+Display:ital,wght@0,600;1,600&display=swap" rel="stylesheet">

    <link rel="stylesheet" href="/styles/dashboard.css">
</head>
<body>

<%@ include file="/views/fragments/sidebar.jsp" %>

<main class="admin-main-content">

    <header class="dashboard-header">
        <h1>Tổng quan Hệ thống</h1>
        <div class="admin-profile">
            <span>Chào, ${not empty authentication ? authentication.username : 'Admin'}</span>
            <div class="admin-avatar">AD</div>
        </div>
    </header>

    <div class="stats-grid">
        <div class="stat-card">
            <span class="title">Doanh thu (Tháng)</span>
            <span class="value">458.5M ₫</span>
            <span class="trend up">↑ +12.5% so với tháng trước</span>
        </div>
        <div class="stat-card">
            <span class="title">Đơn hàng mới</span>
            <span class="value">124</span>
            <span class="trend up">↑ +5.2% so với tháng trước</span>
        </div>
        <div class="stat-card">
            <span class="title">Khách hàng</span>
            <span class="value">3,892</span>
            <span class="trend up">↑ +18 người dùng mới</span>
        </div>
        <div class="stat-card">
            <span class="title">Sản phẩm sắp hết</span>
            <span class="value">8</span>
            <span class="trend down">Cần nhập thêm hàng</span>
        </div>
    </div>

    <div class="recent-orders">
        <h2>Đơn hàng mới nhất</h2>
        <table>
            <thead>
            <tr>
                <th>Mã ĐH</th>
                <th>Khách hàng</th>
                <th>Sản phẩm</th>
                <th>Tổng tiền</th>
                <th>Trạng thái</th>
                <th>Thao tác</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>#BBM-001</td>
                <td>Nguyễn Minh Anh</td>
                <td>iPhone 17 Pro Max 256GB</td>
                <td>34,990,000 ₫</td>
                <td><span class="badge pending">Chờ xử lý</span></td>
                <td><a href="#" class="btn-action">Chi tiết</a></td>
            </tr>
            <tr>
                <td>#BBM-002</td>
                <td>Trần Quốc Bảo</td>
                <td>Samsung Galaxy Z Fold6</td>
                <td>43,990,000 ₫</td>
                <td><span class="badge success">Đã giao</span></td>
                <td><a href="#" class="btn-action">Chi tiết</a></td>
            </tr>
            <tr>
                <td>#BBM-003</td>
                <td>Lê Phương Thảo</td>
                <td>Sạc Anker Nano II</td>
                <td>990,000 ₫</td>
                <td><span class="badge success">Đã giao</span></td>
                <td><a href="#" class="btn-action">Chi tiết</a></td>
            </tr>
            </tbody>
        </table>
    </div>

</main>

</body>
</html>