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
import simpleserver.util.*;
import simpleserver.webserver.dto.LoginData;
import simpleserver.webserver.dto.SigninData;
import simpleserver.webserver.response.*;

/**
 * The Server (Spring controller).
 */
@RestController
public class Server {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final Domain domain;
    private final Users users;
    private final Session session;

    /**
     * Instantiates a new Server.
     * Autowires the Domain and Users services.
     *
     * @param domain the domain
     * @param users the users
     * @param session the session
     * @param props the configuration
     *
     */
    @Autowired
    public Server(Domain domain, Users users, Session session, SSProperties props) {
        this.domain = domain;
        this.users = users;
        this.session = session;
        logger.debug("******************************************************");
        logger.info("Using application properties file in: {}", props.getApplicationPropertiesFilePath());
    }


    /**
     * A very simple validator: just check that no item in the list is empty.
     *
     * @param params List of parameters
     * @return true if parameters ok, false otherwise
     */
    private boolean validateParameters(List<String> params) {
        logger.debug(SSConsts.LOG_ENTER);
        boolean ret;
        if (params == null) {
            throw new IllegalArgumentException("params is null in validateParameters");
        }
        ret = params.stream().noneMatch(item -> ((item == null) || (item.isEmpty())));
        logger.debug(SSConsts.LOG_EXIT);
        return ret;
    }


    /**
     * Gets info.
     *
     * @return the info.
     */
    @GetMapping(path = "/info")
    public Info getInfo() {
        logger.debug(SSConsts.LOG_ENTER);
        Info info = domain.getInfo();
        logger.debug(SSConsts.LOG_EXIT);
        return info;
    }


    /**
     * Posts the sign-in.
     *
     * @return response regarding if the signinData was successful
     */
    @PostMapping(path = "/signin")
    public ResponseEntity<Map> postSignIn(@RequestBody SigninData signinData) {
        logger.debug(SSConsts.LOG_ENTER);
        Response response;
        var params = signinData.getParamsAsList();
        boolean validationPassed = validateParameters(params);
        if (!validationPassed) {
            response = SigninFailedResponseImpl.
                    createSigninFailedResponse("Validation failed - some fields were empty", null);
        }
        else {
            User newUser;
            try {
                 newUser = users.addUser(signinData.email, signinData.firstName, signinData.lastName, signinData.password);
                 if (newUser == null) {
                     response = SigninFailedResponseImpl.
                             createSigninFailedResponse("Users service returned null user - internal error", null);
                 }
                 else {
                     response = SigninOkResponseImpl.createSigninOkResponse(signinData.email);
                 }
            }
            catch (SSException ssEx) {
                if (ssEx.getCode() == SSErrorCode.EMAIL_ALREADY_EXISTS) {
                     response = SigninFailedResponseImpl.
                             createSigninFailedResponse("Email already exists", signinData.email);
                }
                else {
                     response = SigninFailedResponseImpl.
                             createSigninFailedResponse("Some other SSException: " + ssEx.getMessage(), null);
                }
            }
        }
        HttpStatus httpStatus = response.isOk() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        ResponseEntity<Map> responseEntity = new ResponseEntity<>(response.getRestView(), httpStatus);
        logger.debug(SSConsts.LOG_EXIT);
        return responseEntity;
    }


    /**
     * Posts the sign-in.
     *
     * @return response regarding if the signinData was successful
     */
    @PostMapping(path = "/login")
    public ResponseEntity<Map> postLogin(@RequestBody LoginData loginData) {
        logger.debug(SSConsts.LOG_ENTER);
        Response response;
        var params = loginData.getParamsAsList();
        boolean validationPassed = validateParameters(params);
        if (!validationPassed) {
            response = LoginFailedResponseImpl.
                    createLoginFailedResponse("Validation failed - some fields were empty", null);
        }
        else {
            boolean credentialsOk = users.checkCredentials(loginData.email, loginData.password);
            if (!credentialsOk) {
            response = LoginFailedResponseImpl.
                    createLoginFailedResponse("Credentials are not good - either email or password is not correct", loginData.email);
            }
            else {
                String jwt = session.createJsonWebToken(loginData.email);
                response = LoginOkResponseImpl.createLoginOkResponse(jwt);
            }
        }
        HttpStatus httpStatus = response.isOk() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        ResponseEntity<Map> responseEntity = new ResponseEntity<>(response.getRestView(), httpStatus);
        logger.debug(SSConsts.LOG_EXIT);
        return responseEntity;
    }


    /**
     * Gets product groups.
     *
     * @return the product groups
     */
    @GetMapping(path = "/product-groups")
    public Map<String, Object> getProductGroups() {
        logger.debug(SSConsts.LOG_ENTER);
        Response response;
        ProductGroups productGroups = domain.getProductGroups();
        response = ProductGroupsOkResponseImpl.createProductGroupsOkResponse(productGroups);
        logger.debug(SSConsts.LOG_EXIT);
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
        logger.debug(SSConsts.LOG_ENTER);
        Response response;
        List<Product> products = domain.getProducts(pgId);
        response = ProductsOkResponseImpl.createProductsOkResponse(pgId, products);
        logger.debug(SSConsts.LOG_EXIT);
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
        logger.debug(SSConsts.LOG_ENTER);
        Response response;
        Product product = domain.getProduct(pgId, pId);
        response = ProductOkResponseImpl.createProductOkResponse(product);
        logger.debug(SSConsts.LOG_EXIT);
        return response.getRestView();
    }
}
