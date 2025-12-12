package com.txsy.txsyweb;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.*;

@WebServlet(name = "regServlet", value = "/reg")
public class regServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        // 注册页面不需要预加载学生信息，直接转发到注册页面
        request.getRequestDispatcher("/WEB-INF/reg.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        // 获取注册表单数据
        String stuNo = request.getParameter("id");
        String name = request.getParameter("name");
        String birthday = request.getParameter("birthday");
        String pwd = request.getParameter("pwd");
        String sexDisplay = request.getParameter("sex"); // 页面传"男"或"女"
        String major = request.getParameter("major"); // 新增：专业
        String hobbies = request.getParameter("hobbies"); // 新增：爱好

        // 验证必填字段
        String error = null;
        if (isEmpty(stuNo)) {
            error = "学号不能为空";
        } else if (isEmpty(name)) {
            error = "姓名不能为空";
        } else if (isEmpty(pwd)) {
            error = "密码不能为空";
        }

        if (error != null) {
            // 出错时回显数据
            Student student = new Student(stuNo, name, birthday, pwd, sexDisplay, major, hobbies);
            request.setAttribute("error", error);
            request.setAttribute("student", student);
            request.getRequestDispatcher("/WEB-INF/reg.jsp").forward(request, response);
            return;
        }

        // 转换性别为数据库值
        String sexDb = null;
        if ("男".equals(sexDisplay)) {
            sexDb = "1";
        } else if ("女".equals(sexDisplay)) {
            sexDb = "0";
        }

        // 检查学号是否已存在
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = MySQLUtil.getConnection();
            
            // 先检查学号是否存在
            String checkSql = "SELECT COUNT(*) FROM student WHERE stuNo = ?";
            stmt = conn.prepareStatement(checkSql);
            stmt.setString(1, stuNo);
            rs = stmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                error = "该学号已存在，请使用其他学号";
                Student student = new Student(stuNo, name, birthday, pwd, sexDisplay, major, hobbies);
                request.setAttribute("error", error);
                request.setAttribute("student", student);
                request.getRequestDispatcher("/WEB-INF/reg.jsp").forward(request, response);
                return;
            }
            
            // 执行插入操作
            String insertSql = "INSERT INTO student (stuNo, nickName, birthday, stuPwd, sex, major, hobbies) VALUES (?, ?, ?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(insertSql);
            stmt.setString(1, stuNo);
            stmt.setString(2, isEmpty(name) ? null : name.trim());
            stmt.setString(3, isEmpty(birthday) ? null : birthday);
            stmt.setString(4, pwd);
            stmt.setString(5, sexDb);
            stmt.setString(6, isEmpty(major) ? null : major.trim());
            stmt.setString(7, isEmpty(hobbies) ? null : hobbies.trim());

            int result = stmt.executeUpdate();
            if (result > 0) {
                // 注册成功，重定向到学生列表
                response.sendRedirect("/studentlist");
                return;
            } else {
                error = "注册失败，请稍后重试";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            error = "数据库操作出错: " + e.getMessage();
        } finally {
            MySQLUtil.close(conn, stmt, rs);
        }

        // 出错时回显数据
        Student student = new Student(stuNo, name, birthday, pwd, sexDisplay, major, hobbies);
        request.setAttribute("error", error);
        request.setAttribute("student", student);
        request.getRequestDispatcher("/WEB-INF/reg.jsp").forward(request, response);
    }

    private boolean isEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }
}