package GetEntityFromQuery;

import ReadingHoleRecord.ReadingHoleRecord;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class that has methods that allows to get ReadingHoleRecord data from ResultSet
 * or to put it in Prepared Statement
 */
public class GetReadingHoleRecord {
    public static void getReadingHoleRecord(ResultSet resultSet, ReadingHoleRecord readingHoleRecord) throws SQLException {
        readingHoleRecord.setId(resultSet.getInt("id"));
        readingHoleRecord.setUserId(resultSet.getInt("userId"));
        readingHoleRecord.setBookId(resultSet.getInt("bookId"));
    }

    public static int getReadingHoleRecordAndInsert(PreparedStatement statement, ReadingHoleRecord readingHoleRecord) throws SQLException {
        int resultSet = 0;
        statement.setInt(1, readingHoleRecord.getUserId());
        statement.setInt(2, readingHoleRecord.getBookId());
        resultSet = statement.executeUpdate();
        return resultSet;
    }
}
