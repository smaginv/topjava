package ru.javawebinar.topjava;

import org.springframework.lang.NonNull;
import org.springframework.test.context.ActiveProfilesResolver;

import static ru.javawebinar.topjava.Profiles.getActiveDbProfile;

public class ActiveDbProfile {

    //http://stackoverflow.com/questions/23871255/spring-profiles-simple-example-of-activeprofilesresolver
    public static class ActiveDbProfileResolver implements ActiveProfilesResolver {
        @Override
        public @NonNull String[] resolve(@NonNull Class<?> aClass) {
            return new String[]{getActiveDbProfile()};
        }
    }
}
