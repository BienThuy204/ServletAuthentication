package thuy.dev.data.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

import thuy.dev.data.dao.UserDao;
import thuy.dev.data.driver.MySQLDriver;
import thuy.dev.data.model.User;

public class UserImpl implements UserDao {

    Connection con = MySQLDriver.getInstance().getConnection();

    @Override
    public boolean insert(User user) {
        // TODO Auto-generated method stub
        String sql = "INSERT INTO USERS(ID, EMAIL, PASSWORD, ROLE) VALUES(null, ?, ?, ?)";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getRole());
            stmt.execute();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(User user) {
        // TODO Auto-generated method stub
        String sql = "UPDATE USERS SET email = ? ,password = ?, role = ? WHERE id = ?";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getRole());
            stmt.setInt(4, user.getId());
            return stmt.execute();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int userId) {
        // TODO Auto-generated method stub
        String sql = "DELETE FROM USERS WHERE id = ?";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, userId);
            return stmt.execute();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public User find(int userId) {
        // TODO Auto-generated method stub
        String sql = "SELECT * FROM USERS WHERE ID = ?";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String email = rs.getString("email");
                String password = rs.getString("password");
                String role = rs.getString("role");
                return new User(userId, email, password, role);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User findByEmail(String userEmail) {
        // Câu truy vấn SQL để tìm người dùng theo email
        String sql = "SELECT * FROM USERS WHERE email = ?";
        try {
            // Chuẩn bị câu lệnh SQL với tham số email
            PreparedStatement stmt = con.prepareStatement(sql);
            // Gán tham số email (chuỗi) vào vị trí thứ nhất trong câu truy vấn SQL
            stmt.setString(1, userEmail);
            // Thực thi câu lệnh truy vấn
            ResultSet rs = stmt.executeQuery();
            // Nếu có kết quả trả về từ cơ sở dữ liệu
            if (rs.next()) {
                // Lấy các giá trị từ kết quả truy vấn
                String email = rs.getString("email");
                String password = rs.getString("password");
                String role = rs.getString("role");
                int id = rs.getInt("id");  // Giả định bảng có cột userId
                // Trả về một đối tượng User mới với các giá trị đã lấy
                return new User(id, email, password, role);
            }
        } catch (SQLException e) {
            // In ra lỗi nếu có ngoại lệ xảy ra
            e.printStackTrace();
        }
        // Trả về null nếu không tìm thấy người dùng
        return null;
    }

    @Override
    public List<User> findAll() {
        // TODO Auto-generated method stub
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM Users WHERE ID > ? ";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, 0);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String role = rs.getString("role");
                users.add(new User(id, email, password, role));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public User checkLogin(String email, String password) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
