package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class ProductRepository {
    private List<Product> productData = new ArrayList<>();

    public Product create(Product product){
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
}