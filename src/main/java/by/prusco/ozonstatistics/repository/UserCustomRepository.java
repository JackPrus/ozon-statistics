package by.prusco.ozonstatistics.repository;

import by.prusco.ozonstatistics.entity.UserCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCustomRepository extends JpaRepository<UserCustom, Long> {
    Optional<UserCustom> findUserCustomByLogon(String login);
    Optional<UserCustom> findUserCustomByEmail(String email);
}
