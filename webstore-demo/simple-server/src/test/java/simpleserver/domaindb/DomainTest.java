package simpleserver.domaindb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import simpleserver.domaindb.dto.ProductGroups;
import simpleserver.util.Consts;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Domain test")
public class DomainTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    Domain domain = new DomainImpl();

    @Test
    @DisplayName("Tests getting the product groups")
    public void getProductGroupsTest() {
        logger.debug(Consts.LOG_ENTER);
        ProductGroups productGroups = domain.getProductGroups();
        String json = productGroups.getProductGroupsAsJson();
        assertEquals(json, "{\"1\":\"Movies\",\"2\":\"Books\"}");
        logger.debug(Consts.LOG_EXIT);


    }
}

