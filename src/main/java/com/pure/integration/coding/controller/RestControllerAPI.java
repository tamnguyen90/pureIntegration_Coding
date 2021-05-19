package com.pure.integration.coding.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.asm.TypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/breeds")
public class RestControllerAPI {
    @GetMapping("/list/all")
    public ResponseEntity<ResponseObject> listAllBreeds() throws IOException {
        Map<String, List<String>> data = parseJsonData();
        if (data != null) {
            ResponseObject response = new ResponseObject(data, "success");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private Map<String, List<String>> parseJsonData() throws IOException {
        InputStream input = TypeReference.class.getResourceAsStream("/data/data.json");
        try {
            Map<String, List<String>> sortedMap = new LinkedHashMap<>();
            Map<String, List<String>> data = new ObjectMapper().readValue(input, Map.class);
            data.entrySet()
                    .stream()
                    .sorted((a, b) -> a.getKey().compareTo(b.getKey()))
                    .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));
            return sortedMap;
        } catch (IOException e) {
            throw new IOException("Can't parse the json file to data object");
        }
    }
}
