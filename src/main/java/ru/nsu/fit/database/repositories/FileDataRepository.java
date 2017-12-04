package ru.nsu.fit.database.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.nsu.fit.database.entities.File;
import ru.nsu.fit.database.entities.FileData;

public interface FileDataRepository extends CrudRepository<FileData, Long> {
    FileData findByFile(File file);
}
