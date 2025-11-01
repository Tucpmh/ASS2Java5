package org.example.finalass2.controller.admin;

import org.example.finalass2.entity.VaiTro;
import org.example.finalass2.service.VaiTroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/vai-tro")
public class VaiTroController {

    @Autowired
    private VaiTroService vaiTroService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("list", vaiTroService.getAll());
        return "admin/vaitro-list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("vt", new VaiTro());
        return "admin/vaitro-list";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("vt") VaiTro vt) {
        vaiTroService.save(vt);
        return "redirect:/admin/vai-tro";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Integer id, Model model) {
        model.addAttribute("vt", vaiTroService.getById(id));
        return "admin/vaitro-list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        vaiTroService.delete(id);
        return "redirect:/admin/vai-tro";
    }
}
