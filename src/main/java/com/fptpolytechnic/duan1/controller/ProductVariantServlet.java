package com.fptpolytechnic.duan1.controller;

import com.fptpolytechnic.duan1.dto.VariantFormDTO;
import com.fptpolytechnic.duan1.dto.response.ProductVariantResponse;
import com.fptpolytechnic.duan1.dto.response.SimpleProdResponse;
import com.fptpolytechnic.duan1.model.Color;
import com.fptpolytechnic.duan1.model.Product;
import com.fptpolytechnic.duan1.model.ProductVariant;
import com.fptpolytechnic.duan1.model.Version;
import com.fptpolytechnic.duan1.service.ProductService;
import com.fptpolytechnic.duan1.service.ProductVariantService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet({
        "/admin/variants",
        "/admin/variant/add",
        "/admin/variant/update",
        "/admin/variant/delete",
})
public class ProductVariantServlet extends HttpServlet {

    private final ProductService productService;
    private final ProductVariantService productVariantService;

    public ProductVariantServlet() {
        this.productService = new ProductService();
        this.productVariantService = new ProductVariantService();
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String path = req.getServletPath();
        switch (path) {
            case "/admin/variant/add":
                this.responseProductAddingForm(req, resp);
                break;
            case "/admin/variant/update":
                this.responseProduceVariantUpdateForm(req, resp);
                break;
            case "/admin/variants":
                responseVariantManager(req, resp);
                break;
            case "/admin/variant/delete":
                handleDelete(req, resp);
                break;
            default:
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        switch (path) {
            case "/admin/variant/add":
                this.handleProdVariantCreation(req, resp);
                break;
            case "/admin/variant/update":
                this.handleUpdateProductVariant(req, resp);
                break;
        }
    }

    private void handleDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String variantId = req.getParameter("variantId");
        if (variantId == null || variantId.trim().isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/error?code=UNCATEGORIZED");
            return;
        }

        String productId = req.getParameter("productId");
        if (productId == null || productId.trim().isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/error?code=UNCATEGORIZED");
            return;
        }


        productVariantService.delete(Long.parseLong(variantId));

        resp.sendRedirect(req.getContextPath() + "/admin/variants?alertMsg=success&offset=0&productId=" + productId);
    }


    private void responseProduceVariantUpdateForm(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        String productId = req.getParameter("productId");
        if (productId == null || productId.trim().isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/error?code=UNCATEGORIZED");
            return;
        }

        String variantId = req.getParameter("variantId");
        if (variantId == null || variantId.trim().isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/error?code=UNCATEGORIZED");
            return;
        }


        List<Version> versions = new ArrayList<>();
        versions.add(new Version(1, "256Gb"));
        versions.add(new Version(2, "12-256Gb"));
        versions.add(new Version(3, "128Gb"));

        List<Color> colors = new ArrayList<>();
        colors.add(new Color(1, "Orange", "#fb542b"));
        colors.add(new Color(2, "Pink", "#fb578e"));
        colors.add(new Color(3, "Black", "#000000"));


        req.setAttribute("versions", versions);
        req.setAttribute("colors", colors);

        if (req.getParameter("versionError") != null) {
            req.setAttribute("versionError", "Phiên bản không hợp lệ!");
        }
        if (req.getParameter("colorError") != null) {
            req.setAttribute("colorError", "Màu sắc không hợp lệ!");
        }
        if (req.getParameter("priceError") != null) {
            req.setAttribute("price", "Giá không hợp lệ!");
        }
        if (req.getParameter("stockError") != null) {
            req.setAttribute("stockError", "Số lượng không hợp lệ!");
        }

        String alertMsg = req.getParameter("alertMsg");
        if (alertMsg != null) {
            switch (alertMsg) {
                case "success":
                    req.setAttribute("successMsg", "Cập nhật biến thể thành công!");
                    break;

                case "error":
                    req.setAttribute("errorMsg", "Cập nhật biến thể thất bại!");
                    break;
            }
        }

        SimpleProdResponse product = productService.getSimpleProdResponse(Long.parseLong(req.getParameter("productId")));
        ProductVariantResponse variant = productVariantService.findById(Long.parseLong(req.getParameter("variantId")));

        req.setAttribute("product", product);
        req.setAttribute("variant", variant);

        req.getRequestDispatcher("/views/admin/update-variant.jsp").forward(req, resp);

    }


    private void handleUpdateProductVariant(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String productId = req.getParameter("productId");
        if (productId == null || productId.trim().isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/error?code=UNCATEGORIZED");
            return;
        }

        String variantId = req.getParameter("variantId");
        if (variantId == null || variantId.trim().isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/error?code=UNCATEGORIZED");
            return;
        }

        StringBuilder url = new StringBuilder("/admin/variant/update?productId=").append(productId).append("&variantId=").append(variantId);
        VariantFormDTO form = VariantFormDTO.builder()
                .id(variantId)
                .productId(productId)
                .versionId(req.getParameter("versionId"))
                .colorId(req.getParameter("colorId"))
                .price(req.getParameter("price"))
                .stock(req.getParameter("stock"))
                .build();

        Map<String, String> errors = this.validate(form, true);

        if (!errors.isEmpty()) {
            errors.forEach((key, value) -> {
                url.append("&").append(key).append("=").append(value);
            });
        } else {
            ProductVariant productVariant = ProductVariant.builder()
                    .id(Long.parseLong(req.getParameter("variantId")))
                    .productId(Long.parseLong(req.getParameter("productId")))
                    .versionId(Long.parseLong(req.getParameter("versionId")))
                    .colorId(Long.parseLong(req.getParameter("colorId")))
                    .price(BigDecimal.valueOf(Double.parseDouble(req.getParameter("price"))))
                    .stock(Integer.parseInt(req.getParameter("stock")))
                    .updatedAt(LocalDateTime.now())
                    .build();

            if (productVariantService.update(productVariant) != null) {
                url.append("&alertMsg=success");
            } else {
                url.append("&alertMsg=error");
            }

        }
        resp.sendRedirect(req.getContextPath() + url.toString());
    }


    private void responseVariantManager(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        String productId = req.getParameter("productId");
        if (productId == null || productId.trim().isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/error?code=UNCATEGORIZED");
            return;
        }


        int offset = req.getParameter("offset") == null ? 0 : Integer.parseInt(req.getParameter("offset"));

        String alertMsg = req.getParameter("alertMsg");
        if (alertMsg != null) {
            req.setAttribute("alertMsg", "Xóa biến thể thành công!");
        }


        List<ProductVariantResponse> variants = productVariantService.getProductVariantsByProductId(Long.parseLong(productId), offset);
        req.setAttribute("variants", variants);


        SimpleProdResponse product = productService.getSimpleProdResponse(Long.parseLong(req.getParameter("productId")));
        req.setAttribute("product", product);


        req.getRequestDispatcher("/views/admin/variant.jsp").forward(req, resp);
    }

    private void responseProductAddingForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String productId = req.getParameter("productId");
        if (productId == null || productId.trim().isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/error?code=UNCATEGORIZED");
            return;
        }

        List<Version> versions = new ArrayList<>();
        versions.add(new Version(1, "256Gb"));
        versions.add(new Version(2, "12-256Gb"));
        versions.add(new Version(3, "128Gb"));

        List<Color> colors = new ArrayList<>();
        colors.add(new Color(1, "Orange", "#fb542b"));
        colors.add(new Color(2, "Pink", "#fb578e"));
        colors.add(new Color(3, "Black", "#000000"));


        req.setAttribute("versions", versions);
        req.setAttribute("colors", colors);

        if (req.getParameter("versionError") != null) {
            req.setAttribute("versionError", "Phiên bản không hợp lệ!");
        }
        if (req.getParameter("colorError") != null) {
            req.setAttribute("colorError", "Màu sắc không hợp lệ!");
        }
        if (req.getParameter("priceError") != null) {
            req.setAttribute("priceError", "Giá không hợp lệ!");
        }
        if (req.getParameter("stockError") != null) {
            req.setAttribute("stockError", "Số lượng không hợp lệ!");
        }


        String alertMsg = req.getParameter("alertMsg");
        if (alertMsg != null) {
            switch (alertMsg) {
                case "success":
                    req.setAttribute("successMsg", "Thêm biến thể thành công!");
                    break;
                case "error":
                    req.setAttribute("errorMsg", "Thêm biến thể thất bại!");
                    break;
            }
        }

        SimpleProdResponse product = productService.getSimpleProdResponse(Long.parseLong(req.getParameter("productId")));
        req.setAttribute("product", product);

        req.getRequestDispatcher("/views/admin/create-variant.jsp").forward(req, resp);
    }


    private void handleProdVariantCreation(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String productId = req.getParameter("productId");
        if (productId == null || productId.trim().isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/error?code=UNCATEGORIZED");
            return;
        }

        StringBuilder url = new StringBuilder("/admin/variant/add?productId=" + productId);


        VariantFormDTO form = VariantFormDTO.builder()
                .productId(productId)
                .versionId(req.getParameter("versionId"))
                .colorId(req.getParameter("colorId"))
                .price(req.getParameter("price"))
                .stock(req.getParameter("stock"))
                .build();

        Map<String, String> errors = this.validate(form, false);
        if (!errors.isEmpty()) {
            errors.forEach((key, value) -> {
                url.append("&").append(key).append("=").append(value);
            });
        } else {
            ProductVariant productVariant = ProductVariant.builder()
                    .productId(Long.parseLong(form.getProductId()))
                    .versionId(Long.parseLong(form.getVersionId()))
                    .colorId(Long.parseLong(form.getColorId()))
                    .price(BigDecimal.valueOf(Double.parseDouble(form.getPrice())))
                    .stock(Integer.parseInt(form.getStock()))
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            if (productVariantService.create(productVariant) != null) {
                url.append("&alertMsg=success");
            } else {
                url.append("&alertMsg=error");
            }
        }
        resp.sendRedirect(req.getContextPath() + url.toString());
    }

    private Map<String, String> validate(VariantFormDTO form, boolean isUpdate) {
        Map<String, String> errors = new HashMap<>();

        if (isUpdate) {
            if (form.getId() == null || form.getId().trim().isEmpty()) {
                errors.put("idError", "true");
            }
        }
        if (form.getVersionId() == null || form.getVersionId().trim().isEmpty()) {
            errors.put("versionError", "true");
        }
        if (form.getColorId() == null || form.getColorId().trim().isEmpty()) {
            errors.put("colorError", "true");
        }
        if (form.getProductId() == null || form.getProductId().trim().isEmpty()) {
            errors.put("productIdError", "true");
        }
        try {
            BigDecimal price = BigDecimal.valueOf(Double.parseDouble(form.getPrice()));
            if (price.compareTo(BigDecimal.ZERO) < 0) {
                errors.put("priceError", "true");
            }
        } catch (Exception e) {
            errors.put("priceError", "true");
        }

        // Validate Stock
        try {
            int stock = Integer.parseInt(form.getStock());
            if (stock < 0) {
                errors.put("stockError", "true");
            }
        } catch (Exception e) {
            errors.put("stockError", "true");
        }
        return errors;
    }
}
