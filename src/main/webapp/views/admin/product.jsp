<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quản lý Sản phẩm - Admin Bảo Bình Mobile</title>
    <link href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@300;400;500;600;700&display=swap" rel="stylesheet">


</head>
<body>

<%@ include file="/views/fragments/sidebar.jsp" %>

<main class="admin-main-content">

    <header class="page-header">
        <h1>Quản lý Sản phẩm</h1>
        <a href="${pageContext.request.contextPath}/admin/product/add" class="btn-add">
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor" width="18" height="18">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 4v16m8-8H4" />
            </svg>
            Thêm sản phẩm
        </a>
    </header>

    <div class="card">

        <div class="toolbar">
            <div class="search-box">
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z" />
                </svg>
                <input type="text" id="searchInput" placeholder="Nhập tên sản phẩm để tìm nhanh...">
            </div>

            <div class="filters">
                <span style="font-size: 13px; color: #888;">Tổng: 3 sản phẩm</span>
            </div>
        </div>

        <table>
            <thead>
            <tr>
                <th width="35%">Sản phẩm</th>
                <th width="15%">Danh mục</th>
                <th width="15%">Giá bán</th>
                <th width="15%">Trạng thái</th>
                <th width="20%">Thao tác</th>
            </tr>
            </thead>
            <tbody id="productTableBody">

            <tr class="product-row">
                <td>
                    <div class="product-info">
                        <img src="https://via.placeholder.com/50" alt="Z Fold6" class="product-img">
                        <div>
                            <div class="product-name">Samsung Galaxy Z Fold6</div>
                            <div class="product-brand">Thương hiệu: Samsung</div>
                        </div>
                    </div>
                </td>
                <td>Điện thoại</td>
                <td class="price">43.990.000 ₫</td>
                <td><span class="badge instock">Còn hàng</span></td>
                <td>
                    <a href="#" class="btn-action btn-edit">Sửa</a>
                    <a href="#" class="btn-action btn-delete">Xóa</a>
                </td>
            </tr>

            <tr class="product-row">
                <td>
                    <div class="product-info">
                        <img src="https://via.placeholder.com/50" alt="iPhone 17" class="product-img">
                        <div>
                            <div class="product-name">iPhone 17 Pro Max 256GB</div>
                            <div class="product-brand">Thương hiệu: Apple</div>
                        </div>
                    </div>
                </td>
                <td>Điện thoại</td>
                <td class="price">34.990.000 ₫</td>
                <td><span class="badge outstock">Hết hàng</span></td>
                <td>
                    <a href="#" class="btn-action btn-edit">Sửa</a>
                    <a href="#" class="btn-action btn-delete">Xóa</a>
                </td>
            </tr>

            <tr class="product-row">
                <td>
                    <div class="product-info">
                        <img src="https://via.placeholder.com/50" alt="iPad" class="product-img">
                        <div>
                            <div class="product-name">iPad Pro M4 11-inch</div>
                            <div class="product-brand">Thương hiệu: Apple</div>
                        </div>
                    </div>
                </td>
                <td>Máy tính bảng</td>
                <td class="price">28.500.000 ₫</td>
                <td><span class="badge instock">Còn hàng</span></td>
                <td>
                    <a href="#" class="btn-action btn-edit">Sửa</a>
                    <a href="#" class="btn-action btn-delete">Xóa</a>
                </td>
            </tr>

            </tbody>
        </table>

        <div id="noResultMessage" class="no-result">
            Không tìm thấy sản phẩm nào phù hợp với từ khóa của bạn.
        </div>

    </div>
</main>

<script>
    // Lấy các element cần thiết
    const searchInput = document.getElementById('searchInput');
    const productRows = document.querySelectorAll('.product-row');
    const noResultMessage = document.getElementById('noResultMessage');

    // Bắt sự kiện mỗi khi người dùng gõ phím vào ô input
    searchInput.addEventListener('input', function(e) {
        // Lấy từ khóa người dùng gõ, chuyển về chữ thường để so sánh không phân biệt hoa/thường
        const searchTerm = e.target.value.toLowerCase().trim();
        let hasVisibleRow = false; // Biến kiểm tra xem có dòng nào hiển thị không

        // Duyệt qua từng dòng sản phẩm trong bảng
        productRows.forEach(function(row) {
            // Lấy tên sản phẩm ở trong dòng hiện tại
            const productName = row.querySelector('.product-name').textContent.toLowerCase();

            // Nếu tên sản phẩm có chứa từ khóa tìm kiếm
            if (productName.includes(searchTerm)) {
                row.style.display = ''; // Hiện dòng
                hasVisibleRow = true;
            } else {
                row.style.display = 'none'; // Ẩn dòng
            }
        });

        // Nếu không có dòng nào hiển thị -> Hiện thông báo "Không tìm thấy"
        if (!hasVisibleRow) {
            noResultMessage.style.display = 'block';
        } else {
            noResultMessage.style.display = 'none';
        }
    });
</script>
</body>
</html>