package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Event;
import models.EventHost;
import play.Logger;
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
     * ------Comment----------
     */


    /**
     * -------EVENT HOST---------
     */

    /**
     * NOTES: front end JSON object has contains exactly same attibute name
     * as the method defined, e.g. for createEventHost method,
     * JSON object has to have "hostname" and "email" attribute
     * @return
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
        if (evenhostid == null) {
            return badRequest("ERROR: User with user id: " + evenhostid + " does not exist");
        }
        try {

            EventHost.removeById(evenhostid);
        } catch (Exception e) {
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
    public static Result closeEvent() {
        JsonNode json = request().body().asJson();
        String eventid = json.findPath("eventid").textValue();
        if(eventid == null) return badRequest("Invalid eventId json");
        Event event = Event.findById(eventid);
        Logger.debug("start closing event %s",eventid);
        if(event == null) return badRequest("event $s not found",eventid);
        Event.closeEvent(event);
        ObjectNode result = Json.newObject();
        //REST Cient will look for json node name "eventhostid"
        result.put("eventid", eventid);
        Logger.debug("event %s is closed",eventid);
        return ok(toJson(result));
    }

    @BodyParser.Of(play.mvc.BodyParser.Json.class)
    public static Result createEvent() {
        JsonNode json = request().body().asJson();
        String title = json.findPath("title").textValue();
        String location = json.findPath("location").textValue();
        String detail = json.findPath("detail").textValue();
        String creatorId = json.findPath("creatorid").textValue() ;
        Event event = new Event();
        event.detail = detail;
        event.title = title;
        event.location = location;
        event.creatorId = creatorId;

        if (title == null || detail == null) {
            return badRequest("Invalid Json Data");
        }
        /**
         * Duplicate event name is fine
         */

        String createdEventId = Event.create(event).getSavedId();
        ObjectNode result = Json.newObject();
        //REST Cient will look for json node name "eventhostid"
        result.put("createEventId", createdEventId);
        return ok(toJson(result));
    }
    @BodyParser.Of(play.mvc.BodyParser.Json.class)
    public static Result eventsByCreator(String id) {

        List<Event> events = Event.eventsByCreatorId(id);
        if(events == null || events.isEmpty()){
            return badRequest("Did not find event for creator " + id);
        }

       return ok(toJson(events));
    }

}
