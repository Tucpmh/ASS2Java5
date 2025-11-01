package org.example.finalass2.controller.admin;

import org.example.finalass2.entity.GioHang;
import org.example.finalass2.service.GioHangTamService;
import org.example.finalass2.service.NguoiDungService;
import org.example.finalass2.service.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/gio-hang-tam")
public class GioHangController {

    @Autowired
    private GioHangTamService service;

    @Autowired
    private SanPhamService sanPhamService;

    @Autowired
    private NguoiDungService nguoiDungService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("list", service.getAll());
        return "admin/giohangtam-list";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("gioHang", new GioHang());
        model.addAttribute("sanPhams", sanPhamService.getAll());
        model.addAttribute("nguoiDungs", nguoiDungService.getAll());
        return "admin/giohangtam-list";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("gioHang") GioHang gioHang) {
        service.save(gioHang);
        return "redirect:/admin/gio-hang-tam";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("gioHang", service.getById(id));
        model.addAttribute("sanPhams", sanPhamService.getAll());
        model.addAttribute("nguoiDungs", nguoiDungService.getAll());
        return "admin/giohangtam-list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        service.delete(id);
        return "redirect:/admin/gio-hang-tam";
    }
}
