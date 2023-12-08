<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<%@include file="../includes/header.jsp" %>

<section>
<form action = "/home" method = "post" name = "form">
    <div>
        <h1>Technical dictionary SE-PL</h1>
        <div>
            <input type = "text" name = "searchString" autofocus = "autofocus" value = "${searchString}" autocomplete = "off"/>
            <input type = "submit" name = "create" value="Add new..."/>
            <input type = "submit" name = "search" value="Search"/>
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
            <span id="loadingAnimation" hidden="true">
                Loading...
            </span>
        </div>
    <p>${message}</p>
    </div>

    <div>
        <table>
            <tr>
                <th>Swedish</th>
                <th>Polish</th>
                <th>action</th>
            </tr>
            <c:if test="${empty dictionaryStartingWith}">
                <tr><td colspan="3"><b>No results.</b></td></tr>
            </c:if>
            <c:forEach var = "dictionary" items = "${dictionaryStartingWith}">
                <tr>
                    <td><c:out value = "${dictionary.swedishWord}"/>
                        </br>
                        <span class="italic">
                            <c:out value = "${dictionary.conjugation}"/>
                            &nbsp;&nbsp;
                            <c:out value = "${dictionary.partOfSpeech}"/>
                        </span>
                    </td>
                    <td>
                        <c:out value = "${dictionary.polishWord}"/>
                    </td>
                    <td>
                        <a href="/delete/${dictionary.id}">delete</input>
                        <a href="/edit/${dictionary.id}">edit</input>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${!empty dictionaryContaining}">
                <tr><td colspan="3"><b>Other records containing phrase:</b></td></tr>
            <c:forEach var = "dictionary" items = "${dictionaryContaining}">
                <tr>
                    <td>
                        <c:out value = "${dictionary.swedishWord}"/>
                        </br>
                        <span class="italic">
                            <c:out value = "${dictionary.conjugation}"/>
                            &nbsp;&nbsp;
                            <c:out value = "${dictionary.partOfSpeech}"/>
                        </span>
                    </td>
                    <td>
                        <c:out value = "${dictionary.polishWord}"/>
                    </td>
                    <td>
                        <a href="/delete/${dictionary.id}">delete</input>
                        <a href="/edit/${dictionary.id}">edit</input>
                    </td>
                </tr>
            </c:forEach>
            </c:if>
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

    $(inputTxt).on('input', function() {                                 // auto submit form while typing
        if (inputTxt.val().length > 2 && autoRefreshIsOn) {
            submitForm();
        }
    });

    $('input[name="dictionaryLang"]').change(function() {               // submit on language change
        submitForm();
    });

    $('input[name="search"]').click(function() {
        submitForm();
    });

    function keepCursorAtEOL() {                                        // keep cursor at end of line
        var tmpStr = inputTxt.val();
        $(inputTxt).val("").focus().val(tmpStr);
    };

    function submitForm() {                                             // show animation on submit
        $('#loadingAnimation').show();
        $('form').submit();
    };

});
</script>

<%@include file="../includes/footer.jsp" %>