package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentRepositoryTest {

    private PaymentRepository paymentRepository;
    private OrderRepository orderRepository;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();
        orderRepository = new OrderRepository();

        List<Product> products = new ArrayList<>();

        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);
        products.add(product1);

        Order order1 = new Order("13652556-012a-4c07-b546-54eb1396d79b", products, 1708560000L, "Safira Sudrajat");
        orderRepository.save(order1);
    }

    @Test
    void testSaveCreatePayment() {
        Order order = orderRepository.findById("13652556-012a-4c07-b546-54eb1396d79b");
        assertNotNull(order);

        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        Payment payment = new Payment("6c93d3e2-b009-46ba-9d15-f03d85adc2de", "VOUCHER", paymentData, order);
        Payment savedPayment = paymentRepository.save(payment);

        assertEquals(payment.getId(), savedPayment.getId());
        assertEquals(payment.getOrder().getId(), savedPayment.getOrder().getId());
        assertEquals(payment.getMethod(), savedPayment.getMethod());
        assertEquals(payment.getStatus(), savedPayment.getStatus());
    }

    @Test
    void testFindPaymentById() {
        Order order = orderRepository.findById("13652556-012a-4c07-b546-54eb1396d79b");
        assertNotNull(order);

        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        Payment payment = new Payment("6c93d3e2-b009-46ba-9d15-f03d85adc2de", "VOUCHER", paymentData, order);
        paymentRepository.save(payment);

        Payment foundPayment = paymentRepository.findById("6c93d3e2-b009-46ba-9d15-f03d85adc2de");

        assertNotNull(foundPayment);
        assertEquals(payment.getId(), foundPayment.getId());
        assertEquals(payment.getMethod(), foundPayment.getMethod());
        assertEquals(payment.getStatus(), foundPayment.getStatus());
        assertEquals(payment.getOrder().getId(), foundPayment.getOrder().getId());
    }

    @Test
    void testGetAllPayments() {
        Order order = orderRepository.findById("13652556-012a-4c07-b546-54eb1396d79b");
        assertNotNull(order);

        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        Payment payment1 = new Payment("6c93d3e2-b009-46ba-9d15-f03d85adc2de", "VOUCHER", paymentData, order);
        paymentRepository.save(payment1);

        Map<String, String> paymentData2 = new HashMap<>();
        paymentData2.put("voucherCode", "ESHOP8765XYZ4321");

        Payment payment2 = new Payment("af39b3c0-93e0-46ae-a5b8-3f3d0a9469f2", "VOUCHER", paymentData2, order);
        paymentRepository.save(payment2);

        List<Payment> allPayments = paymentRepository.getAllPayments();

        assertEquals(2, allPayments.size());
        assertTrue(allPayments.contains(payment1));
        assertTrue(allPayments.contains(payment2));
    }
}
