package com.cg.dealscoupan.userservice.service;

import com.cg.dealscoupan.userservice.dao.UserProfileRepository;
import com.cg.dealscoupan.userservice.entity.UserProfile;
import lombok.AllArgsConstructor;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;

    public UserProfile addNewUserProfile(UserProfile userProfile) {
        userProfile.setProfileId(getNextProfileId());
        return userProfileRepository.save(userProfile);
    }

    public UserProfile login(Map<String, String> userProfile) {
        return userProfileRepository.findByEmailAndPassword(userProfile.get("userName"), userProfile.get("password"))
                .orElse(UserProfile.builder().build());
    }
    public List<UserProfile> getAllUserProfiles() {
        return userProfileRepository.findAll();
    }
    public UserProfile getByProfileId(int profileId) {
        return userProfileRepository.findByProfileId(profileId).orElse(UserProfile.builder().build());
    }

    public UserProfile getByMobileNumber(long mobileNumber) {
        return userProfileRepository.findByMobileNumber(mobileNumber).orElse(UserProfile.builder().build());
    }

    public UserProfile getByUserName(String fullName) {
        return userProfileRepository.findByFullName(fullName).orElse(UserProfile.builder().build());
    }

    public void updateUserProfile(UserProfile userProfile) {
        userProfileRepository.save(userProfile);
    }

    public void deleteUserProfile(int profileId) {
        Optional<UserProfile> byProfileId = userProfileRepository.findByProfileId(profileId);
        if (byProfileId.isPresent()) {
            userProfileRepository.delete(byProfileId.get());
        }
    }

    @Synchronized
    public int getNextProfileId() {
        UserProfile userProfile = userProfileRepository.findTopByOrderByProfileIdDesc();
        int profileId = (userProfile ==null || userProfile.getProfileId() <= 0) ? 0 : userProfile.getProfileId();
        return ++profileId;
    }
}
