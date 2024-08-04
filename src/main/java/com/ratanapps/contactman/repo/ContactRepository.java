package com.ratanapps.contactman.repo;

import com.ratanapps.contactman.entity.Contact;
import com.ratanapps.contactman.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {

    @Query(value = "Select c FROM Contact c where c.user= :user")
    List<Contact> getContactByUser(@Param("user") User user);

    @Query(value = "Select c FROM Contact c where c.user.id= :userId")
    Page<Contact> getContactByUserId(@Param("userId") Long userId, Pageable pageable);
}
