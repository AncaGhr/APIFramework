package Actions;

import Objects.RequestObject.RequestAccount;
import Objects.RequestObject.RequestAccountToken;
import Objects.ResponseObject.ResponseAccountAuthSuccess;
import Objects.ResponseObject.ResponseAccountFailed;
import Objects.ResponseObject.ResponseAccountSuccess;
import Objects.ResponseObject.ResponseTokenSuccess;
import Rest.RestRequestSatus;
import Service.ServiceImplementation.AccountServiceImpl;
import io.restassured.response.Response;
import org.testng.Assert;

public class AccountActions {

    public AccountServiceImpl accountService;

    public ResponseAccountSuccess createNewAccount(RequestAccount requestAccount){

        accountService= new AccountServiceImpl();

        Response response = accountService.createAccount(requestAccount);

        Assert.assertEquals((int)RestRequestSatus.SC_CREATED, response.getStatusCode());

        ResponseAccountSuccess responseAccountSuccess = response.body().as(ResponseAccountSuccess.class);
        Assert.assertNotNull(responseAccountSuccess.getUserID()); // verificam ca exista o valoare pt field
        Assert.assertEquals(responseAccountSuccess.getUsername(), requestAccount.getUserName()); // verificam ca username are valoarea din request
        Assert.assertNotNull(responseAccountSuccess.getBooks());

        return responseAccountSuccess;
    }

    public ResponseTokenSuccess generateToken(RequestAccountToken requestAccountToken){

        accountService = new AccountServiceImpl();
        Response response = accountService.generateToken(requestAccountToken);

        Assert.assertEquals(RestRequestSatus.SC_OK, response.getStatusCode());

        ResponseTokenSuccess responseTokenSuccess = response.body().as(ResponseTokenSuccess.class);

        Assert.assertNotNull(responseTokenSuccess.getToken());
        Assert.assertNotNull(responseTokenSuccess.getExpires());
        Assert.assertEquals(responseTokenSuccess.getStatus(),"Success");
        Assert.assertEquals(responseTokenSuccess.getResult(),"User authorized successfully.");

        return responseTokenSuccess;
    }

    public void obtainSpecificAccount(String userID, String token, String username){
        accountService = new AccountServiceImpl();
        Response response = accountService.getSpecificAccount(userID, token);

        if(response.getStatusCode() == RestRequestSatus.SC_OK) {

            Assert.assertEquals(response.getStatusCode(), RestRequestSatus.SC_OK);

            ResponseAccountAuthSuccess responseAccountAuthSuccess = response.body().as(ResponseAccountAuthSuccess.class);

            Assert.assertNotNull(responseAccountAuthSuccess.getUserId());
            Assert.assertEquals(responseAccountAuthSuccess.getUsername(), username);
            Assert.assertNotNull(responseAccountAuthSuccess.getBooks());
        }
        if (response.getStatusCode() == RestRequestSatus.SC_UNAUTHORIZED){

            Assert.assertEquals(response.getStatusCode(), RestRequestSatus.SC_UNAUTHORIZED);
            ResponseAccountFailed responseAccountFailed = response.body().as(ResponseAccountFailed.class);

            Assert.assertEquals(responseAccountFailed.getCode(), 1207);
            Assert.assertEquals(responseAccountFailed.getMessage(), "User not found!");
        }
    }

    public void deleteSpecificAccount(String userID, String token){
        accountService = new AccountServiceImpl();
        Response response = accountService.deleteSpecificUser(userID,token);

        Assert.assertEquals(response.getStatusCode(),RestRequestSatus.SC_NOCONTENT);
    }

}
