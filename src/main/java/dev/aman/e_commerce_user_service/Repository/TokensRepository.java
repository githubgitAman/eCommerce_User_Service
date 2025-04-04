package dev.aman.e_commerce_user_service.Repository;

import dev.aman.e_commerce_user_service.Models.Tokens;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface TokensRepository extends JpaRepository<Tokens, Long> {

    //Here we are checking 3 values i.e if token value is present, it is not marked as deleted and it is not expired
    Optional<Tokens> findByValueAndDeletedAndExpiryAtGreaterThan(String value, Boolean deleted, Date expiryAt);
}
