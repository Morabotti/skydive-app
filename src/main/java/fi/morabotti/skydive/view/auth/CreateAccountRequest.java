package fi.morabotti.skydive.view.auth;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import fi.jubic.easyvalue.EasyValue;
import fi.morabotti.skydive.db.enums.AccountRole;

@EasyValue
@JsonDeserialize(builder = CreateAccountRequest.Builder.class)
public abstract class CreateAccountRequest {
    public abstract String getFirstName();

    public abstract String getLastName();

    public abstract String getAddress();

    public abstract String getCity();

    public abstract String getZipCode();

    public abstract String getPhone();

    public abstract String getPassword();

    public abstract String getUsername();

    public abstract AccountRole getRole();

    public abstract Builder toBuilder();

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends EasyValue_CreateAccountRequest.Builder {

    }
}
