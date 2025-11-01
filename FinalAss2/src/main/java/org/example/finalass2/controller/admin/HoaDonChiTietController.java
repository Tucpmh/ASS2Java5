package org.example.finalass2.controller.admin;

import org.example.finalass2.entity.HoaDonChiTiet;
import org.example.finalass2.service.HoaDonChiTietService;
import org.example.finalass2.service.HoaDonService;
import org.example.finalass2.service.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/hoa-don-chi-tiet")
public class HoaDonChiTietController {

    @Autowired
    private HoaDonChiTietService service;

    @Autowired
    private HoaDonService hoaDonService;

    @Autowired
    private SanPhamService sanPhamService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("list", service.getAll());
        return "admin/hoadonchitiet-list";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("ct", new HoaDonChiTiet());
        model.addAttribute("hoaDons", hoaDonService.getAll());
        model.addAttribute("sanPhams", sanPhamService.getAll());
        return "admin/hoadonchitiet-list";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("ct") HoaDonChiTiet ct) {
        service.save(ct);
        return "redirect:/admin/hoa-don-chi-tiet";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("ct", service.getById(id));
        model.addAttribute("hoaDons", hoaDonService.getAll());
        model.addAttribute("sanPhams", sanPhamService.getAll());
        return "admin/hoadonchitiet-list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        service.delete(id);
        return "redirect:/admin/hoa-don-chi-tiet";
    }
}
