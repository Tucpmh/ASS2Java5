package org.example.finalass2.repository;

import org.example.finalass2.entity.HoaDon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface HoaDonRepository extends JpaRepository<HoaDon, Integer> {

    List<HoaDon> findByNguoiDungId(Integer nguoiDungId);

    List<HoaDon> findByLoaiBan(String loaiBan);

    @Query("SELECT h FROM HoaDon h LEFT JOIN FETCH h.chiTiets WHERE h.id = :id")
    HoaDon findByIdWithDetails(Integer id);
}