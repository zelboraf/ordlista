<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<%@include file="../includes/header.jsp" %>

<section>
<form action = "/home" method = "post">
    <div>
        <h1>Technical dictionary SE-PL</h1>
        <div>
            <input type = "text" name = "searchString" autofocus = "autofocus" value = "${searchString}" autocomplete = "off" onfocus = "this.value = this.value;"/>
            <input type = "submit" value = "Search"/>
            <input type = "submit" name = "create" value="Add new..."/>
            <label>
                <input type = "radio" name = "dictionaryLang" value = "SE"
                    <c:if test="${dictionaryLang == 'SE'}">checked</c:if>
                />
                SE
            </label>
            <label>
                <input type = "radio" name = "dictionaryLang" value = "PL"
                    <c:if test="${dictionaryLang == 'PL'}">checked</c:if>
                />
                PL
            </label>
        </div>
    <p>${message}</p>
    </div>

    <div>
        <c:out value="${'starting with:'}"/>
        <table>
            <tr>
                <th>Swedish</th>
                <th>Polish</th>
                <th>action</th>
            </tr>
            <c:forEach var = "dictionary" items = "${dictionaryStartingWith}">
                <tr>
                    <td><c:out value = "${dictionary.swedishWord}"/></td>
                    <td><c:out value = "${dictionary.polishWord}"/></td>
                    <td>
                        <a href="/delete/${dictionary.id}">delete</input>
                        <a href="/edit/${dictionary.id}">edit</input>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <c:out value="${'containing:'}"/></tr>
        <table>
            <c:forEach var = "dictionary" items = "${dictionaryContaining}">
                <tr>
                    <td><c:out value = "${dictionary.swedishWord}"/></td>
                    <td><c:out value = "${dictionary.polishWord}"/></td>
                    <td>
                        <a href="/delete/${dictionary.id}">delete</input>
                        <a href="/edit/${dictionary.id}">edit</input>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</form>
</section>

<%@include file="../includes/footer.jsp" %>