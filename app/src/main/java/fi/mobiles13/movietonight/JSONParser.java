package fi.mobiles13.movietonight;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONParser {

    //When the user signup
    public JSONObject makeRegisterJson(String username, String password, int age, String email) throws JSONException {

        JSONObject object = new JSONObject();

        object.put("username", username);
        object.put("password", password);
        object.put("age", age);
        object.put("email", email);
        // if its in array------
       /*JSONObject finalObject=new JSONObject();
       finalObject.put("request",object);
       return finalObject;*/
        return object;
    }

    //When the user login
    public JSONObject makeLoginJson(String username, String password) throws JSONException {

        JSONObject object = new JSONObject();

        object.put("userName", username);
        object.put("password", password);
        /*JSONObject finalObject=new JSONObject();
        finalObject.put("request",object);
        return finalObject;*/
        return object;
    }
}
