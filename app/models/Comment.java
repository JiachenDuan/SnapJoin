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
 * Created by jiachen on 7/6/14.
 */
public class Comment {
    private static JacksonDBCollection<Comment, String> coll = MongoDB.getCollection("comments", Comment.class, String.class);
    @Id
    @ObjectId
    public String id;
    public String contents;
    @Constraints.Required
    public String ownnerid;
    public Date creationTime;

    public Comment() {
        this.creationTime = new Date();
    }

    public static WriteResult<Comment, String> create(Comment comment) {
        return Comment.coll.save(comment);
    }

    public static Comment findByOwnnerId(String eventHostId) {
        if (eventHostId == null) return null;
        try{
            List<Comment> comments = coll.find().in("ownnerid",eventHostId).toArray();
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
