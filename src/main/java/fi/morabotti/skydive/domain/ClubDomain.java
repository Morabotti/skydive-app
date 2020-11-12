package fi.morabotti.skydive.domain;

import fi.morabotti.skydive.model.Account;
import fi.morabotti.skydive.model.Club;
import fi.morabotti.skydive.model.ClubProfile;
import fi.morabotti.skydive.view.club.ClubCreationRequest;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ClubDomain {

    @Inject
    public ClubDomain() {
    }

    public Club createClub(
            ClubCreationRequest creationRequest,
            Account account
    ) {
        return Club.builder()
                .setId(0L)
                .setIsPublic(creationRequest.getIsPublic())
                .setName(creationRequest.getName())
                .setSlug(creationRequest.getSlug())
                .setCreatorAccount(account)
                .setDeletedAt(null)
                .build();
    }

    public ClubProfile createClubProfile(
            Club club,
            ClubCreationRequest creationRequest
    ) {
        return ClubProfile.builder()
                .setId(0L)
                .setDescription(creationRequest.getDescription())
                .setAddress(creationRequest.getAddress())
                .setCity(creationRequest.getCity())
                .setClub(club)
                .setPhone(creationRequest.getPhone())
                .setZipCode(creationRequest.getZipCode())
                .setDeletedAt(null)
                .build();
    }
}
