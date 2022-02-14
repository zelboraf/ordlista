<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<%@include file="../includes/header.jsp" %>

<section>
    <div>
        <p>Utwórz nowe hasło w słowniku:</p>
        <form action = "/create" method = "post">
            <div>
                <table>
                    <tr>
                        <td>Szwedzki</td>
                        <td>Polski</td>
                    </tr>
                    <tr>
                        <input type="text" name = "swedishWord"/>
                    </tr>
                    <tr>
                        <input type="text" name = "polishWord"/>
                    </tr>
                </table>
            </div>
            <div>
                <input type="submit" value="Dodaj"/>
                <input type="submit" name = "cancel" value="Anuluj"/>
            </div>
        </form>
    </div>
</section>

<%@include file="../includes/footer.jsp" %>