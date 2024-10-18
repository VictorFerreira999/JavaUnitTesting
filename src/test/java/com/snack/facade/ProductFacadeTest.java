package com.snack.facades;

import com.snack.applications.ProductApplication;
import com.snack.entities.Product;
import com.snack.facade.ProductFacade;
import com.snack.repositories.ProductRepository;
import com.snack.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ProductFacadeTest {
    private ProductFacade productFacade;
    private ProductApplication productApplication;
    private ProductRepository productRepository;
    private ProductService productService;
    private Product product1;
    private Product product2;

    @BeforeEach
    public void setup() {
        productRepository = new ProductRepository();
        productService = new ProductService();
        productApplication = new ProductApplication(productRepository, productService);
        productFacade = new ProductFacade(productApplication);

        product1 = new Product(1, "Hot Dog", 10.4f, "C:\\Users\\emanu\\OneDrive\\Imagens\\teste\\cachorro.jpeg");
        product2 = new Product(2, "Pastel", 12.4f, "C:\\Users\\emanu\\OneDrive\\Imagens\\teste\\pastel.jpeg");
    }

    @Test
    public void retornarAListaCompletaDeProdutosAoChamarOMetodoGetAll(){
        productFacade.append(product1);
        productFacade.append(product2);

        List<Product> products = productFacade.getAll();

        assertNotNull(products);
        assertEquals(2, products.size());
        assertEquals(product1, products.get(0));
        assertEquals(product2, products.get(1));

    }

    @Test
    public void retornarOProdutoCorretoAoFornecerUmIDValidoNoMetodoGetById (){
        productFacade.append(product1);

        Product productId1 = productFacade.getById(1);

        assertEquals(productId1, product1);
    }

    @Test
    public void retornarTrueParaUmIDExistenteEFalseParaUmIDInexistenteNoMetodoExists (){
        productFacade.append(product1);

        boolean exists = productFacade.exists(1);
        boolean notExistent = productFacade.exists(3);

        assertTrue(exists);
        assertFalse(notExistent);
    }

    @Test
    public void adicionarUmNovoProdutoCorretamenteAoChamarMetodoAppend (){
        productFacade.append(product1);

        List<Product> products = productFacade.getAll();

        assertNotNull(products);
        assertEquals(1, products.size());
        assertEquals(product1, products.get(0));

    }

    @Test
    public void removerUmProdutoExistenteAoFornecerUmIDValidoNoMetodoRemove (){
        productFacade.append(product1);
        productFacade.append(product2);

        List<Product> products = productFacade.getAll();
        boolean product1Exists = productFacade.exists(1);

        assertEquals(2, products.size());
        assertTrue(product1Exists);

        products.remove(product1);
        boolean product1StillExists = productFacade.exists(1);

        assertEquals(1, products.size());
        assertFalse(product1StillExists);
    }
}





