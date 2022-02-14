<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<%@include file="../includes/header.jsp" %>

<section>
    <div>
        <p>Nowe hasło utworzono pomyślnie, trwa przekierowanie...</p>
          <script>
             setTimeout(function(){
                window.location.href = '/home';
             }, 1000);
          </script>
    </div>
</section>

<%@include file="../includes/footer.jsp" %>