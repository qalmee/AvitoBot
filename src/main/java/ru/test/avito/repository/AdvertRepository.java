package ru.test.avito.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.test.avito.domain.Advert;
import ru.test.avito.domain.UserEntity;

import java.util.List;
import java.util.Optional;

public interface AdvertRepository extends JpaRepository<Advert, Long> {
    Optional<Advert> findByHost(UserEntity host);

    List<Advert> findAllByHost(UserEntity host);
}
