package ru.test.avito.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.test.avito.domain.AdvertInProgress;
import ru.test.avito.domain.UserEntity;

public interface AdvertInProgressRepository extends JpaRepository<AdvertInProgress, Long> {

    AdvertInProgress getByHost(UserEntity host);

    Boolean existsByHost(UserEntity host);

    void deleteByHost(UserEntity host);
}
