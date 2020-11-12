package fi.morabotti.skydive.view;

import javax.annotation.Nullable;
import javax.ws.rs.QueryParam;
import java.time.LocalDate;

public class DateRangeQuery {
    @QueryParam("from")
    private LocalDate from;

    @QueryParam("to")
    @Nullable
    private LocalDate to;

    public DateRangeQuery() {

    }

    public DateRangeQuery(
            @QueryParam("from") LocalDate from,
            @QueryParam("to") @Nullable LocalDate to
    ) {
        this.from = from;
        this.to = to;
    }

    public LocalDate getFrom() {
        return from;
    }

    public LocalDate getTo() {
        return to == null || from.isAfter(to) ? from : to;
    }
}
