package net.in.dayan.springdemo.repository;

import net.in.dayan.springdemo.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Integer> {
}
