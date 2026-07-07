package com.fptpolytechnic.duan1.enums;

public enum ProductStatus {


    ACTIVE("Đang bán"),
    INACTIVE("Ngưng bán"),
    OUT_OF_STOCK("Hết hàng");

    private final String displayName;

    ProductStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static ProductStatus fromDisplayName(String displayName) {
        for (ProductStatus status : ProductStatus.values()) {
            if (status.getDisplayName().equals(displayName)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid product status: " + displayName);
    }

}
