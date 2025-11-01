package org.example.finalass2.controller.admin;

import org.example.finalass2.entity.HoaDon;
import org.example.finalass2.entity.HoaDonChiTiet;
import org.example.finalass2.entity.NguoiDung;
import org.example.finalass2.entity.SanPham;
import org.example.finalass2.service.HoaDonService;
import org.example.finalass2.service.HoaDonChiTietService;
import org.example.finalass2.service.NguoiDungService;
import org.example.finalass2.service.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/pos")
public class POSController {

    @Autowired
    private SanPhamService sanPhamService;

    @Autowired
    private HoaDonService hoaDonService;

    @Autowired
    private HoaDonChiTietService hoaDonChiTietService;

    @Autowired
    private NguoiDungService nguoiDungService;

    // Trang POS chính
    @GetMapping
    public String posDashboard(Model model) {
        model.addAttribute("sanPhams", sanPhamService.getAll());
        model.addAttribute("khachHangs", nguoiDungService.getAll());
        return "admin/pos";
    }

    // Thanh toán hóa đơn tại quầy
    @PostMapping("/checkout")
    @ResponseBody
    public String checkoutPos(
            @RequestParam(required = false) Integer khachHangId,
            @RequestParam(required = false) List<Integer> sanPhamIds,
            @RequestParam(required = false) List<Integer> soLuongs,
            @RequestParam(required = false) List<BigDecimal> giaBans) {

        try {
            if (sanPhamIds == null || sanPhamIds.isEmpty()) {
                return "ERROR:Giỏ hàng trống";
            }

            // Tạo hóa đơn
            HoaDon hoaDon = new HoaDon();

            // Gán khách hàng nếu có
            if (khachHangId != null && khachHangId > 0) {
                NguoiDung khachHang = nguoiDungService.getById(khachHangId);
                hoaDon.setNguoiDung(khachHang);
            }

            hoaDon.setLoaiBan("QUAY");
            hoaDon.setTrangThai("Hoàn thành"); // Bán tại quầy = hoàn thành ngay
            hoaDon.setNgayTao(LocalDateTime.now());

            BigDecimal tongTien = BigDecimal.ZERO;
            List<HoaDonChiTiet> chiTiets = new ArrayList<>();

            // Tạo chi tiết hóa đơn
            for (int i = 0; i < sanPhamIds.size(); i++) {
                SanPham sp = sanPhamService.getById(sanPhamIds.get(i));
                if (sp == null) continue;

                // Kiểm tra tồn kho
                if (sp.getTonKho() < soLuongs.get(i)) {
                    return "ERROR:Sản phẩm " + sp.getTen() + " không đủ hàng";
                }

                HoaDonChiTiet ct = new HoaDonChiTiet();
                ct.setHoaDon(hoaDon);
                ct.setSanPham(sp);
                ct.setSoLuong(soLuongs.get(i));
                ct.setDonGia(giaBans.get(i));

                tongTien = tongTien.add(
                        ct.getDonGia().multiply(new BigDecimal(ct.getSoLuong()))
                );

                chiTiets.add(ct);

                // Trừ tồn kho
                sp.setTonKho(sp.getTonKho() - soLuongs.get(i));
                sanPhamService.save(sp);
            }

            hoaDon.setTongTien(tongTien);
            hoaDon.setChiTiets(chiTiets);

            // Lưu hóa đơn (tự động tạo lịch sử trong HoaDonService)
            HoaDon savedHoaDon = hoaDonService.save(hoaDon);

            // Lưu tất cả chi tiết
            for (HoaDonChiTiet ct : chiTiets) {
                ct.setHoaDon(savedHoaDon);
                hoaDonChiTietService.save(ct);
            }

            return "SUCCESS:" + savedHoaDon.getId();

        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR:Có lỗi xảy ra: " + e.getMessage();
        }
    }

    // Trang thành công
    @GetMapping("/success/{id}")
    public String posSuccess(@PathVariable Integer id, Model model) {
        HoaDon hoaDon = hoaDonService.getById(id);
        model.addAttribute("hoaDon", hoaDon);
        return "admin/pos-success";
    }

    // Xem lịch sử hóa đơn tại quầy
    @GetMapping("/history")
    public String posHistory(Model model) {
        List<HoaDon> hoaDons = hoaDonService.findByLoaiBan("QUAY");
        model.addAttribute("hoaDons", hoaDons);
        return "admin/pos-history";
    }
}