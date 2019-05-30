package ru.test.avito.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.test.avito.domain.Advert;
import ru.test.avito.domain.UserEntity;

import java.util.List;

public interface AdvertRepository extends JpaRepository<Advert, Long> {
    Advert findByHost(UserEntity host);

    List<Advert> findAllByHost(UserEntity host);

    Advert findByHostAndId(UserEntity host, Long id);
}
