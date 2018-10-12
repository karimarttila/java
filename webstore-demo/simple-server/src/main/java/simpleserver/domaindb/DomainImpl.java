package simpleserver.domaindb;

import com.opencsv.CSVReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import simpleserver.domaindb.dto.ProductGroups;
import simpleserver.util.Consts;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


/**
 * Domain class.
 */
@Service
public class DomainImpl implements Domain {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String productGroupFile = "product-groups.csv";
    ResourceLoader resourceLoader;

    @Autowired
    public DomainImpl(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    /**
     * A private helper to read CSV files to a list of String arrays.
     *
     * @param fileName File name
     * @return CSV file content as List of String arrays.
     */
    private List<String[]> readCsv(String fileName) {
        logger.debug(Consts.LOG_ENTER + ", fileName: " + fileName);
        List<String[]> ret = null;
        Resource res = resourceLoader.getResource("classpath:" + fileName);
        try (InputStream is = res.getInputStream();
             Reader reader = new InputStreamReader(is);
             CSVReader csvReader = new CSVReader(reader, '\t');
        ) {
            ret = csvReader.readAll();
        } catch (IOException e) {
            logger.error("Error while processing csv: %s", e.getMessage());
        }

        logger.debug(Consts.LOG_EXIT);
        return ret;
    }


    @Override
    public ProductGroups getProductGroups() {
        var productGroups = new ProductGroups();
        logger.debug(Consts.LOG_ENTER);
        List<String[]> csvList = readCsv(productGroupFile);
        if (csvList != null) {
            csvList.forEach((item) -> {
                String key = item[0];
                String value = item[1];
                productGroups.addProductGroup(key, value);
            });
        }
        logger.debug(Consts.LOG_EXIT);
        return productGroups;
    }

}
