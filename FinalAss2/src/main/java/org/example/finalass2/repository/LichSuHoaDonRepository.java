package org.example.finalass2.repository;

import org.example.finalass2.entity.LichSuHoaDon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LichSuHoaDonRepository extends JpaRepository<LichSuHoaDon, Integer> {
    List<LichSuHoaDon> findByHoaDonIdOrderByNgayCapNhatDesc(Integer hoaDonId);
}