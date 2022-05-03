<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<h1><%= "Hello" %>
    <form href = "hello-servlet" method="post">
        <input type="submit" name="button1" value="Button 1" />
    </form>
</h1>
<br/>
<a href="hello-servlet">Hello Servlet</a>
</body>
</html>
