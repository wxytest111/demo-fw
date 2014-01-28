<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" scope="request" value="Reserve Item"/>
<%@ include file="header.jsp" %>

<div id="surveyReport">


    <div>
        <label>Promoter Percentage:</label>
        <span id="promoterPercentage">${npsReport.promoters}<span>
    </div>


    <div>
        <label>Detractors Percentage:</label>
        <span id="passivePercentage">${npsReport.detractors}</span>
    </div>


    <div>
        <label>Passives Percentage:</label>
        <span id="detractorPercentage">${npsReport.passives}</span>
    </div>


    <div>
        <label>NPS Score:</label>
        <span id="npsScore">${npsReport.npsScore}</span>
    </div>
</div>

<%@ include file="footer.jsp" %>