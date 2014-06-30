package controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import models.User;
import play.data.Form;
import play.data.validation.Constraints;
import play.libs.F;
import play.libs.Json;
import play.mvc.*;

import static play.mvc.Controller.response;

public class SecurityController extends Action.Simple {

    public final static String AUTH_TOKEN_HEADER = "X-AUTH-TOKEN";
    public static final String AUTH_TOKEN = "authToken";

    public F.Promise call(Http.Context ctx) throws Throwable {
        User user = null;
        String[] authTokenHeaderValues = ctx.request().headers().get(AUTH_TOKEN_HEADER);
        if ((authTokenHeaderValues != null) && (authTokenHeaderValues.length == 1) && (authTokenHeaderValues[0] != null)) {
            user = User.findByAuthToken(authTokenHeaderValues[0]);
            if (user != null) {
                ctx.args.put("user", user);
                return delegate.call(ctx);
            }
        }
        Result unauthorized = Results.unauthorized("unauthorized");
        return F.Promise.pure(unauthorized);
    }

    public static User getUser() {

        return (User) Http.Context.current().args.get("user");
    }

    // returns an authToken
    public static Result login(String email, String password) {
//        Form<Login> loginForm = Form.form(Login.class).bindFromRequest();
//
//        if (loginForm.hasErrors()) {
//            return badRequest(loginForm.errorsAsJson());
//        }
//
//        Login login = loginForm.get();

        //   User user = User.findByEmailAddressAndPassword(login.emailAddress, login.password);
        User user = User.findByEmailAddressAndPassword(email, password);

        if (user == null) {
            return unauthorized();
        } else {
            String authToken = user.createToken();
            User updatedUser = User.updateAuthToken(user,authToken);
            ObjectNode authTokenJson = Json.newObject();
            authTokenJson.put(AUTH_TOKEN, authToken);
            response().setCookie(AUTH_TOKEN, authToken);
            return ok(authTokenJson);
        }
    }

    @With(SecurityController.class)
    public static Result logout() {
        response().discardCookie(AUTH_TOKEN);
        getUser().deleteAuthToken();
        return redirect("/");
    }

    public static class Login {

        @Constraints.Required
        @Constraints.Email
        public String emailAddress;

        @Constraints.Required
        public String password;

    }


}
