package com.cg.dealscoupan.userservice.dao;

import com.cg.dealscoupan.userservice.entity.UserProfile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfileRepository extends MongoRepository<UserProfile, String> {

    Optional<UserProfile> findByFullName(String fullName);
    Optional<UserProfile> findByProfileId(int profileId);

    Optional<UserProfile> findByMobileNumber(long mobileNumber);

    Optional<UserProfile> findByEmailAndPassword(String email, String password);

    UserProfile findTopByOrderByProfileIdDesc();

}
