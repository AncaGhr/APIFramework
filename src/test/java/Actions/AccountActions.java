package Actions;

import Objects.RequestObject.RequestAccount;
import Objects.RequestObject.RequestAccountToken;
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

}
