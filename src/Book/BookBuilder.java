package Book;

import java.sql.Date;

public class BookBuilder extends BaseBuilder {

    @Override
    public void buildName(String name) {
        book.setName(name);
    }

    @Override
    public void buildAuthor(String author) {
        book.setAuthor(author);
    }

    @Override
    public void buildPublisher(String publisher) {
        book.setPublisher(publisher);
    }

    @Override
    public void buildDate(Date date) {
        book.setDate(date);
    }

    @Override
    public void buildQuantity(int quantity) {
        book.setQuantity(quantity);
    }

    @Override
    public Book getBook() {
        return book;
    }
}
