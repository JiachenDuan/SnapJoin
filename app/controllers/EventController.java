package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.EventHost;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import scala.util.parsing.json.JSONObject;
import static play.libs.Json.toJson;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiachen on 7/6/14.
 */
public class EventController extends Controller {

    /**
     * -------EVENT HOST---------
     */
    @BodyParser.Of(play.mvc.BodyParser.Json.class)
    public static Result createEventHost() {
        JsonNode json = request().body().asJson();
        String hostname = json.findPath("hostname").textValue();
        String email = json.findPath("email").textValue();
        EventHost evenHost = new EventHost();
        evenHost.hostname = hostname;
        evenHost.email = email;

        String evenHostId = "";
        if (hostname == null || email == null) {
            return badRequest("Invalid Json Data");
        } else {
            //Check if the email already existed, if so update the new hostname
            //otherwise, just create the new one

            EventHost checkEventHost = EventHost.findByEmail(email);
            if (checkEventHost != null) {
                EventHost.updateHostName(checkEventHost, hostname);
                evenHostId = checkEventHost.id;
            } else {
                evenHostId = EventHost.create(evenHost).getSavedId();

            }
        }
        ObjectNode result = Json.newObject();
        //REST Cient will look for json node name "eventhostid"
        result.put("eventhostid", evenHostId);
        return ok(result);

    }

    @BodyParser.Of(play.mvc.BodyParser.Json.class)
    public static Result removeEventHostById() {
        JsonNode json = request().body().asJson();
        String evenhostid = json.findPath("evenhostid").textValue();
        if(evenhostid == null){
            return badRequest("ERROR: User with user id: " + evenhostid + " does not exist");
        }
        try{

            EventHost.removeById(evenhostid);
        }catch(Exception e){
            return badRequest("ERROR: Invalid user id");
        }
        ObjectNode result = Json.newObject();
        //REST Cient will look for json node name "eventhostid"
        result.put("removedeventhostid", evenhostid);
        return ok(result);
    }
    /**
     * -------- EVENT----------
     */
    @BodyParser.Of(play.mvc.BodyParser.Json.class)
    public static Result createEvent() {
        JsonNode json = request().body().asJson();
        String title = json.findPath("title").textValue();
        String location = json.findPath("location").textValue();
        String detail = json.findPath("detail").textValue();

        List<String> scheduleOptionIds = json.findValuesAsText("scheduleOptions");
        List<String> commentIds = json.findValuesAsText("comments");

        if (title == null || detail == null || scheduleOptionIds == null || commentIds == null) {
            return badRequest("Invalid Json Data");
        }
        /**
         * Duplicate event name is fine
         */
        // TODO: not done yet, will come back after finish eventHost class
//       String evenId = Event.create()
        return null;
    }


}
