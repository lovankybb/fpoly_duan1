<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuActive" value="${empty profileMenuActive ? 'profile' : profileMenuActive}"/>

<aside class="card profile-aside">
    <div class="user-avatar">
        <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 24 24" fill="none"
             stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <path d="M19 21v-2a4 4 0 0 0-4-4H9a4 4 0 0 0-4 4v2"></path>
            <circle cx="12" cy="7" r="4"></circle>
        </svg>
    </div>
    <div class="user-info">
        <h2>${authentication.username}</h2>
        <p>
            <c:choose>
                <c:when test="${not empty user.createdAt}">Thành viên từ ${user.createdAt}</c:when>
                <c:otherwise>Thành viên Atelier Mobile</c:otherwise>
            </c:choose>
        </p>
        <c:if test="${authentication.roles.contains('ADMIN')}">
            <span class="user-role">ADMIN</span>
        </c:if>
    </div>

    <ul class="profile-menu">
        <li>
            <a href="${pageContext.request.contextPath}/profile"
               class="${menuActive == 'profile' ? 'menu-active' : ''}">Lịch sử mua hàng</a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/user/info"
               class="${menuActive == 'info' ? 'menu-active' : ''}">Thông tin cá nhân</a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/user/change-pwd"
               class="${menuActive == 'change-pwd' ? 'menu-active' : ''}">Đổi mật khẩu</a>
        </li>
        <li>
            <form action="${pageContext.request.contextPath}/logout" method="post" style="display:inline;">
                <button type="submit"
                        style="background:none; border:none; padding:0; font:inherit; cursor:pointer;"
                        class="btn-logout">Đăng xuất
                </button>
            </form>
        </li>
    </ul>
</aside>
