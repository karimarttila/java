package simpleserver.webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import simpleserver.domaindb.Domain;
import simpleserver.domaindb.dto.Info;
import simpleserver.domaindb.dto.Product;
import simpleserver.domaindb.dto.ProductGroups;
import simpleserver.util.Consts;
import simpleserver.webserver.response.ProductGroupsOkResponseImpl;
import simpleserver.webserver.response.ProductOkResponseImpl;
import simpleserver.webserver.response.Response;
import simpleserver.webserver.response.ProductsOkResponseImpl;

import java.util.List;
import java.util.Map;


/**
 * The Server (Spring controller).
 */
@RestController
public class Server {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final Domain domain;

    /**
     * Instantiates a new Server. Autowires the Domain entity.
     *
     * @param domain the domain
     */
    @Autowired
    public Server(Domain domain) {
        this.domain = domain;
    }


    /**
     * Gets info.
     *
     * @return the info.
     */
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public Info getInfo() {
        logger.debug(Consts.LOG_ENTER);
        Info info = domain.getInfo();
        logger.debug(Consts.LOG_EXIT);
        return info;
    }

    /**
     * Gets product groups.
     *
     * @return the product groups
     */
    @RequestMapping(value = "/product-groups", method = RequestMethod.GET)
    public Map<String, Object> getProductGroups() {
        logger.debug(Consts.LOG_ENTER);
        ProductGroups productGroups = domain.getProductGroups();
        Response productGroupsOkResponse =
                ProductGroupsOkResponseImpl.createProductGroupsOkResponse(productGroups);
        logger.debug(Consts.LOG_EXIT);
        return productGroupsOkResponse.getRestView();
    }


    /**
     * Gets products.
     *
     * @return the products
     */
    @RequestMapping(value = "/products/{pgId}", method = RequestMethod.GET)
    public Map<String, Object>  getProducts(
            @PathVariable("pgId") int pgId) {
        logger.debug(Consts.LOG_ENTER);
        List<Product> products = domain.getProducts(pgId);
        Response productsOkResponse =
                ProductsOkResponseImpl.createProductsOkResponse(pgId, products);
        logger.debug(Consts.LOG_EXIT);
        return productsOkResponse.getRestView();
    }


    /**
     * Gets one product.
     *
     * @return the product
     */
    @RequestMapping(value = "/product/{pgId}/{pId}", method = RequestMethod.GET)
    public Map<String, Object>  getProducts(
            @PathVariable("pgId") int pgId, @PathVariable("pId") int pId) {
        logger.debug(Consts.LOG_ENTER);
        Product product = domain.getProduct(pgId, pId);
        Response productOkResponse =
                ProductOkResponseImpl.createProductOkResponse(product);
        logger.debug(Consts.LOG_EXIT);
        return productOkResponse.getRestView();
    }


}
