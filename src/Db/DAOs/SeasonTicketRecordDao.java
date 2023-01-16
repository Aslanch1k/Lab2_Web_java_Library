package Db.DAOs;

import Db.AbstractDao;
import GetEntityFromQuery.GetSeasonTicketRecord;
import SeasonTicketRecord.SeasonTicketRecord;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *DAO for managing SeasonTickets Data
 */
public class SeasonTicketRecordDao extends AbstractDao<SeasonTicketRecord>{

    private String userLogin = "";

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    private static final String SQL_SELECT_ALL_RECORDS = """
            SELECT * FROM seasonticketforuser{0}
            """;

    private static final String SQL_SELECT_BY_ID = """
            SELECT * FROM seasonticketforuser{0} WHERE id = ?
            """;

    private static final String SQL_INSERT_RECORD = """
            INSERT INTO seasonticketforuser{0}(
            bookId
            ) VALUE (?)
            """;

    private static final String SQL_DELETE_RECORD = """
            DELETE FROM seasonticketforuser{0} WHERE id = ?
            """;




    @Override
    public List<SeasonTicketRecord> findByParam(String param, String valueOfParam) {
        return null;
    }

    @Override
    public List<SeasonTicketRecord> findAll() {
        List<SeasonTicketRecord> seasonTicketRecords = new ArrayList<>();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(MessageFormat.format(SQL_SELECT_ALL_RECORDS, userLogin));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                SeasonTicketRecord seasonTicketRecord = new SeasonTicketRecord();
                GetSeasonTicketRecord.getSeasonTicketRecord(resultSet, seasonTicketRecord);
                seasonTicketRecords.add(seasonTicketRecord);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(statement);
        }
        return seasonTicketRecords;
    }

    @Override
    public SeasonTicketRecord findEntityById(int id) {
        SeasonTicketRecord seasonTicketRecord = new SeasonTicketRecord();
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(MessageFormat.format(SQL_SELECT_BY_ID, userLogin));
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            GetSeasonTicketRecord.getSeasonTicketRecord(resultSet, seasonTicketRecord);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(statement);
        }
        return seasonTicketRecord;
    }

    @Override
    public int delete(int id) {
        int resultSet;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(MessageFormat.format(SQL_DELETE_RECORD, userLogin));
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
    public List<SeasonTicketRecord> sortByValue(String value) {
        return null;
    }

    @Override
    public int create(SeasonTicketRecord entity) {
        int resultSet;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(MessageFormat.format(SQL_INSERT_RECORD, userLogin));
            resultSet = GetSeasonTicketRecord.getSeasonTicketRecordAndInsert(statement, entity);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(statement);
        }
        return resultSet;
    }

    @Override
    public boolean delete(SeasonTicketRecord entity) {
        return false;
    }
}
