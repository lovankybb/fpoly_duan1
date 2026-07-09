package com.fptpolytechnic.duan1.enums;

public enum ProductStatus {


    ACTIVE("Đang bán"),
    INACTIVE("Ngừng bán"),
    DRAFT("Nháp");

    private final String displayName;

    ProductStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
