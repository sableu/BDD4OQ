package com.github.sableu.bdd4oq_jba.controller;

import com.github.sableu.bdd4oq_jba.Bdd4oqJbApplication;
import com.github.sableu.bdd4oq_jba.domain.Participant;
import com.github.sableu.bdd4oq_jba.repository.ParticipantRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
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
import java.util.ArrayList;
import java.util.List;

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

        ParticipantDto participantDto = new ParticipantDto();
        participantDto.lastName = "Hosbach";
        participantDto.firstName = "Andreas";
        participantDto.birthday = "19.03.1975";
        participantDto.gender = "male";

        RequestSpecification request = given();
        request.contentType(ContentType.JSON);
        request.body(participantDto);
        RequestSender sender = request.when();
        Response response = sender.post("/api/participant");

        ValidatableResponse vResponse = response.then();
        vResponse.statusCode(HttpStatus.SC_CREATED);

        Long id = vResponse.extract().as(Long.class);
        Participant andreas = participantRepository.findById(id).get();

        assertThat(andreas.getLastName(), is(participantDto.lastName));
        assertThat(andreas.getFirstName(), is(participantDto.firstName));
        assertThat(andreas.getBirthday(), is(participantDto.birthday));
        assertThat(andreas.getGender(), is(participantDto.gender));
    }

    @Test
    public void getParticipantById() {

        // add to db
        Participant peter = participantRepository.save(new Participant("Peter", "Parker", "03.05.1995", "male"));

        RequestSender sender = when();
        Response response = sender.get("/api/participant/" + peter.getId());
        ValidatableResponse vResponse = response.then();
        vResponse.statusCode(HttpStatus.SC_OK);
        ParticipantDto maybePeter = vResponse.extract().as(ParticipantDto.class);

        assertThat(maybePeter.firstName, is(peter.getFirstName()));
        assertThat(maybePeter.lastName, is(peter.getLastName()));
        assertThat(maybePeter.birthday, is(peter.getBirthday()));
        assertThat(maybePeter.gender, is(peter.getGender()));
    }

    @Test
    public void getParticipantByIdNotFound() {
        RequestSender sender = when();
        Response response = sender.get("/api/participant/" + 100);
        ValidatableResponse vResponse = response.then();
        vResponse.statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void getParticipantByAttributes() {

        // add to db
        Participant peter = participantRepository.save(new Participant("Peter", "Parker", "03.05.1995", "male"));

        // prepare http-request
        RequestSpecification request = given();
        request.param("firstName", peter.getFirstName());
        request.param("lastName", peter.getLastName());
        request.param("birthday", peter.getBirthday());

        // send http-request
        RequestSender sender = request.when();
        Response response = sender.get("/api/participant/search");

        // evaluate response
        ValidatableResponse vResponse = response.then();
        vResponse.statusCode(HttpStatus.SC_OK);
        List<ParticipantDto> maybePeters = vResponse.extract().body().jsonPath().getList(".", ParticipantDto.class);
        assertThat(maybePeters.size(), is(1));
        ParticipantDto maybePeter = maybePeters.get(0);
        assertThat(maybePeter.firstName, is(peter.getFirstName()));
        assertThat(maybePeter.lastName, is(peter.getLastName()));
        assertThat(maybePeter.birthday, is(peter.getBirthday()));
    }

    @Test
    public void participantByAttributesNotFound() {

        RequestSpecification request = given();
        request.param("firstName", "Tony");
        request.param("lastName","Stark");
        request.param("birthday", "29.05.1970");
        RequestSender sender = request.when();
        Response response = sender.get("/api/participant/search");
        ValidatableResponse vResponse = response.then();
        vResponse.statusCode(HttpStatus.SC_NOT_FOUND);
    }


}
