package com.txsy.txsyweb;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "SearchServlet", value = "/search")
public class SearchServlet extends HttpServlet {

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

        String searchType = request.getParameter("searchType");
        String searchValue = request.getParameter("searchValue");

        // 如果没有参数或参数为空，直接转发到 JSP（显示空表单）
        if (searchType == null || searchValue == null || searchValue.trim().isEmpty()) {
            request.setAttribute("user", user);
            request.getRequestDispatcher("/WEB-INF/search-result.jsp").forward(request, response);
            return;
        }

        List<Student> students = new ArrayList<>();
        String error = null;

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;


        try {
            conn = MySQLUtil.getConnection();

            if ("name".equals(searchType)) {
                // 模糊匹配姓名
                String sql = "SELECT stuNo, nickName, birthday, stuPwd, sex, major, hobbies FROM student WHERE nickName LIKE ?";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, "%" + searchValue.trim() + "%");
            } else if ("id".equals(searchType)) {
                String sql = "SELECT stuNo, nickName, birthday, stuPwd, sex, major, hobbies FROM student WHERE stuNo = ?";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, searchValue.trim());
            }

            if (stmt != null && error == null) {
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
            }

        } catch (SQLException e) {
            e.printStackTrace();
            error = "数据库查询出错";
        } finally {
            MySQLUtil.close(conn, stmt, rs);
        }

        // 准备响应内容
        request.setAttribute("searchType", searchType);
        request.setAttribute("searchValue", searchValue);
        request.setAttribute("students", students);
        request.setAttribute("error", error);
        request.setAttribute("user", user);
        
        // 转发到 JSP 页面显示结果
        request.getRequestDispatcher("/WEB-INF/search-result.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}