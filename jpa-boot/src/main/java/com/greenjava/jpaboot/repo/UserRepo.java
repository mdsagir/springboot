package com.greenjava.jpaboot.repo;

import com.greenjava.jpaboot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Arrays;
import java.util.List;

public interface UserRepo extends JpaRepository<User, Integer> {


    List<User> findDistinctNameByNameInAndPasswordInAndStatus(List<String> names, List<String> passwords, boolean status);

    @Query(value = "select distinct u.name ,u.email  from User u where " +
            "u.name in (:name) and u.email in (:email)")
    List<Object[]> getQauryData(@Param("name") List<String> names,
                                @Param("email") List<String> emails);
}
