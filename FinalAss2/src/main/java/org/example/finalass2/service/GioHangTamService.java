package org.example.finalass2.service;

import org.example.finalass2.entity.*;
import org.example.finalass2.repository.GioHangTamRepository;
import org.example.finalass2.repository.SanPhamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GioHangTamService {

    @Autowired private GioHangTamRepository repo;
    @Autowired private SanPhamRepository spRepo;

    public void addToCart(Integer sanPhamId, NguoiDung user) {
        SanPham sp = spRepo.findById(sanPhamId).orElse(null);
        if (sp == null) return;

        GioHang cart = new GioHang();
        cart.setSanPham(sp);
        cart.setSoLuong(1);
        cart.setNguoiDung(user);
        repo.save(cart);
    }

    public List<GioHang> getCart(NguoiDung user) {
        if (user == null) return repo.findByNguoiDungIsNull();
        return repo.findByNguoiDung(user);
    }

    @Transactional
    public void clearCart(NguoiDung user) {
        if (user == null) repo.deleteByNguoiDungIsNull();
        else repo.deleteByNguoiDung(user);
    }

    public List<GioHang> getAll() {
        return repo.findAll();
    }

    public void save(GioHang gioHang) {
        repo.save(gioHang);
    }

    public GioHang getById(Integer id) {
        return repo.findById(id).orElse(null);
    }

    public void delete(Integer id) {
        repo.deleteById(id);
    }
}