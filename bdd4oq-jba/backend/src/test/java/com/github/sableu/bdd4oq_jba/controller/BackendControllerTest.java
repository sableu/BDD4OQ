package com.github.sableu.bdd4oq_jba.controller;

import com.github.sableu.bdd4oq_jba.Bdd4oqJbApplication;
import com.github.sableu.bdd4oq_jba.domain.Participant;
import com.github.sableu.bdd4oq_jba.repository.ParticipantRepository;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSender;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.ResponseCache;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.MatcherAssert.assertThat;
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

    @Autowired
    private ParticipantRepository participantRepository;

    @Before
    public void init() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @BeforeEach
    public void beforeEach() {
        participantRepository.deleteAll();
    }

    @Test
    public void saysHello() {
        RequestSender sender =when();
                Response response = sender.get("/api/hello/Andreas");
                ValidatableResponse vResponse = response.then();
                vResponse.statusCode(HttpStatus.SC_OK);
                vResponse.assertThat().body(is(equalTo(BackendController.HELLO_TEXT + "Andreas")));
    }

    @Test
    public void addParticipant() {

        RequestSpecification request = given();
        request.param("lastName", "Hosbach");
        request.param("firstName", "Andreas");
        request.param("birthday", "19.3.1975");
        request.param("gender", "male");

        RequestSender sender = request.when();
        Response response = sender.put("/api/participant");

        ValidatableResponse vResponse = response.then();
        vResponse.statusCode(HttpStatus.SC_CREATED);

        Integer id = vResponse.extract().as(Integer.class);
        Participant andreas = participantRepository.findById(id);

        assertThat(andreas.getFirstName(), is("Andreas"));
        assertThat(andreas.getLastName(), is("Hosbach"));
        assertThat(andreas.getBirthday(), is("19.3.1975"));
        assertThat(andreas.getGender(), is("male"));
    }

    @Test
    public void getParticipant() {

        // add to db
        Participant peter = participantRepository.save(new Participant("Peter", "Parker", "03.05.1995", "male"));

        RequestSender sender = when();
        Response response = sender.get("/api/participant/" + peter.getId());
        ValidatableResponse vResponse = response.then();
        vResponse.statusCode(HttpStatus.SC_OK);
        Participant maybePeter = vResponse.extract().as(Participant.class);

        assertThat(maybePeter.getFirstName(), is(peter.getFirstName()));
        assertThat(maybePeter.getLastName(), is(peter.getLastName()));
        assertThat(maybePeter.getBirthday(), is(peter.getBirthday()));
        assertThat(maybePeter.getGender(), is(peter.getGender()));
    }
}
