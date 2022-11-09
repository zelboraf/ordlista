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
                        <th>Szwedzki</th>
                        <th>Polski</th>
                    </tr>
                    <tr>
                        <td>
                            <input type = "text" name = "swedishWord" autocomplete = "off" value="${dictionary.swedishWord}" autofocus = "autofocus"/>
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

<%@include file="../includes/footer.jsp" %>