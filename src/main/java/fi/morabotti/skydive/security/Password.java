package fi.morabotti.skydive.security;

import fi.jubic.easyvalue.EasyValue;

@EasyValue
public abstract class Password {
    public abstract byte[] getHash();

    public abstract byte[] getSalt();

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends EasyValue_Password.Builder {
    }
}

