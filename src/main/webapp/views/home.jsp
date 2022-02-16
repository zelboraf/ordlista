<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<%@include file="../includes/header.jsp" %>

<section>
    <p>${sessionScope.message}</p>
    <div>
        <p>Tekniska ordboken</p>
        <form action = "/home" method = "post">
            <div>
                <input type="text" path="query" value="${sessionScope.query}"/>
            </div>
            <div>
                <input type="submit" value="Szukaj"/>
                <input type="submit" name = "create" value="Dodaj..."
            </div>
        </form>
    </div>

    <div>
        <table>
            <tr>
                <td>Szwedzki</td>
                <td>Polski</td>
            </tr>
            <form action = "/home" method = "post">
                <c:forEach var = "dictionary" items = "${dictionaryList}">
                    <tr>
                        <td><c:out value = "${dictionary.swedishWord}"/></td>
                        <td><c:out value = "${dictionary.polishWord}"/></td>
                        <td><input type = "submit" name = "action" value = "delete${dictionary.id}"/></td>
                    </tr>
                </c:forEach>
            </form>
        </table>
    </div>
</section>

<%@include file="../includes/footer.jsp" %>