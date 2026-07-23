<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${product.name} — Atelier.</title>

    <link href="https://fonts.googleapis.com/css2?family=Be+Vietnam+Pro:wght@300;400;500;600;700&family=Playfair+Display:ital,wght@0,600;0,700;1,400&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

    <link href="${pageContext.request.contextPath}/styles/prod-detail.css" rel="stylesheet">

</head>
<body>

<%@include file="fragments/header.jsp"%>

<main class="container main-content">
    <div class="product-grid">

        <div class="gallery-container">
            <div class="thumbnails">
                <c:forEach items="${product.images}" var="img" varStatus="loop">
                    <button type="button" onclick="changeMainImage('${pageContext.request.contextPath}/image?name=${img.imageUrl}', this)" class="thumb-btn ${loop.first ? 'active' : ''}">
                        <img src="${pageContext.request.contextPath}/image?name=${img.imageUrl}" alt="Thumbnail">
                    </button>
                </c:forEach>
            </div>

            <div class="main-image-wrapper">
                <span class="badge-new">Mới</span>
                <img id="mainImage" src="${pageContext.request.contextPath}/image?name=${product.images[0].imageUrl}" alt="${product.name}">
            </div>
        </div>

        <div class="product-details">
            <div>
                <div class="meta-row">
                        <span class="brand-name">
                            <c:out value="${product.brand}" default="THƯƠNG HIỆU" />
                        </span>
                </div>

                <h1 class="product-title">${product.name}</h1>

                <div class="price-box">
                    <span id="displayPrice" class="current-price">0 ₫</span>
                </div>

                <form method="GET" id="purchaseForm">
                    <input type="hidden" name="productId" value="${product.id}">
                    <input type="hidden" name="productVariantId" id="selectedVariantId" value="">

                    <div class="form-group">
                        <label class="form-label">
                            Màu sắc: <span id="selectedColorName">...</span>
                        </label>
                        <div class="swatches-grid" id="colorSwatchesContainer"></div>
                    </div>

                    <div class="form-group">
                        <label class="form-label">Phiên bản:</label>
                        <div class="versions-grid" id="versionButtonsContainer"></div>
                    </div>

                    <div class="stock-info">
                        <i class="fa-solid fa-box-open"></i> Tồn kho: <span id="stockCount">0</span> sản phẩm
                    </div>

                    <div class="purchase-actions">
                        <div class="actions-row">
                            <div class="quantity-box">
                                <button type="button" onclick="updateQty(-1)" class="qty-btn">
                                    <i class="fa-solid fa-minus"></i>
                                </button>
                                <input type="number" name="quantity" id="quantity" value="1" min="1" class="qty-input">
                                <button type="button" onclick="updateQty(1)" class="qty-btn">
                                    <i class="fa-solid fa-plus"></i>
                                </button>
                            </div>

                            <button type="submit" formaction="${pageContext.request.contextPath}/add-to-cart" id="addToCartBtn" class="btn-add-cart">
                                <i class="fa-solid fa-cart-plus"></i>
                                <span>Thêm vào giỏ</span>
                            </button>
                        </div>
                        <button type="submit" formaction="${pageContext.request.contextPath}/checkout" id="buyNowBtn" class="btn-buy-now">
                            <span>Mua ngay</span>
                            <i class="fa-solid fa-arrow-right"></i>
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <div class="tabs-section">
        <div class="tab-headers">
            <button type="button" class="tab-link active">Mô tả sản phẩm</button>
        </div>
        <div class="tab-content">
            <p>${product.description}</p>
        </div>
    </div>
</main>

<script>
    // 1. Map dữ liệu từ List<ProductVariantResponse> của backend sang Array JS
    const variants = [
        <c:forEach items="${product.variants}" var="v" varStatus="loop">
        {
            id: ${v.id},
            colorName: "${v.color.name}",
            colorHex: "${v.color.hex}",
            versionName: "${v.version.name}",
            price: ${v.price},
            stock: ${v.stock}
        }${!loop.last ? ',' : ''}
        </c:forEach>
    ];

    let selectedColorName = null;
    let selectedVersionName = null;

    // 2. Khởi tạo giao diện khi tải trang
    document.addEventListener("DOMContentLoaded", function() {
        if (variants.length === 0) return;

        const uniqueColors = [];
        const seenColorNames = new Set();

        variants.forEach(v => {
            if (!seenColorNames.has(v.colorName)) {
                seenColorNames.add(v.colorName);
                uniqueColors.push({ name: v.colorName, hex: v.colorHex });
            }
        });

        if (uniqueColors.length > 0) {
            selectedColorName = uniqueColors[0].name;
        }

        const colorContainer = document.getElementById('colorSwatchesContainer');
        if (colorContainer) {
            colorContainer.innerHTML = '';
            uniqueColors.forEach((col) => {
                const btn = document.createElement('button');
                btn.type = 'button';
                btn.className = 'color-swatch ' + (col.name === selectedColorName ? 'active' : '');
                btn.style.backgroundColor = col.hex;
                btn.title = col.name;
                btn.onclick = () => selectColor(col.name, btn);
                colorContainer.appendChild(btn);
            });
        }

        renderVersionsBySelectedColor();
        updateVariantDetails();
    });

    // 3. Logic lọc và render lại Phiên Bản theo Màu Sắc
    function renderVersionsBySelectedColor() {
        const versionContainer = document.getElementById('versionButtonsContainer');
        if (!versionContainer) return;

        const variantsOfColor = variants.filter(v => v.colorName === selectedColorName);

        const availableVersions = [];
        const seenVersions = new Set();
        variantsOfColor.forEach(v => {
            if (!seenVersions.has(v.versionName)) {
                seenVersions.add(v.versionName);
                availableVersions.push(v.versionName);
            }
        });

        if (!availableVersions.includes(selectedVersionName)) {
            selectedVersionName = availableVersions.length > 0 ? availableVersions[0] : null;
        }

        versionContainer.innerHTML = '';
        availableVersions.forEach((ver) => {
            const btn = document.createElement('button');
            btn.type = 'button';
            btn.className = 'variant-btn ' + (ver === selectedVersionName ? 'active' : '');
            btn.innerText = ver;
            btn.onclick = () => selectVersion(ver, btn);
            versionContainer.appendChild(btn);
        });
    }

    // 4. Các hàm xử lý sự kiện click
    function changeMainImage(url, btn) {
        const mainImg = document.getElementById('mainImage');
        if (!mainImg) return;

        mainImg.style.opacity = '0.3';
        document.querySelectorAll('.thumb-btn').forEach(el => el.classList.remove('active'));
        if (btn) btn.classList.add('active');

        setTimeout(() => {
            mainImg.src = url;
            mainImg.style.opacity = '1';
        }, 150);
    }

    function selectColor(name, btn) {
        if (selectedColorName === name) return;

        selectedColorName = name;
        document.querySelectorAll('.color-swatch').forEach(el => el.classList.remove('active'));
        if (btn) btn.classList.add('active');

        renderVersionsBySelectedColor();
        updateVariantDetails();
    }

    function selectVersion(name, btn) {
        selectedVersionName = name;
        document.querySelectorAll('.variant-btn').forEach(el => el.classList.remove('active'));
        if (btn) btn.classList.add('active');
        updateVariantDetails();
    }

    // 5. Cập nhật giao diện & Khóa/Mở khóa ĐỒNG THỜI 2 nút mua hàng
    function updateVariantDetails() {
        const textSelectedColor = document.getElementById('selectedColorName');
        if (textSelectedColor) {
            textSelectedColor.innerText = selectedColorName || "";
        }

        const matchedVariant = variants.find(v => v.colorName === selectedColorName && v.versionName === selectedVersionName);

        const displayPrice = document.getElementById('displayPrice');
        const stockCount = document.getElementById('stockCount');
        const variantInput = document.getElementById('selectedVariantId');

        const addToCartBtn = document.getElementById('addToCartBtn');
        const buyNowBtn = document.getElementById('buyNowBtn');

        if (matchedVariant) {
            if (variantInput) variantInput.value = matchedVariant.id;

            if (displayPrice) {
                displayPrice.innerText = new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(matchedVariant.price);
            }
            if (stockCount) stockCount.innerText = matchedVariant.stock;

            // Xử lý trạng thái 2 nút khi Còn Hàng vs Hết Hàng
            if (matchedVariant.stock > 0) {
                toggleButtonsState(false, '<span>Thêm vào giỏ</span>', '<span>Mua ngay</span> <i class="fa-solid fa-arrow-right"></i>');
            } else {
                toggleButtonsState(true, '<span>Tạm hết hàng</span>', '<span>Tạm hết hàng</span>');
            }
        } else {
            if (variantInput) variantInput.value = "";
            if (stockCount) stockCount.innerText = "0";
            toggleButtonsState(true, '<span>Không có sẵn</span>', '<span>Không có sẵn</span>');
        }
    }

    // Hàm phụ trợ để điều khiển 2 nút cùng lúc cho code sạch sẽ
    function toggleButtonsState(isDisabled, cartText, buyText) {
        const addToCartBtn = document.getElementById('addToCartBtn');
        const buyNowBtn = document.getElementById('buyNowBtn');

        [addToCartBtn, buyNowBtn].forEach(btn => {
            if (!btn) return;
            btn.disabled = isDisabled;
            if (isDisabled) {
                btn.classList.add('btn-disabled');
            } else {
                btn.classList.remove('btn-disabled');
            }
        });

        if (addToCartBtn && cartText) addToCartBtn.innerHTML = '<i class="fa-solid fa-cart-plus"></i> ' + cartText;
        if (buyNowBtn && buyText) buyNowBtn.innerHTML = buyText;
    }

    function updateQty(change) {
        const qtyInput = document.getElementById('quantity');
        if (!qtyInput) return;

        let currentVal = parseInt(qtyInput.value) || 1;
        const stockEl = document.getElementById('stockCount');
        const maxStock = stockEl ? (parseInt(stockEl.innerText) || 0) : 1;

        currentVal += change;
        if (currentVal < 1) currentVal = 1;
        if (currentVal > maxStock && maxStock > 0) {
            alert("Số lượng vượt quá số lượng sản phẩm có sẵn trong kho!");
            currentVal = maxStock;
        }
        qtyInput.value = currentVal;
    }
</script>
</body>
</html>