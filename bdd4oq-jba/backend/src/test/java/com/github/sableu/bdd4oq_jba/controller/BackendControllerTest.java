package com.github.sableu.bdd4oq_jba.controller;

import com.github.sableu.bdd4oq_jba.Bdd4oqJbApplication;
import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = Bdd4oqJbApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class BackendControllerTest {

    @LocalServerPort
    private int port;

    @Before
    public void init() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    public void saysHello() {
        when()
                .get("/api/hello/Andreas")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .assertThat()
                .body(is(equalTo(BackendController.HELLO_TEXT + "Andreas")));
    }

    @Test
    public void addParticipant() {
        given()
                .param("lastName", "Hosbach")
                .param("firstName", "Andreas")
        .when()
                .put("/api/participant")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .assertThat()
                .body(is(equalTo("1")));

        when()
                .get("/api/participant/1")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .assertThat()
                .body(is(equalTo("{\"id\":1,\"firstName\":\"Andreas\",\"lastName\":\"Hosbach\"}")));
    }
}
