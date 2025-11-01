package org.example.finalass2.controller.admin;

import org.example.finalass2.entity.HoaDon;
import org.example.finalass2.entity.LichSuHoaDon;
import org.example.finalass2.service.HoaDonService;
import org.example.finalass2.service.LichSuHoaDonService;
import org.example.finalass2.service.NguoiDungService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/hoa-don")
public class HoaDonController {

    @Autowired
    private HoaDonService hoaDonService;

    @Autowired
    private NguoiDungService nguoiDungService;

    @Autowired
    private LichSuHoaDonService lichSuHoaDonService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("list", hoaDonService.getAll());
        return "admin/hoadon-list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("hoaDon", new HoaDon());
        model.addAttribute("nguoiDungs", nguoiDungService.getAll());
        return "admin/hoadon-list";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("hoaDon") HoaDon hoaDon) {
        hoaDonService.save(hoaDon);
        return "redirect:/admin/hoa-don";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("hoaDon", hoaDonService.getById(id));
        model.addAttribute("nguoiDungs", nguoiDungService.getAll());
        return "admin/hoadon-list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        hoaDonService.delete(id);
        return "redirect:/admin/hoa-don";
    }

    /**
     * XEM TIMELINE CỦA HÓA ĐƠN
     */
    @GetMapping("/timeline/{id}")
    public String viewTimeline(@PathVariable Integer id, Model model) {
        HoaDon hoaDon = hoaDonService.getById(id);
        if (hoaDon == null) {
            return "redirect:/admin/hoa-don";
        }

        List<LichSuHoaDon> timeline = lichSuHoaDonService.getTimelineByHoaDonId(id);

        model.addAttribute("hoaDon", hoaDon);
        model.addAttribute("timeline", timeline);
        return "admin/hoadon-timeline";
    }

    /**
     * CẬP NHẬT TRẠNG THÁI VÀ GHI LỊCH SỬ
     */
    @PostMapping("/update-status/{id}")
    public String updateStatus(
            @PathVariable Integer id,
            @RequestParam String trangThai,
            @RequestParam(required = false) String ghiChu,
            RedirectAttributes redirectAttributes) {

        try {
            hoaDonService.updateStatus(id, trangThai, ghiChu, "Admin");
            redirectAttributes.addFlashAttribute("success", "Cập nhật trạng thái thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/admin/hoa-don/timeline/" + id;
    }
}