package com.pure.integration.coding;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pure.integration.coding.controller.ResponseObject;
import org.apache.coyote.Response;
import org.junit.jupiter.api.Test;
import org.springframework.asm.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class PureIntegrationCodingApplicationTests {
    @Autowired
    RestTemplate restTemplate;

    @Test
    void testRestAPI() throws IOException {
        InputStream resultData = TypeReference.class.getResourceAsStream("/data/response.json");
        ResponseObject expectedResult = new ObjectMapper().readValue(resultData, ResponseObject.class);
        ResponseObject callResult = restTemplate.getForObject("http://localhost:8080/api/breeds/list/all", ResponseObject.class);
        Assert.isTrue(expectedResult.equals(callResult), "Testing successfully.");

    }
}
