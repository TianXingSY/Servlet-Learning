package com.txsy.txsyweb;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "StudentListServlet", value = "/studentlist")
public class StudentListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("currentUser") == null){
            response.sendRedirect("/login");
            return;
        }
        String user = session.getAttribute("currentUser").toString();

        List<Student> students = new ArrayList<>();
        String error = null;

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = MySQLUtil.getConnection();
            String sql = "SELECT * FROM student";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Student student = new Student(
                        rs.getString("stuNo"),  // 改为 getString
                        rs.getString("nickName"),
                        rs.getString("birthday"),
                        rs.getString("stuPwd"),
                        rs.getString("sex"),
                        rs.getString("major"),
                        rs.getString("hobbies")
                );
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            error = "数据库查询出错";
        } finally {
            MySQLUtil.close(conn, stmt, rs);
        }
        request.setAttribute("user", user);
        request.setAttribute("students", students);
        request.setAttribute("error", error);
        request.getRequestDispatcher("/WEB-INF/student-list.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}