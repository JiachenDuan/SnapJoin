package models;

import net.vz.mongodb.jackson.Id;
import net.vz.mongodb.jackson.JacksonDBCollection;
import net.vz.mongodb.jackson.ObjectId;
import net.vz.mongodb.jackson.WriteResult;
import play.modules.mongodb.jackson.MongoDB;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by jiachen on 7/6/14.
 */
public class Event {

    private static JacksonDBCollection<Event, String> coll = MongoDB.getCollection("Event", Event.class, String.class);
    @Id
    @ObjectId
    public String id;
    public String title;
    public String location;
    public String detail;
    @ObjectId
    public ArrayList<String> scheduleOptions;
    @ObjectId
    public ArrayList<String> comments;
    @ObjectId
    public String creatorId; //Id of a eventHost object
    public Date creationTime;

    public Event() {
        this.creationTime = new Date();
    }

    public static WriteResult<Event, String> create(Event event) {
       return  Event.coll.save(event);
    }
}
