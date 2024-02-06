package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Repository
public class ProductRepository {
    private List<Product> productData = new ArrayList<>();

    public Product create(Product product){
        if (product.getProductId() == null){
            String productId = String.valueOf(UUID.randomUUID());
            product.setProductId(productId);
        }
        productData.add(product);
        return product;
    }

    public Iterator<Product> findAll(){
        return productData.iterator();
    }

    public Product update(Product updatedProduct) {
        for (int i = 0; i < productData.size(); i++) {
            Product initialProduct = productData.get(i);
            if (initialProduct.getProductId().equals(updatedProduct.getProductId())) {
                initialProduct.setProductName(updatedProduct.getProductName());
                initialProduct.setProductQuantity(updatedProduct.getProductQuantity());
                return initialProduct;
            }
        }
        return null;
    }
    public boolean delete(String productId) {
        Iterator<Product> iterator = findAll();
        while (iterator.hasNext()) {
            Product product = iterator.next();
            if (product.getProductId().equals(productId)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }
}