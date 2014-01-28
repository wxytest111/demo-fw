<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="pageTitle" scope="request" value="Thank you"/>

<div id="surveyThankYou">Thank you for filling out our survey!</div>
<button id="closeButton">Close</button>

<script type="text/javascript">
    var closeButton = document.getElementById("closeButton");
    closeButton.onclick = function(){
        window.close();
    };
</script>

<%@ include file="footer.jsp" %>