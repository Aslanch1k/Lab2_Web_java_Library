package Db.DAOs;

import Db.AbstractDao;
import GetEntityFromQuery.GetReadingHoleRecord;
import ReadingHoleRecord.ReadingHoleRecord;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *DAO for managing users ReadingHole data
 */
public class ReadingHoleDao extends AbstractDao<ReadingHoleRecord> {
    private final static String SQL_SELECT_ALL_RD_HL_REC = """
            SELECT * FROM readinghole
            """;
    private final static String SQL_SELECT_RD_HL_REC_BY_ID = """
            SELECT * FROM readinghole WHERE id = ?
            """;
    private final static String SQL_DELETE_RD_HL_REC = """
            DELETE FROM readinghole WHERE id = ?
            """;
    private final static String SQL_INSERT_RD_HL_REC = """
            INSERT INTO readinghole(
            userId,
            bookId
            ) VALUES (?, ?)
            """;
    @Override
    public List<ReadingHoleRecord> findByParam(String param, String valueOfParam) {
        return null;
    }

    @Override
    public List<ReadingHoleRecord> findAll() {
        List<ReadingHoleRecord> readingHoleRecords = new ArrayList<>();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_SELECT_ALL_RD_HL_REC);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                ReadingHoleRecord readingHoleRecord = new ReadingHoleRecord();
                GetReadingHoleRecord.getReadingHoleRecord(resultSet, readingHoleRecord);
                readingHoleRecords.add(readingHoleRecord);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(statement);
        }
        return readingHoleRecords;
    }

    @Override
    public ReadingHoleRecord findEntityById(int id) {
        ReadingHoleRecord readingHoleRecord = new ReadingHoleRecord();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_SELECT_RD_HL_REC_BY_ID);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            GetReadingHoleRecord.getReadingHoleRecord(resultSet, readingHoleRecord);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(statement);
        }
        return readingHoleRecord;
    }

    @Override
    public int delete(int id) {
        int resultSet;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_DELETE_RD_HL_REC);
            statement.setInt(1, id);
            resultSet = statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(statement);
        }
        return resultSet;
    }

    @Override
    public int update(int id, String param, String valueOfParam) {
        return 0;
    }

    @Override
    public List<ReadingHoleRecord> sortByValue(String value) {
        return null;
    }

    @Override
    public int create(ReadingHoleRecord entity) {
        int resultSet = 0;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_INSERT_RD_HL_REC);
            resultSet = GetReadingHoleRecord.getReadingHoleRecordAndInsert(statement, entity);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(statement);
        }
        return resultSet;
    }

    @Override
    public boolean delete(ReadingHoleRecord entity) {
        return false;
    }
}
