package Objects.RequestObject;

import java.util.HashMap;

public class RequestAccount {

    private String userName;
    private String password;

    public RequestAccount(HashMap<String, String> testData) {
        populateObject(testData);
//        this.userName = userName;
//        this.password = password;
    }
    private void populateObject(HashMap<String, String> testData) {
        for (String Key : testData.keySet()) {
            switch (Key) {
                case "userName":
                    setUserName(testData.get(Key)+System.currentTimeMillis());
                    break;
                case "password":
                    setPassword(testData.get(Key));
                break;
            }
        }
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
