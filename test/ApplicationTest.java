import com.fasterxml.jackson.databind.JsonNode;
import models.EventHost;
import org.junit.Test;
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
    @Test
    public void testCreateEvenHost() {
        running(fakeApplication(), new Runnable() {
            @Override
            public void run() {
                Map eventhost = new HashMap();
                eventhost.put("hostname","JK");
                eventhost.put("email","keki@gmail.com");
                JsonNode node = Json.toJson(eventhost);
                Result result = routeAndCall(fakeRequest("POST","/createeventhost").withJsonBody(node));
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
                Result result = routeAndCall(fakeRequest("POST","/createeventhost").withJsonBody(node));
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
                Result result = routeAndCall(fakeRequest("POST","/createeventhost").withJsonBody(node));
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
                Result resultDummy = routeAndCall(fakeRequest("POST","/createeventhost").withJsonBody(nodeDumy));

                //Test the remove function
                EventHost eventHost = EventHost.findByEmail("keki@gmail.com");
                Map eventhost = new HashMap();
                eventhost.put("evenhostid",eventHost.id);
                JsonNode node = Json.toJson(eventhost);
                Result result = routeAndCall(fakeRequest("POST","/removeeventhostbyid").withJsonBody(node));
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
                Result resultDummy = routeAndCall(fakeRequest("POST","/createeventhost").withJsonBody(nodeDumy));

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
