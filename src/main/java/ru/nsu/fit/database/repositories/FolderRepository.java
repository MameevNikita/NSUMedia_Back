package ru.nsu.fit.database.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.nsu.fit.database.entities.Folder;

public interface FolderRepository extends CrudRepository<Folder, Long> {
}
