<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib  prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Meals</title>
    <style>
        table {
            border-collapse: collapse;
            border: 1px solid black;
        }
        th {
            border: 1px solid black;
            width: 150px;
            height: 40px;
            text-align: center;
        }
        td {
            padding: 10px;
            border: 1px solid black;
            width: auto;
            text-align: left;
        }
    </style>
</head>
<body>
    <h3><a href="index.html">Home</a></h3>
    <hr/>
    <h2>Meals</h2>
    <p><a href="addMeal?action=add">Add meal</a></p>
    <table>
        <thead>
            <tr>
                <th>Date</th>
                <th>Description</th>
                <th>Calories</th>
                <th style="width: auto">&nbsp;</th>
                <th style="width: auto">&nbsp;</th>
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
                    <td>
                        <a href="meals?action=edit&id=<c:out value="${mealTo.id}"/>">Update</a>
                    </td>
                    <td>
                        <a href="meals?action=delete&id=<c:out value="${mealTo.id}"/>">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>
