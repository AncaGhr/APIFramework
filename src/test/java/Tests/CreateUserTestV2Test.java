package Tests;

import Actions.AccountActions;
import Hooks.Hooks;
import Objects.RequestObject.RequestAccount;
import Objects.RequestObject.RequestAccountToken;
import Objects.ResponseObject.ResponseAccountAuthSuccess;
import Objects.ResponseObject.ResponseAccountSuccess;
import Objects.ResponseObject.ResponseTokenSuccess;
import PropertyUtility.PropertyUtility;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Properties;

public class CreateUserTestV2Test extends Hooks {

    public String userID;
    public String username;
    public String password;
    public String token;
    public AccountActions accountActions;
    public RequestAccount requestAccount;


    @Test
    public void testMethod() {

        System.out.println("Step 1 : Create User");
        createUser();
        System.out.println(" ");

        System.out.println("Step 2 : Generate Token");
        generateToken();
        System.out.println(" ");

        System.out.println("Step 3 : Obtain new user");
        interractNewUser();
        System.out.println(" ");

        System.out.println("Step 4 : Delete new user");
        deleteSpecificUser();
        System.out.println(" ");

        System.out.println("Step 5 : Obtain new user");
        interractNewUser();
        System.out.println(" ");
    }

    public void createUser() {

        accountActions = new AccountActions();
        PropertyUtility propertyUtility = new PropertyUtility("CreateUser");

//        username = "AncaAnca" + System.currentTimeMillis();
//        password = "ghemaR1966!";

        requestAccount = new RequestAccount(propertyUtility.getAllData());
        ResponseAccountSuccess responseAccountSuccess = accountActions.createNewAccount(requestAccount);

        userID = responseAccountSuccess.getUserID();
    }


    //Facem un request care ne genereaza un token - Autentificare si Autorizare

    public void generateToken() {

        accountActions = new AccountActions();
        RequestAccountToken requestAccountToken = new RequestAccountToken(requestAccount.getUserName(), requestAccount.getPassword());
        ResponseTokenSuccess responseTokenSuccess = accountActions.generateToken(requestAccountToken);

        token = responseTokenSuccess.getToken();
    }


    //Facem un get pentru userul creat

    public void interractNewUser() {

        accountActions = new AccountActions();
        accountActions.obtainSpecificAccount(userID, token, requestAccount.getUserName());
    }

    //Stergem noul user creat

    public void deleteSpecificUser(){

        accountActions = new AccountActions();
        accountActions.deleteSpecificAccount(userID, token);
    }
}
