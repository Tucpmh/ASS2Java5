package org.example.finalass2.service;

import org.example.finalass2.entity.HoaDon;
import org.example.finalass2.entity.LichSuHoaDon;
import org.example.finalass2.repository.HoaDonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class HoaDonService {

    @Autowired
    private HoaDonRepository hoaDonRepository;

    @Autowired
    private LichSuHoaDonService lichSuHoaDonService;

    public List<HoaDon> getAll() {
        return hoaDonRepository.findAll();
    }

    public HoaDon getById(Integer id) {
        return hoaDonRepository.findById(id).orElse(null);
    }

    /**
     * Lưu hóa đơn VÀ tự động tạo lịch sử
     */
    @Transactional
    public HoaDon save(HoaDon hoaDon) {
        boolean isNew = (hoaDon.getId() == null);
        String oldStatus = null;

        // Nếu đang cập nhật, lấy trạng thái cũ
        if (!isNew) {
            HoaDon existing = hoaDonRepository.findById(hoaDon.getId()).orElse(null);
            if (existing != null) {
                oldStatus = existing.getTrangThai();
            }
        }

        // Lưu hóa đơn
        HoaDon saved = hoaDonRepository.save(hoaDon);

        // Tạo lịch sử nếu là hóa đơn mới
        if (isNew) {
            createLichSu(saved, saved.getTrangThai(), "Tạo hóa đơn", "System");
        }
        // Hoặc nếu trạng thái thay đổi
        else if (oldStatus != null && !oldStatus.equals(saved.getTrangThai())) {
            createLichSu(saved, saved.getTrangThai(),
                    "Cập nhật từ: " + oldStatus + " → " + saved.getTrangThai(),
                    "Admin");
        }

        return saved;
    }

    /**
     * Cập nhật trạng thái hóa đơn và ghi lịch sử
     */
    @Transactional
    public HoaDon updateStatus(Integer hoaDonId, String trangThaiMoi, String ghiChu, String nhanVien) {
        HoaDon hoaDon = getById(hoaDonId);
        if (hoaDon == null) {
            throw new RuntimeException("Không tìm thấy hóa đơn #" + hoaDonId);
        }

        String oldStatus = hoaDon.getTrangThai();
        hoaDon.setTrangThai(trangThaiMoi);
        HoaDon saved = hoaDonRepository.save(hoaDon);

        // Ghi lịch sử
        String ghiChuFull = (ghiChu != null ? ghiChu : "") +
                " (Từ: " + oldStatus + " → " + trangThaiMoi + ")";
        createLichSu(saved, trangThaiMoi, ghiChuFull, nhanVien);

        return saved;
    }

    /**
     * Tạo bản ghi lịch sử
     */
    private void createLichSu(HoaDon hoaDon, String trangThai, String ghiChu, String nhanVien) {
        LichSuHoaDon lichSu = new LichSuHoaDon();
        lichSu.setHoaDon(hoaDon);
        lichSu.setTrangThai(trangThai);
        lichSu.setGhiChu(ghiChu);
        lichSu.setNhanVienCapNhat(nhanVien != null ? nhanVien : "System");
        lichSu.setNgayCapNhat(LocalDateTime.now());
        lichSuHoaDonService.save(lichSu);
    }

    public void delete(Integer id) {
        hoaDonRepository.deleteById(id);
    }

    public List<HoaDon> findByLoaiBan(String loaiBan) {
        return hoaDonRepository.findByLoaiBan(loaiBan);
    }
}