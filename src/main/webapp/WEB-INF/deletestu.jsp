<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.txsy.txsyweb.Student" %>
<%@ page import="java.util.List" %>
<%
    String error = (String) request.getAttribute("error");
    List<Student> students = (List<Student>) request.getAttribute("students");
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>Delete Result</title>
</head>
<body>
    <% if (error != null) { %>
    <h1 style="color: red"><%= error %></h1>
    <% } else { %>
    <h1>删除成功！</h1>
    <p>已删除的学生信息：</p>
    <table>
        <tr>
            <th>学号</th>
            <th>姓名</th>
            <th>出生日期</th>
            <th>密码</th>
            <th>性别</th>
        </tr>
        <% for (Student student : students) { %>
        <tr>
            <td><%= student.getId() %></td>
            <td><%= student.getName() %></td>
            <td><%= student.getBirthday() %></td>
            <td><%= student.getPwd() %></td>
            <td><%= student.getSex() %></td>
        </tr>
        <% } %>
    </table>
    <% } %>
</body>
</html>
