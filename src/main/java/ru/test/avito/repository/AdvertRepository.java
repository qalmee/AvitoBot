package ru.test.avito.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.test.avito.domain.Advert;

import java.util.Optional;

public interface AdvertRepository extends JpaRepository<Advert, Long> {
    Optional<Advert> findByHostId(Long hostId);
}
