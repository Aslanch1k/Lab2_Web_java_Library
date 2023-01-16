package GetEntityFromQuery;

import SeasonTicketRecord.SeasonTicketRecord;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class that has methods that allows to get SeasonTicketRecord data from ResultSet
 * or to put it in Prepared Statement
 */
public class GetSeasonTicketRecord {
    public static void getSeasonTicketRecord(ResultSet resultSet, SeasonTicketRecord seasonTicketRecord) throws SQLException {
        seasonTicketRecord.setId(resultSet.getInt("id"));
        seasonTicketRecord.setBookId(resultSet.getInt("bookId"));
        seasonTicketRecord.setStartDate(resultSet.getDate("startDate"));
        seasonTicketRecord.setFinishDate(resultSet.getDate("finishDate"));
    }

    public static int getSeasonTicketRecordAndInsert(PreparedStatement statement, SeasonTicketRecord seasonTicketRecord) throws SQLException {
        int resultSet;
        statement.setInt(1, seasonTicketRecord.getBookId());
        resultSet = statement.executeUpdate();
        return resultSet;
    }
}
