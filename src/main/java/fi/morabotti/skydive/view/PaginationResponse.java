package fi.morabotti.skydive.view;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import fi.jubic.easyvalue.EasyValue;

import java.util.List;

@EasyValue
@JsonDeserialize(builder = PaginationResponse.Builder.class)
public abstract class PaginationResponse<T> {
    public abstract List<T> getResult();

    public abstract Long getLength();

    public abstract Builder<T> toBuilder();

    public static <T> Builder<T> builder() {
        return new Builder<>();
    }

    public static class Builder<T> extends EasyValue_PaginationResponse.Builder<T> {
    }

    public static <T> PaginationResponse<T> create(
            List<T> result,
            Long length
    ) {
        return PaginationResponse.<T>builder()
                .setResult(result)
                .setLength(length)
                .build();
    }
}
