package fi.morabotti.skydive;

import dagger.Component;
import dagger.Module;
import dagger.Provides;
import fi.jubic.easyconfig.ConfigMapper;
import fi.jubic.easyutils.transactional.TransactionProvider;
import fi.jubic.snoozy.auth.Authentication;
import fi.jubic.snoozy.auth.Authenticator;
import fi.jubic.snoozy.auth.implementation.DefaultAuthorizer;
import fi.jubic.snoozy.auth.implementation.HeaderParser;
import fi.morabotti.skydive.config.Configuration;
import fi.morabotti.skydive.controller.AccountController;
import fi.morabotti.skydive.model.Account;
import fi.morabotti.skydive.resources.ResourceModule;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import javax.inject.Singleton;
import java.util.function.Function;

@Singleton
@Component(modules = {
        AppComponent.AppModule.class,
        ResourceModule.class
})
public interface AppComponent {
    Application getApplication();

    @Module
    class AppModule {
        @Provides
        @Singleton
        static Configuration provideConfiguration() {
            return new ConfigMapper().read(Configuration.class);
        }

        @Provides
        @Singleton
        static TransactionProvider<DSLContext> provideTransactionProvider(
                Configuration configuration
        ) {
            DSLContext context = DSL.using(configuration.getJooqConfiguration().getConfiguration());
            return new TransactionProvider<DSLContext>() {
                @Override
                public <T> T runWithTransaction(Function<DSLContext, T> procedure) {
                    return context
                            .transactionResult(
                                transaction -> procedure.apply(DSL.using(transaction))
                            );
                }

                @Override
                public <T> T runWithoutTransaction(Function<DSLContext, T> function) {
                    return null;
                }
            };
        }

        @Provides
        @Singleton
        static Authenticator<Account> provideAuthenticator(AccountController accountController) {
            return accountController::authenticate;
        }

        @Provides
        @Singleton
        public Authentication<Account> provideAuthentication(
                Authenticator<Account> authenticator
        ) {
            return Authentication.<Account>builder()
                    .setAuthenticator(authenticator)
                    .setAuthorizer(new DefaultAuthorizer<>())
                    .setTokenParser(HeaderParser.of("Authorization"))
                    .setUserClass(Account.class)
                    .build();
        }
    }
}
