package GetEntityFromQuery;

import Book.Book;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class that has methods that allows to get Book data from ResultSet
 * or to put it in Prepared Statement
 */
public class GetBook{
    public static void getBook(ResultSet resultSet, Book book) throws SQLException {
        book.setId(resultSet.getInt("id"));
        book.setName(resultSet.getString("name"));
        book.setAuthor(resultSet.getString("author"));
        book.setPublisher(resultSet.getString("publisher"));
        book.setDate(resultSet.getDate("date"));
        book.setQuantity(resultSet.getInt("quantity"));
    }

    public static int getBookAndInsert(PreparedStatement statement, Book book) throws SQLException {
        int resultSet = 0;
        statement.setString(1, book.getName());
        statement.setString(2, book.getAuthor());
        statement.setString(3, book.getPublisher());
        statement.setDate(4, book.getDate());
        statement.setInt(5, book.getQuantity());
        resultSet = statement.executeUpdate();
        return resultSet;
    }
}
