package fi.morabotti.skydive.view.auth;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import fi.jubic.easyvalue.EasyValue;

@EasyValue
@JsonDeserialize(builder = RegisterRequest.Builder.class)
public abstract class RegisterRequest {
    public abstract String getFirstName();

    public abstract String getLastName();

    public abstract String getAddress();

    public abstract String getCity();

    public abstract String getZipCode();

    public abstract String getPhone();

    public abstract String getPassword();

    public abstract String getUsername();

    public abstract Builder toBuilder();

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends EasyValue_RegisterRequest.Builder {

    }

    public static RegisterRequest of(CreateAccountRequest accountRequest) {
        return RegisterRequest.builder()
                .setUsername(accountRequest.getUsername())
                .setPassword(accountRequest.getPassword())
                .setAddress(accountRequest.getAddress())
                .setCity(accountRequest.getCity())
                .setFirstName(accountRequest.getFirstName())
                .setLastName(accountRequest.getLastName())
                .setPhone(accountRequest.getPhone())
                .setZipCode(accountRequest.getZipCode())
                .build();
    }
}
