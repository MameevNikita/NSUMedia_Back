package ru.nsu.fit.database.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.nsu.fit.database.entities.User;
import ru.nsu.fit.database.types.Role;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByLogin(String login);
    User findById(int id);
    User[] findAllByRole(Role role);
}