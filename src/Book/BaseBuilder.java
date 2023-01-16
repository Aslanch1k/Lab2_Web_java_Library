package Book;

import User.User;

import java.sql.Date;


public abstract class BaseBuilder {

    protected Book book = new Book();

    public abstract Book getBook();
    public abstract void buildName(String name);
    public abstract void buildAuthor(String author);
    public abstract void buildPublisher(String publisher);
    public abstract void buildDate(Date date);
    public abstract void buildQuantity(int quantity);
}

