package org.example.finalass2.controller.admin;

import org.example.finalass2.entity.NguoiDung;
import org.example.finalass2.service.NguoiDungService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/nguoi-dung")
public class NguoiDungController {

    @Autowired
    private NguoiDungService nguoiDungService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("list", nguoiDungService.getAll());
        return "admin/nguoidung-list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("nd", new NguoiDung());
        return "admin/nguoidung-list";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("nd") NguoiDung nd) {
        nguoiDungService.save(nd);
        return "redirect:/admin/nguoi-dung";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Integer id, Model model) {
        model.addAttribute("nd", nguoiDungService.getById(id));
        return "admin/nguoidung-list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        nguoiDungService.delete(id);
        return "redirect:/admin/nguoi-dung";
    }
}
