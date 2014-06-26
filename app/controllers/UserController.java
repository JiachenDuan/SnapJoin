package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Photo;
import models.User;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;
import static play.libs.Json.toJson;
/**
 * Created by jiachen on 6/24/14.
 */
public class UserController extends Controller {


    /**
     * #############REST Webservice Methods################
     */
    /**
     * Create User
     * @return user id just return
     */
    @BodyParser.Of(play.mvc.BodyParser.Json.class)
    public static Result createUser(){
        JsonNode json = request().body().asJson();
        String username = json.findPath("username").textValue();
        String password = json.findPath("password").textValue();
        String photoUrl = json.findPath("photoUrl").textValue();
        String thumbUrl = json.findPath("thumbnailUrl").textValue();
        Photo photo = new Photo();
        photo.photoUrl = photoUrl;
        photo.thumbnailUrl = thumbUrl;
        String photoId = Photo.create(photo).getSavedId();
        User user = new User();
        user.username = username;
        user.password = password;
        user.photoId = photoId;

        if(username==null || password==null || photoUrl==null || thumbUrl ==null){
            return badRequest("Exception Json Data");
        }else {
            String userid = User.create(user).getSavedId();
            ObjectNode result = Json.newObject();
            result.put("userId",userid);
            return ok(result);
        }
    }


    @BodyParser.Of(play.mvc.BodyParser.Json.class)
    public static Result FindAllUsers() {
        JsonNode json = request().body().asJson();
        List<User> users = User.all();
        ObjectNode result = Json.newObject();
        return ok(toJson(users));
    }

    public static Result findUserById() {
        return play.mvc.Results.TODO;
    }
}
