<%@ include file="/WEB-INF/jspf/taglib.jspf" %>
<c:set var="title" value="Cabinet"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>

<%@ include file="/WEB-INF/jspf/header.jspf" %>


<section id="page-breadcrumb">
    <div class="vertical-center sun">
        <div class="container">
            <div class="row">
                <div class="action">
                    <div class="col-sm-12">
                        <h1 class="title"><spring:message code="comeon"/></h1>
                        <p><spring:message code="choose.chat"/></p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

<section id="services">
    <div class="container">
        <div class="row">
            <c:if test="${not empty chats}">
                <table class="table table-hover padding-top">
                    <thead>
                    <tr>
                        <th class="col-md-3"><h2><spring:message code="companion"/></h2></th>
                        <th class="col-md-7"><h2><spring:message code="last.msg"/></h2></th>
                        <th class="col-md-2"></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="elem" items="${chats.entrySet()}">
                        <tr>
                            <td onclick="location.href='enterChat?chatId=${elem.getKey().getId()}';">
                                <div class="col-md-3">
                                    <c:choose>
                                        <c:when test="${empty elem.getKey().getUsers().get(0).getUrl()}">
                                            <img src="resources/images/blank-avatar.jpg" class="avatar-ico"/>
                                        </c:when>
                                        <c:otherwise>
                                            <img src="${elem.getKey().getUsers().get(0).getUrl()}" class="avatar-ico"/>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <div class="col-md-9 inline header-3">
                                    <img src="resources/images/flags/${elem.getKey().getUsers().get(0).getCountry().toLowerCase()}.png"
                                         class="flag-mini"> ${elem.getKey().getUsers().get(0).getFirstName()} ${elem.getKey().getUsers().get(0).getLastName()}<br/>
                                        ${languages[1].getByCode(elem.getKey().getUsers().get(0).getNativeLanguage())}
                                </div>
                            </td>
                            <td onclick="location.href='enterChat?chatId=${elem.getKey().getId()}';">${elem.getValue().getContent()}</td>
                            <td>
                                <button class="btn btn-sm btn-warning"
                                        onclick="location.href = 'leave?id=${elem.getKey().getId()}'; return false;">
                                    <i class="fa fa-times"></i> <spring:message code="leave"/>
                                </button>
                                <button class="btn btn-sm btn-danger"
                                        onclick="location.href = 'complain?id=${elem.getKey().getId()}'; return false;">
                                    <i class="fa fa-exclamation-triangle"></i> <spring:message code="complain"/>
                                </button>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:if>
            <c:if test="${empty chats}">
                <h2 class="align-center padding-top"><spring:message code="empty.chats"/></h2>
            </c:if>
        </div>
    </div>
</section>

<section id="team">
    <div class="container">
        <div class="row">
            <c:if test="${not empty user.invites}">
                <h1 class="title text-center wow fadeInDown" data-wow-duration="500ms" data-wow-delay="300ms">
                    <spring:message code="invitations"/></h1>
                <p class="text-center wow fadeInDown" data-wow-duration="400ms" data-wow-delay="400ms"><spring:message
                        code="choose.friend"/></p>
                <div id="team-carousel" class="carousel slide wow fadeIn" data-ride="carousel" data-wow-duration="400ms"
                     data-wow-delay="400ms">
                    <ol class="carousel-indicators visible-xs">
                        <li data-target="#team-carousel" data-slide-to="0" class="active"></li>
                        <li data-target="#team-carousel" data-slide-to="1"></li>
                    </ol>
                    <div class="carousel-inner">
                        <div class="item active">
                            <c:forEach var="invite" items="${user.invites}" end="3">
                                <c:set var="elem" value="${invitations.get(invite.fromUserId)}"/>
                                <div class="col-sm-3 col-xs-6">
                                    <div class="team-single-wrapper">
                                        <div class="team-single">
                                            <div class="person-thumb">
                                                <c:choose>
                                                    <c:when test="${empty elem.url}">
                                                        <img src="resources/images/blank-avatar.jpg"
                                                             class="img-responsive avatar-chat"
                                                             style="border-color: ${searchBean.randomColor}">
                                                    </c:when>
                                                    <c:otherwise>
                                                        <img src="${elem.url}" class="img-responsive avatar-chat"
                                                             style="border-color: ${searchBean.randomColor}">
                                                    </c:otherwise>
                                                </c:choose>
                                                <c:if test="${online.contains(elem.id)}">
                                                    <img src="resources/images/online.ico" class="online-icon"/>
                                                </c:if>
                                            </div>
                                            <div class="social-profile">
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
                                                    <li><a href="decline?id=${invite.id}"><i
                                                            class="fa fa-thumbs-o-down"></i></a></li>
                                                </ul>
                                            </div>
                                        </div>
                                        <div class="person-info align-center">
                                            <h2><img src="resources/images/flags/${elem.country.toLowerCase()}.png"
                                                     class="flag">
                                                    ${elem.firstName} ${elem.lastName},
                                                <spring:message code="age"/>: ${elem.age}</h2>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                        <c:if test="${user.invites.size() gt 3}">
                            <div class="item">
                                <c:forEach var="invite" items="${user.invites}" begin="4">
                                    <c:set var="elem" value="${invitations.get(invite.fromUserId)}"/>
                                    <div class="col-sm-3 col-xs-6">
                                        <div class="team-single-wrapper">
                                            <div class="team-single">
                                                <div class="person-thumb">
                                                    <c:choose>
                                                        <c:when test="${empty elem.url}">
                                                            <img src="resources/images/blank-avatar.jpg"
                                                                 class="img-responsive avatar-chat"
                                                                 style="border-color: ${searchBean.randomColor}">
                                                        </c:when>
                                                        <c:otherwise>
                                                            <img src="${elem.url}" class="img-responsive avatar-chat"
                                                                 style="border-color: ${searchBean.randomColor}">
                                                        </c:otherwise>
                                                    </c:choose>
                                                    <c:if test="${online.contains(elem.id)}">
                                                        <img src="resources/images/online.ico" class="online-icon"/>
                                                    </c:if>
                                                </div>
                                                <div class="social-profile">
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
                                                        <li><a href="decline?id=${invite.id}"><i
                                                                class="fa fa-thumbs-o-down"></i></a></li>
                                                    </ul>
                                                </div>
                                            </div>
                                            <div class="person-info align-center">
                                                <h2><img src="resources/images/flags/${elem.country.toLowerCase()}.png"
                                                         class="flag">
                                                        ${elem.firstName} ${elem.lastName},
                                                    <spring:message code="age"/>: ${elem.age}</h2>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </c:if>
                    </div>

                    <a class="left team-carousel-control hidden-xs" href="#team-carousel" data-slide="prev">left</a>
                    <a class="right team-carousel-control hidden-xs" href="#team-carousel" data-slide="next">right</a>
                </div>
                <c:forEach var="invite" items="${user.invites}">
                    <c:set var="elem" value="${invitations.get(invite.fromUserId)}"/>
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
                                <b><spring:message
                                        code="native.language"/>:</b> ${languages[1].getByCode(elem.nativeLanguage)}
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
                                <button class="btn btn-submit"
                                        onclick="location.href = 'accept?id=${invite.id}'; return false;">
                                    <i class="fa fa-check-circle"></i><spring:message code="accept"/>
                                </button>
                            </div>
                        </div>
                        <a class="close" href="#close"></a>
                    </div>
                </c:forEach>
            </c:if>
            <c:if test="${empty user.invites}">
                <h2 class="align-center padding-top"><spring:message code="empty.invites"/></h2>
            </c:if>
        </div>
    </div>
</section>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>

</body>