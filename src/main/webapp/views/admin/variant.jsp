<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quản lý biến thể</title>
    <link href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@300;400;500;600;700&display=swap"
          rel="stylesheet">

    <link href="${pageContext.request.contextPath}/styles/variant.css" rel="stylesheet">
</head>
<body>

<%@ include file="/views/fragments/sidebar.jsp" %>

<main class="admin-main-content">

    <header class="page-header">
        <h1>Quản lý biến thể [${product.name}]</h1>
        <a href="${pageContext.request.contextPath}/admin/variant/add?productId=${product.id}" class="btn-add">
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor" width="18"
                 height="18">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 4v16m8-8H4"/>
            </svg>
            Thêm biến thể
        </a>
    </header>

    <c:if test="${not empty alertMsg}">
        <div class="alert">${alertMsg}
        </div>
    </c:if>

    <div class="card">
        <div class="toolbar">
            <div class="filters">
                <span style="font-size: 13px; color: #888;">Tổng: ${variants.size()} biến thể</span>
            </div>
        </div>
        <div class="product-info">
            <img src="${pageContext.request.contextPath}/image?name=${product.image}"
                 alt="${product.name}"
                 class="product-img">
            <div>
                <div class="product-name">${product.name}</div>
                <div class="product-brand">brand</div>
                <div class="product-brand">category</div>
            </div>
        </div>
        <table>
            <thead>
            <tr>
                <th width="15%">Phiên bản</th>
                <th width="15%">Màu sắc</th>
                <th width="15%">Giá</th>
                <th width="15%">Số lượng</th>
                <th width="20%">Thao tác</th>
            </tr>
            </thead>
            <tbody id="variantTableBody">

            <c:forEach var="v" items="${variants}">
                <tr class="variant-row">
                    <td>${v.version.name}</td>
                    <td>
                        <div class="preview-color-container">
                            <div class="preview-color" style="background-color: ${v.color.hex}"></div>
                            <span>${v.color.name} </span>
                        </div>
                    </td>
                    <td class="price">
                        <div>
                            <div class="price-box">
                                <!-- Thêm class dùng chung và data-price chứa số gốc -->
                                <p class="price js-format-price" data-price="${v.price}"></p>
                            </div>
                        </div>
                    </td>
                    <td>${v.stock}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/admin/variant/update?variantId=${v.id}&productId=${product.id}"
                           class="btn-action btn-edit">Sửa</a>
                        <button class="btn-action btn-delete" value="variantId=${v.id}&productId=${product.id}">Xóa</button>
                    </td>
                </tr>
            </c:forEach>

            </tbody>
        </table>

        <div id="noResultMessage" class="no-result">
            Không tìm thấy sản phẩm nào phù hợp với từ khóa của bạn.
        </div>

    </div>

    <c:if test="${offset gt 0}">
        <a href="${pageContext.request.contextPath}/admin/variants?offset=${offset - 10}&productId=${product.id}">
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"
                 stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
                 class="lucide lucide-circle-arrow-left-icon lucide-circle-arrow-left">
                <circle cx="12" cy="12" r="10"/>
                <path d="m12 8-4 4 4 4"/>
                <path d="M16 12H8"/>
            </svg>
        </a>
    </c:if>
    <br/>
    <a href="${pageContext.request.contextPath}/admin/variants?offset=${offset + 10}&productId=${product.id}">
        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"
             stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
             class="lucide lucide-circle-arrow-right-icon lucide-circle-arrow-right">
            <circle cx="12" cy="12" r="10"/>
            <path d="m12 16 4-4-4-4"/>
            <path d="M8 12h8"/>
        </svg>
    </a>
</main>

<script>
    const deleteButtons = document.querySelectorAll('.btn-delete');
    deleteButtons.forEach(deleteButton => {
        deleteButton.addEventListener('click', (e) => {
            e.preventDefault();
            let rs = confirm('Bạn có chắc muốn xóa biến thể này?');

            if (rs) {
                window.location.href = "${pageContext.request.contextPath}/admin/variant/delete?" + e.target.value;
            }
        })
    });


    document.addEventListener("DOMContentLoaded", function () {
        // Tìm tất cả các thẻ có class js-format-price
        const priceElements = document.querySelectorAll('.js-format-price');

        priceElements.forEach(function (el) {
            // Lấy con số từ data-price
            const rawPrice = Number(el.getAttribute('data-price'));

            // Nếu có giá trị hợp lệ thì format và gán lại
            if (!isNaN(rawPrice) && rawPrice > 0) {
                el.innerText = rawPrice.toLocaleString('vi-VN') + ' ₫';
            }
        });
    });

</script>
</body>
</html>