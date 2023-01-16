package Db.DAOs;

import Book.Book;
import Db.AbstractDao;
import GetEntityFromQuery.GetBook;
import java.sql.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *DAO for managing Books Data
 */
public class BookDao extends AbstractDao<Book>{

    public static final String SQL_SELECT_SORTED_BY_PARAM = "SELECT * FROM books ORDER BY {0}";
    public static final String SQL_DELETE_BOOK = "DELETE FROM books WHERE id = ?";
    public static final String SQL_SELECT_BY_ID = "SELECT * FROM books WHERE id = ?";
    private static final String SQL_UPDATE_BOOK = """
            UPDATE books SET {0} = ? WHERE id = ?
            """;
    public static final String SQL_SELECT_BY_PARAM = "SELECT * FROM books WHERE {0} = ?";
    private final String SQL_SELECT_ALL_BOOKS = "SELECT * FROM books";
    private static final String SQL_INSERT_BOOK = """
            INSERT INTO books(
                              name,
                              author,
                              publisher,
                              date,
                              quantity
                              )
            VALUES (?,?,?,?,?)
            """;

    @Override
    public List<Book> findByParam(String param, String valueOfParam) {
        List<Book> books = new ArrayList<>();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(MessageFormat.format(SQL_SELECT_BY_PARAM, param.toLowerCase()));
            statement.setString(1, valueOfParam);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Book book = new Book();
                GetBook.getBook(resultSet, book);
                books.add(book);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(statement);
        }
        return books;
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(SQL_SELECT_ALL_BOOKS);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Book book = new Book();
                GetBook.getBook(resultSet, book);
                books.add(book);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(statement);
        }
        return books;
    }

    @Override
    public Book findEntityById(int id) {
        Book book = new Book();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_SELECT_BY_ID);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            GetBook.getBook(resultSet, book);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(statement);
        }
        return book;
    }

    @Override
    public int delete(int id) {
        int resultSet;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_DELETE_BOOK);
            statement.setInt(1, id);
            resultSet = statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } close(statement);
        return resultSet;
    }

    @Override
    public int update(int id, String param, String valueOfParam) {
        int resultSet;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(MessageFormat.format(SQL_UPDATE_BOOK, param.toLowerCase()));
            statement.setInt(2, id);
            if(param.equalsIgnoreCase("quantity")){
                statement.setInt(1, Integer.parseInt(valueOfParam));
            }
            else {
                statement.setString(1, valueOfParam);
            }
            resultSet = statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(statement);
        }
        return resultSet;
    }

    @Override
    public int create(Book entity) {
        int resultSet;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_INSERT_BOOK);
            resultSet = GetBook.getBookAndInsert(statement, entity);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(statement);
        }
        return resultSet;
    }

    @Override
    public List<Book> sortByValue(String value){
        List<Book> books = new ArrayList<>();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(MessageFormat.format(SQL_SELECT_SORTED_BY_PARAM, value));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Book book = new Book();
                GetBook.getBook(resultSet, book);
                books.add(book);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(statement);
        }
        return books;
    }

    @Override
    public boolean delete(Book entity) {
        return false;
    }
}
