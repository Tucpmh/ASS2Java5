package org.example.finalass2.service;

import org.example.finalass2.entity.VaiTro;
import org.example.finalass2.repository.VaiTroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class VaiTroService {
    @Autowired
    private VaiTroRepository vaiTroRepository;

    public List<VaiTro> getAll() { return vaiTroRepository.findAll(); }
    public VaiTro getById(Integer id) { return vaiTroRepository.findById(id).orElse(null); }
    public VaiTro save(VaiTro vt) { return vaiTroRepository.save(vt); }
    public void delete(Integer id) { vaiTroRepository.deleteById(id); }
}
