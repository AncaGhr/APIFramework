package Objects.ResponseObject;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ResponseAccountSuccess {

    @JsonProperty("userID") //definim fiecare camp din consola pentru a sti cum sa il citeasca
    private String userID;
    @JsonProperty("username")
    private String username;

    //invatam conceptul Compozitie (verisor cu mostenirea) - compozitia construieste obiecte
    @JsonProperty("books")
    private List<BookObject> books;

    public String getUserID() {
        return userID;
    }

    public String getUsername() {
        return username;
    }

    public List<BookObject> getBooks() {
        return books;
    }
}
