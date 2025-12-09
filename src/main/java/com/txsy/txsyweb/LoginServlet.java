package com.txsy.txsyweb;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;
import java.sql.*;

@WebServlet(name = "LoginServlet", value = "/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/login.html").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String username = Objects.requireNonNull(request.getParameter("username"), "").trim();
        String password = Objects.requireNonNull(request.getParameter("password"), "").trim();
        System.out.println(username + password);
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean loginSuccess = false;
        try {
            conn = MySQLUtil.getConnection();
            String sql = "SELECT stuPwd FROM student WHERE stuNo = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            rs = pstmt.executeQuery();
            if (rs.next()){
                String dbPassword = rs.getString("stuPwd");
                if(password.equals(dbPassword)){
                    loginSuccess = true;
                }
            }
            if (loginSuccess){
                out.write("<script>alert('登陆成功')</script>");
            }else {
                out.write("<script>alert('用户名或密码错误')</script>");
            }
        }catch (SQLException e){
            e.printStackTrace();
            out.write("数据库异常");
        }finally {
            MySQLUtil.close(conn, pstmt, rs);
        }
    }
}
