package simpleserver.webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import simpleserver.domaindb.Domain;
import simpleserver.domaindb.dto.ProductGroups;
import simpleserver.util.Consts;

import java.util.concurrent.atomic.AtomicLong;

/**
 * The Server (Spring controller).
 */
@RestController
public class Server {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
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
     * Gets product groups.
     *
     * @return the product groups
     */
    @RequestMapping("/product-groups")
    public ProductGroups getProductGroups() {
        logger.debug(Consts.LOG_ENTER);
        ProductGroups productGroups = domain.getProductGroups();
        logger.debug(Consts.LOG_EXIT);
        return productGroups;
    }


}