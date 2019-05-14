package ru.test.avito.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.test.avito.domain.TestEntity;

public interface TestRepository extends JpaRepository<TestEntity, Long> {
}
