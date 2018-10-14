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
import java.util.Map;
import simpleserver.domaindb.dto.Info;
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
}
