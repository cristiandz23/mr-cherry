package com.MrCherry.app.controller;

import com.MrCherry.app.dto.PaymentResponse;
import com.MrCherry.app.service.servInterface.IPaymentService;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.MrCherry.app.configuration.ApplicationEnvironment.MP_ACCESS_TOKEN;

@RestController
@RequestMapping("/payment/api")
public class PaymentController {

    @Autowired
    private IPaymentService paymentService;


    @PostMapping("toPay/{orderId}")
    public ResponseEntity<PaymentResponse> payOrder(@PathVariable Long orderId) throws MPException, MPApiException {
        return ResponseEntity.status(HttpStatus.OK).body(paymentService.doPayment(orderId));
    }


    @PostMapping("/Mp-notification")
    public ResponseEntity<String> receiveNotification(@RequestBody Map<String,Object> response) throws MPException, MPApiException {
        paymentService.handleMercadoPagoWebhook(response);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

}
