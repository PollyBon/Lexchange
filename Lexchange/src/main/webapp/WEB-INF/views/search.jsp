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
                                        <c:if test="${online.contains(elem.id)}">
                                            <img src="resources/images/online.ico" class="online-icon"/>
                                        </c:if>
                                        <c:if test="${elem.haveInviteFrom(user.id)}">
                                            <img src="resources/images/invite.png" class="invite-icon"/>
                                        </c:if>
                                    </div>
                                    <div class="portfolio-view">
                                        <ul class="nav nav-pills">
                                            <li><a href="#dialog${elem.id}"><i class="fa fa-link"></i></a></li>
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
                                <div class="portfolio-info align-center">
                                    <h3><img src="resources/images/flags/${elem.country.toLowerCase()}.png" class="flag">
                                        ${elem.firstName} ${elem.lastName},
                                        <spring:message code="age"/>: ${elem.age}</h3>
                                    <h2><spring:message code="interested.language"/>:
                                        <c:forEach var="lang" items="${elem.interested}">
                                            ${languages[1].getByCode(lang)}
                                        </c:forEach>
                                    </h2>
                                </div>
                            </div>
                        </div>
                        <a href="#x" class="overlay" id="dialog${elem.id}"></a>
                        <div class="popup">
                            <div class="col-md-6 align-center">
                                <c:choose>
                                    <c:when test="${empty elem.url}">
                                        <img src="resources/images/blank-avatar.jpg" class="avatar">
                                    </c:when>
                                    <c:otherwise>
                                        <img src="${elem.url}" class="avatar">
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <div class="col-md-6">
                                <h3>${elem.firstName} ${elem.lastName} <spring:message code="from"/>
                                    <img src="resources/images/flags/${elem.country.toLowerCase()}.png" class="flag">
                                    ${countries[0].getByCode(elem.country)}
                                </h3>
                                <hr/>
                                <p class="header-3">
                                    <b><spring:message code="native.language"/>:</b> ${languages[1].getByCode(elem.nativeLanguage)}
                                </p>
                                <p class="header-3">
                                    <b><spring:message code="interested.language"/>:</b>
                                    <c:forEach var="lang" items="${elem.interested}">
                                        ${languages[0].getByCode(lang)}
                                    </c:forEach>
                                </p>
                                <p class="header-3">
                                    <b><spring:message code="email"/>:</b> ${elem.email}
                                </p>
                                <p class="header-3">
                                    <b><spring:message code="age"/>:</b> ${elem.age}
                                </p>
                                <hr/>
                                <div class="col-md-6">
                                    <b><spring:message code="music"/>:</b> ${elem.music}<br/>
                                    <b><spring:message code="sport"/>:</b> ${elem.sport}<br/>
                                    <b><spring:message code="art"/>:</b> ${elem.art}<br/>
                                    <b><spring:message code="trips"/>:</b> ${elem.trips}<br/>
                                </div>
                                <div class="col-md-6">
                                    <b><spring:message code="politics"/>:</b> ${elem.politics}<br/>
                                    <b><spring:message code="science"/>:</b> ${elem.science}<br/>
                                    <b><spring:message code="religion"/>:</b> ${elem.religion}<br/>
                                    <b><spring:message code="games"/>:</b> ${elem.games}<br/>
                                </div>
                            </div>
                            <div class="col-md-12">
                                <div class="padding-top-20">
                                    <button class="btn btn-submit" onclick="location.href = 'invite?id=${elem.id}'; return false;">
                                        <i class="fa fa-envelope-o"></i><spring:message code="invite"/>
                                    </button>
                                </div>
                            </div>
                            <a class="close" href="#close"></a>
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
