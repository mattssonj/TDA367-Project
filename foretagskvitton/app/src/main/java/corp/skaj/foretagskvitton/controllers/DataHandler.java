package corp.skaj.foretagskvitton.controllers;

import corp.skaj.foretagskvitton.model.User;

public class DataHandler {
    private static final String GET_DATA_KEY = "GET_USER_DATA";
    private static DataHandler dataHandler;
    private User user;

    private DataHandler() {
    }

    public void setUser(User user) {
        if (user == null) {
            this.user = user;
        }
    }

    public User getUser() {
        return user;
    }

    public static DataHandler getInstance() {
        if (dataHandler == null) {
            dataHandler = new DataHandler();
        }
        return dataHandler;
    }

    public static String getGetDataKey() {
        return GET_DATA_KEY;
    }
}