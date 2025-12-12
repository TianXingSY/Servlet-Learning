package com.txsy.txsyweb;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;
import java.sql.*;
import java.util.ArrayList;


@WebServlet(name = "DeleteStudentServlet", value = "/delstu")
public class DeleteStudentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String id = request.getParameter("id");
        List<Student>  students = new ArrayList<>();
        String error = null;

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        if(id == null || id.trim().isEmpty()){
            request.setAttribute("error", "传入参数不正确");
        }else {
            try{
                conn = MySQLUtil.getConnection();
                String sql = "SELECT * FROM student WHERE stuNo = ?";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, id);
                rs = stmt.executeQuery();
                if (!rs.next()) {
                    error = "没有该学生";
                }else {
                    Student student = new Student(
                            rs.getString("stuNo"),
                            rs.getString("nickName"),
                            rs.getString("birthday"),
                            rs.getString("stuPwd"),
                            rs.getString("sex"),
                            rs.getString("major"),
                            rs.getString("hobbies")
                    );
                    students.add(student);

                    sql = "DELETE FROM student WHERE stuNo = ?";
                    stmt = conn.prepareStatement(sql);
                    stmt.setString(1, id);
                    stmt.executeUpdate();
                }

            }catch (SQLException e){
                e.printStackTrace();
                error = "数据库连接出错" + e.getMessage();
            }finally {
                MySQLUtil.close(conn, stmt, rs);
            }
        }
        request.setAttribute("error", error);
        request.setAttribute("students", students);
        request.getRequestDispatcher("/WEB-INF/deletestu.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        doGet(request, response);
    }
}