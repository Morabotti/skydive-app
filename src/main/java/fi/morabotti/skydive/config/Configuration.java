package fi.morabotti.skydive.config;

import fi.jubic.easyconfig.annotations.ConfigProperty;
import fi.jubic.easyconfig.extensions.DbUnitExtension;
import fi.jubic.easyconfig.extensions.LiquibaseExtension;
import fi.jubic.easyconfig.jooq.JooqConfiguration;
import fi.jubic.easyconfig.snoozy.SnoozyServerConfiguration;
import fi.jubic.snoozy.ServerConfiguration;
import fi.jubic.snoozy.ServerConfigurator;

import javax.inject.Singleton;

@Singleton
public class Configuration implements ServerConfigurator {
    private final String deploymentEnvironment;
    private final ServerConfiguration serverConfiguration;
    private final JooqConfiguration jooqConfiguration;

    public Configuration(
            @ConfigProperty("DEPLOYMENT_ENVIRONMENT") String deploymentEnvironment,
            @ConfigProperty("SERVER_") SnoozyServerConfiguration serverConfiguration,
            @ConfigProperty("")
            @LiquibaseExtension(migrations = "migrations.xml")
            @DbUnitExtension(dataset = "dataset.xml", dtd = "dataset.dtd")
                    JooqConfiguration jooqConfiguration
    ) {
        this.deploymentEnvironment = deploymentEnvironment;
        this.serverConfiguration = serverConfiguration;
        this.jooqConfiguration = jooqConfiguration;
    }

    public boolean isDevelopmentEnv() {
        return !deploymentEnvironment.equalsIgnoreCase("production");
    }

    @Override
    public ServerConfiguration getServerConfiguration() {
        return this.serverConfiguration;
    }

    public JooqConfiguration getJooqConfiguration() {
        return this.jooqConfiguration;
    }
}
