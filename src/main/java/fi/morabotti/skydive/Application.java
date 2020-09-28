package fi.morabotti.skydive;

import fi.jubic.snoozy.AuthenticatedApplication;
import fi.jubic.snoozy.MethodAccess;
import fi.jubic.snoozy.Snoozy;
import fi.jubic.snoozy.StaticFiles;
import fi.jubic.snoozy.auth.Authentication;
import fi.jubic.snoozy.filters.UrlRewrite;
import fi.jubic.snoozy.undertow.UndertowServer;
import fi.morabotti.skydive.config.Configuration;
import fi.morabotti.skydive.exception.mapper.ApplicationExceptionMapper;
import fi.morabotti.skydive.exception.mapper.JsonMappingExceptionMapper;
import fi.morabotti.skydive.model.Account;

import javax.inject.Inject;
import javax.ws.rs.ApplicationPath;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ApplicationPath("api")
public class Application implements AuthenticatedApplication<Account> {
    @Inject
    Configuration configuration;

    @Inject
    Set<Object> resources;

    @Inject
    Authentication<Account> authentication;

    @Inject
    Application() {

    }

    @Override
    public Set<StaticFiles> getStaticFiles() {
        return Collections.singleton(
                StaticFiles.builder()
                        .setPrefix("static")
                        .setClassLoader(Application.class.getClassLoader())
                        .setMethodAccess(MethodAccess.anonymous())
                        .setRewrite(
                                UrlRewrite.of(
                                        "^\\/(?!(((api|assets|locales).*)|.*\\.(html|js|txt)$)).*$",
                                        "/index.html"
                                )
                        )
                        .build()
        );
    }

    @Override
    public Set<Object> getSingletons() {
        Set<Object> singletons = Stream.concat(
                resources.stream(),
                Snoozy.builtins().stream()
        ).collect(Collectors.toSet());

        singletons.add(new JsonMappingExceptionMapper(configuration.isDevelopmentEnv()));
        singletons.add(new ApplicationExceptionMapper());

        return singletons;
    }

    @Override
    public Authentication<Account> getAuthentication() {
        return authentication;
    }

    public Configuration getConfiguration() {
        return this.configuration;
    }

    public static void main(String[] args) {
        Application app = DaggerAppComponent.create().getApplication();
        Configuration configuration = app.getConfiguration();

        new UndertowServer().start(app, configuration);
    }
}
