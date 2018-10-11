package simpleserver.domaindb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import simpleserver.domaindb.dto.ProductGroups;
import simpleserver.util.Consts;

/**
 * Domain class.
 */
public class DomainImpl implements Domain {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public ProductGroups getProductGroups() {
        var productGroups = new ProductGroups();
        logger.debug(Consts.LOG_ENTER);
        productGroups.addProductGroup("1", "Movies");
        productGroups.addProductGroup("2", "Books");

        logger.debug(Consts.LOG_EXIT);
        return productGroups;
    }
}
