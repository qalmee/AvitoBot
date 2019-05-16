package ru.test.avito.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.test.avito.domain.AdvertInProgress;

public interface AdvertInProgressRepository extends JpaRepository<AdvertInProgress, Long> {
    AdvertInProgress getByHostId(Integer hostId);

    Boolean existsByHostId(Integer hostId);

    void deleteByHostId(Integer hostId);
}
