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
    <title>List of Student</title>
</head>
<body>
<h1>学生列表</h1>
<% if (students != null || error != null) { %>
<div style="margin-top: 20px; min-height: 50px; border-top: 1px solid #ccc; padding-top: 10px;">
    <!-- 搜索结果 -->
    <% if (error != null) { %>
    <p style="color: red;">错误: <%= error %></p>
    <% } else if (students == null || students.isEmpty()) { %>
    <p>未找到学生。</p>
    <% } else { %>
    <p>找到 <%= students.size() %> 个学生：</p>
    <table border="1" cellpadding="5" cellspacing="0" style="margin-top: 10px;">
        <tr>
            <th>ID</th>
            <th>姓名</th>
            <th>生日</th>
            <th>性别</th>
            <th>密码</th>
            <th>专业</th>
            <th>修改按钮</th>
            <th>删除按钮</th>
        </tr>
        <% for (Student s : students) { %>
        <tr>
            <td><%= s.getId() %></td>
            <td><%= s.getName() != null ? s.getName() : ""%></td>
            <td><%= s.getBirthday() != null ? s.getBirthday() : "" %></td>
            <td><%= s.getSex() != null ? s.getSex() : "" %></td>
            <td><%= s.getPwd() != null ? s.getPwd() : "" %></td>
            <td><%= s.getMajor() != null ? s.getMajor() : "" %></td>
            <td><a href="${pageContext.request.contextPath}/modifystu?id=<%= s.getId() %>">修改</a></td>
            <td><a href="${pageContext.request.contextPath}/delstu?id=<%= s.getId() %>">删除</a></td>
        </tr>
        <% } %>
    </table>
    <% } %>
</div>
<% } %>
</body>
</html>
