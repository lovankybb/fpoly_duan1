package com.fptpolytechnic.duan1.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data @Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VariantFormDTO {

    String id;
    String productId;
    String versionId;
    String colorId;
    String price;
    String stock;
}
