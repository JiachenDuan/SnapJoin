package models;

import net.vz.mongodb.jackson.*;
import play.modules.mongodb.jackson.MongoDB;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by jiachen on 6/21/14.
 */
public class User {
    @Id
    @ObjectId
    public String id;
    private String authToken;
    private byte[] shaPassword;
    private static JacksonDBCollection<User, String> coll = MongoDB.getCollection("users", User.class, String.class);
    //

    public String username;
    //TODO: has to define the constrains about password,like length, and regired
    public String password;
    public Date createdTime; //Time when the user is created
    private String email;
    //photo url
    @ObjectId
    public String photoId;
    //All the polls that the use has involved
    @ObjectId
    public List<String> pollIds;

    public User() {
        this.createdTime = new Date();
    }

    public User(String username, String password, String email) {
        this.username = username;
        setPassword(password);
        setEmail(email);
        this.createdTime = new Date();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String createToken() {
        authToken = UUID.randomUUID().toString();
        return authToken;
    }

    public void deleteAuthToken() {
        authToken = null;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getAuthToken() {
        return authToken;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        shaPassword = getSha512(password);
    }

    public void setEmail(String email) {
        this.email = email.toLowerCase();
    }

    public String getEmail() {
        return email;
    }

    private byte[] getSha512(String value) {
        try {
            return MessageDigest.getInstance("SHA-512").digest(value.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static WriteResult<User, String> create(User user) {
        return User.coll.save(user);
    }

    public static List<User> all() {
        return User.coll.find().toArray();
    }

    //Find
    public static User findById(String id) {
        User user = User.coll.findOneById(id);
        return user;
    }

    public static User findByEmailAddressAndPassword(String emailAddress, String password) {
        List<User> users = coll.find().and(DBQuery.is("email", emailAddress), DBQuery.is("password", password)).toArray();

        if (users != null && users.size() != 0) {
            return users.get(0);
        }
        return null;
    }

    public static User findByAuthToken(String authToken) {
        if (authToken == null) {
            return null;
        }
        try {
            List<User> users = coll.find().in("authToken", authToken).toArray();
            if (users != null && users.size() != 0) {
                return users.get(0);
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }

    }

    public static User updateAuthToken(User user, String authToken) {
        WriteResult<User, String> updatedUser = coll.updateById(user.id, DBUpdate.set("authToken", authToken));
        return updatedUser.getSavedObject();
    }
}
