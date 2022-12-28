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
                            <input type = "text" name = "swedishWord" autocomplete = "off" value = "${dictionary.swedishWord}" autofocus = "autofocus"/>
                        </td>
                        <td>
                            <input type = "text" name = "polishWord" autocomplete = "off" value = "${dictionary.polishWord}"/>
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