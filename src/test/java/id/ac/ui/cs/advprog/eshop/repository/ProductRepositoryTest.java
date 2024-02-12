package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {
    @InjectMocks
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct.getProductId());
        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testUpdateExistingProduct() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Product updatedProduct = new Product();
        updatedProduct.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        updatedProduct.setProductName("Sampo Cap Baru");
        updatedProduct.setProductQuantity(150);

        Product result = productRepository.update(updatedProduct);

        assertNotNull(product);
        assertEquals(updatedProduct.getProductName(), result.getProductName());
        assertEquals(updatedProduct.getProductQuantity(), result.getProductQuantity());
        assertEquals("eb558e9f-1c39-460e-8860-71af6af63bd6", result.getProductId());

    }

    @Test
    void testUpdateNonExistingProduct() {
        Product updatedProduct = new Product();
        updatedProduct.setProductId("non-existent-id");
        updatedProduct.setProductName("New Product");
        updatedProduct.setProductQuantity(50);

        Product result = productRepository.update(updatedProduct);

        assertNull(result);
    }

    @Test
    void testDeleteExistingProduct() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        boolean isDeleted = productRepository.delete(product.getProductId());

        assertTrue(isDeleted);

        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testDeleteNonExistingProduct() {
        boolean isDeleted = productRepository.delete("non-existent-id");

        assertFalse(isDeleted);
    }

    @Test
    void testCreateWithNullProductId() {
        Product product = new Product();
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);

        Product createdProduct = productRepository.create(product);

        assertNotNull(createdProduct);
        assertNotNull(createdProduct.getProductId());
        assertEquals(product.getProductName(), createdProduct.getProductName());
        assertEquals(product.getProductQuantity(), createdProduct.getProductQuantity());
    }

    @Test
    void testUpdateProductWithDifferentId() {
        Product product = new Product();
        product.setProductId("existing-id");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Product updatedProduct = new Product();
        updatedProduct.setProductId("different-id");
        updatedProduct.setProductName("Updated Product");
        updatedProduct.setProductQuantity(50);

        Product result = productRepository.update(updatedProduct);

        assertNull(result);
    }

    @Test
    void testDeleteProductWithDifferentId() {
        Product product = new Product();
        product.setProductId("existing-id");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        boolean isDeleted = productRepository.delete("different-id");

        assertFalse(isDeleted);
    }
}