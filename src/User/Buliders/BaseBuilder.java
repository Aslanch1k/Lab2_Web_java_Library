package User.Buliders;

import User.User;

/**
 * Abstract class BaseBuilder
 * Defines a builder constructor
 * for users (User, Admin, Librarian)
 */
public abstract class BaseBuilder {
    protected User user = new User();

    public User getUser(){
        return user;
    }

    public abstract void buildLogin(String login);
    public abstract void buildPassword(String password);
    public abstract void buildStatus();
    public abstract void buildBlockStatus();
}

