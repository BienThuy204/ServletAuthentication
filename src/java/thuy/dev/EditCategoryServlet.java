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
import thuy.dev.data.dao.CategoryDao;
import thuy.dev.data.dao.DatabaseDao;
import thuy.dev.data.model.Category;

/**
 *
 * @author Asus
 */
@WebServlet(name = "EditCategoryServlet", urlPatterns = {"/EditCategoryServlet"})
public class EditCategoryServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        CategoryDao categoryDao = DatabaseDao.getInstance().getCategoryDao();
        Category category = categoryDao.find(id);
        category.setName("Ten moi");
        
        categoryDao.update(category);
        response.sendRedirect("CategoryServlet");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

}
