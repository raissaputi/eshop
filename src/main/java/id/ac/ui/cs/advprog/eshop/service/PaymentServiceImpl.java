package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderService orderService;

    @Override
    public Payment addPayment(Order order, String method, Map<String, String> paymentData) {
        if (paymentRepository.findById(order.getId()) == null) {
            Payment payment = new Payment(order.getId(), method, paymentData, order);
            paymentRepository.save(payment);
            return payment;
        }
        return null;
    }

    @Override
    public Payment setStatus(Payment payment, String status) {
        Payment updatedPayment = paymentRepository.findById(payment.getId());

        if (updatedPayment != null) {
            updatedPayment.setStatus(status);

            if ("SUCCESS".equals(status)) {
                orderService.updateStatus(updatedPayment.getOrder().getId(), "SUCCESS");
            } else if ("REJECTED".equals(status)) {
                orderService.updateStatus(updatedPayment.getOrder().getId(), "FAILED");
            }

            return paymentRepository.save(updatedPayment);
        } else {
            throw new NoSuchElementException("Payment not found");
        }
    }

    @Override
    public Payment getPayment(String paymentId) {
        return paymentRepository.findById(paymentId);
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.getAllPayments();
    }
}
