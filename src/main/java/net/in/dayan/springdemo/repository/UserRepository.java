package net.in.dayan.springdemo.repository;

import net.in.dayan.springdemo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    public User findByLogin(String username);
}
