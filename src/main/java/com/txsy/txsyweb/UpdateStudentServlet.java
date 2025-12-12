package com.txsy.txsyweb;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.*;

@WebServlet(name = "UpdateStudentServlet", value = "/modifystu")
public class UpdateStudentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String id = request.getParameter("id");
        Student student = null;
        String error = null;

        if (id == null || id.trim().isEmpty()) {
            error = "学生ID不能为空";
        } else {
            Connection conn = null;
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try {
                conn = MySQLUtil.getConnection();
                String sql = "SELECT stuNo, nickName, birthday, stuPwd, sex, major, hobbies FROM student WHERE stuNo = ?";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, id);
                rs = stmt.executeQuery();

                if (rs.next()) {
                    String dbSex = rs.getString("sex");
                    String displaySex = "";
                    if ("1".equals(dbSex)) {
                        displaySex = "男";
                    } else if ("0".equals(dbSex)) {
                        displaySex = "女";
                    } // 否则留空

                    student = new Student(
                            rs.getString("stuNo"),
                            rs.getString("nickName"),
                            rs.getString("birthday"),
                            rs.getString("stuPwd"),
                            displaySex,  // ← 这里传"男"或"女"，匹配你的 Student 类
                            rs.getString("major"),
                            rs.getString("hobbies")
                    );
                } else {
                    error = "未找到该学生";
                }
            } catch (SQLException e) {
                e.printStackTrace();
                error = "数据库查询出错";
            } finally {
                MySQLUtil.close(conn, stmt, rs);
            }
        }

        request.setAttribute("error", error);
        request.setAttribute("student", student);
        request.getRequestDispatcher("/WEB-INF/updatestu.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String stuNo = request.getParameter("id"); // 注意：表单中 name 应为 "id"
        String name = request.getParameter("name");
        String birthday = request.getParameter("birthday");
        String pwd = request.getParameter("pwd");
        String sexDisplay = request.getParameter("sex"); // 页面传"男"或"女"
        String major = request.getParameter("major"); // 新增：专业
        String hobbies = request.getParameter("hobbies"); // 新增：爱好

        // 转为数据库值
        String sexDb = null;
        if ("男".equals(sexDisplay)) {
            sexDb = "1";
        } else if ("女".equals(sexDisplay)) {
            sexDb = "0";
        }

        String error = null;

        if (stuNo == null || stuNo.trim().isEmpty()) {
            error = "学生ID缺失";
        } else {
            Connection conn = null;
            PreparedStatement stmt = null;
            try {
                conn = MySQLUtil.getConnection();
                String sql = "UPDATE student SET nickName = ?, birthday = ?, stuPwd = ?, sex = ?, major = ?, hobbies = ? WHERE stuNo = ?";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, isEmpty(name) ? null : name.trim());
                stmt.setString(2, isEmpty(birthday) ? null : birthday);
                stmt.setString(3, isEmpty(pwd) ? null : pwd);
                stmt.setString(4, sexDb);
                stmt.setString(5, isEmpty(major) ? null : major.trim());
                stmt.setString(6, isEmpty(hobbies) ? null : hobbies.trim());
                stmt.setString(7, stuNo);

                if (stmt.executeUpdate() == 0) {
                    error = "更新失败：学生不存在";
                } else {
                    response.sendRedirect("/studentlist");
                    return;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                error = "数据库更新出错";
            } finally {
                MySQLUtil.close(conn, stmt, null);
            }
        }

        // 出错时回显：构造 Student，sex 用"男/女"（符合你的类）
        Student student = new Student(stuNo, name, birthday, pwd, sexDisplay, major, hobbies);
        request.setAttribute("error", error);
        request.setAttribute("student", student);
        request.getRequestDispatcher("/WEB-INF/updatestu.jsp").forward(request, response);
    }

    private boolean isEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }
}