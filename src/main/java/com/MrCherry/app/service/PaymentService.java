package com.MrCherry.app.service;

import com.MrCherry.app.dto.*;
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
import com.MrCherry.app.service.servInterface.IProductService;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static com.MrCherry.app.configuration.ApplicationEnvironment.MP_ACCESS_TOKEN;
import static com.mercadopago.serialization.Serializer.deserializeFromJson;
@Service
public class PaymentService implements IPaymentService {

    private static String ORDER_ID = "orderId";

    @Autowired
    private IOrderService orderService;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private PaymentMapper paymentMapper;
    @Autowired
    private IProductService productService;

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
    public PaymentResponse doPayment(Long orderId) throws MPException, MPApiException {

        Order order = orderService.findOrder(orderId);

        productService.verifyStock(order.getOrderItems());

        orderService.validateTransition(order.getOrderStatus(),OrderStatus.PAYED);

        if(order.getPayment().getPaymentType() == PaymentType.VIRTUAL_WALLET){
            Preference preference = payOrderWhitMercadoPago(order);

            return PaymentResponse.builder()
                    .url(preference.getInitPoint())
                    .paymentStatus(PaymentStatus.PENDING)
                    .amount(order.getAmount())
                    .id(order.getPayment().getId())
                    .orderId(order.getId())
                    .paymentType(order.getPayment().getPaymentType())
                    .build();
        }
        throw new RuntimeException("Permitido el pago solo con mercado pago");
    }

    public void hola(Map<String,Object> response){
        Map<String,Object> data = (Map<String, Object>) response.get("data");
        Long id = Long.parseLong((String) data.get("id"));//extraemos el id del pago de marcado pago


    }

    @Override
    public void handleMercadoPagoWebhook(Map<String,Object> response) throws MPException, MPApiException {
        System.out.println("Se comenzo con la notificacion con response " + response.toString());
        var data = (Map<String, Object>) response.get("data");
        if (data != null){
            Long id = Long.parseLong((String) data.get("id"));//extraemos el id del pago de marcado pago
            PaymentClient client = new PaymentClient();
            var payment = client.get(id, MPRequestOptions.builder().accessToken(MP_ACCESS_TOKEN).build());
            Long orderId = Long.parseLong(payment.getExternalReference());
            Order order = orderService.findOrder(orderId);
            System.out.println("Status: " + payment.getStatus());
            System.out.println("Status detail: " + payment.getStatusDetail());
            System.out.println("Transaction detail: " + payment.getTransactionDetails());
            System.out.println("Transaction amount: " + payment.getTransactionAmount());
            System.out.println("Date approved: " + payment.getDateApproved());
            System.out.println("Description: " + payment.getDescription());
            Payment paymentToSave = order.getPayment();
            paymentToSave.setPaymentDate(LocalDate.now());
            paymentToSave.setAmount(order.getAmount());
            if ("approved".equals(payment.getStatus())){
                System.out.println("fue aprobrado");
                orderService.payOrder(orderId);
                paymentToSave.setPaymentStatus(PaymentStatus.APPROVED);
                paymentRepository.save(paymentToSave);
            }else {
                System.out.println("fue trachazado");
                orderService.rejectOrder(orderId);
                paymentToSave.setPaymentStatus(PaymentStatus.REJECT);
                paymentRepository.save(paymentToSave);
            }
        }

    }

    private Preference payOrderWhitMercadoPago(Order order) throws MPException, MPApiException {
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
                .externalReference(String.valueOf(order.getId()))
                .notificationUrl("https://57d9-131-221-65-229.ngrok-free.app/payment/api/Mp-notification")
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
