package models;

import net.vz.mongodb.jackson.DBRef;
import net.vz.mongodb.jackson.Id;
import net.vz.mongodb.jackson.MongoCollection;
import net.vz.mongodb.jackson.ObjectId;

import java.util.List;

/**
 * Created by jiachen on 6/21/14.
 */
public class Poll {
    @Id
    @ObjectId
    public String id;

    @ObjectId
    public List<String> userIds;

}
