package com.rentwise.user.repository;

import com.rentwise.user.model.User;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Override
    <S extends User> S save(S entity);

    List<User> findAll();

    @Override
    <S extends User> List<S> findAll(Example<S> example);

    @Override
    Optional<User> findById(Long id);

    @Override
    void deleteById(Long id);
}
