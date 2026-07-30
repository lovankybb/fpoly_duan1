package com.fptpolytechnic.duan1.exception;


import lombok.Getter;

@Getter
public enum ErrorCode {

    UNAUTHORIZED(1001,"Unauthorized", "Bạn không thể truy cập mục này!"),
    UNAUTHENTICATED(1002, "Unauthenticated", "Đăng nhập không thành công vui lòng thử lại!"),

    UNCATEGORIZED(9999, "Uncategorized","Lỗi không xác đinh!");

    private final int code;
    private final String message;
    private final String description;
    ErrorCode(int code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }
}
