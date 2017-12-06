package ru.nsu.fit.database.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.nsu.fit.database.entities.File;
import ru.nsu.fit.database.entities.Folder;

public interface FileRepository extends CrudRepository<File, Long> {
    File findById(int id);
    File[] findAllByParentFolder(Folder parentFolder);
}
