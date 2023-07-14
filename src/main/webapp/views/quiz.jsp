<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<%@include file="../includes/header.jsp" %>

<section>
    <div>
        <form action = "/quiz" method = "post">
            <div>
                <table>
                    <tr>
                        <td>
                            ${question.swedishWord}
                            <div hidden id="answer">
                                ${question.polishWord}
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>
                        <c:forEach items = "${answers}" var = "answer">
                            <button type="button" id= "${answer.id}">${answer.polishWord}</button>
                            <br/>
                        </c:forEach>
                        </td>
                    </tr>
                </table>
            </div>
            <div>
                <input type="submit" value="Next question"/>
                <button type="button">Check answer</button>
            </div>
        </form>
    </div>
</section>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
<script>
$(document).ready(function(){
    $("button").click(function(){
        $("#answer").show();
        var answer = $(this).attr('id');
        if (${question.id} == answer) {
            console.log('bravo')
        }
    });
});
</script>

<%@include file="../includes/footer.jsp" %>