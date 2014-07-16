import com.fasterxml.jackson.databind.JsonNode;
import models.Event;
import models.Participant;
import models.ScheduleOption;
import org.junit.Test;
import play.Logger;
import play.libs.Json;
import play.mvc.Result;

import java.util.*;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.*;


/**
*
* Simple (JUnit) tests that can call all parts of a play app.
* If you are interested in mocking a whole application, see the wiki for more details.
*
*/
public class ApplicationTest {

private static final String CONTENT_TYPE = "application/json";

    /**
     * ScheduleOption
     */

    public void testCreatScheduleOption(){
        running(fakeApplication(), new Runnable() {
            @Override
            public void run() {

                String eventId = "53c447d044aefb07bed0d394";
                ScheduleOption scheduleOption1 = new ScheduleOption(eventId);
                String t1 = "Finishing in Half moon bay";
                scheduleOption1.title = t1;
                String d1 = "We will car pool to Half moon bay in the morning";
                scheduleOption1.detail = d1;
                scheduleOption1.scheduleTime = new Date();
                ScheduleOption scheduleOption2 = new ScheduleOption(eventId);
                String t2 = "Hiking in SF";
                scheduleOption2.title = t2;
                String d2 = "We will start from Our church, and go to SF golden Gate park";
                scheduleOption2.detail= d2;
                scheduleOption2.scheduleTime = new Date();
                List<ScheduleOption> options  = new ArrayList<ScheduleOption>();
                options.add(scheduleOption1);
                options.add(scheduleOption2);

                ScheduleOption.createScheduleOptions(options);
                List<ScheduleOption> s1 = ScheduleOption.findByTitle(t1);
                // List<ScheduleOption> s2 = ScheduleOption.findByTitle(t2);

                assertThat(s1).isNotNull();
                assertThat(s1).isNotEmpty();
                assertThat(s1.get(0).detail).isEqualTo(d1);
//                 assertThat(s2).isNotNull();
//                 assertThat(s2).isNotEmpty();
//                 assertThat(s2.get(0).detail).isEqualTo(d2);


                 Map optionMap = new HashMap();
                 optionMap.put("scheduleoptions",options);
//                 JsonNode node = Json.toJson(optionMap);
//                 Result result = routeAndCall(fakeRequest("POST","/scheduleoptions"))
//
//                 event.put("title", "BBQ");
//                 event.put("location", "Central Park Santa Clara");
//                 event.put("detail", "Everyone should bring something");
//                 // event.put("creatorid", "53bb83c444ae82a2e2a0549b");
//                 String creatorId = Participant.findByEmail("keki@gmail.com").id;
//                 event.put("creatorid", creatorId);
//                 JsonNode node = Json.toJson(event);
//                 Result result = routeAndCall(fakeRequest("POST","/events").withJsonBody(node));
//                 assertThat(status(result)).isEqualTo(OK);
//                 assertThat(contentType(result)).isEqualTo(CONTENT_TYPE);
//                 JsonNode node2 = Json.parse(contentAsString(result));
//                 String eventHostId = node2.get("createEventId").asText();
//                 Event event2 = Event.findById(eventHostId);
//                 assertThat(event2.title).isEqualTo("BBQ");
//                 assertThat(event2.location).isEqualTo("Central Park Santa Clara");
//                 assertThat(event2.detail).isEqualTo("Everyone should bring something");
//                 assertThat(event2.creatorId).isEqualTo("53bb83c444ae82a2e2a0549b");

//
//                 Result result = route(fakeRequest("GET", "/events/creator/53bb83c444ae82a2e2a0549b"));
//                 assertThat(status(result)).isEqualTo(OK);
//                 assertThat(contentType(result)).isEqualTo(CONTENT_TYPE);
//
//                 JsonNode node2 = Json.parse(contentAsString(result));

            }
        });
    }


     @Test
    public void testCreatScheduleOptionService() {
         running(fakeApplication(), new Runnable() {
             @Override
             public void run() {

                 String eventId = "53c447d044aefb07bed0d394";
                 ScheduleOption scheduleOption1 = new ScheduleOption(eventId);
                 String t1 = "Finishing in Half moon bay";
                 scheduleOption1.title = t1;
                 String d1 = "We will car pool to Half moon bay in the morning";
                 scheduleOption1.detail = d1;
                 scheduleOption1.scheduleTime = new Date();
                 ScheduleOption scheduleOption2 = new ScheduleOption(eventId);
                 String t2 = "Hiking in SF";
                 scheduleOption2.title = t2;
                 String d2 = "We will start from Our church, and go to SF golden Gate park";
                 scheduleOption2.detail= d2;
                 scheduleOption2.scheduleTime = new Date();
                List<ScheduleOption> options  = new ArrayList<ScheduleOption>();
                 options.add(scheduleOption1);
                 options.add(scheduleOption2);

                 ScheduleOption.createScheduleOptions(options);
                 List<ScheduleOption> s1 = ScheduleOption.findByTitle(t1);
                // List<ScheduleOption> s2 = ScheduleOption.findByTitle(t2);

                 assertThat(s1).isNotNull();
                 assertThat(s1).isNotEmpty();
                 assertThat(s1.get(0).detail).isEqualTo(d1);
//                 assertThat(s2).isNotNull();
//                 assertThat(s2).isNotEmpty();
//                 assertThat(s2.get(0).detail).isEqualTo(d2);


//                 Map optionMap = new HashMap();
//                 optionMap.put("scheduleoptions",options);
//                 JsonNode node = Json.toJson(optionMap);
//                 Result result = routeAndCall(fakeRequest("POST","/scheduleoptions"))
//
//                 event.put("title", "BBQ");
//                 event.put("location", "Central Park Santa Clara");
//                 event.put("detail", "Everyone should bring something");
//                 // event.put("creatorid", "53bb83c444ae82a2e2a0549b");
//                 String creatorId = Participant.findByEmail("keki@gmail.com").id;
//                 event.put("creatorid", creatorId);
//                 JsonNode node = Json.toJson(event);
//                 Result result = routeAndCall(fakeRequest("POST","/events").withJsonBody(node));
//                 assertThat(status(result)).isEqualTo(OK);
//                 assertThat(contentType(result)).isEqualTo(CONTENT_TYPE);
//                 JsonNode node2 = Json.parse(contentAsString(result));
//                 String eventHostId = node2.get("createEventId").asText();
//                 Event event2 = Event.findById(eventHostId);
//                 assertThat(event2.title).isEqualTo("BBQ");
//                 assertThat(event2.location).isEqualTo("Central Park Santa Clara");
//                 assertThat(event2.detail).isEqualTo("Everyone should bring something");
//                 assertThat(event2.creatorId).isEqualTo("53bb83c444ae82a2e2a0549b");

//
//                 Result result = route(fakeRequest("GET", "/events/creator/53bb83c444ae82a2e2a0549b"));
//                 assertThat(status(result)).isEqualTo(OK);
//                 assertThat(contentType(result)).isEqualTo(CONTENT_TYPE);
//
//                 JsonNode node2 = Json.parse(contentAsString(result));

             }
         });
     }

    /**
     * Event Tests
     */

    @Test
    public void testEventsByCreator(){
        running(fakeApplication(),new Runnable(){
            @Override
            public void run() {
                Result result = route(fakeRequest("GET", "/events/creator/53bb83c444ae82a2e2a0549b"));
                assertThat(status(result)).isEqualTo(OK);
                assertThat(contentType(result)).isEqualTo(CONTENT_TYPE);

                JsonNode node2 = Json.parse(contentAsString(result));

            }
        });
    }

    @Test
    public void testCloseEvent(){
        running(fakeApplication(),new Runnable(){

            @Override
            public void run() {
                //create a test event
                Map event = new HashMap();
                event.put("title", "TestCloseEvent");
                event.put("location", "Starbucks Santa Clara");
                event.put("detail", "The status of this event should be flase");
                event.put("creatorid", "53bb83c444ae82a2e2a0549b");
                JsonNode node = Json.toJson(event);
                Result result = routeAndCall(fakeRequest("POST","/events").withJsonBody(node));

                assertThat(status(result)).isEqualTo(OK);
                assertThat(contentType(result)).isEqualTo(CONTENT_TYPE);

                JsonNode node2 = Json.parse(contentAsString(result));
                String eventId = node2.get("createEventId").asText();
                Event event2 = Event.findById(eventId);
                // close the event
                Map closemap = new HashMap();
                closemap.put("eventid",event2.id);
                JsonNode nodeclose = Json.toJson(closemap);
                Result resultclose = routeAndCall(fakeRequest("POST","/events/close").withJsonBody(nodeclose));
                JsonNode closeResult = Json.parse(contentAsString(resultclose));
                String closedeventId = closeResult.get("eventid").asText();
                Event closedEvent = Event.findById(closedeventId);
                assertThat(closedEvent.status).isEqualTo(false);

            }
        });
    }

    @Test
    public void testCreateEvent(){
        running(fakeApplication(),new Runnable(){

            @Override
            public void run() {
                Map event = new HashMap();
                event.put("title", "BBQ");
                event.put("location", "Central Park Santa Clara");
                event.put("detail", "Everyone should bring something");
                // event.put("creatorid", "53bb83c444ae82a2e2a0549b");
                String creatorId = Participant.findByEmail("keki@gmail.com").id;
                event.put("creatorid", creatorId);
                JsonNode node = Json.toJson(event);
                Result result = routeAndCall(fakeRequest("POST","/events").withJsonBody(node));
                assertThat(status(result)).isEqualTo(OK);
                assertThat(contentType(result)).isEqualTo(CONTENT_TYPE);
                JsonNode node2 = Json.parse(contentAsString(result));
                String eventHostId = node2.get("createEventId").asText();
                Event event2 = Event.findById(eventHostId);
                assertThat(event2.title).isEqualTo("BBQ");
                assertThat(event2.location).isEqualTo("Central Park Santa Clara");
                assertThat(event2.detail).isEqualTo("Everyone should bring something");
                assertThat(event2.creatorId).isEqualTo("53bb83c444ae82a2e2a0549b");
            }
        });
    }

    @Test
    public void testAddParticipantsNameToEvent(){
        running(fakeApplication(),new Runnable(){

            @Override
            public void run() {
                //
                Logger.debug("Testing testAddParticipantsNameToEvent");
                String eventId = "53c447d044aefb07bed0d394";
                Event event3 = Event.findById(eventId);
                event3.addParticipantNames(event3, Arrays.asList("JK","Yishu"));
                Event updatedEvent = Event.findById(eventId);
                assertThat(updatedEvent.participantsName).hasSize(2);
                assertThat(updatedEvent.participantsName).contains("JK");
                assertThat(updatedEvent.participantsName).contains("Yishu");

            }
        });
    }

    /**
     * Test add already existed participants name to event,
     * it's not supposed to update the event.
     */
    @Test
    public void testAddExistingParticipantsNameToEvent(){
        running(fakeApplication(),new Runnable(){

            @Override
            public void run() {
                //
                Logger.debug("Testing testAddParticipantsNameToEvent");
                String eventId = "53c447d044aefb07bed0d394";
                Event event3 = Event.findById(eventId);
                event3.addParticipantNames(event3, Arrays.asList("JK","Yishu"));
               //add it twice
                event3.addParticipantNames(event3, Arrays.asList("JK","Yishu"));
                Event updatedEvent = Event.findById(eventId);

                //the result should be still hasSize(2)
                assertThat(updatedEvent.participantsName).hasSize(2);
                assertThat(updatedEvent.participantsName).contains("JK");
                assertThat(updatedEvent.participantsName).contains("Yishu");

            }
        });
    }
    /**
     * Comment Tests
     */

    @Test
    public void testCreateComment(){
      running(fakeApplication(),new Runnable(){
          @Override
          public void run() {

          }
      });
    }


    /**
     * Participants
     */
    @Test
    public void testCreateParticipant() {
        running(fakeApplication(), new Runnable() {
            @Override
            public void run() {
                Map participant = new HashMap();
                participant.put("name","Yishu");
                participant.put("email","yishu@gmail.com");
                JsonNode node = Json.toJson(participant);
                Result result = routeAndCall(fakeRequest("POST","/participants").withJsonBody(node));
                assertThat(status(result)).isEqualTo(OK);
                assertThat(contentType(result)).isEqualTo(CONTENT_TYPE);
                JsonNode node2 = Json.parse(contentAsString(result));
                String eventHostId = node2.get("participantid").asText();
                Participant host = Participant.findById(eventHostId);
                assertThat(host.name).isEqualTo("Yishu");
                assertThat(host.email).isEqualTo("yishu@gmail.com");
            }
        });
    }

    @Test
    public void testCreatParticipantWithExistingEmail() {
        running(fakeApplication(), new Runnable() {
            @Override
            public void run() {
                Map participant = new HashMap();
                participant.put("name","change to JK from keki");
                participant.put("email","keki@gmail.com");
                JsonNode node = Json.toJson(participant);
                Result result = routeAndCall(fakeRequest("POST","/participants").withJsonBody(node));
                System.out.println(result.toString());
                assertThat(status(result)).isEqualTo(OK);
                assertThat(contentType(result)).isEqualTo(CONTENT_TYPE);
                JsonNode node2 = Json.parse(contentAsString(result));
                String eventHostId = node2.get("participantid").asText();
                Participant host = Participant.findById(eventHostId);
                assertThat(host.name).isEqualTo("change to JK from keki");
                assertThat(host.email).isEqualTo("keki@gmail.com");
            }
        });
    }
    @Test
    public void testCreatParticipantWithNullEmail() {
        running(fakeApplication(), new Runnable() {
            @Override
            public void run() {
                Map participant = new HashMap();
                participant.put("name","change to JK from keki");
              //  participant.put("email","@gmail.com");
                JsonNode node = Json.toJson(participant);
                Result result = routeAndCall(fakeRequest("POST","/participants").withJsonBody(node));
                System.out.println(result.toString());
                assertThat(status(result)).isEqualTo(BAD_REQUEST);
                assertThat(contentType(result)).isEqualTo("text/plain");
            }
        });
    }


    @Test
    public void testRemoveParticipantById() {
        running(fakeApplication(), new Runnable() {
            @Override
            public void run() {
             //   Create dummyParticipant before test deletion
                Map participantDummy = new HashMap();
                participantDummy.put("name","change to JK from keki");
                participantDummy.put("email","keki23@gmail.com");
                JsonNode nodeDumy = Json.toJson(participantDummy);
                Result resultDummy = routeAndCall(fakeRequest("POST","/participants").withJsonBody(nodeDumy));

                //Test the remove function
                Participant participant = Participant.findByEmail("keki23@gmail.com");
                Map participantMap = new HashMap();
                participantMap.put("participantid", participant.id);
                JsonNode node = Json.toJson(participantMap);
                Result result = routeAndCall(fakeRequest("POST","/participants/remove").withJsonBody(node));
                System.out.println(result.toString());
                assertThat(status(result)).isEqualTo(OK);
                Participant participant2 = Participant.findByEmail("keki23@gmail.com");
                //assertThat(contentType(result)).isEqualTo(CONTENT_TYPE);
                assertThat(participant2).isNull();
            }
        });
    }

    @Test
    public void testRemoveParticipantByInvalidId() {
        running(fakeApplication(), new Runnable() {
            @Override
            public void run() {
                //Create dummyParticipant before test deletion
                Map participantDummy = new HashMap();
                participantDummy.put("name","change to JK from keki");
                participantDummy.put("email","keki@gmail.com");
                JsonNode nodeDumy = Json.toJson(participantDummy);
                Result resultDummy = routeAndCall(fakeRequest("POST","/participants").withJsonBody(nodeDumy));

                Participant participant = Participant.findByEmail("keki@gmail.com");
                Map participantMap = new HashMap();
                //    participant.put("participantid",participant.id);
                participantMap.put("participantid","participant.id");
                JsonNode node = Json.toJson(participantMap);
                Result result = routeAndCall(fakeRequest("POST","/participants/remove").withJsonBody(node));
                System.out.println(result.toString());
                assertThat(status(result)).isEqualTo(BAD_REQUEST);

            }
        });
    }
    public void badRoute() {
        running(fakeApplication(), new Runnable() {
            @Override
            public void run() {
                Result result = route(fakeRequest("GET", "/xx/Kiki"));
                assertThat(result).isNull();
            }
        });
}


public void testFindAllUsers() {
        running(fakeApplication(), new Runnable() {
@Override
public void run() {
        Result result = route(fakeRequest("GET", "/users/all"));

        }
        });
        }
        }
