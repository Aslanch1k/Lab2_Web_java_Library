package MVC;

import Book.Book;
import Db.AbstractDao;
import Db.DAOs.BookDao;
import Db.DAOs.SeasonTicketRecordDao;
import Db.DAOs.UserDao;
import Db.EntityTransaction;
import SeasonTicketRecord.SeasonTicketRecord;
import User.User;
import org.w3c.dom.Entity;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class, that implements class Model and
 * override methods for interaction with Database
 */
public class DbModel implements Model{

    @Override
    public Entity findEntityById(AbstractDao dao, int id) throws SQLException, ClassNotFoundException {
        Entity entity = null;
        EntityTransaction transaction = new EntityTransaction();
        transaction.initTransaction(dao);
        try {
            entity = dao.findEntityById(id);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            transaction.endTransaction();
        }
        return entity;
    }

    @Override
    public void updateEntity(AbstractDao dao, int id, String param, String valueOfParam) throws SQLException, ClassNotFoundException {
        EntityTransaction transaction = new EntityTransaction();
        transaction.initTransaction(dao);
        try {
            System.out.println(dao.update(id, param, valueOfParam));
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            transaction.endTransaction();
        }
    }

    @Override
    public void deleteEntity(AbstractDao dao, int id) throws SQLException, ClassNotFoundException {
        EntityTransaction transaction = new EntityTransaction();
        transaction.initTransaction(dao);
        try {
            System.out.println(dao.delete(id));
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            transaction.endTransaction();
        }
    }

    @Override
    public List findAllEntity(AbstractDao dao) throws SQLException, ClassNotFoundException {
        EntityTransaction transaction = new EntityTransaction();
        transaction.initTransaction(dao);
        List entities = new ArrayList<>();
        try {
            entities = dao.findAll();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            transaction.endTransaction();
        }
        return entities;
    }

    @Override
    public int createEntity(AbstractDao dao, Entity entity) throws SQLException, ClassNotFoundException {
        EntityTransaction transaction = new EntityTransaction();
        transaction.initTransaction(dao);
        int orderStatus = 0;
        try {
            orderStatus = dao.create(entity);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            transaction.endTransaction();
        }
        return orderStatus;
    }

    @Override
    public List<Entity> findByParamEntity(
            AbstractDao dao,
            String param,
            String valueOfParam
    ) throws SQLException, ClassNotFoundException {
        EntityTransaction transaction = new EntityTransaction();
        transaction.initTransaction(dao);
        List<Entity> entities = new ArrayList<>();
        try {
            entities = dao.findByParam(param, valueOfParam);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            transaction.endTransaction();
        }
        return entities;
    }

    @Override
    public User authUser(UserDao dao, String login, String password) throws SQLException, ClassNotFoundException {
        EntityTransaction transaction = new EntityTransaction();
        transaction.initTransaction(dao);
        User user = null;
        try {
            user = dao.checkForExisting(login, password);
            transaction.commit();
        } catch (Exception e){
            transaction.rollback();
        } finally {
            transaction.endTransaction();
        }
        return user;
    }

    @Override
    public User getUser(UserDao dao, int userId) throws SQLException, ClassNotFoundException {
        EntityTransaction transaction = new EntityTransaction();
        transaction.initTransaction(dao);
        User user = null;
        try {
            user = dao.findEntityById(userId);
            transaction.commit();
        } finally {
            transaction.endTransaction();
        }
        return user;
    }

    @Override
    public Book getBookById(BookDao dao, int bookId) throws SQLException, ClassNotFoundException {
        EntityTransaction transaction = new EntityTransaction();
        transaction.initTransaction(dao);
        Book book = null;
        try {
            book = dao.findEntityById(bookId);
            transaction.commit();
        } finally {
            transaction.endTransaction();
        }
        return book;
    }

    @Override
    public SeasonTicketRecord getRecordById(SeasonTicketRecordDao dao, int recordId) throws SQLException, ClassNotFoundException {
        EntityTransaction transaction = new EntityTransaction();
        transaction.initTransaction(dao);
        SeasonTicketRecord record = null;
        try {
            record = dao.findEntityById(recordId);
            transaction.commit();
        } finally {
            transaction.endTransaction();
        }
        return record;
    }

    @Override
    public List<Entity> sortBooksByValue(AbstractDao dao, String value) throws SQLException, ClassNotFoundException {
        EntityTransaction transaction = new EntityTransaction();
        transaction.initTransaction(dao);
        List<Entity> books = null;
        try {
            books = dao.sortByValue(value);
            transaction.commit();
        } finally {
            transaction.endTransaction();
        }
        return books;
    }
}
