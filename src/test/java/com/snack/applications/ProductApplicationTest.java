package com.snack.applications;


import com.snack.entities.Product;
import com.snack.services.ProductService;
import com.snack.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class ProductApplicationTest {

    private ProductService productService;
    private ProductRepository productRepository;
    private String testImageDir = "/home/victor/Desktop/NLayerLanche/images/";
    private String testImage = "hotdog.jpeg";

    @BeforeEach
    public void setup() {
        productRepository = new ProductRepository();
        productService = new ProductService();
    }

    @Test
    public void listarTodosOsProdutosDoRepositorio() {
        productRepository.append(new Product(1, "Hotdog", 5.00f, testImageDir + testImage));
        productRepository.append(new Product(2, "Burger", 7.50f, testImageDir + "burger.jpeg"));

        assertEquals(2, productRepository.getAll().size());
    }

    @Test
    public void obterProdutoPorIdValido() {
        Product product = new Product(1, "Hotdog", 5.00f, testImageDir + testImage);
        productRepository.append(product);

        Product foundProduct = productRepository.getById(1);
        assertNotNull(foundProduct);
        assertEquals(1, foundProduct.getId());
    }

    @Test
    public void retornarNuloAoObterProdutoPorIdInvalido() {
        Product product = productRepository.getById(999);
        assertNull(product);
    }

    @Test
    public void verificarSeProdutoExistePorIdValido() {
        Product product = new Product(1, "Hotdog", 5.00f, testImageDir + testImage);
        productRepository.append(product);

        assertTrue(productRepository.exists(1));
    }

    @Test
    public void retornarFalsoAoVerificarExistenciaDeProdutoComIdInvalido() {
        assertFalse(productRepository.exists(999));
    }

    @Test
    public void adicionarNovoProdutoESalvarImagemCorretamente() throws Exception {
        Product product = new Product(1, "Hotdog", 5.00f, testImageDir + testImage);

        boolean saved = productService.save(product);
        assertTrue(saved);

        Path savedImagePath = Paths.get(testImageDir + "1.jpeg");
        assertTrue(Files.exists(savedImagePath));
    }

    @Test
    public void removerProdutoExistenteEDeletarSuaImagem() throws Exception {
        Product product = new Product(1, "Hotdog", 5.00f, testImageDir + testImage);
        productService.save(product);

        productService.remove(1);

        Path imagePath = Paths.get(testImageDir + "1.jpeg");
        assertFalse(Files.exists(imagePath));
        assertFalse(productRepository.exists(1));
    }

    @Test
    public void naoAlterarSistemaAoRemoverProdutoComIdInexistente() {
        int initialProductCount = productRepository.getAll().size();
        productService.remove(999);
        int productCountAfterRemoval = productRepository.getAll().size();

        assertEquals(initialProductCount, productCountAfterRemoval);
    }

    @Test
    public void atualizarProdutoExistenteESubstituirImagem() throws Exception {
        Product originalProduct = new Product(1, "Hotdog", 5.00f, testImageDir + testImage);
        productService.save(originalProduct);

        Path originalImagePath = Paths.get(testImageDir + "1.jpeg");
        assertTrue(Files.exists(originalImagePath));

        // Atualizar o produto com uma nova imagem
        Product updatedProduct = new Product(1, "Hotdog Deluxe", 6.00f, testImageDir + "new_hotdog.jpeg");
        productService.update(updatedProduct);

        Path updatedImagePath = Paths.get(testImageDir + "1.jpeg");
        assertTrue(Files.exists(updatedImagePath));
        assertNotEquals(originalProduct.getImage(), updatedProduct.getImage());
    }
}

