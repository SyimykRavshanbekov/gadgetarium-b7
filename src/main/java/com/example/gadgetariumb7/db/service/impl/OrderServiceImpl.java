package com.example.gadgetariumb7.db.service.impl;

import com.example.gadgetariumb7.db.entity.Order;
import com.example.gadgetariumb7.db.entity.Subproduct;
import com.example.gadgetariumb7.db.enums.OrderStatus;
import com.example.gadgetariumb7.db.repository.OrderRepository;
import com.example.gadgetariumb7.db.service.OrderService;
import com.example.gadgetariumb7.dto.response.*;
import com.example.gadgetariumb7.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    @Override
    public PaginationOrderResponse findAllOrders(OrderStatus orderStatus, String keyWord, int page, int size, LocalDate startDate, LocalDate endDate) {
        List<OrderResponse> orderResponses;
        Page<OrderResponse> orderResponsesPagination;
        PaginationOrderResponse paginationOrderResponse = new PaginationOrderResponse();
        Pageable pageable = PageRequest.of(page - 1, size);

        if (keyWord == null) {
            orderResponsesPagination = orderRepository.findAllOrdersByStatus(orderStatus, pageable);
            orderResponses = orderResponsesPagination.getContent();
        } else {
            orderResponsesPagination = orderRepository.search(keyWord, pageable, orderStatus);
            orderResponses = orderResponsesPagination.getContent();
        }

        if (startDate != null && endDate != null) {
            orderResponses = orderResponses.stream().filter(o -> o.getDateOfOrder().toLocalDate().isAfter(startDate) && o.getDateOfOrder().toLocalDate()
                    .isBefore(endDate)).toList();
        }

        paginationOrderResponse.setOrderResponses(orderResponses);
        paginationOrderResponse.setCurrentPage(pageable.getPageNumber() + 1);
        paginationOrderResponse.setTotalPage(orderResponsesPagination.getTotalPages());

        return paginationOrderResponse;
    }

    @Override
    public Long countByOrderStatus(OrderStatus orderStatus) {
        return orderRepository.countByOrderStatus(orderStatus);
    }

    public SimpleResponse deleteOrderById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Order not found"));
        order.getUser().getOrders().remove(order);
        order.getSubproducts().forEach(x -> x.getOrders().remove(order));
        orderRepository.delete(order);
        return new SimpleResponse("Order successfully deleted!", "ok");
    }

    @Override
    public Long getCountOfOrders() {
        return orderRepository.getCountOfOrders();
    }

    @Override
    public SimpleResponse update(Long id, OrderStatus orderStatus) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Order for update not found! "));
        if(orderStatus != null) order.setOrderStatus(orderStatus);
        orderRepository.save(order);
        return new SimpleResponse("Order successfully updated", "ok");
    }

    @Override
    public OrderPaymentResponse getOrdersPaymentInfo(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Order  not found!"));
        OrderPaymentResponse orderPaymentResponse = new OrderPaymentResponse();
        orderPaymentResponse.setId(order.getId());
        orderPaymentResponse.setFullName(order.getFirstName()+" "+ order.getLastName());
        orderPaymentResponse.setOrderNumber(order.getOrderNumber());
        orderPaymentResponse.setCountOfProduct(order.getCountOfProduct());
        orderPaymentResponse.setTotalSum(order.getTotalSum());
        orderPaymentResponse.setTotalDiscount(order.getTotalDiscount());
        for (Subproduct subproduct:order.getSubproducts()) {
            orderPaymentResponse.setProductName(subproduct.getProduct().getProductName());
            int discount = 0;
            if (subproduct.getProduct().getDiscount().getAmountOfDiscount() != null) {
                discount = (order.getTotalDiscount() * 100) / order.getTotalSum();
                orderPaymentResponse.setDiscount(discount);
            }
        }
        return orderPaymentResponse;
    }

    @Override
    public OrderInfoResponse getOrderInfoById(Long id) {
        return orderRepository.findById(id)
                .map(order -> {
                    OrderInfoResponse orderInfoResponse = new OrderInfoResponse();
                    orderInfoResponse.setOrderNumber(order.getOrderNumber());
                    orderInfoResponse.setPhoneNumber(order.getPhoneNumber());
                    orderInfoResponse.setAddress(order.getAddress());
                    return orderInfoResponse;
                })
                .orElseThrow(() -> new NotFoundException("Order not found!"));
    }

}

