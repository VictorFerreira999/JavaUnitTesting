package com.snack.repositories;

import com.snack.entities.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductRepositoryTest {

    private ProductRepository productRepository;
    private Product product1;
    private Product product2;

    @BeforeEach
    public void FazerMassaDeTesteComum() {
        productRepository = new ProductRepository();
        product1 = new Product(1, "Hotdog", 4.00f, "C:\\Images\\HotDog.jpg");
        product2 = new Product(2, "Burger", 5.00f, "C:\\Images\\Burger.jpg");
    }

    @Test
    public void FazerAdicaoCorretaDeProdutoAoRepositorio() {
        productRepository.append(product1);
        assertTrue(productRepository.getAll().contains(product1));
    }

    @Test
    public void TestarRecuperacaoDeProdutoPorId() {
        productRepository.append(product1);
        Product retrievedProduct = productRepository.getById(1);
        assertEquals(product1, retrievedProduct);
    }

    @Test
    public void ValidarExistenciaDeProdutoNoRepositorio() {
        productRepository.append(product1);
        assertTrue(productRepository.exists(1));
    }

    @Test
    public void TestarRemocaoCorretaDeProduto() {
        productRepository.append(product1);
        productRepository.remove(1);
        assertFalse(productRepository.exists(1));
    }

    @Test
    public void FazerAtualizacaoCorretaDeProduto() {
        productRepository.append(product1);
        Product updatedProduct = new Product(1, "Updated Hotdog", 4.50f, "C:\\Images\\UpdatedHotDog.jpg");
        productRepository.update(1, updatedProduct);

        Product retrievedProduct = productRepository.getById(1);
        assertEquals("Updated Hotdog", retrievedProduct.getDescription());
        assertEquals(4.50f, retrievedProduct.getPrice());
        assertEquals("C:\\Images\\UpdatedHotDog.jpg", retrievedProduct.getImage());
    }

    @Test
    public void TestarRecuperacaoDeTodosOsProdutos() {
        productRepository.append(product1);
        productRepository.append(product2);

        List<Product> products = productRepository.getAll();
        assertEquals(2, products.size());
        assertTrue(products.contains(product1));
        assertTrue(products.contains(product2));
    }

    @Test
    public void ValidarComportamentoAoRemoverProdutoInexistente() {
        productRepository.append(product1);
        productRepository.remove(99);
        assertEquals(1, productRepository.getAll().size());
    }

    @Test
    public void TestarAtualizacaoDeProdutoInexistente() {
        assertThrows(RuntimeException.class, () -> {
            Product nonExistentProduct = new Product(99, "Nonexistent", 10.00f, "C:\\Images\\Nonexistent.jpg");
            productRepository.update(99, nonExistentProduct);
        });
    }

    @Test
    public void ValidarAdicaoDeProdutoComIdDuplicado() {
        productRepository.append(product1);
        Product duplicateProduct = new Product(1, "Duplicate Hotdog", 4.00f, "C:\\Images\\DuplicateHotDog.jpg");
        productRepository.append(duplicateProduct);

        List<Product> products = productRepository.getAll();
        assertEquals(2, products.size());
    }

    @Test
    public void ValidarListaVaziaAoInicializarRepositorio() {
        List<Product> products = productRepository.getAll();
        assertTrue(products.isEmpty());
    }
}
