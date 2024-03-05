package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentTest {
    private List<Product> products;
    private Order order;

    @BeforeEach
    void setUp() {
        products = new ArrayList<>();

        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductQuantity(2);
        product1.setProductName("Sampo Cap Bambang");

        Product product2 = new Product();
        product2.setProductId("a2c62328-4a37-4664-83c7-f32db8620155");
        product2.setProductQuantity(1);
        product2.setProductName("Sampo Cap Usep");

        products.add(product1);
        products.add(product2);

        Order order = new Order("13652556-012a-4c07-b546-54eb1396d79b", products, 1708560000L, "Safira Sudarajat");
    }

    @Test
    void testCreatePaymentEmptyOrder() {
        Map<String, String> paymentDataVoucher = new HashMap<>();
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("0176dc9d-3381-4b14-8705-8f66a8b86acf", "VOUCHER", paymentDataVoucher, null);
        });
    }

    @Test
    void testCreatePaymentSuccessfulWithOrder() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment = new Payment("0176dc9d-3381-4b14-8705-8f66a8b86acf", "VOUCHER", paymentData, order);
        assertNotNull(payment.getOrder());
    }

    @Test
    void testCreatePaymentVoucherMethod() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment = new Payment("0176dc9d-3381-4b14-8705-8f66a8b86acf", "VOUCHER", paymentData, order);
        assertEquals("VOUCHER", payment.getMethod());
    }

    @Test
    void testCreatePaymentBankTransferMethod() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "mandiri");
        paymentData.put("referenceCode", "abc");
        Payment payment = new Payment("0176dc9d-3381-4b14-8705-8f66a8b86acf", "BANK", paymentData, order);
        assertEquals("BANK", payment.getMethod());
    }

    @Test
    void testCreatePaymentInvalidMethod() {
        Map<String, String> paymentData = new HashMap<>();
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("0176dc9d-3381-4b14-8705-8f66a8b86acf", "INVALID", paymentData, order);
        });
    }

    @Test
    void testSuccessValidVoucherData() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment = new Payment("0176dc9d-3381-4b14-8705-8f66a8b86acf", "VOUCHER", paymentData, order);
        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testInvalidVoucherNot16CharsLong() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678999");
        Payment payment = new Payment("0176dc9d-3381-4b14-8705-8f66a8b86acf", "VOUCHER", paymentData, order);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testInvalidVoucherNotStartWithESHOP() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "EEEEE1234ABC5678999");
        Payment payment = new Payment("0176dc9d-3381-4b14-8705-8f66a8b86acf", "VOUCHER", paymentData, order);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testInvalidVoucherNot8Num() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOPAAAA1111111");
        Payment payment = new Payment("0176dc9d-3381-4b14-8705-8f66a8b86acf", "VOUCHER", paymentData, order);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentBankTransferValidData() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "mandiri");
        paymentData.put("referenceCode", "abc123");
        Payment payment = new Payment("0176dc9d-3381-4b14-8705-8f66a8b86acf", "BANK", paymentData, order);
        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testCreatePaymentBankTransferMissingBankName() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("referenceCode", "abc123");
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("0176dc9d-3381-4b14-8705-8f66a8b86acf", "BANK", paymentData, order);
        });
    }

    @Test
    void testCreatePaymentBankTransferMissingReferenceCode() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "mandiri");
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("0176dc9d-3381-4b14-8705-8f66a8b86acf", "BANK", paymentData, order);
        });
    }

    @Test
    void testCreatePaymentBankTransferNullBankName() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", null);
        paymentData.put("referenceCode", "abc123");
        assertEquals("REJECTED", new Payment("0176dc9d-3381-4b14-8705-8f66a8b86acf", "BANK", paymentData, order).getStatus());
    }

    @Test
    void testCreatePaymentBankTransferNullReferenceCode() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "mandiri");
        paymentData.put("referenceCode", null);
        assertEquals("REJECTED", new Payment("0176dc9d-3381-4b14-8705-8f66a8b86acf", "BANK", paymentData, order).getStatus());
    }
}