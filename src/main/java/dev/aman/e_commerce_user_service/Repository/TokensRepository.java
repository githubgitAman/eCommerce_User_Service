package dev.aman.e_commerce_user_service.Repository;

import dev.aman.e_commerce_user_service.Models.Tokens;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokensRepository extends JpaRepository<Tokens, Long> {
}
