<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<%@include file="../includes/header.jsp" %>

<section>
<form action = "/home" method = "post" name = "form">
    <div>
        <h1>Technical dictionary SE-PL</h1>
        <div>
            <input type = "text" name = "searchString" autofocus = "autofocus" value = "${searchString}" autocomplete = "off"/>
            <input type = "submit" name = "search" value="Search"/>
            <input type = "submit" name = "create" value="Add new..."/>
            <input type = "radio" id = "langSE" name = "dictionaryLang" value = "SE"
                <c:if test="${dictionaryLang == 'SE'}">checked</c:if>
            />
                <label for = "langSE">SE</label>
            <input type = "radio" id = "langPL" name = "dictionaryLang" value = "PL"
                <c:if test="${dictionaryLang == 'PL'}">checked</c:if>
            />
                <label for = "langPL">PL</label>
            <input type = "checkbox" name = "autoRefresh"
                <c:if test="${autoRefresh}">checked</c:if>
            />
                <label for = "autoRefresh">autoRefresh</label>
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

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
<script>
var runOnce = true;
$(document).ready(function() {
    var inputTxt = $('input[name="searchString"]');

    keepCursorAtEOL();

    var isUpdated = ('${message}' == "updated") ? true : false;         // select all text after update
    if (runOnce && isUpdated) {
        $(inputTxt).select();
        runOnce = false;
    };

    var autoRefreshIsOn = $('input[name="autoRefresh"]').prop('checked');
    $('input[name="autoRefresh"]').change(function() {                   // set autoRefresh on checkbox change
        if (autoRefreshIsOn) {
            autoRefreshIsOn = false;
            <c:set var = "autoRefresh" scope = "session" value="false" />
        } else {
            autoRefreshIsOn = true;
            <c:set var = "autoRefresh" scope = "session" value="true" />
        }
        keepCursorAtEOL();
    });

    $(inputTxt).on('input', function() {                                 // auto submit form on changes
        if (inputTxt.val().length > 2 && autoRefreshIsOn) {
            $('form').submit();
        }
    });

    $('input[name="dictionaryLang"]').change(function() {
        $('form').submit();
    });

    function keepCursorAtEOL() {
        var tmpStr = inputTxt.val();                                    // keep cursor at end of line
        $(inputTxt).val("").focus().val(tmpStr);
    }
});
</script>

<%@include file="../includes/footer.jsp" %>