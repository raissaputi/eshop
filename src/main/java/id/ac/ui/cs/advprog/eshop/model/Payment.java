package id.ac.ui.cs.advprog.eshop.model;

import enums.OrderStatus;
import enums.PaymentStatus;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class Payment {
    String id;
    Order order;
    String method;
    Map<String, String> paymentData;
    String status;

    @Builder
    public Payment(String id, String method, Map<String, String> paymentData, Order order) {
        this.id = id;
        this.order = order;
        this.method = method;
        this.paymentData = paymentData;
        this.status = "SUCCESS";

        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }

        validatePaymentData();
        updateStatus();
    }

    private void validatePaymentData() {
        if ("VOUCHER".equals(method)) {
            validateVoucherPaymentData();
        } else if ("BANK".equals(method)) {
            validateBankTransferPaymentData();
        } else {
            throw new IllegalArgumentException("Invalid payment method");
        }
    }

    private void validateVoucherPaymentData() {
        String voucherCode = paymentData.get("voucherCode");
        if (voucherCode == null ||
                voucherCode.length() != 16 ||
                !voucherCode.startsWith("ESHOP")) {
            setStatus(PaymentStatus.REJECTED.getValue());
        }

        int counter = 0;
        for (int i = 0; i < voucherCode.length(); i++) {
            if (Character.isDigit(voucherCode.charAt(i))) {
                counter++;
            }
        }
        if (counter != 8) {
            setStatus(PaymentStatus.REJECTED.getValue());
        }
    }

    private void validateBankTransferPaymentData() {
        String bankName = paymentData.get("bankName");
        String referenceCode = paymentData.get("referenceCode");
        if (bankName == null || referenceCode == null || bankName.isEmpty() || referenceCode.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }

    private void updateStatus() {
        if ("VOUCHER".equals(method) && PaymentStatus.SUCCESS.getValue().equals(status)) {
            order.setStatus(OrderStatus.SUCCESS.getValue());
        } else if ("VOUCHER".equals(method) && PaymentStatus.REJECTED.getValue().equals(status)) {
            order.setStatus(OrderStatus.FAILED.getValue());
        }
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
