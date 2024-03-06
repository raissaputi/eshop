package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceImplTest {
    @InjectMocks
    PaymentServiceImpl paymentService;
    @Mock
    PaymentRepository paymentRepository;
    @Mock
    OrderService orderService;

    List<Payment> payments;

    @BeforeEach
    void setUp() {
        List<Product> products = new ArrayList<>();

        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);
        products.add(product1);

        Order order = new Order("13652556-012a-4c07-b546-54eb1396d79b", products, 1708560000L, "Safira Sudrajat");

        payments = new ArrayList<>();
        Map<String, String> paymentData1 = new HashMap<>();
        paymentData1.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment1 = new Payment("6c93d3e2-b009-46ba-9d15-f03d85adc2de", "VOUCHER", paymentData1, order);
        payments.add(payment1);

        Map<String, String> paymentData2 = new HashMap<>();
        paymentData2.put("bankName", "mandiri");
        paymentData2.put("referenceCode", "abc");
        Payment payment2 = new Payment("0176dc9d-3381-4b14-8705-8f66a8b86acf", "BANK", paymentData2, order);
        payments.add(payment2);
    }

    @Test
    void testAddPayment() {
        Payment payment = payments.get(0);
        Order order = payment.getOrder();
        when(paymentRepository.findById(order.getId())).thenReturn(null);
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        Payment result = paymentService.addPayment(order, "VOUCHER", paymentData);

        assertNotNull(result);
        assertEquals(order.getId(), result.getOrder().getId());
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testAddPaymentAlreadyExists() {
        Payment payment = payments.get(0);
        Order order = payment.getOrder();
        when(paymentRepository.findById(order.getId())).thenReturn(payment);

        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        Payment result = paymentService.addPayment(order, "VOUCHER", paymentData);

        assertNull(result);
        verify(paymentRepository, times(0)).save(any(Payment.class));
    }

    @Test
    void testSetStatusSuccess() {
        Payment payment = payments.get(0);
        Order order = payment.getOrder();
        when(paymentRepository.findById(payment.getId())).thenReturn(payment);
        when(orderService.updateStatus(order.getId(), "SUCCESS")).thenReturn(order);
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

        Payment result = paymentService.setStatus(payment, "SUCCESS");

        assertNotNull(result);
        assertEquals("SUCCESS", result.getStatus());
        verify(orderService, times(1)).updateStatus(order.getId(), "SUCCESS");
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testSetStatusRejected() {
        Payment payment = payments.get(0);
        Order order = payment.getOrder();
        when(paymentRepository.findById(payment.getId())).thenReturn(payment);
        when(orderService.updateStatus(order.getId(), "FAILED")).thenReturn(order);
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

        Payment result = paymentService.setStatus(payment, "REJECTED");

        assertNotNull(result);
        assertEquals("REJECTED", result.getStatus());
        verify(orderService, times(1)).updateStatus(order.getId(), "FAILED");
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testGetPayment() {
        Payment payment = payments.get(0);
        when(paymentRepository.findById(payment.getId())).thenReturn(payment);

        Payment result = paymentService.getPayment(payment.getId());

        assertNotNull(result);
        assertEquals(payment.getId(), result.getId());
        verify(paymentRepository, times(1)).findById(payment.getId());
    }

    @Test
    void testGetAllPayments() {
        when(paymentRepository.getAllPayments()).thenReturn(payments);

        List<Payment> result = paymentService.getAllPayments();

        assertNotNull(result);
        assertEquals(payments.size(), result.size());
        verify(paymentRepository, times(1)).getAllPayments();
    }
}
