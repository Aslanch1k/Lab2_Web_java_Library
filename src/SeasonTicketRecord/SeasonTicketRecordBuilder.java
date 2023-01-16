package SeasonTicketRecord;

/**
 * Just a builder for SeasonTicketRecords
 */
public class SeasonTicketRecordBuilder extends BaseBuilder{

    @Override
    public SeasonTicketRecord getSeasonTicketRecord() {
        return seasonTicketRecord;
    }

    @Override
    public void buildBookId(int bookId) {
        seasonTicketRecord.setBookId(bookId);
    }
}
