package com.arvin.springbootjwt.repository;

import com.arvin.springbootjwt.model.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<Users, Integer> {
     Users findByEmail(String email);
}
