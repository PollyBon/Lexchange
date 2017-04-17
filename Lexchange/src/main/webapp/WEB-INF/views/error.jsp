<%@ include file="/WEB-INF/jspf/taglib.jspf" %>
<c:set var="title" value="Error"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>

<%@ include file="/WEB-INF/jspf/header.jspf" %>

<section id="error-page">
    <div class="error-page-inner">
        <div class="container-fluid">
            <div class="row">
                <div class="col-sm-12">
                    <div class="text-center">
                        <div class="bg-404">
                            <div class="error-image">
                                <img class="img-responsive" src="resources/images/404.png" alt="">
                            </div>
                        </div>
                        <h2><spring:message code="error"/></h2>
                        <p><spring:message code="error.msg"/></p>
                        <a href="/lexchange/" class="btn btn-error"><spring:message code="error.return"/></a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>

</body>
</html>
