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
import simpleserver.domaindb.dto.Product;
import simpleserver.domaindb.dto.ProductGroups;
import simpleserver.util.SSConsts;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.*;
import java.util.stream.Collectors;


/**
 * Domain class.
 */
@Service
public class DomainImpl implements Domain {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ResourceLoader resourceLoader;
    // Map for products in products group (key: product group id).
    private final Map<String, List<Product>> syncProductsCache =
            Collections.synchronizedMap(new HashMap<>());

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
        logger.debug("{}, fileName: {}", SSConsts.LOG_ENTER, fileName);
        List<String[]> ret = null;
        Resource res = resourceLoader.getResource("classpath:" + fileName);
        CSVParser parser = new CSVParserBuilder().withSeparator('\t').build();
        try (InputStream is = res.getInputStream();
             Reader reader = new InputStreamReader(is);
             CSVReader csvReader = new CSVReaderBuilder(reader).withCSVParser(parser).build()
        ) {
            ret = csvReader.readAll();
        } catch (IOException e) {
            logger.error("Error while processing csv: {}", e.getMessage());
        }

        logger.debug(SSConsts.LOG_EXIT);
        return ret;
    }


    @Override
    public Info getInfo() {
        var info = new Info();
        logger.debug(SSConsts.LOG_ENTER);
        logger.debug(SSConsts.LOG_EXIT);
        return info;
    }


    @Override
    public ProductGroups getProductGroups() {
        var productGroups = new ProductGroups();
        logger.debug(SSConsts.LOG_ENTER);
        String productGroupFile = "product-groups.csv";
        List<String[]> csvList = readCsv(productGroupFile);
        if (csvList != null) {
            csvList.forEach(item -> {
                String key = item[0];
                String value = item[1];
                productGroups.addProductGroup(key, value);
            });
        }
        logger.debug(SSConsts.LOG_EXIT);
        return productGroups;
    }


    @Override
    public List<Product> getProducts(int pgId) {
        List<Product> products;
        logger.debug("{}, pgId: {}", SSConsts.LOG_ENTER, pgId);
        products = syncProductsCache.get(Integer.toString(pgId));
        if (products == null) {
            logger.debug(" Loading pgId {} to cache", pgId);
            String productsFile = "pg-" + pgId + "-products.csv";
            List<String[]> csvList = readCsv(productsFile);
            List<Product> newProductsCache = new ArrayList<>();
            if (csvList != null) {
                csvList.forEach(item -> {
                    int pId = Integer.parseInt(item[0]);
                    int myPgId = Integer.parseInt(item[1]);
                    String title = item[2];
                    double price = Double.parseDouble(item[3]);
                    String authorOrDirector = item[4];
                    int year = Integer.parseInt(item[5]);
                    String country = item[6];
                    String genreOrLanguage = item[7];
                    Product product = new Product(myPgId, pId, title, price,
                            authorOrDirector, year, country,
                            genreOrLanguage);
                    newProductsCache.add(product);
                });
            }
            syncProductsCache.put(Integer.toString(pgId), newProductsCache);
            products = newProductsCache;
        }
        logger.debug(SSConsts.LOG_EXIT);
        return products;
    }


    @Override
    public Product getProduct(int pgId, int pId) {
        logger.debug("{}, pgId: {}, pId: {}", SSConsts.LOG_ENTER, pgId, pId);
        var products = syncProductsCache.get(Integer.toString(pgId));
        if (products == null) {
            products = getProducts(pgId);
        }
        Product product = null;
        if (products != null) {
            List<Product> result = products.stream().filter(thisProduct ->
                    (thisProduct.pId == pId) && (thisProduct.pgId == pgId))
                    .collect(Collectors.toList());
            // There should be 0 or 1.
            if (result.size() == 1) {
                product = result.get(0);
            }
            else {
                logger.error("Didn't find exactly one product, count is: {}", result.size());
            }
        }
        else {
            logger.error("Couldn't find products for pgId: {}", pgId);
        }
        logger.debug(SSConsts.LOG_EXIT);
        return product;
    }
}
