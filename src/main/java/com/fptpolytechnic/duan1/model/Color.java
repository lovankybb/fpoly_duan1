package com.fptpolytechnic.duan1.model;

public class Color {
    private int id;
    private String name;
    private String hex;

    public Color() {}

    // Dùng khi THÊM MỚI (chưa có id, DB tự sinh)
    public Color(String name, String hex) {
        this.name = name;
        this.hex = hex;
    }

    // Dùng khi ĐỌC TỪ DB hoặc CẬP NHẬT (đã có id)
    public Color(int id, String name, String hex) {
        this.id = id;
        this.name = name;
        this.hex = hex;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getHex() { return hex; }
    public void setHex(String hex) { this.hex = hex; }
}
