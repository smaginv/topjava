<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="meal != null">
    <jsp:useBean id="meal" scope="request" type="ru.javawebinar.topjava.model.Meal"/>
</c:if>
<html>
<head>
    <title>${meal==null ? 'Add meal' : 'Edit meal'}</title>
    <style>
        table {
            border-collapse: collapse;
            border: 0;
        }

        td {
            padding: 5px;
            width: auto;
            text-align: left;
        }
    </style>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr/>
<h2>${meal == null ? 'Add meal' : 'Edit meal'}</h2>
<form method="post" action="meals">
    <table>
        <tr>
            <td><label for="date">DateTime: </label></td>
            <td>
                <input type="hidden" name="id" value="${meal.id}">
                <input type="datetime-local" name="dateTime" id="date" value="${meal.dateTime}"/>
            </td>
        </tr>
        <tr>
            <td><label for="desc">Description: </label></td>
            <td>
                <input type="text" style="width: 300pt" name="description" id="desc" value="${meal.description}"/>
            </td>
        </tr>
        <tr>
            <td><label for="cal">Calories: </label></td>
            <td>
                <input type="number" name="calories" id="cal" value="${meal.calories}"/>
            </td>
        </tr>
        <tr>
            <td><input type="submit" value="Save"/></td>
            <td><a href="meals"><input type="button" value="Cancel" name="cancel"/></a></td>
        </tr>
    </table>
</form>
</body>
</html>
