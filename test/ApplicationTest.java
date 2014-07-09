import com.fasterxml.jackson.databind.JsonNode;
import models.Comment;
import models.Event;
import models.EventHost;
import org.junit.Test;
import play.Logger;
import play.libs.Json;
import play.mvc.Result;

import java.util.HashMap;
import java.util.Map;

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
                event.put("creatorid", "53bb83c444ae82a2e2a0549b");
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
     * Event Host tests
     */
    @Test
    public void testCreateEvenHost() {
        running(fakeApplication(), new Runnable() {
            @Override
            public void run() {
                Map eventhost = new HashMap();
                eventhost.put("hostname","JK");
                eventhost.put("email","keki@gmail.com");
                JsonNode node = Json.toJson(eventhost);
                Result result = routeAndCall(fakeRequest("POST","/eventhosts").withJsonBody(node));
                assertThat(status(result)).isEqualTo(OK);
                assertThat(contentType(result)).isEqualTo(CONTENT_TYPE);
                JsonNode node2 = Json.parse(contentAsString(result));
                String eventHostId = node2.get("eventhostid").asText();
                EventHost host = EventHost.findById(eventHostId);
                assertThat(host.hostname).isEqualTo("JK");
                assertThat(host.email).isEqualTo("keki@gmail.com");
            }
        });
    }

    @Test
    public void testCreatexistingEmail() {
        running(fakeApplication(), new Runnable() {
            @Override
            public void run() {
                Map eventhost = new HashMap();
                eventhost.put("hostname","change to JK from keki");
                eventhost.put("email","keki@gmail.com");
                JsonNode node = Json.toJson(eventhost);
                Result result = routeAndCall(fakeRequest("POST","/eventhosts").withJsonBody(node));
                System.out.println(result.toString());
                assertThat(status(result)).isEqualTo(OK);
                assertThat(contentType(result)).isEqualTo(CONTENT_TYPE);
                JsonNode node2 = Json.parse(contentAsString(result));
                String eventHostId = node2.get("eventhostid").asText();
                EventHost host = EventHost.findById(eventHostId);
                assertThat(host.hostname).isEqualTo("change to JK from keki");
                assertThat(host.email).isEqualTo("keki@gmail.com");
            }
        });
    }
    @Test
    public void testCreatEventHostWithNullEmail() {
        running(fakeApplication(), new Runnable() {
            @Override
            public void run() {
                Map eventhost = new HashMap();
                eventhost.put("hostname","change to JK from keki");
              //  eventhost.put("email","@gmail.com");
                JsonNode node = Json.toJson(eventhost);
                Result result = routeAndCall(fakeRequest("POST","/eventhosts").withJsonBody(node));
                System.out.println(result.toString());
                assertThat(status(result)).isEqualTo(BAD_REQUEST);
                assertThat(contentType(result)).isEqualTo("text/plain");
            }
        });
    }


    @Test
    public void testRemoveEventHostById() {
        running(fakeApplication(), new Runnable() {
            @Override
            public void run() {
                //Create dummyEventHost before test deletion
                Map eventhostDummy = new HashMap();
                eventhostDummy.put("hostname","change to JK from keki");
                eventhostDummy.put("email","keki@gmail.com");
                JsonNode nodeDumy = Json.toJson(eventhostDummy);
                Result resultDummy = routeAndCall(fakeRequest("POST","/eventhosts").withJsonBody(nodeDumy));

                //Test the remove function
                EventHost eventHost = EventHost.findByEmail("keki@gmail.com");
                Map eventhost = new HashMap();
                eventhost.put("evenhostid",eventHost.id);
                JsonNode node = Json.toJson(eventhost);
                Result result = routeAndCall(fakeRequest("POST","/eventhosts/remove").withJsonBody(node));
                System.out.println(result.toString());
                assertThat(status(result)).isEqualTo(OK);
                EventHost eventHost2 = EventHost.findByEmail("keki@gmail.com");
                //assertThat(contentType(result)).isEqualTo(CONTENT_TYPE);
                assertThat(eventHost2).isNull();
            }
        });
    }

    @Test
    public void testRemoveEventHostByInvalidId() {
        running(fakeApplication(), new Runnable() {
            @Override
            public void run() {
                //Create dummyEventHost before test deletion
                Map eventhostDummy = new HashMap();
                eventhostDummy.put("hostname","change to JK from keki");
                eventhostDummy.put("email","keki@gmail.com");
                JsonNode nodeDumy = Json.toJson(eventhostDummy);
                Result resultDummy = routeAndCall(fakeRequest("POST","/eventhosts").withJsonBody(nodeDumy));

                EventHost eventHost = EventHost.findByEmail("keki@gmail.com");
                Map eventhost = new HashMap();
                //    eventhost.put("evenhostid",eventHost.id);
                eventhost.put("evenhostid","eventHost.id");
                JsonNode node = Json.toJson(eventhost);
                Result result = routeAndCall(fakeRequest("POST","/removeeventhostbyid").withJsonBody(node));
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
