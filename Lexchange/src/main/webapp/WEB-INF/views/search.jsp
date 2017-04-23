<%@ include file="/WEB-INF/jspf/taglib.jspf" %>
<c:set var="title" value="Search"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>

<%@ include file="/WEB-INF/jspf/header.jspf" %>

<a name="search"></a>
<form:form method="GET" modelAttribute="searchBean">
    <section id="page-breadcrumb">
        <div class="vertical-center sun">
            <div class="container">
                <div class="row">
                    <div class="action">
                        <div class="col-sm-12">
                            <div class="col-md-3 align-center">
                                <h3><spring:message code="native.language"/></h3>
                                <form:select path="nativeLanguage" id="nativeLanguage" class="form-control" onchange="submit()">
                                    <option value="not"
                                            <c:if test="${'not'.equals(searchBean.nativeLanguage)}">
                                                selected
                                            </c:if>
                                    >-<spring:message code="select"/>-
                                    </option>
                                    <c:forEach var="elem" items="${languages}">
                                        <option value="${elem.code}"
                                                <c:if test="${elem.code.equals(searchBean.nativeLanguage)}">
                                                    selected
                                                </c:if>
                                        > ${elem.name} </option>
                                    </c:forEach>
                                </form:select>
                            </div>
                            <div class="col-md-3 align-center">
                                <h3><spring:message code="interested.language"/></h3>
                                <form:select path="interestedIn" id="interestedIn" class="form-control" onchange="submit()">
                                    <option value="not"
                                            <c:if test="${'not'.equals(searchBean.interestedIn)}">
                                                selected
                                            </c:if>
                                    >-<spring:message code="select"/>-
                                    </option>
                                    <c:forEach var="elem" items="${languages}">
                                        <option value="${elem.code}"
                                                <c:if test="${elem.code.equals(searchBean.interestedIn)}">
                                                    selected
                                                </c:if>
                                        > ${elem.name} </option>
                                    </c:forEach>
                                </form:select>
                            </div>
                            <div class="col-md-3 align-center">
                                <h3><spring:message code="country"/></h3>
                                <form:select path="country" id="country" class="form-control" onchange="submit()">
                                    <option value="not"
                                            <c:if test="${'not'.equals(searchBean.country)}">
                                                selected
                                            </c:if>
                                    >-<spring:message code="select"/>-
                                    </option>
                                    <c:forEach var="elem" items="${countries}">
                                        <option value="${elem.code}"
                                                <c:if test="${elem.code.equals(searchBean.country)}">
                                                    selected
                                                </c:if>
                                        >${elem.name} </option>
                                    </c:forEach>
                                </form:select>
                            </div>
                            <div class="col-md-3 align-center">
                                <h3><spring:message code="age"/></h3>
                                <form:select path="age" id="age" class="form-control" onchange="submit()">
                                    <option value="not"
                                            <c:if test="${'not'.equals(searchBean.age)}">
                                                selected
                                            </c:if>
                                    >-<spring:message code="select"/>-
                                    </option>
                                    <c:forEach var="elem" items="${ages}">
                                        <option value="${elem.name()}"
                                                <c:if test="${elem.name().equals(searchBean.age)}">
                                                    selected
                                                </c:if>
                                        > ${elem.name()} </option>
                                    </c:forEach>
                                </form:select>
                            </div>
                            <div class="col-md-12">
                                <div class="col-md-1-5 padding-top-10">
                                    <form:checkbox path="interests" value="music" class="form-control-inline" onchange="submit()"/>
                                    <p class="inline header-3"><spring:message code="music"/></p>
                                </div>
                                <div class="col-md-1-5 padding-top-10">
                                    <form:checkbox path="interests" value="sport" class="form-control-inline" onchange="submit()"/>
                                    <p class="inline header-3"><spring:message code="sport"/></p>
                                </div>
                                <div class="col-md-1-5 padding-top-10">
                                    <form:checkbox path="interests" value="art" class="form-control-inline" onchange="submit()"/>
                                    <p class="inline header-3"><spring:message code="art"/></p>
                                </div>
                                <div class="col-md-1-5 padding-top-10">
                                    <form:checkbox path="interests" value="trips" class="form-control-inline" onchange="submit()"/>
                                    <p class="inline header-3"><spring:message code="trips"/></p>
                                </div>
                                <div class="col-md-1-5 padding-top-10">
                                    <form:checkbox path="interests" value="politics" class="form-control-inline"
                                                   onchange="submit()"/>
                                    <p class="inline header-3"><spring:message code="politics"/></p>
                                </div>
                                <div class="col-md-1-5 padding-top-10">
                                    <form:checkbox path="interests" value="science" class="form-control-inline"
                                                   onchange="submit()"/>
                                    <p class="inline header-3"><spring:message code="science"/></p>
                                </div>
                                <div class="col-md-1-5 padding-top-10">
                                    <form:checkbox path="interests" value="games" class="form-control-inline" onchange="submit()"/>
                                    <p class="inline header-3"><spring:message code="games"/></p>
                                </div>
                                <div class="col-md-1-5 padding-top-10">
                                    <form:checkbox path="interests" value="religion" class="form-control-inline"
                                                   onchange="submit()"/>
                                    <p class="inline header-3"><spring:message code="religion"/></p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <section id="portfolio">
        <div class="container">
            <div class="row">
                <div class="portfolio-items">
                    <c:forEach var="elem" items="${searchBean.pageUsers}">
                        <div class="col-xs-6 col-sm-4 col-md-3 portfolio-item padding-top-10">
                            <div class="portfolio-wrapper">
                                <div class="portfolio-single">
                                    <div class="portfolio-thumb">
                                        <c:choose>
                                            <c:when test="${empty elem.url}">
                                                <img src="resources/images/blank-avatar.jpg" class="avatar-search"
                                                    style="border-color: ${searchBean.randomColor}">
                                            </c:when>
                                            <c:otherwise>
                                                <img src="${elem.url}" class="avatar-search"
                                                     style="border-color: ${searchBean.randomColor}">
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                    <div class="portfolio-view">
                                        <ul class="nav nav-pills">
                                            <li><a href="portfolio-details.html"><i class="fa fa-link"></i></a></li>
                                            <li>
                                                <c:choose>
                                                    <c:when test="${empty elem.url}">
                                                        <a href="resources/images/blank-avatar.jpg"
                                                           data-lightbox="example-set"><i class="fa fa-eye"></i></a>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <a href="${elem.url}" data-lightbox="example-set"><i
                                                                class="fa fa-eye"></i></a>
                                                    </c:otherwise>
                                                </c:choose>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                                <div class="portfolio-info">
                                    <h3>${elem.firstName} ${elem.lastName}, <spring:message
                                            code="age"/>: ${elem.age}</h3>
                                    <h2><spring:message code="interested.language"/>:
                                        <c:forEach var="lang" items="${elem.interested}">
                                            ${languages[1].getByCode(lang)}
                                        </c:forEach>
                                    </h2>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
                <div class="col-md-12">
                    <div class="col-md-4">
                        <form:input path="stringCriteria" class="form-control" onchange="submit()"/>
                    </div>
                    <div class="col-md-4 align-center">
                        <ul class="pagination">
                            <li>
                                <a href="${requestScope['javax.servlet.forward.request_uri']}?currentPage=${searchBean.currentPage-1}&${requestScope['javax.servlet.forward.query_string']}#search">left</a>
                            </li>
                            <c:forEach var="elem" items="${searchBean.pages}">
                                <li
                                        <c:if test="${elem.equals(searchBean.currentPage)}">class="active"</c:if> >
                                    <a href="${requestScope['javax.servlet.forward.request_uri']}?currentPage=${elem}&${requestScope['javax.servlet.forward.query_string']}#search">${elem}</a>
                                </li>
                            </c:forEach>
                            <li>
                                <a href="${requestScope['javax.servlet.forward.request_uri']}?currentPage=${searchBean.currentPage+1}&${requestScope['javax.servlet.forward.query_string']}#search">right</a>
                            </li>
                        </ul>
                    </div>
                    <div class="col-md-4">
                        <input type="reset" onclick="location.href = 'search#search';" class="btn btn-reset" value="<spring:message code="clear"/>">
                    </div>
                </div>
            </div>
        </div>
    </section>
</form:form>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>

</body>
