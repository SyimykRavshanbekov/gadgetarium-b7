package com.example.gadgetariumb7.db.service.impl;
import com.example.gadgetariumb7.db.entity.Discount;
import com.example.gadgetariumb7.db.entity.Product;
import com.example.gadgetariumb7.db.repository.DiscountRepository;
import com.example.gadgetariumb7.db.repository.ProductRepository;
import com.example.gadgetariumb7.db.service.DiscountService;
import com.example.gadgetariumb7.dto.request.DiscountRequest;
import com.example.gadgetariumb7.dto.response.SimpleResponse;
import com.example.gadgetariumb7.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class DiscountServiceImpl implements DiscountService {
    private final DiscountRepository discountRepository;
    private final ProductRepository productRepository;

    @Override
    public SimpleResponse addDiscount(DiscountRequest discountRequest) {

        Product product = productRepository.findById(discountRequest.getProductId()).orElseThrow(() -> {
            log.error("Not found product");
            throw new NotFoundException("Product not found");
        });
        Discount discount = new Discount();
        discount.setAmountOfDiscount(discountRequest.getAmountOfDiscount());
        discount.setDiscountStartDate(discountRequest.getStartDate());
        discount.setDiscountEndDate(discountRequest.getEndDate());

        product.setDiscount(discount);
        discount.setProduct(product);

        discountRepository.save(discount);
        productRepository.save(product);

        log.info("successfully works the add discount method");
        return new SimpleResponse("Discount successfully saved!", "ok");

    }
}
