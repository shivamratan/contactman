package com.ratanapps.contactman.repo;

import com.ratanapps.contactman.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "Select u FROM User u where u.email= :email")
    public User getUserByUserName(@Param("email") String email);


}
