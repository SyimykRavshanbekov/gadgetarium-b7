package com.example.gadgetariumb7.db.service;

import com.example.gadgetariumb7.db.enums.OrderStatus;
import com.example.gadgetariumb7.dto.response.PaginationOrderResponse;
import com.example.gadgetariumb7.dto.response.SimpleResponse;

import java.time.LocalDate;

public interface OrderService {
    PaginationOrderResponse findAllOrders(OrderStatus orderStatus, String keyWord, int page, int size, LocalDate startDate, LocalDate endDate);
    SimpleResponse deleteOrderById(Long id);
}
