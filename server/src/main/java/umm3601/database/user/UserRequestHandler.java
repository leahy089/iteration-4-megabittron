
package umm3601.database.user;

import spark.Request;
import spark.Response;

/**
 *
 */
public class UserRequestHandler {

    private final UserController userController;
    public UserRequestHandler(UserController userController){
        this.userController = userController;
    }
    /**Method called from Server when the 'api/users/:id' endpoint is received.
     * Get a JSON response with a list of all the users in the database.
     *
     * @param req the HTTP request
     * @param res the HTTP response
     * @return one user in JSON formatted string and if it fails it will return text with a different HTTP status code
     */
    public String getUserJSON(Request req, Response res){
        res.type("application/json");
        String id = req.params("id");
        String user;
        try {
            user = userController.getUser(id);
        } catch (IllegalArgumentException e) {
            // This is thrown if the ID doesn't have the appropriate
            // form for a Mongo Object ID.
            // https://docs.mongodb.com/manual/reference/method/ObjectId/
            res.status(400);
            res.body("The requested user id " + id + " wasn't a legal Mongo Object ID.\n" +
                "See 'https://docs.mongodb.com/manual/reference/method/ObjectId/' for more info.");
            return "";
        }
        if (user != null) {
            return user;
        } else {
            res.status(404);
            res.body("The requested user with id " + id + " was not found");
            return "";
        }
    }



    /**Method called from Server when the 'api/users' endpoint is received.
     * This handles the request received and the response
     * that will be sent back.
     *@param req the HTTP request
     * @param res the HTTP response
     * @return an array of users in JSON formatted String
     */
    public String getUsers(Request req, Response res)
    {
        res.type("application/json");
        return userController.getUsers(req.queryMap().toMap());
    }


    /*
        changes a user's style settings
     */
    public String editUserStyleSetting(Request req, Response res){
        res.type("application/json");
        return userController.editUserStyleSetting(req.queryMap().toMap());
    }
}
