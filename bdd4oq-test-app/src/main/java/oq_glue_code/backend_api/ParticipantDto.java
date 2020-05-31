package oq_glue_code.backend_api;

public class ParticipantDto {
    public Long id;
    public String lastName;
    public String firstName;
    public String birthday;
    public String gender;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getBirthday() {
        return birthday;
    }




}
