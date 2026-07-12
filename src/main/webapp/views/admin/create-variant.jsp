<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thêm Biến thể</title>
    <link href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@300;400;500;600;700&display=swap"
          rel="stylesheet">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/create-variant.css">
</head>
<body>

<%@ include file="/views/fragments/sidebar.jsp" %>

<main class="admin-main-content">
    <header class="page-header">
        <h1>Thêm biến thể mới [${product.name}]</h1>
        <a href="${pageContext.request.contextPath}/admin/variants?productId=${product.id}&offset=0" class="btn-back">
            <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" fill="none" viewBox="0 0 24 24"
                 stroke="currentColor">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 19l-7-7m0 0l7-7m-7 7h18"/>
            </svg>
            Quay lại
        </a>
    </header>

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
    <div class="form-card">
        <c:if test="${not empty errorMsg}">
            <div class="alert alert-error">${errorMsg}</div>
        </c:if>
        <c:if test="${not empty successMsg}">
            <div class="alert alert-success">${successMsg}</div>
        </c:if>

        <form action="${pageContext.request.contextPath}/admin/variant/add" method="post">
            <div class="form-grid">

                <input name="productId" value="${product.id}" type="hidden">
                <div class="form-group">
                    <label>Version</label>
                    <select name="versionId" required>
                        <option value="">-- Chọn phiên bản --</option>
                        <c:forEach var="version" items="${versions}">
                            <option value="${version.id}">${version.name}</option>
                        </c:forEach>
                    </select>
                    <span class="error">${versionError}</span>
                </div>


                <div class="form-group">
                    <label>Màu sắc</label>
                    <select name="colorId" required>
                        <option value="">-- Chọn màu sắc --</option>
                        <c:forEach var="color" items="${colors}">
                            <option value="${color.id}" style="color: ${color.hex}; font-weight: bold;">
                                ■ ${color.name}
                            </option>
                        </c:forEach>
                    </select>
                    <span class="error">${colorError}</span>
                </div>

                <div class="form-group">
                    <label>Giá bán ra(VNĐ)</label>
                    <input value="${product.salePrice}" type="number" name="price" placeholder="VD: 1400000" min="0"
                    required>
                    <span class="error">${priceError}</span>
                </div>
                <div class="form-group">
                    <label>Số lượng</label>
                    <input value="0" type="number" name="stock" placeholder="VD: 10" min="0" required>
                    <span class="error">${stockError}</span>
                </div>
                <div class="form-actions">
                    <a href="${pageContext.request.contextPath}/admin/variants?productId=${product.id}&offset=0"
                       class="btn btn-cancel">Hủy</a>
                    <button type="submit" class="btn btn-save">Tạo biến thể</button>
                </div>
            </div>
        </form>
    </div>
</main>

</body>
</html>