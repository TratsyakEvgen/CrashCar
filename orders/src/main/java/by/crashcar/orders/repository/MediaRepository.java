package by.crashcar.orders.repository;

import by.crashcar.orders.entity.Media;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MediaRepository extends JpaRepository<Media, Long> {
    Optional<Media> findByIdAndSecret(long id, String secret);
}