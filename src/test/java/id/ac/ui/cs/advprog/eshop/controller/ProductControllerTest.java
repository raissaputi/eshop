package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;

    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createProductPage() {
        String viewName = productController.createProductPage(model);
        assertEquals("CreateProduct", viewName);
    }

    @Test
    void createProductPost() {
        Product product = new Product();
        String viewName = productController.createProductPost(product, model);
        assertEquals("redirect:list", viewName);
    }

    @Test
    void productListPage() {
        String viewName = productController.productListPage(model);
        assertEquals("ProductList", viewName);
    }

    @Test
    void updateProductPage() {
        String productId = "testProductId";
        Product product = new Product();
        String viewName = productController.updateProductPage(productId, model);

        assertEquals("UpdateProduct", viewName);
    }

    @Test
    void updateProductPut() {
        Product updatedProduct = new Product();
        String viewName = productController.updateProductPut(updatedProduct, model);

        assertEquals("redirect:/product/list", viewName);
    }

    @Test
    void deleteProduct() {
        String productId = "testProductId";
        String viewName = productController.deleteProduct(productId);

        assertEquals("redirect:/product/list", viewName);
    }
}
