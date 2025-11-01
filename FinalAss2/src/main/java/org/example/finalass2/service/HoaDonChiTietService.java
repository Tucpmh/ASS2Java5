package org.example.finalass2.service;

import org.example.finalass2.entity.HoaDonChiTiet;
import org.example.finalass2.repository.HoaDonChiTietRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HoaDonChiTietService {
    @Autowired
    private HoaDonChiTietRepository repo;

    public List<HoaDonChiTiet> getAll() { return repo.findAll(); }
    public HoaDonChiTiet getById(Integer id) { return repo.findById(id).orElse(null); }
    public HoaDonChiTiet save(HoaDonChiTiet hdct) { return repo.save(hdct); }
    public void delete(Integer id) { repo.deleteById(id); }
}
