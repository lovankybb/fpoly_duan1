<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<style>
    /* CSS cho Sidebar */
    .admin-sidebar {
        width: 260px;
        background-color: #ffffff;
        border-right: 1px solid #eaeaea;
        height: 100vh;
        position: fixed;
        top: 0;
        left: 0;
        display: flex;
        flex-direction: column;
        padding: 20px 0;
        z-index: 100;
        box-shadow: 2px 0 20px rgba(0, 0, 0, 0.02);
    }

    .sidebar-brand {
        padding: 0 25px 30px 25px;
        font-family: 'Playfair Display', serif;
        font-size: 22px;
        font-weight: 700;
        color: #1a1a1a;
        letter-spacing: 1px;
    }

    .sidebar-brand span {
        color: #9b59b6;
    }

    .sidebar-menu {
        list-style: none;
        padding: 0;
        margin: 0;
        flex: 1; /* Đẩy phần footer xuống dưới cùng */
    }

    .sidebar-menu li {
        padding: 0 15px;
        margin-bottom: 5px;
    }

    .sidebar-menu a {
        display: flex;
        align-items: center;
        padding: 12px 15px;
        color: #666666;
        text-decoration: none;
        border-radius: 8px;
        font-size: 14px;
        font-weight: 500;
        transition: all 0.3s ease;
    }

    /* Hiệu ứng hover và trạng thái Active */
    .sidebar-menu a:hover, .sidebar-menu a.active {
        background-color: #f8f9fa;
        color: #1a1a1a;
        font-weight: 600;
    }

    .sidebar-menu a svg {
        width: 18px;
        height: 18px;
        margin-right: 12px;
        stroke-width: 2;
    }

    .sidebar-footer {
        padding: 20px 25px;
        border-top: 1px solid #eaeaea;
    }

    .btn-admin-logout {
        display: flex;
        align-items: center;
        color: #e74c3c;
        text-decoration: none;
        font-size: 14px;
        font-weight: 600;
        transition: color 0.3s;
    }

    .btn-admin-logout:hover {
        color: #c0392b;
    }

    .btn-admin-logout svg {
        margin-right: 10px;
        width: 18px;
        height: 18px;
    }
</style>

<aside class="admin-sidebar">
    <div class="sidebar-brand">
        Admin
    </div>

    <ul class="sidebar-menu">
        <li>
            <a href="${pageContext.request.contextPath}/admin/dashboard" class="active">
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round"
                          d="M3 12l2-2m0 0l7-7 7 7M5 10v10a1 1 0 001 1h3m10-11l2 2m-2-2v10a1 1 0 01-1 1h-3m-6 0a1 1 0 001-1v-4a1 1 0 011-1h2a1 1 0 011 1v4a1 1 0 001 1m-6 0h6"/>
                </svg>
                Tổng quan
            </a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/admin/products?offset=0">
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round"
                          d="M20 7l-8-4-8 4m16 0l-8 4m8-4v10l-8 4m0-10L4 7m8 4v10M4 7v10l8 4"/>
                </svg>
                Sản phẩm
            </a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/admin/orders">
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round"
                          d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z"/>
                </svg>
                Đơn hàng
            </a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/admin/users">
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round"
                          d="M12 4.354a4 4 0 110 5.292M15 21H3v-1a6 6 0 0112 0v1zm0 0h6v-1a6 6 0 00-9-5.197M13 7a4 4 0 11-8 0 4 4 0 018 0z"/>
                </svg>
                Khách hàng
            </a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/admin/categories">
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"
                     stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
                     class="lucide lucide-layers-icon lucide-layers">
                    <path d="M12.83 2.18a2 2 0 0 0-1.66 0L2.6 6.08a1 1 0 0 0 0 1.83l8.58 3.91a2 2 0 0 0 1.66 0l8.58-3.9a1 1 0 0 0 0-1.83z"/>
                    <path d="M2 12a1 1 0 0 0 .58.91l8.6 3.91a2 2 0 0 0 1.65 0l8.58-3.9A1 1 0 0 0 22 12"/>
                    <path d="M2 17a1 1 0 0 0 .58.91l8.6 3.91a2 2 0 0 0 1.65 0l8.58-3.9A1 1 0 0 0 22 17"/>
                </svg>
                Danh mục
            </a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/admin/brands">
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"
                     stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
                     class="lucide lucide-file-badge-icon lucide-file-badge">
                    <path d="M13 22h5a2 2 0 0 0 2-2V8a2.4 2.4 0 0 0-.706-1.706l-3.588-3.588A2.4 2.4 0 0 0 14 2H6a2 2 0 0 0-2 2v3.3"/>
                    <path d="M14 2v5a1 1 0 0 0 1 1h5"/>
                    <path d="m7.69 16.479 1.29 4.88a.5.5 0 0 1-.698.591l-1.843-.849a1 1 0 0 0-.879.001l-1.846.85a.5.5 0 0 1-.692-.593l1.29-4.88"/>
                    <circle cx="6" cy="14" r="3"/>
                </svg>
                Thương hiệu
            </a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/admin/colors">
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"
                     stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
                     class="lucide lucide-palette-icon lucide-palette">
                    <path d="M12 22a1 1 0 0 1 0-20 10 9 0 0 1 10 9 5 5 0 0 1-5 5h-2.25a1.75 1.75 0 0 0-1.4 2.8l.3.4a1.75 1.75 0 0 1-1.4 2.8z"/>
                    <circle cx="13.5" cy="6.5" r=".5" fill="currentColor"/>
                    <circle cx="17.5" cy="10.5" r=".5" fill="currentColor"/>
                    <circle cx="6.5" cy="12.5" r=".5" fill="currentColor"/>
                    <circle cx="8.5" cy="7.5" r=".5" fill="currentColor"/>
                </svg>
                Màu sắc
            </a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/admin/versions">
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"
                     stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
                     class="lucide lucide-gallery-horizontal-end-icon lucide-gallery-horizontal-end">
                    <path d="M2 7v10"/>
                    <path d="M6 5v14"/>
                    <rect width="12" height="18" x="10" y="3" rx="2"/>
                </svg>
                Phiên bản
            </a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/admin/variants">
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round"
                          d="M20 7l-8-4-8 4m16 0l-8 4m8-4v10l-8 4m0-10L4 7m8 4v10M4 7v10l8 4"/>
                </svg>
                Biến thể
            </a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/">
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"
                     stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
                     class="lucide lucide-square-arrow-left-icon lucide-square-arrow-left">
                    <rect width="18" height="18" x="3" y="3" rx="2"/>
                    <path d="m12 8-4 4 4 4"/>
                    <path d="M16 12H8"/>
                </svg>
                Về trang chủ
            </a>
        </li>
    </ul>

    <div class="sidebar-footer">
        <form action="${pageContext.request.contextPath}/logout" method="post" style="margin: 0;">
            <button type="submit" style="background: none; border: none; padding: 0; cursor: pointer; width: 100%;"
                    class="btn-admin-logout">
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round"
                          d="M17 16l4-4m0 0l-4-4m4 4H7m6 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h4a3 3 0 013 3v1"/>
                </svg>
                Đăng xuất
            </button>
        </form>
    </div>
</aside>