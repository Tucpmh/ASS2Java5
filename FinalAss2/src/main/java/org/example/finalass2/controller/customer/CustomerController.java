package org.example.finalass2.controller.customer;

import org.example.finalass2.entity.GioHang;
import org.example.finalass2.entity.HoaDon;
import org.example.finalass2.entity.HoaDonChiTiet;
import org.example.finalass2.entity.NguoiDung;
import org.example.finalass2.service.GioHangTamService;
import org.example.finalass2.service.HoaDonService;
import org.example.finalass2.service.HoaDonChiTietService;
import org.example.finalass2.service.NguoiDungService;
import org.example.finalass2.service.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired private SanPhamService sanPhamService;
    @Autowired private GioHangTamService gioHangTamService;
    @Autowired private HoaDonService hoaDonService;
    @Autowired private HoaDonChiTietService hoaDonChiTietService;
    @Autowired private NguoiDungService nguoiDungService;

    // Lấy user từ authentication
    private NguoiDung getUser(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()
                && !authentication.getName().equals("anonymousUser")) {
            return nguoiDungService.findByEmail(authentication.getName());
        }
        return null;
    }

    // Trang lobby
    @GetMapping("/lobby")
    public String lobby(Model model) {
        model.addAttribute("products", sanPhamService.getAll());
        return "customer/lobby";
    }

    @GetMapping("/products")
    public String viewProducts(Model model) {
        model.addAttribute("list", sanPhamService.getAll());
        return "customer/products";
    }

    // Thêm vào giỏ hàng (khách hoặc user)
    @GetMapping("/add-to-cart/{id}")
    public String addToCart(@PathVariable Integer id, Authentication authentication) {
        NguoiDung user = getUser(authentication);
        gioHangTamService.addToCart(id, user);
        return "redirect:/customer/cart";
    }

    // Xem giỏ hàng
    @GetMapping("/cart")
    public String viewCart(Model model, Authentication authentication) {
        NguoiDung user = getUser(authentication);
        List<GioHang> cartItems = gioHangTamService.getCart(user);

        // Tính tổng tiền
        BigDecimal tongTien = cartItems.stream()
                .map(i -> i.getSanPham().getGia().multiply(new BigDecimal(i.getSoLuong())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        model.addAttribute("cart", cartItems);
        model.addAttribute("tongTien", tongTien);
        return "customer/cart";
    }

    // Thanh toán
    @PostMapping("/checkout")
    public String checkout(Authentication authentication) {
        NguoiDung user = getUser(authentication);
        List<GioHang> cartItems = gioHangTamService.getCart(user);

        if (cartItems.isEmpty()) {
            return "redirect:/customer/cart";
        }

        // Tạo hóa đơn (người dùng có thể null)
        HoaDon hoaDon = new HoaDon();
        hoaDon.setNguoiDung(user);
        hoaDon.setLoaiBan("WEB");
        hoaDon.setTrangThai("Chờ xử lý"); // Đặt trạng thái ban đầu

        BigDecimal tongTien = cartItems.stream()
                .map(i -> i.getSanPham().getGia().multiply(new BigDecimal(i.getSoLuong())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        hoaDon.setTongTien(tongTien);

        // Lưu hóa đơn (tự động tạo lịch sử đầu tiên trong HoaDonService)
        HoaDon savedHoaDon = hoaDonService.save(hoaDon);

        // Lưu chi tiết hóa đơn
        for (GioHang item : cartItems) {
            HoaDonChiTiet ct = new HoaDonChiTiet();
            ct.setHoaDon(savedHoaDon);
            ct.setSanPham(item.getSanPham());
            ct.setSoLuong(item.getSoLuong());
            ct.setDonGia(item.getSanPham().getGia());
            hoaDonChiTietService.save(ct);
        }

        // Xóa giỏ hàng sau khi thanh toán
        gioHangTamService.clearCart(user);

        return "redirect:/customer/order-success";
    }

    // Xóa sản phẩm khỏi giỏ hàng
    @GetMapping("/remove-from-cart/{id}")
    public String removeFromCart(@PathVariable Integer id, Authentication authentication) {
        gioHangTamService.delete(id);
        return "redirect:/customer/cart";
    }

    // Trang sau khi thanh toán xong
    @GetMapping("/order-success")
    public String orderSuccess() {
        return "customer/order-success";
    }

    // Xem lịch sử đơn hàng của khách
    @GetMapping("/my-orders")
    public String myOrders(Model model, Authentication authentication) {
        NguoiDung user = getUser(authentication);
        if (user == null) {
            return "redirect:/login";
        }

        // Lấy danh sách hóa đơn của user
        List<HoaDon> orders = hoaDonService.getAll().stream()
                .filter(h -> h.getNguoiDung() != null && h.getNguoiDung().getId().equals(user.getId()))
                .toList();

        model.addAttribute("orders", orders);
        return "customer/my-orders";
    }

    // Xem timeline đơn hàng (cho khách)
    @GetMapping("/order-timeline/{id}")
    public String orderTimeline(@PathVariable Integer id, Model model, Authentication authentication) {
        NguoiDung user = getUser(authentication);
        HoaDon hoaDon = hoaDonService.getById(id);

        // Kiểm tra quyền: chỉ xem được đơn của mình
        if (hoaDon == null ||
                (user != null && hoaDon.getNguoiDung() != null &&
                        !hoaDon.getNguoiDung().getId().equals(user.getId()))) {
            return "redirect:/customer/my-orders";
        }

        return "redirect:/admin/hoa-don/timeline/" + id;
    }
}