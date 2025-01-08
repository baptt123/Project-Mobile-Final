package com.example.demo_app_chat.repository;

import com.example.demo_app_chat.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, Integer> {
    User findByUsernameAndPassword(String username, String password);

    Optional<Object> findById(String id);

    User findByUsername(String userName);

    User findByEmail(String email);

    List<User> findByStatus(int status);
//    @Query("SELECT u FROM User u WHERE u.id != :userId AND u.id NOT IN (SELECT f.id FROM User u2 JOIN u2.friends f WHERE u2.id = :userId)")
//    List<User> findSuggestedFriends(@Param("userId") String userId);
    @Query("SELECT u FROM User u LEFT JOIN u.followers f ON f.id = :userId WHERE u.id != :userId AND f.id IS NULL")
    List<User> findSuggestedFriends(@Param("userId") String userId);

}
