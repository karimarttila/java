package simpleserver.webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import simpleserver.domaindb.Domain;
import simpleserver.domaindb.dto.Info;
import simpleserver.domaindb.dto.ProductGroups;
import simpleserver.util.Consts;
import simpleserver.webserver.response.ProductGroupsOkResponseImpl;
import simpleserver.webserver.response.ProductGroupsResponse;

import java.util.Map;
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
     * Gets info.
     *
     * @return the info.
     */
    @RequestMapping("/info")
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
    @RequestMapping("/product-groups")
    public Map<String, Object> getProductGroups() {
        logger.debug(Consts.LOG_ENTER);
        ProductGroups productGroups = domain.getProductGroups();
        ProductGroupsResponse productGroupsOkResponse = ProductGroupsOkResponseImpl.createProductGroupsOkResponse(productGroups);
        logger.debug(Consts.LOG_EXIT);
        Map<String, Object> ret = productGroupsOkResponse.getResponse();
        return ret;
    }


}
