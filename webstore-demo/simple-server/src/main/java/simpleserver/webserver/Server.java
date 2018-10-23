package simpleserver.webserver;

import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import simpleserver.domaindb.Domain;
import simpleserver.domaindb.dto.Info;
import simpleserver.domaindb.dto.Product;
import simpleserver.domaindb.dto.ProductGroups;
import simpleserver.userdb.Users;
import simpleserver.userdb.dto.User;
import simpleserver.util.Consts;
import simpleserver.util.SSErrorCode;
import simpleserver.util.SSException;
import simpleserver.webserver.dto.Signin;
import simpleserver.webserver.response.*;

/**
 * The Server (Spring controller).
 */
@RestController
public class Server {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final Domain domain;
    private final Users users;

    /**
     * Instantiates a new Server.
     * Autowires the Domain and Users services.
     *
     * @param domain the domain
     * @param users the users
     */
    @Autowired
    public Server(Domain domain, Users users) {
        this.domain = domain;
        this.users = users;
    }


    /**
     * A very simple validator: just check that no item in the list is empty.
     *
     * @param params List of parameters
     * @return true if parameters ok, false otherwise
     */
    private boolean validateParameters(List<String> params) {
        logger.debug(Consts.LOG_ENTER);
        boolean ret;
        if (params == null) {
            throw new IllegalArgumentException("params is null in validateParameters");
        }
        ret = params.stream().noneMatch(item -> ((item == null) || (item.isEmpty())));
        logger.debug(Consts.LOG_EXIT);
        return ret;
    }


    /**
     * Gets info.
     *
     * @return the info.
     */
    @GetMapping(path = "/info")
    public Info getInfo() {
        logger.debug(Consts.LOG_ENTER);
        Info info = domain.getInfo();
        logger.debug(Consts.LOG_EXIT);
        return info;
    }


    /**
     * Posts the sign-in.
     *
     * @return response regarding if the signin was successful
     */
    @PostMapping(path = "/signin")
    public ResponseEntity<Map> postSignIn(@RequestBody Signin signin) {
        logger.debug(Consts.LOG_ENTER);
        Response response;
        var params = signin.getParamsAsList();
        boolean validationPassed = validateParameters(params);
        if (!validationPassed) {
            response = SigninFailedResponseImpl.
                    createSigninFailedResponse("Validation failed - some fields were empty", null);
        }
        else {
            User newUser;
            try {
                 newUser = users.addUser(signin.email, signin.firstName, signin.lastName, signin.password);
                 if (newUser == null) {
                     response = SigninFailedResponseImpl.
                             createSigninFailedResponse("Users service returned null user - internal error", null);
                 }
                 else {
                     response = SigninOkResponseImpl.createSigninOkResponse(signin.email);
                 }
            }
            catch (SSException ssEx) {
                if (ssEx.getCode() == SSErrorCode.EMAIL_ALREADY_EXISTS) {
                     response = SigninFailedResponseImpl.
                             createSigninFailedResponse("Email already exists", signin.email);
                }
                else {
                     response = SigninFailedResponseImpl.
                             createSigninFailedResponse("Some other SSException: " + ssEx.getMessage(), null);
                }
            }
        }
        HttpStatus httpStatus = response.isOk() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        ResponseEntity<Map> responseEntity = new ResponseEntity<>(response.getRestView(), httpStatus);
        logger.debug(Consts.LOG_EXIT);
        return responseEntity;
    }


    /**
     * Gets product groups.
     *
     * @return the product groups
     */
    @GetMapping(path = "/product-groups")
    public Map<String, Object> getProductGroups() {
        logger.debug(Consts.LOG_ENTER);
        Response response;
        ProductGroups productGroups = domain.getProductGroups();
        response = ProductGroupsOkResponseImpl.createProductGroupsOkResponse(productGroups);
        logger.debug(Consts.LOG_EXIT);
        return response.getRestView();
    }


    /**
     * Gets products.
     *
     * @return the products
     */
    @GetMapping(path = "/products/{pgId}")
    public Map<String, Object>  getProducts(
            @PathVariable("pgId") int pgId) {
        logger.debug(Consts.LOG_ENTER);
        Response response;
        List<Product> products = domain.getProducts(pgId);
        response = ProductsOkResponseImpl.createProductsOkResponse(pgId, products);
        logger.debug(Consts.LOG_EXIT);
        return response.getRestView();
    }


    /**
     * Gets one product.
     *
     * @return the product
     */
    @GetMapping(path = "/product/{pgId}/{pId}")
    public Map<String, Object>  getProducts(
            @PathVariable("pgId") int pgId, @PathVariable("pId") int pId) {
        logger.debug(Consts.LOG_ENTER);
        Response response;
        Product product = domain.getProduct(pgId, pId);
        response = ProductOkResponseImpl.createProductOkResponse(product);
        logger.debug(Consts.LOG_EXIT);
        return response.getRestView();
    }
}
