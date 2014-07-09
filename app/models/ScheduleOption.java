package models;

import net.vz.mongodb.jackson.*;
import play.Logger;
import play.modules.mongodb.jackson.MongoDB;

import java.util.Date;
import java.util.HashSet;

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
    public Date scheduleTime;

    /**
     * TODO: participantIds is for store those user who signed up with username and password
     * for now we just use another field to temporary store the participants' name
     */
    @ObjectId
    public HashSet<String> participantIds;
    public HashSet<String> tempParticipantNames; // non-registered participants' names, name should be unique within same event
    public Date creationTime;
    @ObjectId
    public String eventId;

    public ScheduleOption(String evenId) {
        this.eventId = evenId;
        this.creationTime = new Date();
    }

    public static WriteResult<ScheduleOption, String> create(ScheduleOption scheduleOption) {
        return ScheduleOption.coll.save(scheduleOption);
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

        HashSet<String> participantIds = scheduleOption.participantIds;
        participantIds.add(participantid);
        return updateScheduleOptionParticipants(scheduleOption, participantIds);

    }

    public static ScheduleOption removeParticipantsByScheduleOptionId(String scheduleId, String participantid) {
        Logger.debug("Start removing participantId %s from scheduleOption %s", participantid, scheduleId);
        ScheduleOption scheduleOption = findById(scheduleId);
        if (scheduleOption == null) return null;
        HashSet<String> participantIds = scheduleOption.participantIds;
        if (participantIds.remove(participantid)) {
            Logger.debug("Participant %s is removed successfully", participantid);
        } else {
            Logger.debug("Oops..Participant %s does not exist", participantid);
        }
        return scheduleOption;
    }

    public static ScheduleOption updateScheduleOptionParticipants(ScheduleOption scheduleOption, HashSet<String> newParticipants) {
        WriteResult<ScheduleOption, String> updatedScheduleOption = coll.updateById(scheduleOption.id, DBUpdate.set("participantIds", newParticipants));
        ScheduleOption result = findById(scheduleOption.id);
        return result;
    }
}
