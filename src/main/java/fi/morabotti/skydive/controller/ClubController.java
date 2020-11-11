package fi.morabotti.skydive.controller;

import fi.morabotti.skydive.domain.ClubDomain;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ClubController {
    private final ClubDomain clubDomain;

    @Inject
    public ClubController(
            ClubDomain clubDomain
    ) {
        this.clubDomain = clubDomain;
    }
}
