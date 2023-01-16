package ReadingHoleRecord;

/**
 * Just builder
 */
public abstract class BaseBuilder {
    protected ReadingHoleRecord readingHoleRecord = new ReadingHoleRecord();

    public abstract ReadingHoleRecord getReadingHoleRecord();
    public abstract void buildUserId(int userId);
    public abstract void buildBookId(int bookId);
}
