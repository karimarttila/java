package simpleserver.domaindb;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import simpleserver.domaindb.dto.Info;
import simpleserver.domaindb.dto.ProductGroups;
import simpleserver.util.Consts;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;


/**
 * Domain class.
 */
@Service
public class DomainImpl implements Domain {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ResourceLoader resourceLoader;

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
        CSVParser parser = new CSVParserBuilder().withSeparator('\t').build();
        try (InputStream is = res.getInputStream();
             Reader reader = new InputStreamReader(is);
             CSVReader csvReader = new CSVReaderBuilder(reader).withCSVParser(parser).build()
        ) {
            ret = csvReader.readAll();
        } catch (IOException e) {
            logger.error("Error while processing csv: %s", e.getMessage());
        }

        logger.debug(Consts.LOG_EXIT);
        return ret;
    }


    @Override
    public Info getInfo() {
        var info = new Info();
        logger.debug(Consts.LOG_ENTER);
        logger.debug(Consts.LOG_EXIT);
        return info;
    }


    @Override
    public ProductGroups getProductGroups() {
        var productGroups = new ProductGroups();
        logger.debug(Consts.LOG_ENTER);
        String productGroupFile = "product-groups.csv";
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
