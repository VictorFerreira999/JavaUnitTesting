package com.snack.services;

import com.snack.entities.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class ProductServiceTest {

    private ProductService productService;
    private Product product1;
    private String validImagePath = "/home/victor/Desktop/NLayerLanche/images/hotdog.jpeg";
    private String invalidImagePath = "/home/victor/Desktop/NLayerLanche/InvalidImages/nonexistent.jpg";

    @BeforeEach
    public void setUp() {
        productService = new ProductService();
        product1 = new Product(1, "Hotdog", 5.00f, validImagePath);
    }

    @Test
    public void TestarSalvarProdutoComImagemValida() {
        boolean result = productService.save(product1);
        assertTrue(result);

        File savedImage = new File(product1.getImage());
        assertTrue(savedImage.exists());
    }

    @Test
    public void TestarSalvarProdutoComImagemInexistente() {
        Product product2 = new Product(2, "Burger", 7.00f, invalidImagePath);
        boolean result = productService.save(product2);
        assertFalse(result);
    }

    @Test
    public void TestarAtualizacaoDeProdutoExistente() {
        productService.save(product1);

        Product updatedProduct = new Product(1, "Hotdog Deluxe", 6.00f, validImagePath);

        productService.update(updatedProduct); //verificar no metodo no sirvice

        File updatedImage = new File(updatedProduct.getImage());
        assertTrue(updatedImage.exists());
    }

    @Test
    public void TestarRemocaoDeProdutoExistente() {
        productService.save(product1);
        productService.remove(product1.getId());

        File imageFile = new File(product1.getImage());
        assertFalse(imageFile.exists());
    }

    @Test
    public void TestarObterCaminhoDaImagemPorId() {
        productService.save(product1);

        String imagePath = productService.getImagePathById(product1.getId());
        assertNotNull(imagePath);

        File imageFile = new File(imagePath);
        assertTrue(imageFile.exists());
    }
}
