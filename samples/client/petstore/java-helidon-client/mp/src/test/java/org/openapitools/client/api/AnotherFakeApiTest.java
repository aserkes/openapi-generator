/**
 * OpenAPI Petstore
 * This spec is mainly for testing Petstore server and contains fake endpoints, models. Please do not use this for any other purpose. Special characters: \" \\
 *
 * The version of the OpenAPI document: 1.0.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package org.openapitools.client.api;

import org.openapitools.client.model.Client;
import org.junit.Test;
import org.junit.BeforeClass;
import static org.junit.Assert.*;

import org.eclipse.microprofile.rest.client.RestClientBuilder;

import java.net.URL;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * OpenAPI Petstore Test
 *
 * API tests for AnotherFakeApi
 */
public class AnotherFakeApiTest {

    private static AnotherFakeApi client;
    private static final String baseUrl = "http://localhost:8080";

    @BeforeClass
    public static void setup() throws MalformedURLException {
        client = RestClientBuilder.newBuilder()
                        .baseUrl(new URL(baseUrl))
                        .register(ApiException.class)
                        .build(AnotherFakeApi.class);
    }

    
    /**
     * To test special tags
     *
     * To test special tags and operation ID starting with number
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void call123testSpecialTagsTest() throws Exception {
        //Client response = client.call123testSpecialTags(client);
        //assertNotNull(response);
    }
    
}
