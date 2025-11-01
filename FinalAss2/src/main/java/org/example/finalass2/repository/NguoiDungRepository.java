package org.example.finalass2.repository;

import org.example.finalass2.entity.NguoiDung;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface NguoiDungRepository extends JpaRepository<NguoiDung, Integer> {

    Optional<NguoiDung> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("SELECT n FROM NguoiDung n JOIN FETCH n.vaiTros WHERE n.email = :email")
    Optional<NguoiDung> findByEmailWithRoles(String email);

}