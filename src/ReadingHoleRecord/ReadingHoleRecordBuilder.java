package ReadingHoleRecord;

/**
 * Just a builder for ReadingHoleRecords
 */
public class ReadingHoleRecordBuilder extends BaseBuilder{
    @Override
    public ReadingHoleRecord getReadingHoleRecord() {
        return readingHoleRecord;
    }

    @Override
    public void buildUserId(int userId) {
        readingHoleRecord.setUserId(userId);
    }

    @Override
    public void buildBookId(int bookId) {
        readingHoleRecord.setBookId(bookId);
    }
}
