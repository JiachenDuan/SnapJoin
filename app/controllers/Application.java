package controllers;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.api.libs.json.JsPath;
import static play.libs.Json.toJson;

import play.libs.Json;
import play.mvc.*;
import play.data.*;

import models.*;

import java.util.List;


public class Application extends Controller {
    static Form<Task> taskForm = Form.form(Task.class);
    static Form<User> userForm = Form.form(User.class);

    public static Result index() {
        return redirect(routes.Application.tasks());
    }

    public static Result tasks() {
        return ok(
                views.html.index.render(Task.all(), taskForm)
        );
    }

    /**
     * #############REST Webservice Methods################
     */
    /**
     * Create User
     * @return user id just return
     */
    @BodyParser.Of(play.mvc.BodyParser.Json.class)
    public static Result newUserRest(){
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

    /**
     * Find user by id
     * @return
     */

    /**
     * #################PLAY METHODS################
     */
    public static Result newTask() {
        Form<Task> filledForm = taskForm.bindFromRequest();
        if (filledForm.hasErrors()) {
            return badRequest(
                    views.html.index.render(Task.all(), filledForm)
            );
        } else {
            Task.create(filledForm.get());
            return redirect(routes.Application.tasks());
        }
    }

    public static Result deleteTask(String id) {
        Task.delete(id);
        return redirect(routes.Application.tasks());
    }

    //
    public static Result users() {
        return ok(
                views.html.user.render(User.all(), userForm)
        );
    }


//    public static Result newUser() {
//        Form<User> filledForm = userForm.bindFromRequest();
//        if (filledForm.hasErrors()) {
//            return badRequest(
//                    views.html.user.render(User.all(), filledForm)
//            );
//        } else {
//            User.create(filledForm.get());
//            return redirect(routes.Application.users());
//        }
//    }

    public static Result userjson(){
        List<User> users = User.all();

        return ok(toJson(users));
    }

    public static Result taskjson(){
        List<Task> tasks = Task.all();
        return ok(toJson(tasks));
    }



    public static Result newUserRestGet(String username,String password,String thumbUrl,String photoUrl){
       return userjson();
    }

  @BodyParser.Of(play.mvc.BodyParser.Json.class)
    public static Result sayhello() {
      JsonNode json = request().body().asJson();
        String name = json.findPath("name").textValue();
        if(name == null){
            return badRequest("Exception Json Data");
        }else{
            return ok("Hello " + name);
        }

    }

    public static Result getPhoto(){
        List<Photo> photos = Photo.all();
        return ok(toJson(photos));
    }

}
