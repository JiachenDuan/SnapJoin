package models;

import net.vz.mongodb.jackson.DBRef;
import net.vz.mongodb.jackson.Id;
import net.vz.mongodb.jackson.MongoCollection;
import net.vz.mongodb.jackson.ObjectId;

import java.util.List;

/**
 * Created by jiachen on 6/22/14.
 */
public class PollOption {
    @Id
    @ObjectId
    public String id;

    @ObjectId
    public String photoId;

    @ObjectId
    public List<String> usersIds; //user who vote this option in the poll
}
