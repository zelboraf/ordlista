<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<%@include file="../includes/header.jsp" %>

<section>
    <div>
        <p>Edit a record:</p>
        <form action = "/edit" method = "post">
            <div>
                <table>
                    <tr>
                        <th>Swedish</th>
                        <th>Polish</th>
                    </tr>
                    <tr>
                        <td>
                            <div class="justifyRight"
                                <label>Swedish word: </label>
                                <input type = "text" name = "swedishWord" autocomplete = "off" value = "${dictionary.swedishWord}" autofocus = "autofocus"/></br>
                                <label>Conjugation: </label>
                                <input type = "text" name = "conjugation" value = "${dictionary.conjugation}"/></br>
                                <label>Part of speech: </label>
                                <input type = "text" name = "partOfSpeech" value = "${dictionary.partOfSpeech}"/>
                            </div
                        </td>
                        <td>
                            <div class="justifyRight">
                                <label>Polish word: </label>
                                <input type = "text" name = "polishWord" autocomplete = "off" value = "${dictionary.polishWord}"/>
                            </div>
                        </td>
                        <input type = "hidden" name = "id" value = "${dictionary.id}"/>
                    </tr>

                </table>
            </div>
            <div>
                <input type="submit" value="Save"/>
                <input type="submit" name = "cancel" value="Cancel"/>
            </div>
        </form>
    </div>
</section>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
<script>
$(document).ready(function(){
    var runOnlyOnce = true;
    if (runOnlyOnce) {
        var inputTxt = $('input[name="swedishWord"]');
        var tmpStr = inputTxt.val();
        $(inputTxt).val("").focus().val(tmpStr);    // keep cursor at end of line
    }
});
</script>

<%@include file="../includes/footer.jsp" %>