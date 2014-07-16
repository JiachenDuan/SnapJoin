package models;

import net.vz.mongodb.jackson.*;
import play.Logger;
import play.modules.mongodb.jackson.MongoDB;

import java.util.*;

/**
 * Created by jiachen on 7/6/14.
 * This class is the modle of schedule options that host will add to
 * the schedule poll of the event
 */
public class ScheduleOption {
    private static JacksonDBCollection<ScheduleOption, String> coll = MongoDB.getCollection("scheduleOption", ScheduleOption.class, String.class);
    @Id
    @ObjectId
    public String id;
    //TODO: will display title and details on seperate lines on the page, not on the table, cause it
    //TODO: it is too much information
    public String title;
    public String detail;
    public Date scheduleTime;

    /**
     * TODO: participantIds is for store those user who signed up with username and password
     * for now we just use another field to temporary store the participants' name
     */
    @ObjectId
    public List<String> participantIds;
    public List<String> ParticipantNames; // non-registered participants' names, name should be unique within same event
    public Date creationTime;
    @ObjectId
    public String eventId;

    public ScheduleOption(){

    }
    public ScheduleOption(String evenId) {
        this.eventId = evenId;
        this.creationTime = new Date();
    }

    public static WriteResult<ScheduleOption, String> create(ScheduleOption scheduleOption) {

        return ScheduleOption.coll.save(scheduleOption);
    }

    /**
     * Most of the case, We are going to create multiple scheduleOptions at once
     * @param scheduleOptionlist
     * @return
     */
    public static WriteResult<ScheduleOption,String> createScheduleOptions(List<ScheduleOption> scheduleOptionlist){
        WriteResult<ScheduleOption,String> result = coll.insert(scheduleOptionlist);
        return result;
    }

    public static List<ScheduleOption> findByTitle(String title){
        if(title == null) return null;
        try{
            List<ScheduleOption> scheduleOptionList = coll.find().in("title",title).toArray();
            if(scheduleOptionList == null || scheduleOptionList.isEmpty()){
                return null;
            }else{
                return scheduleOptionList;
            }

        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static ScheduleOption findById(String id) {
        ScheduleOption scheduleOption = ScheduleOption.coll.findOneById(id);
        return scheduleOption;
    }

    /**
     * Find a specific scheduleoption and add new participant to it,
     *
     * @param scheduleId
     * @param participantid
     * @return the new scheduleOption
     */
    public static ScheduleOption addParticipantsByScheduleOptionId(String scheduleId, String participantid) {
        Logger.debug("Start add new participantsId " + participantid + "into scheduleID " + scheduleId);
        ScheduleOption scheduleOption = findById(scheduleId);
        if (scheduleOption == null) return null;

        List<String> participantIds = scheduleOption.participantIds;
        participantIds.add(participantid);
        return updateScheduleOptionParticipants(scheduleOption, participantIds);

    }

    public static ScheduleOption removeParticipantsByScheduleOptionId(String scheduleId, String participantid) {
        Logger.debug("Start removing participantId %s from scheduleOption %s", participantid, scheduleId);
        ScheduleOption scheduleOption = findById(scheduleId);
        if (scheduleOption == null) return null;
        List<String> participantIds = scheduleOption.participantIds;
        if (participantIds.remove(participantid)) {
            Logger.debug("Participant %s is removed successfully", participantid);
        } else {
            Logger.debug("Oops..Participant %s does not exist", participantid);
        }
        return scheduleOption;
    }

    public static ScheduleOption updateParticipantNames(ScheduleOption scheduleOption,List<String> newParticipantNames){
        Event event = Event.findById(scheduleOption.eventId);
        //it also has to add these new participant into the relative Event
        Event.addParticipantNames(event,newParticipantNames);
        //add this ParticipantNames to scheduleOption
        coll.updateById(scheduleOption.id,DBUpdate.addToSet("ParticipantNames", newParticipantNames));

        ScheduleOption result = findById(scheduleOption.id);
        return result;
    }

    public static ScheduleOption updateScheduleOptionParticipants(ScheduleOption scheduleOption, List<String> newParticipants) {
        WriteResult<ScheduleOption, String> updatedScheduleOption = coll.updateById(scheduleOption.id, DBUpdate.set("participantIds", newParticipants));
        ScheduleOption result = findById(scheduleOption.id);
        return result;
    }
}
