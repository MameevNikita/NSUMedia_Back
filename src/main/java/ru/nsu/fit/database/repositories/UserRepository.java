package ru.nsu.fit.database.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.nsu.fit.database.entities.User;

public interface UserRepository extends CrudRepository<User, Long> {

}