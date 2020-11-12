package fi.morabotti.skydive.view;

import javax.annotation.Nullable;
import javax.ws.rs.QueryParam;
import java.util.Optional;

public class PaginationQuery {
    @QueryParam("limit")
    @Nullable
    private Integer limit;

    @QueryParam("offset")
    @Nullable
    private Integer offset;

    public PaginationQuery() {

    }

    public PaginationQuery(
            @Nullable Integer limit,
            @Nullable Integer offset
    ) {
        this.limit = limit;
        this.offset = offset;
    }

    public Optional<Integer> getLimit() {
        return Optional.ofNullable(this.limit);
    }

    public Optional<Integer> getOffset() {
        return Optional.ofNullable(this.offset);
    }
}

