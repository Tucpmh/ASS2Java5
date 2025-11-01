package org.example.finalass2.service;

import org.example.finalass2.entity.NguoiDung;
import org.example.finalass2.repository.NguoiDungRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NguoiDungService {

    @Autowired
    private NguoiDungRepository nguoiDungRepository;

    public List<NguoiDung> getAll() {
        return nguoiDungRepository.findAll();
    }

    public NguoiDung getById(Integer id) {
        return nguoiDungRepository.findById(id).orElse(null);
    }

    public NguoiDung save(NguoiDung nd) {
        return nguoiDungRepository.save(nd);
    }

    public void delete(Integer id) {
        nguoiDungRepository.deleteById(id);
    }

    public NguoiDung findByEmail(String email) {
        return nguoiDungRepository.findByEmail(email).orElse(null);
    }
}
