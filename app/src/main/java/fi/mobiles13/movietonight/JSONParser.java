package fi.mobiles13.movietonight;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONParser {

    //When the user signup
    public JSONObject makeRegisterJson(String name, String email, String password, long phone) throws JSONException {

        JSONObject object = new JSONObject();

        object.put("name", name);
        object.put("email", email);
        object.put("password", password);
        object.put("phone", phone);
        // if its in array------
       /*JSONObject finalObject=new JSONObject();
       finalObject.put("request",object);
       return finalObject;*/
        return object;
    }

    //When the user login
    public JSONObject makeLoginJson(String Name, String password) throws JSONException {

        JSONObject object = new JSONObject();

        object.put("userName", Name);
        object.put("password", password);
        /*JSONObject finalObject=new JSONObject();
        finalObject.put("request",object);
        return finalObject;*/
        return object;
    }
}
