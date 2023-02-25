package com.example.gadgetariumb7.dto.response;
import lombok.*;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductSingleResponse {
    private Long id;
    private String productName;
    private int productCount;
    private Long productVendorCode;
    private String categoryName;
    private String subCategoryName;
    private int countOfReviews;
    private int productPrice;
    private Double productRating;
    private String color;
    private byte amountOfDiscount;
    private List<SubproductResponse> subproducts;
    private boolean isFavorite;
    private Map<String, Object> attribute;
    private String videoReview;




    public ProductSingleResponse(long id, String productName, int productCount, long productVendorCode, String categoryName, String subCategoryName,
                                 int countOfReviews, int productPrice, double productRating, String color, List<SubproductResponse> subproducts) {
        this.id = id;
        this.productName = productName;
        this.productCount = productCount;
        this.productVendorCode = productVendorCode;
        this.categoryName = categoryName;
        this.subCategoryName = subCategoryName;
        this.countOfReviews = countOfReviews;
        this.productPrice = productPrice;
        this.productRating = productRating;
        this.color = color;
        this.subproducts = subproducts;
    }

    public void setAttribute(String name, Object value){
        if(attribute == null){
            attribute = new HashMap<>();
        }
        attribute.put(name, value);
    }
}
