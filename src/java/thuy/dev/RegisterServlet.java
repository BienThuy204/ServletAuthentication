/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package thuy.dev;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import thuy.dev.data.dao.Database;
import thuy.dev.data.dao.DatabaseDao;
import thuy.dev.data.dao.UserDao;
import thuy.dev.data.model.User;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 *
 * @author Asus
 */
@WebServlet(name = "RegisterServlet", urlPatterns = {"/RegisterServlet"})
public class RegisterServlet extends BaseServlet {
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                request.getRequestDispatcher("register.jsp").include(request, response);
    }

    private boolean isValidEmail(String email) {
    String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    return email.matches(emailRegex);
}
private boolean isStrongPassword(String password) {
    if (password.length() < 8) return false;
    if (!password.matches(".*[A-Z].*")) return false; // Kiểm tra chữ hoa
    if (!password.matches(".*[a-z].*")) return false; // Kiểm tra chữ thường
    if (!password.matches(".*\\d.*")) return false; // Kiểm tra số
    if (!password.matches(".*[!@#$%^&*()].*")) return false; // Kiểm tra ký tự đặc biệt
    return true;
}
private String hashPassword(String password) {
    try {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(password.getBytes());
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    } catch (NoSuchAlgorithmException e) {
        throw new RuntimeException(e);
    }
}
    @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    HttpSession session = request.getSession();
    String email = request.getParameter("email");
    String password = request.getParameter("password");
    String confirmPassword = request.getParameter("confirmPassword");

    UserDao userDao = DatabaseDao.getInstance().getUserDao();

    // Kiểm tra xem email có tồn tại không
    User user = userDao.findByEmail(email);
    
    // 1. Kiểm tra email hợp lệ
    if (!isValidEmail(email)) {
        session.setAttribute("error", "Email không hợp lệ.");
        request.getRequestDispatcher("register.jsp").include(request, response);
        return;
    }
    
    // 2. Kiểm tra xem email đã tồn tại hay chưa
    if (user != null) {
        session.setAttribute("error", "Email đã tồn tại.");
        request.getRequestDispatcher("register.jsp").include(request, response);
        return;
    }

    // 3. Kiểm tra độ mạnh của mật khẩu
    if (!isStrongPassword(password)) {
        session.setAttribute("error", "Mật khẩu phải chứa ít nhất 8 ký tự, bao gồm chữ hoa, chữ thường, số và ký tự đặc biệt.");
        request.getRequestDispatcher("register.jsp").include(request, response);
        return;
    }

    // 4. Kiểm tra xác nhận mật khẩu
//    if (!password.equals(confirmPassword)) {
//        session.setAttribute("error", "Mật khẩu xác nhận không khớp.");
//        request.getRequestDispatcher("login.jsp").include(request, response);
//        return;
//    }

    // 5. Mã hóa mật khẩu trước khi lưu
    String hashedPassword = hashPassword(password);
    
    // Tạo người dùng mới
    user = new User(email, hashedPassword, "user");
    userDao.insert(user);
    
    // Chuyển hướng sau khi đăng ký thành công
    response.sendRedirect("RegisterServlet");
}
    
}
