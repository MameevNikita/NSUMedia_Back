package ru.nsu.fit.database.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.nsu.fit.database.entities.File;

public interface FileRepository extends CrudRepository<File, Long> {
}
