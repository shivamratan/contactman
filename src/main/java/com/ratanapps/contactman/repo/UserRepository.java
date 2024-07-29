package com.ratanapps.contactman.repo;

import com.ratanapps.contactman.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "Select * FROM USER where email = :email", nativeQuery = true)
    public User getUserByUserName(String email);


}
