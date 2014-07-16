package models;

/**
 * Created by jiachen on 7/14/14.
 */
/*
 * This class as the DTO of transffering the data of the Row of Poll table
 */
public class PollTableDTO {

    public String userName;
    public ParticipantStatusEnum status;
    public String sheduleTitle;

    @Override
    public String toString() {
        return userName + ", " + status + ", " + sheduleTitle;
    }
}
