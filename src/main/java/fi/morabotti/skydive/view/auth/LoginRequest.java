package fi.morabotti.skydive.view.auth;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import fi.jubic.easyvalue.EasyValue;

@EasyValue
@JsonDeserialize(builder = LoginRequest.Builder.class)
public abstract class LoginRequest {
    public abstract String getUsername();

    public abstract String getPassword();

    public static class Builder extends EasyValue_LoginRequest.Builder {
    }
}