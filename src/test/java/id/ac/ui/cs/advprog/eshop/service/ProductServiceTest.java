package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Collections;

class ProductServiceTest {

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private Model model;

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProduct() {
        Product product = new Product();
        product.setProductId("test-id");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(10);

        when(productRepository.create(product)).thenReturn(product);
        Product createdProduct = productService.create(product);

        assertNotNull(createdProduct);
        assertEquals("test-id", createdProduct.getProductId());
        assertEquals("Sampo Cap Bambang", createdProduct.getProductName());
        assertEquals(10, createdProduct.getProductQuantity());

        verify(productRepository, times(1)).create(product);
    }

    @Test
    void testFindAll(){
        when(productRepository.findAll()).thenReturn(Collections.emptyIterator());
        productService.findAll();

        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testEditProduct() {
        Product product = new Product();
        product.setProductId("test-id");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(10);

        when(productRepository.update(product)).thenReturn(product);
        Product updatedProduct = productService.update(product);

        assertNotNull(updatedProduct);
        assertEquals(product, updatedProduct);
        verify(productRepository, times(1)).update(product);
    }

    @Test
    void testDeleteProduct() {
        Product product = new Product();
        product.setProductId("test-id");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(10);

        when(productRepository.delete(product.getProductId())).thenReturn(true);
        boolean isDeleted = productService.delete(product.getProductId());

        assertTrue(isDeleted);
        verify(productRepository, times(1)).delete(product.getProductId());
    }

    @Test
    void testFindById() {
        String productId = "test-id";
        Product existingProduct = new Product();
        existingProduct.setProductId(productId);
        existingProduct.setProductName("Sampo Cap Bambang");
        existingProduct.setProductQuantity(10);

        when(productRepository.findAll()).thenReturn(Collections.singletonList(existingProduct).iterator());
        Product foundProduct = productService.findById(productId);

        assertNotNull(foundProduct);
        assertEquals(existingProduct, foundProduct);
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testFindByIdEmptyRepository() {
        String productId = "non-existent-id";

        when(productRepository.findAll()).thenReturn(Collections.emptyIterator());
        Product foundProduct = productService.findById(productId);

        assertNull(foundProduct);
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testFindByIdNonExistentId() {
        String productId = "non-existent-id";
        Product existingProduct = new Product();
        existingProduct.setProductId("test-id");
        existingProduct.setProductName("Sampo Cap Bambang");
        existingProduct.setProductQuantity(10);

        when(productRepository.findAll()).thenReturn(Collections.singletonList(existingProduct).iterator());
        Product foundProduct = productService.findById(productId);

        assertNull(foundProduct);
        verify(productRepository, times(1)).findAll();
    }
}