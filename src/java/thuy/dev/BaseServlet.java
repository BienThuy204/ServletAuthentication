/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package thuy.dev;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import thuy.dev.data.dao.Database;
import thuy.dev.data.dao.DatabaseDao;

/**
 *
 * @author Asus
 */
public class BaseServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        DatabaseDao.init(new Database());
    }
    
}
