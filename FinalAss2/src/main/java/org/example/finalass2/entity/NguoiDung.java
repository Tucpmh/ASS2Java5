package org.example.finalass2.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "nguoi_dung")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NguoiDung {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ten", length = 50)
    private String ten;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "mat_khau", length = 255)
    private String matKhau;

    @Column(name = "sdt", length = 15)
    private String sdt;

    @Column(name = "dia_chi", length = 255)
    private String diaChi;

    @Column(name = "ngay_tao")
    private LocalDateTime ngayTao;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "nguoi_dung_vai_tro",
            joinColumns = @JoinColumn(name = "nguoi_dung_id"),
            inverseJoinColumns = @JoinColumn(name = "vai_tro_id")
    )
    private List<VaiTro> vaiTros = new ArrayList<>();

    @OneToMany(mappedBy = "nguoiDung")
    private List<HoaDon> hoaDons = new ArrayList<>();

    @OneToMany(mappedBy = "nguoiDung")
    private List<GioHang> gioHangs = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        ngayTao = LocalDateTime.now();
    }
}