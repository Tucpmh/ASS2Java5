package org.example.finalass2.repository;

import org.example.finalass2.entity.GioHang;
import org.example.finalass2.entity.NguoiDung;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface GioHangTamRepository extends JpaRepository<GioHang, Integer> {


    List<GioHang> findByNguoiDung(NguoiDung user);

    List<GioHang> findByNguoiDungIsNull();

    void deleteByNguoiDung(NguoiDung user);

    void deleteByNguoiDungIsNull();
}