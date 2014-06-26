package models;

import net.vz.mongodb.jackson.*;
import play.modules.mongodb.jackson.MongoDB;

import java.util.Date;
import java.util.List;

/**
 * Created by jiachen on 6/21/14.
 */
public class User {
    @Id
    @ObjectId
    public String id;
    private static JacksonDBCollection<User, String> coll = MongoDB.getCollection("users", User.class, String.class);
    //
    public String username;
    public String password;
    public Date createdTime; //Time when the user is created
    //photo url
    @ObjectId
    public String photoId;
    //All the polls that the use has involved
    @ObjectId
    public List<String> pollIds;
    public User(){
        this.createdTime = new Date();
    }
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.createdTime = new Date();
    }

    public static WriteResult<User,String> create(User user){
        return User.coll.save(user);
    }

    public static List<User> all(){
        return User.coll.find().toArray();
    }
    public static User findById(String id){
        User user = User.coll.findOneById(id);
        return user;
    }
}
