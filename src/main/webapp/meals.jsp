<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib  prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <link rel="stylesheet" href="resources/css/style.css" type="text/css"/>
    <title>Meals</title>
</head>
<body>
    <h3><a href="index.html">Home</a></h3>
    <hr/>
    <h2>Meals</h2>
    <table>
        <thead>
            <tr>
                <th>Date</th>
                <th>Description</th>
                <th>Calories</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${mealsTo}" var="mealTo">
                <tr style="color: ${mealTo.excess ? 'red' : 'green'}">
                    <td>
                        <fmt:parseDate value="${mealTo.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />
                        <fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${parsedDateTime}" />
                    </td>
                    <td>
                        <c:out value="${mealTo.description}"/>
                    </td>
                    <td>
                        <c:out value="${mealTo.calories}"/>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>
