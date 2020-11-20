package fi.morabotti.skydive.view.auth;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import fi.jubic.easyvalue.EasyValue;

@EasyValue
@JsonDeserialize(builder = ChangePasswordRequest.Builder.class)
public abstract class ChangePasswordRequest {
    public abstract String getPassword();

    public static class Builder extends EasyValue_ChangePasswordRequest.Builder {
    }
}