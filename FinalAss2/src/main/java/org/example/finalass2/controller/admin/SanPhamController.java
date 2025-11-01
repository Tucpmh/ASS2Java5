package org.example.finalass2.controller.customer;

import org.example.finalass2.entity.SanPham;
import org.example.finalass2.service.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/san-pham")
public class SanPhamController {

    @Autowired
    private SanPhamService sanPhamService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("list", sanPhamService.getAll());
        return "admin/sanpham-list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("sp", new SanPham());
        return "admin/sanpham-list";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("sp") SanPham sanPham) {
        sanPhamService.save(sanPham);
        return "redirect:/admin/san-pham";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Integer id, Model model) {
        model.addAttribute("sp", sanPhamService.getById(id));
        return "admin/sanpham-list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        sanPhamService.delete(id);
        return "redirect:/admin/san-pham";
    }
}
