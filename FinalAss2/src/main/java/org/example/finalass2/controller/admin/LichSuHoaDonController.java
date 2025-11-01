package org.example.finalass2.controller.admin;

import org.example.finalass2.entity.LichSuHoaDon;
import org.example.finalass2.service.HoaDonService;
import org.example.finalass2.service.LichSuHoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/lich-su-hoa-don")
public class LichSuHoaDonController {

    @Autowired
    private LichSuHoaDonService lichSuService;

    @Autowired
    private HoaDonService hoaDonService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("list", lichSuService.getAll());
        return "admin/lichsu-list";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("lichSu", new LichSuHoaDon());
        model.addAttribute("hoaDons", hoaDonService.getAll());
        return "admin/lichsu-list";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("lichSu") LichSuHoaDon lichSu) {
        lichSuService.save(lichSu);
        return "redirect:/admin/lich-su-hoa-don";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("lichSu", lichSuService.getById(id));
        model.addAttribute("hoaDons", hoaDonService.getAll());
        return "admin/lichsu-list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        lichSuService.delete(id);
        return "redirect:/admin/lich-su-hoa-don";
    }
}
