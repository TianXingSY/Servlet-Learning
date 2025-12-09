<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.txsy.txsyweb.Student" %>
<%
    String error = (String) request.getAttribute("error");
    Student student = (Student) request.getAttribute("student");
%>
<html>
<head>
    <meta charset="UTF-8">
    <title>修改学生信息</title>
    <style>
        body { font-family: sans-serif; margin: 30px; }
        .form-group { margin: 12px 0; }
        label { display: inline-block; width: 80px; }
        input, select { padding: 5px; width: 180px; }
        .error { color: red; margin-bottom: 15px; }
        .btn { padding: 6px 12px; background: #007bff; color: white; border: none; cursor: pointer; }
    </style>
</head>
<body>

<h2>修改学生信息</h2>

<% if (error != null) { %>
<div class="error"><%= error %></div>
<% } %>

<% if (student == null) { %>
<p>无法加载学生信息，请返回<a href="${pageContext.request.contextPath}/studentlist">学生列表</a>。</p>
<% } else { %>
<form method="post" action="${pageContext.request.contextPath}/modifystu">
    <div class="form-group">
        <label>学号:</label>
        <input type="text" name="id" value="<%= student.getId() %>" readonly />
    </div>

    <div class="form-group">
        <label>昵称:</label>
        <input type="text" name="name" value="<%= student.getName() != null ? student.getName() : "" %>" />
    </div>

    <div class="form-group">
        <label>生日:</label>
        <input type="date" name="birthday" value="<%= student.getBirthday() != null ? student.getBirthday() : "" %>" />
    </div>

    <div class="form-group">
        <label>密码:</label>
        <input type="password" name="pwd" value="<%= student.getPwd() != null ? student.getPwd() : "" %>" />
    </div>

    <div class="form-group">
        <label>性别:</label>
        <select name="sex">
            <option value="">-- 请选择 --</option>
            <option value="男" <%= "男".equals(student.getSex()) ? "selected" : "" %>>男</option>
            <option value="女" <%= "女".equals(student.getSex()) ? "selected" : "" %>>女</option>
        </select>
    </div>

    <div class="form-group">
        <button type="submit" class="btn">保存修改</button>
        <a href="${pageContext.request.contextPath}/studentlist" style="margin-left: 10px;">取消</a>
    </div>
</form>
<% } %>

</body>
</html>