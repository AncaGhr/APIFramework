package Service;

import Rest.RestRequest;
import Rest.RestRequestType;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class CommonAPIService {

    //Aceasta clasa contine metode pentru tipuri de request-uri pentru diferiti parametri (polimorifsm)

    public Response post(Object body, String URL){

        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.body(body);

        APIServiceHelper.requestLogs(requestSpecification, URL, RestRequestType.REQUEST_POST);

        Response response = performRequest(RestRequestType.REQUEST_POST, requestSpecification, URL);
        return  response;
    }


    //Facem o instanta de RestRequest care sa peleze metoda de perfom Request


    public Response get(String URL, String token){

        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.header("Authorization","Bearer "+ token);

        Response response = performRequest(RestRequestType.REQUEST_GET, requestSpecification, URL);

        return  response;

        //Trebuie sa implementam log-urile pentru request si response
    }

    private Response performRequest(String requestType, RequestSpecification requestSpecification,
                                    String URL){

        return new RestRequest().performRequest(requestType, requestSpecification, URL);
    }

}
