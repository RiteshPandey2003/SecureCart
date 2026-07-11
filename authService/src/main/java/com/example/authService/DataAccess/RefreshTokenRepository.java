package com.example.authService.DataAccess;

import com.example.authService.entities.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {

    RefreshToken findByUserId(Integer userId);
}
