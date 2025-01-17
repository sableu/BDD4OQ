package com.github.sableu.bdd4oq_jba.controller;

import com.github.sableu.bdd4oq_jba.Bdd4oqJbApplication;
import com.github.sableu.bdd4oq_jba.domain.Participant;
import com.github.sableu.bdd4oq_jba.domain.WeightEntry;
import com.github.sableu.bdd4oq_jba.repository.ParticipantRepository;
import com.github.sableu.bdd4oq_jba.repository.WeightEntryRepository;
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

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


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

    @Autowired
    private WeightEntryRepository weightEntryRepository;

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
        Participant peter = participantRepository.save(new Participant("Peter", "Parker", "03.05.1995", "male", true));

        RequestSender sender = when();
        Response response = sender.get("/api/participant/" + peter.getId());
        ValidatableResponse vResponse = response.then();
        vResponse.statusCode(HttpStatus.SC_OK);
        ParticipantDto maybePeter = vResponse.extract().as(ParticipantDto.class);

        assertThat(maybePeter.firstName, is(peter.getFirstName()));
        assertThat(maybePeter.lastName, is(peter.getLastName()));
        assertThat(maybePeter.birthday, is(peter.getBirthday()));
        assertThat(maybePeter.gender, is(peter.getGender()));
        assertThat(maybePeter.consent, is(peter.getConsent()));
    }

    @Test
    public void deleteParticipant() {

        // add to db
        Participant peter = participantRepository.save(new Participant("Peter", "Parker", "03.05.1995", "male", false));

        RequestSender sender = when();
        Response response = sender.delete("/api/participant/" + peter.getId());
        ValidatableResponse vResponse = response.then();
        vResponse.statusCode(HttpStatus.SC_NO_CONTENT);

        assertThat(participantRepository.findById(peter.getId()).isPresent(), is(false));
    }

    @Test
    public void deleteParticipantNotfound() {

        RequestSender sender = when();
        Response response = sender.delete("/api/participant/1");
        ValidatableResponse vResponse = response.then();
        vResponse.statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void getParticipants() {

        // add to db
        Participant steve = participantRepository.save(new Participant("Steve", "Rodgers", "04.07.1920", "male", true));
        Participant clinton = participantRepository.save(new Participant("Clinton", "Barton", "01.06.1973", "male", false));
        Participant natasha = participantRepository.save(new Participant("Natasha", "Romanova", "18.07.1975", "female", true));

        RequestSender sender = when();
        Response response = sender.get("/api/participant");
        ValidatableResponse vResponse = response.then();
        vResponse.statusCode(HttpStatus.SC_OK);
        List<ParticipantDto> avengers = vResponse.extract().body().jsonPath().getList(".", ParticipantDto.class);

        assertThat(avengers.size(), greaterThanOrEqualTo(3));

        checkParticipant(getParicipantWithId(avengers, steve.getId()), steve);
        checkParticipant(getParicipantWithId(avengers, clinton.getId()), clinton);
        checkParticipant(getParicipantWithId(avengers, natasha.getId()), natasha);
    }

    private ParticipantDto getParicipantWithId(List<ParticipantDto> avengers, Long id) {
        return avengers.stream().filter(p -> p.id == id).findFirst().orElse(null);
    }

    private void checkParticipant(ParticipantDto maybeParticipant, Participant  participant) {
        assertThat(maybeParticipant, is(notNullValue()));
        assertThat(maybeParticipant.firstName, is(participant.getFirstName()));
        assertThat(maybeParticipant.lastName, is(participant.getLastName()));
        assertThat(maybeParticipant.birthday, is(participant.getBirthday()));
        assertThat(maybeParticipant.gender, is(participant.getGender()));
        assertThat(maybeParticipant.consent, is(participant.getConsent()));
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
        Participant peter = participantRepository.save(new Participant("Peter", "Parker", "03.05.1995", "male", false));

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
        request.param("lastName", "Stark");
        request.param("birthday", "29.05.1970");
        RequestSender sender = request.when();
        Response response = sender.get("/api/participant/search");
        ValidatableResponse vResponse = response.then();
        vResponse.statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void setBaselineWeightMeasurement() {
        Participant petra = participantRepository.save(new Participant("Petra", "Parker", "03.05.1995", "female", true));

        WeightEntryDto weightEntryDto = new WeightEntryDto();
        weightEntryDto.weight = 55.5;
        weightEntryDto.dateTime = "15.5.20, 4:30pm";
        weightEntryDto.comment = "a comment";

        RequestSpecification request = given();
        request.contentType(ContentType.JSON);
        request.body(weightEntryDto);
        RequestSender sender = request.when();
        Response response = sender.post("/api/participant/" + petra.getId() + "/weights/baseline");

        ValidatableResponse vResponse = response.then();
        vResponse.statusCode(HttpStatus.SC_CREATED);

        Long id = vResponse.extract().as(Long.class);
        WeightEntry baselineMeasurement = weightEntryRepository.findById(id).get();

        assertThat(baselineMeasurement.getWeight(), is(weightEntryDto.weight));
        assertThat(baselineMeasurement.getDateTime(), is(weightEntryDto.dateTime));
        assertThat(baselineMeasurement.getComment(), is(weightEntryDto.comment));
    }

    @Test
    public void setBaselineWeightMeasurementDeclined() {
        Participant piotr = participantRepository.save(new Participant("Piotr", "Parker", "03.05.1995", "male", true));

        WeightEntry firstEntry = weightEntryRepository.save(new WeightEntry(60d, "5.5.20, 4:30pm", "no comment", piotr.getId(), true));

        WeightEntryDto weightEntryDto = new WeightEntryDto();
        weightEntryDto.weight = 55.5;
        weightEntryDto.dateTime = "15.5.20, 4:30pm";
        weightEntryDto.comment = "a comment";

        RequestSpecification request = given();
        request.contentType(ContentType.JSON);
        request.body(weightEntryDto);
        RequestSender sender = request.when();
        Response response = sender.post("/api/participant/" + piotr.getId() + "/weights/baseline");

        ValidatableResponse vResponse = response.then();
        vResponse.statusCode(HttpStatus.SC_FORBIDDEN);
    }

    @Test
    public void getBaselineWeightMeasurementByParticipant() {
        Participant isabella = participantRepository.save(new Participant("Isabella", "Parker", "03.05.1995", "female", true));
        WeightEntry weightEntryIsabella = weightEntryRepository.save(new WeightEntry(54.8d, "5.6.20, 2:30pm", "no comment", isabella.getId(), true));

        // prepare http-request
        RequestSpecification request = given();
        request.param("weight", weightEntryIsabella.getWeight());
        request.param("dateTime", weightEntryIsabella.getDateTime());
        request.param("comment", weightEntryIsabella.getComment());

        // send http-request
        RequestSender sender = request.when();
        Response response = sender.get("/api/participant/" + isabella.getId() + "/weights/baseline");

        // evaluate response
        ValidatableResponse vResponse = response.then();
        vResponse.statusCode(HttpStatus.SC_OK);
        WeightEntryDto maybeIsabellaBaselineMeasurement = vResponse.extract().as(WeightEntryDto.class);
        assertThat(maybeIsabellaBaselineMeasurement.weight, is(weightEntryIsabella.getWeight()));
        assertThat(maybeIsabellaBaselineMeasurement.dateTime, is(weightEntryIsabella.getDateTime()));
        assertThat(maybeIsabellaBaselineMeasurement.comment, is(weightEntryIsabella.getComment()));
    }

    @Test
    public void updateParticipant() {

        // add to db
        Participant pietro = participantRepository.save(new Participant("Pietro", "Maximoff", "12.12.1989", "male", false));

        ParticipantDto participantDto = ParticipantDto.fromParticipant(pietro);
        participantDto.consent = true;


        RequestSpecification request = given();
        request.contentType(ContentType.JSON);
        request.body(participantDto);
        RequestSender sender = request.when();
        Response response = sender.put("/api/participant/"+ pietro.getId());

        ValidatableResponse vResponse = response.then();
        vResponse.statusCode(HttpStatus.SC_OK);

        Participant updatedPietro = participantRepository.findById(pietro.getId()).get();

        assertThat(updatedPietro.getLastName(), is(participantDto.lastName));
        assertThat(updatedPietro.getFirstName(), is(participantDto.firstName));
        assertThat(updatedPietro.getBirthday(), is(participantDto.birthday));
        assertThat(updatedPietro.getGender(), is(participantDto.gender));
        assertThat(updatedPietro.getConsent(), is(participantDto.consent));
    }


}
