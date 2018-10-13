package simpleserver.domaindb;

import simpleserver.domaindb.dto.Info;
import simpleserver.domaindb.dto.ProductGroups;

/**
 * The interface Domain.
 */
public interface Domain {

    /**
     * Gets product groups.
     *
     * @return the product groups
     */
    ProductGroups getProductGroups();

    /**
     * Gets info.
     *
     * @return the info
     */
    Info getInfo();

}
