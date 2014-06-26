package models;

import net.vz.mongodb.jackson.*;
import play.modules.mongodb.jackson.MongoDB;

import java.util.Date;
import java.util.List;

/**
 * Created by jiachen on 6/22/14.
 */
public class Photo {

    @Id
    @ObjectId
    public String id;
    private static JacksonDBCollection<Photo, String> coll = MongoDB.getCollection("photos", Photo.class, String.class);
    //photo url
    public String photoUrl;
    public String thumbnailUrl;
    public Date timeStamp;

    public Photo(){
          timeStamp = new Date();
    }

    public static List<Photo> all(){
        return Photo.coll.find().toArray();
    }

    public static WriteResult<Photo,String> create(Photo photo){
      return Photo.coll.save(photo);
    }

    public static Photo findById(String id){
        return Photo.coll.findOneById(id);
    }
}
