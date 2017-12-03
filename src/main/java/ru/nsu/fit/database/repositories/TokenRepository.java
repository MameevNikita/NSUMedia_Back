package ru.nsu.fit.database.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.nsu.fit.database.entities.Token;

public interface TokenRepository extends CrudRepository<Token, Long> {
}
