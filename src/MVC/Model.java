package MVC;

import Book.Book;
import Db.AbstractDao;
import Db.DAOs.BookDao;
import Db.DAOs.SeasonTicketRecordDao;
import Db.DAOs.UserDao;
import SeasonTicketRecord.SeasonTicketRecord;
import User.User;
import org.w3c.dom.Entity;

import java.sql.SQLException;
import java.util.List;

/**
 * Interface Model, part of MVC pattern
 */
public interface Model {

    Entity findEntityById(AbstractDao dao, int id) throws SQLException, ClassNotFoundException;
    void updateEntity(
            AbstractDao dao,
            int id,
            String param,
            String valueOfParam
    ) throws SQLException,ClassNotFoundException;
    void deleteEntity(AbstractDao dao, int id) throws SQLException, ClassNotFoundException;
    List<Entity> findAllEntity(AbstractDao dao) throws SQLException, ClassNotFoundException;
    int createEntity(AbstractDao dao, Entity entity) throws SQLException, ClassNotFoundException;
    List<Entity> findByParamEntity(
            AbstractDao dao,
            String param,
            String valueOfParam
    ) throws SQLException,ClassNotFoundException;

    User authUser(UserDao dao, String login, String password) throws SQLException, ClassNotFoundException ;

    Book getBookById(BookDao dao, int bookId) throws SQLException, ClassNotFoundException;
    SeasonTicketRecord getRecordById(
            SeasonTicketRecordDao dao,
            int recordId
    ) throws SQLException, ClassNotFoundException;
    User getUser(UserDao dao, int userId) throws SQLException, ClassNotFoundException;
    List<Entity> sortBooksByValue(AbstractDao dao, String value) throws SQLException, ClassNotFoundException;
}
