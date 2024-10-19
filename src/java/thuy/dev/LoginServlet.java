/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package thuy.dev;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import thuy.dev.data.dao.DatabaseDao;
import thuy.dev.data.dao.UserDao;
import thuy.dev.data.model.User;

/**
 *
 * @author Asus
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends BaseServlet {


    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").include(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        UserDao userDao = DatabaseDao.getInstance().getUserDao();
        User user = userDao.checkLogin(email, password);
        
        if(user == null){
            session.setAttribute("error", "Login Failesl");
            response.sendRedirect("LoginServlet");
        }else{
            session.setAttribute("user", user);
            if(user.getRole().equals("admin")){
                
                response.sendRedirect("DashboardServlet");        
            }else{
                response.sendRedirect("HomeServlet");
            }
        }
    }

    
}
