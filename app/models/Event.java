package models;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import net.vz.mongodb.jackson.*;
import net.vz.mongodb.jackson.ObjectId;
import org.bson.types.*;
import play.modules.mongodb.jackson.MongoDB;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Create Event in DB, and return EvenID first
 *  -> add ScheduleOption with EventID
 *  -> add Comment with EventID,
 * Created by jiachen on 7/6/14.
 */
public class Event {

    private static JacksonDBCollection<Event, String> coll = MongoDB.getCollection("event", Event.class, String.class);
    @Id
    @ObjectId
    public String id;
    public String title;
    public String location;
    public String detail;
    public boolean status;
    @ObjectId
    public String creatorId; //Id of a eventHost object
    public Date creationTime;

    public Event() {
        this.creationTime = new Date();
        this.status = true;
    }

    public static WriteResult<Event, String> create(Event event) {
       return  Event.coll.save(event);
    }

    /**
     * Get all the events by creatorId
     * @param id
     * @return
     */
    public static List<Event> eventsByCreatorId(String id){
       try{
           /**
            * !!!How to query a ObjectId type field
            */
           DBObject dbObject = new BasicDBObject("creatorId",new org.bson.types.ObjectId(id));
           List<Event> events = coll.find(dbObject).toArray();

           if(events == null || events.size() == 0){
               return null;
           }else{
               return events;
           }

       }catch(Exception ex){
           return null;
       }
    }
    /**
     *
     * @param id
     * @return event and if event not found, it will return null
     */
    public static Event findById(String id){
        return Event.coll.findOneById(id);
    }
    public static String updateTitle(Event event,String title){
        if(event == null) return null;
        WriteResult<Event,String> updatedEvent = Event.coll.updateById(event.id, DBUpdate.set("title",title));
        return event.id;
    }
    public static String updateDetail(Event event,String detail){
        if(event == null) return null;
        WriteResult<Event,String> updatedEvent = Event.coll.updateById(event.id, DBUpdate.set("detail",detail));
        return event.id;
    }
    public static String updateLocation(Event event,String location){
        if(event == null) return null;
        WriteResult<Event,String> updatedEvent = Event.coll.updateById(event.id, DBUpdate.set("location",location));
        return event.id;
    }
    public static String closeEvent(Event event){
        if(event == null) return null;
        WriteResult<Event,String> closedEvent = Event.coll.updateById(event.id,DBUpdate.set("status",false));
        return event.id;
    }
}
