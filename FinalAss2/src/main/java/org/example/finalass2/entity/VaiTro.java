package org.example.finalass2.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vai_tro")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VaiTro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ten", length = 30)
    private String ten;

    @Column(name = "mo_ta", length = 100)
    private String moTa;

    @Column(name = "muc_do")
    private Integer mucDo;

    @Column(name = "ngay_tao")
    private LocalDateTime ngayTao;

    @ManyToMany(mappedBy = "vaiTros")
    private List<NguoiDung> nguoiDungs = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        ngayTao = LocalDateTime.now();
    }
}