package fi.morabotti.skydive.view;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import fi.jubic.easyvalue.EasyValue;

import java.util.UUID;

@EasyValue
@JsonDeserialize(builder = TokenResponse.Builder.class)
public abstract class TokenResponse {
    public abstract UUID getToken();

    public abstract AccountView getUser();

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends EasyValue_TokenResponse.Builder {
    }
}
