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
        List<Product> books = domain.getProducts(1);
        assertNotNull(books);
        List<Product> movies = domain.getProducts(2);
        assertNotNull(movies);
        assertEquals(35, books.size());
        assertEquals(169, movies.size());
        Product movie = movies.get(48);
        assertNotNull(movie);
        assertEquals("Once Upon a Time in the West", movie.title);
        logger.debug(Consts.LOG_EXIT);
    }


    @DisplayName("Tests getting the product")
    @Test
    public void getProductTest() {
        logger.debug(Consts.LOG_ENTER);
        Product product = domain.getProduct(2, 49);
        assertNotNull(product);
        assertEquals(49, product.pId);
        assertEquals(2, product.pgId);
        // What a coincidence! The chosen movie is the best western of all times!
        assertEquals("Once Upon a Time in the West", product.title);
        logger.debug(Consts.LOG_EXIT);
    }

}
