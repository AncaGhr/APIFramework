package Tests;

import Actions.AccountActions;
import Hooks.Hooks;
import Objects.RequestObject.RequestAccount;
import Objects.RequestObject.RequestAccountToken;
import Objects.ResponseObject.ResponseAccountAuthSuccess;
import Objects.ResponseObject.ResponseAccountSuccess;
import Objects.ResponseObject.ResponseTokenSuccess;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CreateUserTestV2Test extends Hooks {

    public String userID;
    public String username;
    public String password;
    public String token;
    public AccountActions accountActions;


    @Test
    public void testMethod() {

        System.out.println("Step 1 : Create User");
        createUser();
        System.out.println("Step 2 : Generate Token");
        generateToken();
//        System.out.println("Step 3 : Obtain new user");
//        interractNewUser();
    }

    public void createUser() {

        accountActions = new AccountActions();

        username = "AncaAnca" + System.currentTimeMillis();
        password = "ghemaR1966!";

        RequestAccount requestAccount = new RequestAccount(username, password);
        ResponseAccountSuccess responseAccountSuccess = accountActions.createNewAccount(requestAccount);

        userID = responseAccountSuccess.getUserID();
    }


    //Facem un request care ne genereaza un token - Autentificare si Autorizare

    public void generateToken(){

        accountActions = new AccountActions();

        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.baseUri("https://demoqa.com");
        requestSpecification.contentType("application/json");

        RequestAccountToken requestAccountToken = new RequestAccountToken(username,password);
        ResponseTokenSuccess responseTokenSuccess = accountActions.generateToken(requestAccountToken);

        token = responseTokenSuccess.getToken();
    }


    //Facem un get pentru userul creat

    public void interractNewUser(){

        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.baseUri("https://demoqa.com");
        requestSpecification.contentType("application/json");
        requestSpecification.header("Authorization","Bearer "+ token); // autorizarea care foloseste token

        Response response = requestSpecification.get("/Account/v1/User/"+ userID); // compunere de endpoint din url+userID
        Assert.assertEquals(response.getStatusCode(), 200);

        ResponseAccountAuthSuccess responseAccountAuthSuccess = response.body().as(ResponseAccountAuthSuccess.class);

        Assert.assertNotNull(responseAccountAuthSuccess.getUserId()); // verificam ca exista o valoare pt field
        Assert.assertEquals(responseAccountAuthSuccess.getUsername(), username); // verificam ca username are valoarea din request
        Assert.assertNotNull(responseAccountAuthSuccess.getBooks());
    }

}
