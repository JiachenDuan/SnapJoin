package models;

import net.vz.mongodb.jackson.*;
import play.data.validation.Constraints;
import play.modules.mongodb.jackson.MongoDB;

import java.util.Date;
import java.util.List;

/**
 * Store the even host information,
 * Created by jiachen on 7/6/14.
 */
public class Participant {
    private static JacksonDBCollection<Participant, String> coll = MongoDB.getCollection("participants", Participant.class, String.class);
    @Id
    @ObjectId
    public String id;
    public String name;
    @Constraints.Email
    @Constraints.Required
    public String email;
    public Date creationDate;

    public Participant(){
        this.creationDate = new Date();
    }

    public static WriteResult<Participant, String> create(Participant participant) {
        return  Participant.coll.save(participant);
    }

    public static Participant findByEmail(String email){
        if(email == null){
            return null;
        }
        try {
            List<Participant> participants = coll.find().in("email", email).toArray();
            if (participants == null || participants.size() == 0) {
                return null;
            } else {
                return participants.get(0);
            }
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static Participant updateParticipantName(Participant participant,String participantName){
        WriteResult<Participant,String> updateparticipant = coll.updateById(participant.id, DBUpdate.set("name",participantName));
        Participant newparticipant = findById(participant.id);
        return newparticipant;
    }

    public static Participant findById(String id) {
        Participant participant = Participant.coll.findOneById(id);
        return participant;
    }

    public static Participant removeById(String id){
        /**
         * There is no Object return inside WriteResult for removeById
         */
        WriteResult<Participant,String> deletedParticipant = Participant.coll.removeById(id);
        return null;
    }
}
