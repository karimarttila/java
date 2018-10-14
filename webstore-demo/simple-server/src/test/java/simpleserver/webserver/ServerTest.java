package simpleserver.webserver;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.io.IOException;
import java.util.HashMap;
import simpleserver.util.Consts;


@ExtendWith(SpringExtension.class)
@WebMvcTest(Server.class)
public class ServerTest {
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

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/info").contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8);
        MvcResult mvcResult = this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedResultJson))
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

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/product-groups").contentType(MediaType.APPLICATION_JSON_UTF8)
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

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/products/1").contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8);
        MvcResult mvcResult = this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.ret").value("ok"))
                .andExpect(jsonPath("$.pg-id").value("1"))
                .andExpect(jsonPath("$.products", hasSize(35)))
                .andReturn();

        logger.trace("Content: " +  mvcResult.getResponse().getContentAsString());
    }

}

