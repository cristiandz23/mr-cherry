package com.MrCherry.app.service;

import com.MrCherry.app.dto.MpNotification;
import com.MrCherry.app.dto.OrderItemDto;
import com.MrCherry.app.dto.OrderResponse;
import com.MrCherry.app.dto.PaymentResponse;
import com.MrCherry.app.mapper.PaymentMapper;
import com.MrCherry.app.model.Order;
import com.MrCherry.app.model.OrderItem;
import com.MrCherry.app.model.Payment;
import com.MrCherry.app.model.enums.OrderStatus;
import com.MrCherry.app.model.enums.PaymentStatus;
import com.MrCherry.app.model.enums.PaymentType;
import com.MrCherry.app.repository.PaymentRepository;
import com.MrCherry.app.service.servInterface.IOrderService;
import com.MrCherry.app.service.servInterface.IPaymentService;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import com.mercadopago.resources.preference.PreferenceBackUrls;
import jakarta.transaction.Transactional;
import org.apache.velocity.runtime.directive.Parse;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

public class PaymentService implements IPaymentService {

    @Autowired
    private IOrderService orderService;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private PaymentMapper paymentMapper;

    @Override
    @Transactional
    public PaymentResponse payWithCash(Long orderId) {
        Order order = orderService.findOrder(orderId);
        if(!(order.getOrderStatus() == OrderStatus.CREATED))
            throw new RuntimeException("No puede pagar una orden en este estado"+order.getOrderStatus());
        Payment payment = new Payment();
        payment.setPaymentDate(LocalDate.now());
        payment.setPaymentStatus(PaymentStatus.APPROVED);
        payment.setAmount(order.getAmount());
        payment.setOrder(order);
        payment.setPaymentType(PaymentType.CASH);
        payment = paymentRepository.save(payment);
        orderService.payOrder(orderId);
        return paymentMapper.toResponse(payment);
    }

    @Override
    public String doPayment(Long orderId) throws MPException, MPApiException {

        Preference preference = payOrderWhitMercadoPago(orderId);
        return preference.getExternalReference();

    }

    @Override
    public void handleMercadoPagoWebhook(MpNotification notification) {

    }

    private Preference payOrderWhitMercadoPago(Long orderId) throws MPException, MPApiException {
        Order order = orderService.findOrder(orderId);
        List<OrderItem> orderItem = order.getOrderItems();
        List<PreferenceItemRequest> items = new ArrayList<>();

        orderItem.forEach(item -> {
            PreferenceItemRequest preference = PreferenceItemRequest.builder()
                    .id(item.getProduct().getId().toString())
                    .title(item.getProduct().getName())
                    .quantity(item.getQuantity())
                    .unitPrice(item.getProduct().getPrice())
                    .build();
            items.add(preference);

        });
        PreferenceBackUrlsRequest urls = PreferenceBackUrlsRequest.builder()
                .success("asd")//CPNFIGURAR ESTAS URLS
                .failure("")
                .build();
        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                .binaryMode(true)
                .items(items)
                .backUrls(urls)
                .marketplace("Mr. Cherry")
                .metadata(Map.of( "orderId", order.getId()))
                .build();
        PreferenceClient preferenceClient = new PreferenceClient();
        return preferenceClient.create(preferenceRequest);
    }

    @Override
    public PaymentResponse refundMercadoPagoPayment(String mercadoPagoPaymentId, BigDecimal amount) {
        return null;
    }
}
