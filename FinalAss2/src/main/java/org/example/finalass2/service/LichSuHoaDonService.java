package org.example.finalass2.service;

import org.example.finalass2.entity.LichSuHoaDon;
import org.example.finalass2.repository.LichSuHoaDonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LichSuHoaDonService {

    @Autowired
    private LichSuHoaDonRepository repository;

    public List<LichSuHoaDon> getAll() {
        return repository.findAll();
    }

    public LichSuHoaDon getById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public LichSuHoaDon save(LichSuHoaDon entity) {
        return repository.save(entity);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }

    /**
     * Lấy timeline của một hóa đơn (sắp xếp từ mới → cũ)
     */
    public List<LichSuHoaDon> getTimelineByHoaDonId(Integer hoaDonId) {
        return repository.findByHoaDonIdOrderByNgayCapNhatDesc(hoaDonId);
    }
}