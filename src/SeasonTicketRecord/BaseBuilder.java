package SeasonTicketRecord;

/**
 * Abstract builder for SeasonTicketRecords
 */
public abstract class BaseBuilder {
    protected SeasonTicketRecord seasonTicketRecord = new SeasonTicketRecord();

    public abstract SeasonTicketRecord getSeasonTicketRecord();
    public abstract void buildBookId(int bookId);
}
