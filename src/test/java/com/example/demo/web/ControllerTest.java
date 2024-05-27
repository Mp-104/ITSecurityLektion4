package com.example.demo.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.config.http.MatcherType.mvc;

@TestPropertySource(properties = { "spring.security.user.name=user", "spring.security.user.password=secret", "eureka.client.enabled=false" } )
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
        "spring.security.user.name=user"
        ,
        "spring.security.user.password=secret"
})
@TestPropertySource(properties = {
        "spring.security.user.name=user"
        ,
        "spring.security.user.password=secret"
})
public class ControllerTest {

    @Autowired
    TestRestTemplate testRestTemplate;


    @Test
    public void endPointTest () {

        //testRestTemplate = new TestRestTemplate()/*.withBasicAuth("user", "4edcc4e7-be67-4220-8749-8b4ed65b46fc")*/;

        ResponseEntity<String> retur = testRestTemplate.withBasicAuth("user", "password").getForEntity("http://localhost:8080/", String.class);


        assertThat(retur.getStatusCode()).isEqualTo(HttpStatus.OK);

        assertThat(retur.getBody()).isEqualTo("Welcome to my site!");
    }


}
