<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<%@include file="../includes/header.jsp" %>

<section>
<form action = "/home" method = "post">
    <div>
        <h1>Słownik techniczny szwedzko-polski i polsko-szwedzki</h1>
        <div>
            <input type = "text" name = "searchString" autofocus = "autofocus" value = "${searchString}" onfocus = "this.value = this.value;"/>
            <input type = "submit" value = "Search"/>
            <input type = "submit" name = "create" value="Add new..."/>
            <label>
                <input type = "radio" name = "selectedDictionary" value = "SE"
                    <c:if test="${selectedDictionary == 'SE'}">checked</c:if>
                />
                SE
            </label>
            <label>
                <input type = "radio" name = "selectedDictionary" value = "PL"
                    <c:if test="${selectedDictionary == 'PL'}">checked</c:if>
                />
                PL
            </label>
            <label>
                <input type = "radio" name = "selectedDictionary" value = "both"
                    <c:if test="${selectedDictionary == 'both'}">checked</c:if>
                />
                both
            </label>
        </div>
    <p>${message}</p>
    </div>

    <div>
        <table>
            <tr>
                <th>Szwedzki</th>
                <th>Polski</th>
                <th>akcja</th>
            </tr>
            <c:forEach var = "dictionary" items = "${dictionaryList}">
                <tr>
                    <td><c:out value = "${dictionary.swedishWord}"/></td>
                    <td><c:out value = "${dictionary.polishWord}"/></td>
                    <td><input type = "submit" name = "action" value = "delete${dictionary.id}"/></td>
                </tr>
            </c:forEach>
        </table>
    </div>
</form>
</section>

<%@include file="../includes/footer.jsp" %>