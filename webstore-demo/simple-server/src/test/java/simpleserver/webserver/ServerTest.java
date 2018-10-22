package simpleserver.webserver;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.Collections;
import java.util.HashMap;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import simpleserver.util.Consts;
import simpleserver.webserver.dto.Signin;


@ExtendWith(SpringExtension.class)
@WebMvcTest(Server.class)
class ServerTest {
    @SuppressWarnings("unused")
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        logger.debug(Consts.LOG_ENTER);
        logger.debug(Consts.LOG_EXIT);
    }


    @Test
    void getInfoTest() throws Exception {
        logger.debug(Consts.LOG_ENTER);
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

        logger.trace("Content: " +  mvcResult.getResponse().getContentAsString());

    }


    @Test
    void postOkSigninTest() throws Exception {
        logger.debug(Consts.LOG_ENTER);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/signin")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{ " +
                        "\"email\": \"jamppa.jamppanen@foo.com\"," +
                        " \"first-name\": \"Jamppa\"," +
                        " \"last-name\": \"Jamppanen\"," +
                        " \"password\": \"JampanSalasana\"" +
                        " }")
                .accept(MediaType.APPLICATION_JSON_UTF8);
        MvcResult mvcResult = this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.ret").value("ok"))
                .andExpect(jsonPath("$.email").value("jamppa.jamppanen@foo.com"))
                .andReturn();
        logger.trace("Content: " +  mvcResult.getResponse().getContentAsString());
    }


    @Test
    void postEmailAlreadyExistsSigninTest() throws Exception {
        logger.debug(Consts.LOG_ENTER);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/signin")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{ " +
                        "\"email\": \"kari.karttinen@foo.com\"," +
                        " \"first-name\": \"Kari\"," +
                        " \"last-name\": \"Karttinen\"," +
                        " \"password\": \"KarinSalasana\"" +
                        " }")
                .accept(MediaType.APPLICATION_JSON_UTF8);
        MvcResult mvcResult = this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(jsonPath("$.ret").value("failed"))
                .andExpect(jsonPath("$.msg").value("Email already exists"))
                .andReturn();
        logger.trace("Content: " +  mvcResult.getResponse().getContentAsString());
    }


    @Test
    void getProductGroupsTest() throws Exception {
        logger.debug(Consts.LOG_ENTER);
        HashMap<String, String> productGroups = new HashMap<>();
        productGroups.put("1", "Books");
        productGroups.put("2", "Movies");
        HashMap<String, Object> expectedResult = new HashMap<>();
        expectedResult.put("ret", "ok");
        expectedResult.put("product-groups", productGroups);
        String expectedResultJson = new JSONObject(expectedResult).toString();

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .get("/product-groups")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8);
        MvcResult mvcResult = this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedResultJson))
                .andReturn();

        logger.trace("Content: " +  mvcResult.getResponse().getContentAsString());
    }


    @Test
    void getProductsTest() throws Exception {
        logger.debug(Consts.LOG_ENTER);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .get("/products/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8);
        MvcResult mvcResult = this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.ret").value("ok"))
                .andExpect(jsonPath("$.pg-id").value("1"))
                .andExpect(jsonPath("$.products", hasSize(35)))
                .andReturn();

        logger.trace("Content: " +  mvcResult.getResponse().getContentAsString());
    }


    @Test
    void getProductTest() throws Exception {
        logger.debug(Consts.LOG_ENTER);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .get("/product/2/49")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
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
        logger.trace("Content: " +  mvcResult.getResponse().getContentAsString());
    }

}
