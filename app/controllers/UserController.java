package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import models.Photo;
import models.User;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;
import views.html.user;

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
        String email = json.findPath("email").textValue();
        String authToken = json.findPath("authToken").textValue();
        Photo photo = new Photo();
        photo.photoUrl = photoUrl;
        photo.thumbnailUrl = thumbUrl;
        String photoId = Photo.create(photo).getSavedId();
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.photoId = photoId;
        user.setEmail(email);
        user.setAuthToken(authToken);

        if(username==null || password==null || photoUrl==null || thumbUrl ==null){
            return badRequest("Exception Json Data");
        }else {
            //check if the user already exited
            User checkUser  = User.findByEmailAddressAndPassword(user.getEmail(),user.password);
            if(checkUser!=null){
                return badRequest("User already existed");
            }
            String userid = User.create(user).getSavedId();
            ObjectNode result = Json.newObject();
            result.put("userId",userid);
            return ok(result);
        }
    }

  //  @With(SecurityController.class)
    @BodyParser.Of(play.mvc.BodyParser.Json.class)
    public static Result FindAllUsers() {
        JsonNode json = request().body().asJson();
        List<User> users = User.all();
        ObjectNode result = Json.newObject();
        return ok(toJson(users));
    }


    @BodyParser.Of(play.mvc.BodyParser.Json.class)
    public static Result Login() {
        JsonNode json = request().body().asJson();
        String password = json.findPath("password").textValue();
        String email = json.findPath("email").textValue();
        if(password == null || email == null) {
            return badRequest("ERROR:UI did not pass Email and password correctly");
        }

        User user = User.findByEmailAddressAndPassword(email,password);
        if(user==null){
            return badRequest("ERROR:Email and password not valid");
        }
        return ok(toJson(user));
    }

    @BodyParser.Of(play.mvc.BodyParser.Json.class)
    public static Result FindByEmailAndPassword(String email,String password) {
        User user = User.findByEmailAddressAndPassword(email,password);
                if(user==null){
                    return badRequest("ERROR:Email and password not valid");
                }
        return ok(toJson(user));
    }



    public static Result findUserById() {
        return play.mvc.Results.TODO;
    }
}
