<%@ include file="/WEB-INF/jspf/taglib.jspf" %>
<c:set var="title" value="Cabinet"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>

<%@ include file="/WEB-INF/jspf/header.jspf" %>

<section id="services">
    <div class="container">
        <div class="row">

        </div>
    </div>
</section>

<section id="team">
    <div class="container">
        <div class="row">
            <c:if test="${empty complains}">
                <h2 class="align-center"><spring:message code="no.complains"/></h2>
            </c:if>
            <div class="align-center" id="table_div"></div>
        </div>
    </div>
</section>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>

</body>