package fi.morabotti.skydive.resources;

import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Module
public class ResourceModule {
    @Provides
    @Singleton
    static Set<Object> provideResources(
            AuthenticationResource authenticationResource,
            ClubResource clubResource,
            ActivityResource activityResource
    ) {
        return Stream.of(
                authenticationResource,
                clubResource,
                activityResource
        )
                .collect(Collectors.toSet());
    }
}
