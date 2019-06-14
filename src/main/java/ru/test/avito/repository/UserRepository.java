package ru.test.avito.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.test.avito.domain.UserEntity;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUserId(Integer userId);

    Boolean existsByUserId(Integer userId);

    List<UserEntity> findBySavedAdvertIdsContaining(Long advertId);
}
