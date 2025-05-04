package com.MrCherry.app.service;

import com.MrCherry.app.dto.*;
import com.MrCherry.app.mapper.PaymentMapper;
import com.MrCherry.app.model.Order;
import com.MrCherry.app.model.OrderItem;
import com.MrCherry.app.model.Payment;
import com.MrCherry.app.model.enums.DeliveryType;
import com.MrCherry.app.model.enums.OrderStatus;
import com.MrCherry.app.model.enums.PaymentStatus;
import com.MrCherry.app.model.enums.PaymentType;
import com.MrCherry.app.repository.PaymentRepository;
import com.MrCherry.app.service.servInterface.IOrderService;
import com.MrCherry.app.service.servInterface.IPaymentService;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.MPRequest;
import com.mercadopago.resources.preference.Preference;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static com.mercadopago.serialization.Serializer.deserializeFromJson;

public class PaymentService implements IPaymentService {

    @Autowired
    private IOrderService orderService;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private PaymentMapper paymentMapper;

    private static final String MP_ACCESS_TOKEN = "ASD";
    @Override
    @Transactional
    public PaymentResponse payWithCash(Long orderId) {
        Order order = orderService.findOrder(orderId);
        Payment payment = order.getPayment();
        payment.setPaymentDate(LocalDate.now());
        payment.setPaymentStatus(PaymentStatus.APPROVED);
        payment.setOrder(order);
        payment = paymentRepository.save(payment);
        orderService.payOrder(orderId);

        return paymentMapper.toResponse(payment);
    }

    @Override
    public String doPayment(Long orderId) throws MPException, MPApiException {

        Order order = orderService.findOrder(orderId);
        if(!orderService.isValidTransition(order.getOrderStatus(),OrderStatus.PAYED)){
            throw new RuntimeException("EL ESTADO DE LA ORDEN NO ES VALIDO");
        }
        if(order.getPayment().getPaymentType()== PaymentType.VIRTUAL_WALLET){
            payOrderWhitMercadoPago(order.getId());
        } else if (order.getPayment().getPaymentType() == PaymentType.CASH) {
            payWithCash(order.getId());
        }
        Preference preference = payOrderWhitMercadoPago(order.getId());
        return preference.getExternalReference();
    }

    @Override
    public PaymentResponse handleMercadoPagoWebhook(Map<String,Object> response) throws MPException, MPApiException {
        try{
            Map<String,Object> data = (Map<String, Object>) response.get("data");
            Long id = Long.parseLong((String) data.get("id"));

            PaymentClient client = new PaymentClient();

            com.mercadopago.resources.payment.Payment payment = client.get(id);
            com.mercadopago.resources.payment.Payment result = deserializeFromJson(com.mercadopago.resources.payment.Payment.class, payment.getResponse().getContent());

            Map<String,Object> metaData=  result.getMetadata();
            Long orderId = Long.parseLong((String) metaData.get("orderId"));
            Order order = orderService.findOrder(orderId);

            String status = result.getStatus();
            System.out.println(result.getStatusDetail());
            System.out.println(result.getTransactionDetails());
            System.out.println(result.getTransactionAmount());
            System.out.println(result.getDateApproved());
            System.out.println(result.getDescription());
            Payment payment1 = order.getPayment();
            payment1.setPaymentDate(LocalDate.now());
            payment1.setAmount(order.getAmount());
            payment1.setPaymentType(PaymentType.VIRTUAL_WALLET);
            if (status == "approved"){
                orderService.payOrder(orderId);
                payment1.setPaymentStatus(PaymentStatus.APPROVED);
                return paymentMapper.toResponse(paymentRepository.save(payment1));
            }else {
                orderService.rejectOrder(orderId);
                payment1.setPaymentStatus(PaymentStatus.APPROVED);
                return paymentMapper.toResponse(paymentRepository.save(payment1));
            }


        }catch (ClassCastException ex){
            throw new RuntimeException("Ocurrio un error capurando los datos en el webhook");
        }


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
        MPRequestOptions mpRequestOptions = MPRequestOptions.builder()
                .accessToken(MP_ACCESS_TOKEN)
                .build();
        return preferenceClient.create(preferenceRequest,mpRequestOptions);
    }

    @Override
    public PaymentResponse refundMercadoPagoPayment(String mercadoPagoPaymentId, BigDecimal amount) {
        return null;
    }
}
