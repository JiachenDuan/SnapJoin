package models;

import akka.io.Tcp;
import net.vz.mongodb.jackson.*;
import play.data.validation.Constraints;
import play.modules.mongodb.jackson.MongoDB;

import java.util.Date;
import java.util.List;

/**
 * Store the even host information,
 * Created by jiachen on 7/6/14.
 */
public class EventHost {
    private static JacksonDBCollection<EventHost, String> coll = MongoDB.getCollection("eventhosts", EventHost.class, String.class);
    @Id
    @ObjectId
    public String id;
    public String hostname;
    @Constraints.Email
    @Constraints.Required
    public String email;
    public Date creationDate;

    public EventHost(){
        this.creationDate = new Date();
    }

    public static WriteResult<EventHost, String> create(EventHost eventhost) {
        return  EventHost.coll.save(eventhost);
    }

    public static EventHost findByEmail(String email){
        if(email == null){
            return null;
        }
        try {
            List<EventHost> eventHosts = coll.find().in("email", email).toArray();
            if (eventHosts == null || eventHosts.size() == 0) {
                return null;
            } else {
                return eventHosts.get(0);
            }
        }catch(Exception e){
            return null;
        }
    }

    public static EventHost updateHostName(EventHost eventhost,String hostname){
        WriteResult<EventHost,String> updateEvenHost = coll.updateById(eventhost.id, DBUpdate.set("hostname",hostname));
        EventHost newevenhost = findById(eventhost.id);
        return newevenhost;
    }

    public static EventHost findById(String id) {
        EventHost eventHost = EventHost.coll.findOneById(id);
        return eventHost;
    }

    public static EventHost removeById(String id){
        /**
         * There is no Object return inside WriteResult for removeById
         */
        WriteResult<EventHost,String> deletedHost = EventHost.coll.removeById(id);
        return null;
    }
}
