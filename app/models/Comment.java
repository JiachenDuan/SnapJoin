package models;

import net.vz.mongodb.jackson.Id;
import net.vz.mongodb.jackson.JacksonDBCollection;
import net.vz.mongodb.jackson.ObjectId;
import net.vz.mongodb.jackson.WriteResult;
import play.data.validation.Constraints;
import play.modules.mongodb.jackson.MongoDB;

import java.util.Date;
import java.util.List;

/**
 * The Comment will be selected by EventId, and display it by the order of createTime
 * Created by jiachen on 7/6/14.
 */
public class Comment {
    private static JacksonDBCollection<Comment, String> coll = MongoDB.getCollection("comments", Comment.class, String.class);
    @Id
    @ObjectId
    public String id;
    public String contents;
    //Here is only the owner name, not the Id, at this point, the user does
    // have to be registered.
    public String ownerName;
    public Date creationTime;
    @ObjectId
    public String eventId;

    public Comment() {
        this.creationTime = new Date();
    }

    public static WriteResult<Comment, String> create(Comment comment) {
        return Comment.coll.save(comment);
    }

    public static Comment findByEventId(String eventId) {
        if (eventId == null) return null;
        try{
            List<Comment> comments = coll.find().in("eventId",eventId).toArray();
            if(comments == null || comments.size() == 0){
                return null;
            }else{
                return comments.get(0);
            }
        }catch(Exception e){
            return null;
        }
    }

    public static Comment findById(String id){
        Comment comment = Comment.coll.findOneById(id);
        return comment;
    }
}
