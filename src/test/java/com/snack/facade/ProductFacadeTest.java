package com.snack.facade;

import com.snack.applications.ProductApplication;
import com.snack.entities.Product;
import com.snack.repositories.ProductRepository;
import com.snack.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProductFacadeTest {

    private ProductFacade productFacade;
    private ProductApplication productApplication;
    private ProductRepository productRepository;
    private ProductService productService;

    @BeforeEach
    public void setup() {
        // Inicialize as classes reais
        productRepository = new ProductRepository(); // Repositório real
        productService = new ProductService();       // Serviço real
        productApplication = new ProductApplication(productRepository, productService); // Aplicação real
        productFacade = new ProductFacade(productApplication);  // Facade usando a aplicação
    }

    @Test
    public void retornarListaCompletaDeProdutosAoChamarMetodoGetAll() {
        Product product1 = new Product(1, "Hotdog", 5.00f, "hotdog.jpeg");
        Product product2 = new Product(2, "Burger", 7.50f, "burger.jpeg");

        // Adiciona os produtos diretamente no repositório real
        productFacade.append(product1);
        productFacade.append(product2);

        List<Product> productList = productFacade.getAll();

        assertEquals(2, productList.size());
        assertEquals("Hotdog", productList.get(0).getDescription());
        assertEquals("Burger", productList.get(1).getDescription());
    }

    @Test
    public void retornarProdutoCorretoAoFornecerIdValidoNoMetodoGetById() {
        Product product = new Product(1, "Hotdog", 5.00f, "hotdog.jpeg");

        productFacade.append(product);

        Product result = productFacade.getById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Hotdog", result.getDescription());
    }

    @Test
    public void retornarTrueParaIdExistenteEFalseParaIdInexistenteNoMetodoExists() {
        Product product = new Product(1, "Hotdog", 5.00f, "hotdog.jpeg");

        productFacade.append(product);

        assertTrue(productFacade.exists(1));
        assertFalse(productFacade.exists(999));
    }

    @Test
    public void adicionarNovoProdutoCorretamenteAoChamarMetodoAppend() {
        Product product = new Product(1, "Hotdog", 5.00f, "hotdog.jpeg");

        productFacade.append(product);

        Product result = productFacade.getById(1);

        assertNotNull(result);
        assertEquals("Hotdog", result.getDescription());
    }

    @Test
    public void removerProdutoExistenteAoFornecerIdValidoNoMetodoRemove() {
        Product product = new Product(1, "Hotdog", 5.00f, "hotdog.jpeg");

        productFacade.append(product);

        productFacade.remove(1);

        assertNull(productFacade.getById(1));  // Após remover, o produto não deve mais existir
    }
}
