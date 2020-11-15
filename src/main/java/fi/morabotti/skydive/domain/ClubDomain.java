package fi.morabotti.skydive.domain;

import fi.morabotti.skydive.model.Club;
import fi.morabotti.skydive.model.ClubProfile;
import fi.morabotti.skydive.view.club.ClubInformationRequest;
import fi.morabotti.skydive.view.club.ClubView;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collections;

@Singleton
public class ClubDomain {

    @Inject
    public ClubDomain() {
    }

    public Club updateClub(
            Club club,
            ClubInformationRequest informationRequest
    ) {
        return club.toBuilder()
                .setIsPublic(informationRequest.getIsPublic())
                .setName(informationRequest.getName())
                .setSlug(informationRequest.getSlug())
                .build();
    }

    public Club viewToClub(
            ClubView clubView
    ) {
        return Club.builder()
                .setId(clubView.getId())
                .setSlug(clubView.getSlug())
                .setName(clubView.getName())
                .setIsPublic(clubView.getIsPublic())
                .setClubProfiles(Collections.emptyList())
                .setDeletedAt(clubView.getDeletedAt())
                .build();
    }

    public ClubProfile updateClubProfile(
            ClubProfile clubProfile,
            ClubInformationRequest informationRequest
    ) {
        return clubProfile.toBuilder()
                .setDescription(informationRequest.getDescription())
                .setAddress(informationRequest.getAddress())
                .setCity(informationRequest.getCity())
                .setPhone(informationRequest.getPhone())
                .setZipCode(informationRequest.getZipCode())
                .build();
    }

    public Club createClub(ClubInformationRequest informationRequest) {
        return Club.builder()
                .setId(0L)
                .setIsPublic(informationRequest.getIsPublic())
                .setName(informationRequest.getName())
                .setSlug(informationRequest.getSlug())
                .setDeletedAt(null)
                .build();
    }

    public ClubProfile createClubProfile(
            Club club,
            ClubInformationRequest informationRequest
    ) {
        return ClubProfile.builder()
                .setId(0L)
                .setDescription(informationRequest.getDescription())
                .setAddress(informationRequest.getAddress())
                .setCity(informationRequest.getCity())
                .setClub(club)
                .setPhone(informationRequest.getPhone())
                .setZipCode(informationRequest.getZipCode())
                .setDeletedAt(null)
                .build();
    }
}
