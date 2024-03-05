package id.ac.ui.cs.advprog.eshop.model;

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
    public Payment(String id, String method, Map<String, String> paymentData, Order order)

}
