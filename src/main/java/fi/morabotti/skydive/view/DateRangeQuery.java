package fi.morabotti.skydive.view;

import javax.annotation.Nullable;
import javax.ws.rs.QueryParam;
import java.time.LocalDate;
import java.util.Optional;

public class DateRangeQuery {
    @QueryParam("from")
    @Nullable
    private LocalDate from;

    @QueryParam("to")
    @Nullable
    private LocalDate to;

    public DateRangeQuery() {

    }

    public DateRangeQuery(
            @Nullable LocalDate from,
            @Nullable LocalDate to
    ) {
        this.from = from;
        this.to = to;
    }


    public Optional<LocalDate> getFrom() {
        return Optional.ofNullable(this.from);
    }

    public Optional<LocalDate> getTo() {
        return Optional.ofNullable(this.to);
    }
}
