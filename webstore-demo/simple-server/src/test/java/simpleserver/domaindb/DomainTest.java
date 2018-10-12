package simpleserver.domaindb;


import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import simpleserver.domaindb.dto.ProductGroups;
import simpleserver.util.Consts;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Domain test")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { DomainImpl.class })
public class DomainTest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    Domain domain;

    @DisplayName("Tests getting the product groups")
    @Test
    public void getProductGroupsTest() {
        logger.debug(Consts.LOG_ENTER);
        ProductGroups productGroups = domain.getProductGroups();
        String json = productGroups.getProductGroupsAsJson();
        assertEquals(json, "{\"1\":\"Books\",\"2\":\"Movies\"}");
        logger.debug(Consts.LOG_EXIT);


    }
}

