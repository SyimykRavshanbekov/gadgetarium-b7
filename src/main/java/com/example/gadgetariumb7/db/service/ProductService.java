package com.example.gadgetariumb7.db.service;

import com.example.gadgetariumb7.dto.request.ProductRequest;
import com.example.gadgetariumb7.dto.response.AllProductResponse;
import com.example.gadgetariumb7.dto.request.InforgraphicsRequest;
import com.example.gadgetariumb7.dto.response.ProductAdminResponse;
import com.example.gadgetariumb7.dto.response.SimpleResponse;

import java.time.LocalDate;
import java.util.List;

public interface ProductService {

    AllProductResponse getAllProductToMP();

    List<ProductAdminResponse> getProductAdminResponses(String searchText, String productType, String fieldToSort, String discountField, LocalDate startDate, LocalDate endDate, int page, int size);

    SimpleResponse addProduct(ProductRequest productRequest, int price);

    InforgraphicsRequest inforgraphics();

    SimpleResponse delete(Long id);

    SimpleResponse update(Long id, Integer vendorCode, Integer productCount, Integer productPrice);

}
