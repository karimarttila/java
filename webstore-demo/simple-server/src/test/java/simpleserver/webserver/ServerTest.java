package simpleserver.webserver;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import simpleserver.util.SSConsts;

import java.util.Base64;
import java.util.HashMap;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


// NOTE: Spring Rest Controller test reads application.properties file
// from /main/webapp directory!
@SpringJUnitWebConfig(Server.class)
@AutoConfigureMockMvc
@ComponentScan(basePackages = {"simpleserver"})
class ServerTest {
    @SuppressWarnings("unused")
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MockMvc mockMvc;


    @BeforeEach
    void setup() {
        logger.debug(SSConsts.LOG_ENTER);
        logger.debug(SSConsts.LOG_EXIT);
    }


    @Test
    void getInfoTest() throws Exception {
        logger.debug(SSConsts.LOG_ENTER);
        HashMap<String, String> expectedResult = new HashMap<>();
        expectedResult.put("info", "index.html => Info in HTML format");
        String expectedResultJson = new JSONObject(expectedResult).toString();
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .get("/info")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8);
        MvcResult mvcResult = this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedResultJson))
                .andReturn();

        logger.trace("Content: " + mvcResult.getResponse().getContentAsString());

    }


    @Test
    void postSigninTest() throws Exception {
        logger.debug(SSConsts.LOG_ENTER);
        String requestBody = "{ " +
                "\"email\": \"jamppa.jamppanen@foo.com\"," +
                " \"first-name\": \"Jamppa\"," +
                " \"last-name\": \"Jamppanen\"," +
                " \"password\": \"JampanSalasana\"" +
                " }";
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/signin")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(requestBody)
                .accept(MediaType.APPLICATION_JSON_UTF8);
        MvcResult mvcResult = this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.ret").value("ok"))
                .andExpect(jsonPath("$.email").value("jamppa.jamppanen@foo.com"))
                .andReturn();
        // Check that cannot add another time.
        builder = MockMvcRequestBuilders.post("/signin")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(requestBody)
                .accept(MediaType.APPLICATION_JSON_UTF8);
        mvcResult = this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(jsonPath("$.ret").value("failed"))
                .andExpect(jsonPath("$.msg").value("Email already exists"))
                .andReturn();
        logger.trace("Content: " + mvcResult.getResponse().getContentAsString());
        logger.debug(SSConsts.LOG_EXIT);
    }


    @Test
    void postLoginTest() throws Exception {
        logger.debug(SSConsts.LOG_ENTER);
        String requestBodyOk = "{ " +
                "\"email\": \"kari.karttinen@foo.com\"," +
                " \"password\": \"Kari\"" +
                " }";
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/login")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(requestBodyOk)
                .accept(MediaType.APPLICATION_JSON_UTF8);
        MvcResult mvcResult = this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.ret").value("ok"))
                .andExpect(jsonPath("$.msg").value("Credentials ok"))
                .andReturn();
    }


    @Test
    void postFailedLoginTest() throws Exception {
        logger.debug(SSConsts.LOG_ENTER);
        String requestBodyNotOk = "{ " +
                "\"email\": \"kari.karttinen@foo.com\"," +
                " \"password\": \"WRONG-PASSWORD\"" +
                " }";
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/login")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(requestBodyNotOk)
                .accept(MediaType.APPLICATION_JSON_UTF8);
        MvcResult mvcResult = this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(jsonPath("$.ret").value("failed"))
                .andExpect(jsonPath("$.msg").value("Credentials are not good - either email or password is not correct"))
                .andReturn();
        logger.trace("Content: " + mvcResult.getResponse().getContentAsString());
        logger.debug(SSConsts.LOG_EXIT);
    }


    String getEncodedJwt() throws Exception {
        logger.debug(SSConsts.LOG_ENTER);
        String requestBodyOk = "{ " +
                "\"email\": \"kari.karttinen@foo.com\"," +
                " \"password\": \"Kari\"" +
                " }";
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/login")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(requestBodyOk)
                .accept(MediaType.APPLICATION_JSON_UTF8);
        MvcResult mvcResult = this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.ret").value("ok"))
                .andExpect(jsonPath("$.msg").value("Credentials ok"))
                .andReturn();
        MockHttpServletResponse mhsRes = mvcResult.getResponse();
        String res = mhsRes.getContentAsString();
        JSONObject jsonObject = new JSONObject(res);
        String jwt = jsonObject.getString("json-web-token");
        String encodedJwt = Base64.getEncoder().encodeToString(jwt.getBytes());
        logger.debug(SSConsts.LOG_EXIT);
        return encodedJwt;
    }


    @Test
    void getProductGroupsTest() throws Exception {
        logger.debug(SSConsts.LOG_ENTER);
        HashMap<String, String> productGroups = new HashMap<>();
        productGroups.put("1", "Books");
        productGroups.put("2", "Movies");
        HashMap<String, Object> expectedResult = new HashMap<>();
        expectedResult.put("ret", "ok");
        expectedResult.put("product-groups", productGroups);
        String expectedResultJson = new JSONObject(expectedResult).toString();
        String encodedJwt = getEncodedJwt();

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .get("/product-groups")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .header("Authorization", "Basic " + encodedJwt)
                .accept(MediaType.APPLICATION_JSON_UTF8);
        MvcResult mvcResult = this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedResultJson))
                .andReturn();
        logger.trace("Content: " + mvcResult.getResponse().getContentAsString());
    }


    @Test
    void getProductsTest() throws Exception {
        logger.debug(SSConsts.LOG_ENTER);
        String encodedJwt = getEncodedJwt();

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .get("/products/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .header("Authorization", "Basic " + encodedJwt)
                .accept(MediaType.APPLICATION_JSON_UTF8);
        MvcResult mvcResult = this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.ret").value("ok"))
                .andExpect(jsonPath("$.pg-id").value("1"))
                .andExpect(jsonPath("$.products", hasSize(35)))
                .andReturn();

        logger.trace("Content: " + mvcResult.getResponse().getContentAsString());
    }


    @Test
    void getProductTest() throws Exception {
        logger.debug(SSConsts.LOG_ENTER);
        String encodedJwt = getEncodedJwt();

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .get("/product/2/49")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .header("Authorization", "Basic " + encodedJwt)
                .accept(MediaType.APPLICATION_JSON_UTF8);
        MvcResult mvcResult = this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.ret").value("ok"))
                .andExpect(jsonPath("$.pg-id").value("2"))
                .andExpect(jsonPath("$.p-id").value("49"))
                .andExpect(jsonPath("$.product", hasSize(8)))
                // What a coincidence! The chosen movie is the best western of all times!
                .andExpect(jsonPath("$.product[2]").value("Once Upon a Time in the West"))
                .andReturn();
        logger.trace("Content: " + mvcResult.getResponse().getContentAsString());
    }

}
