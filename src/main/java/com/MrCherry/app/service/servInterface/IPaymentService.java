package com.MrCherry.app.service.servInterface;

import com.MrCherry.app.dto.MpNotification;
import com.MrCherry.app.dto.PaymentResponse;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;

import java.math.BigDecimal;
import java.util.Map;

public interface IPaymentService {


        /**
         * Procesa un pago en efectivo para una orden.
         * - Verifica que la orden exista y esté en estado correcto.
         * - Marca la orden como PAGED o COMPLETED según tu flujo.
         * - Guarda un registro de “pago en efectivo” en la base.
         */
        PaymentResponse payWithCash(Long orderId);



        /**
         * Crea una preferencia de pago en Mercado Pago:
         * - Genera la preferencia (items, montos, callbacks).
         * - Devuelve la URL o el id de preferencia para redirigir al cliente.
         */
//        Preference payOrderWhitMercadoPago(Long orderId) throws MPException, MPApiException;
        String doPayment(Long orderId) throws MPException, MPApiException;

        /**
         * Maneja el callback/webhook que Mercado Pago envía cuando cambia el estado del pago.
         * - Actualiza tu entidad Payment y, si aplica, el estado de la orden.
         */
        PaymentResponse handleMercadoPagoWebhook(Map<String,Object> response) throws MPException, MPApiException;

        /**
         * Consulta el estado de un pago en Mercado Pago.
         * - Útil para reconsultar manualmente o en procesos asíncronos.
         */
        //PaymentStatusResponse getMercadoPagoStatus(String mercadoPagoPaymentId);

        /**
         * (Opcional) Genera una devolución parcial o total si soportas refund en Mercado Pago.
         */
        PaymentResponse refundMercadoPagoPayment(String mercadoPagoPaymentId, BigDecimal amount);

}
