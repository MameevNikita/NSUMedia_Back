package ru.nsu.fit.database.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.nsu.fit.database.entities.TestEntity;

public interface TestRepository extends CrudRepository<TestEntity, Long> {

}
