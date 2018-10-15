package simpleserver.domaindb;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Map;
import simpleserver.domaindb.dto.Info;
import simpleserver.domaindb.dto.Product;
import simpleserver.domaindb.dto.ProductGroups;
import simpleserver.domaindb.dto.Products;
import simpleserver.util.Consts;



@DisplayName("Domain test")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {DomainImpl.class})
public class DomainTest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    Domain domain;


    @DisplayName("Tests getting the info")
    @Test
    public void getInfoTest() {
        logger.debug(Consts.LOG_ENTER);
        Info info = domain.getInfo();
        String infoStr = info.getInfo();
        assertEquals(infoStr, "index.html => Info in HTML format");
        logger.debug(Consts.LOG_EXIT);
    }


    @DisplayName("Tests getting the product groups")
    @Test
    public void getProductGroupsTest() {
        logger.debug(Consts.LOG_ENTER);
        ProductGroups productGroups = domain.getProductGroups();
        Map<String, String> pg = productGroups.getProductGroups();
        assertEquals(2, pg.size());
        assertEquals("Books", pg.get("1"));
        assertEquals("Movies", pg.get("2"));
        logger.debug(Consts.LOG_EXIT);
    }


    @DisplayName("Tests getting the products")
    @Test
    public void getProductsTest() {
        logger.debug(Consts.LOG_ENTER);
        Products bookProducts = domain.getProducts(1);
        List<List> books = bookProducts.getProducts();
        Products movieProducts = domain.getProducts(2);
        List<List> movies = movieProducts.getProducts();
        assertEquals(35, books.size());
        assertEquals(169, movies.size());
        List movie = movies.get(48);
        assertEquals("Once Upon a Time in the West", movie.get(2));
        logger.debug(Consts.LOG_EXIT);
    }


    @DisplayName("Tests getting the product")
    @Test
    public void getProductTest() {
        logger.debug(Consts.LOG_ENTER);
        Product product = domain.getProduct(2, 49);
        List<String> list = product.getProduct();
        assertNotNull(list);
        assertEquals(true, list.size() > 0);
        assertEquals("49", list.get(0));
        assertEquals("2", list.get(1));
        // What a coincidence! The chosen movie is the best western of all times!
        assertEquals("Once Upon a Time in the West", list.get(2));
        logger.debug(Consts.LOG_EXIT);
    }

}
