<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<%@include file="../includes/header.jsp" %>

<section>
    <div>
        <p>Tekniska ordboken</p>
        <form action = "/dictionary" method = "post">
            <div>
                <input type="text" path="query" value="${sessionScope.query}"/>
            </div>
            <div>
                <input type="submit" value="Szukaj"/>
            </div>
        </form>
    </div>

    <div>
        <table>
            <tr>
                <td>Szwedzki</td>
                <td>Polski</td>
            </tr>
            <form action = "/dictionary" method = "get">
                <c:forEach var = "entry" items = "${entryList}">
                    <tr>
                        <td><c:out value = "${entry.swedishWord}"/></td>
                        <td><c:out value = "${entry.polishWord}"/></td>
                    </tr>
                </c:forEach>
            </form>
        </table>
    </div>
</section>

<%@include file="../includes/footer.jsp" %>