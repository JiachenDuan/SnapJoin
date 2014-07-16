package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Event;
import models.Participant;
import models.ScheduleOption;
import play.Logger;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import scala.util.parsing.json.JSONArray;

import static play.libs.Json.fromJson;
import static play.libs.Json.toJson;

import java.util.List;

/**
 * Created by jiachen on 7/6/14.
 */
public class EventController extends Controller {

    /**
     * -------ScheduleOptions----------
     */
    /**
     * Will create multiple schedule options at once,
     * will pass a jsonArray of scheduleOptions as default,
     * if this is only one scheduleoptin, just put one item inside the jsonArray
     * @return
     */
//    @BodyParser.Of(play.mvc.BodyParser.Json.class)
//    public static Result createScheduleOptions() {
//     JsonNode json = request().body().asJson();
////        List<ScheduleOption> options = json.find
////        List<JsonNode> options = json.("scheduleoptions");
//       // List<ScheduleOption> options = fromJson(json.findValues("scheduleoptions"), List<ScheduleOption>);
//     if(options ==null) return badRequest("Invalid Scheduleoptions Json Data");
//
//
//
//        return null;
//    }


    /**
     * -------Participant---------
     */

    /**
     * NOTES: front end JSON object has contains exactly same attibute name
     * as the method defined, e.g. for createparticipant method,
     * JSON object has to have "name" and "email" attribute
     * @return
     */
    @BodyParser.Of(play.mvc.BodyParser.Json.class)
    public static Result createParticipant() {
        JsonNode json = request().body().asJson();
        String participantName = json.findPath("name").textValue();
        String email = json.findPath("email").textValue();
        Participant participant = new Participant();
        participant.name = participantName;
        participant.email = email;

        String participantId = "";
        if (participantName == null || email == null) {
            return badRequest("Invalid Json Data");
        } else {
            //Check if the email already existed, if so update the new name
            //otherwise, just create the new one

            Participant checkParticipant = Participant.findByEmail(email);
            if (checkParticipant != null) {
                Participant.updateParticipantName(checkParticipant, participantName);
                participantId = checkParticipant.id;
            } else {
                participantId = Participant.create(participant).getSavedId();

            }
        }
        ObjectNode result = Json.newObject();
        //REST Cient will look for json node name "participantid"
        result.put("participantid", participantId);
        return ok(result);

    }

    @BodyParser.Of(play.mvc.BodyParser.Json.class)
    public static Result removeParticipantById() {
        JsonNode json = request().body().asJson();
        String participantid = json.findPath("participantid").textValue();
        if (participantid == null) {
            return badRequest("ERROR: User with user id: " + participantid + " does not exist");
        }
        try {

            Participant.removeById(participantid);
        } catch (Exception e) {
            return badRequest("ERROR: Invalid user id");
        }
        ObjectNode result = Json.newObject();
        //REST Cient will look for json node name "participantid"
        result.put("removedparticipantid", participantid);
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
        //REST Cient will look for json node name "participantid"
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
        //REST Cient will look for json node name "participantid"
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
