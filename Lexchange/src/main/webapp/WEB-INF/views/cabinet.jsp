<%@ include file="/WEB-INF/jspf/taglib.jspf" %>
<c:set var="title" value="Home"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>

<%@ include file="/WEB-INF/jspf/header.jspf" %>

<section id="home-slider">
    <div class="container">
        <form:form method="POST" modelAttribute="appUser">
            <form:input type="hidden" path="id" id="id"/>
            <div class="col-md-4">
                <h2 class="align-right">Nice to meet you !</h2>
                <div class="form-group">
                    <form:input path="firstName" id="firstName" class="form-control" required="required"
                                placeholder="Name"/>
                    <form:errors path="firstName" cssClass="error"/>
                </div>
                <div class="form-group">
                    <form:input path="url" id="url" class="form-control" required="required" placeholder="Avatar"/>
                    <form:errors path="url" cssClass="error"/>
                </div>
                <div class="form-group">
                    <form:select path="nativeLanguage" id="nativeLanguage" class="form-control">
                        <c:forEach var="elem" items="${languages}">
                            <option value="${elem.code}"
                                    <c:if test="${elem.code.equals(appUser.nativeLanguage)}">
                                        selected
                                    </c:if>
                            > ${elem.name} </option>
                        </c:forEach>
                    </form:select>
                </div>
                <div class="form-group">
                    <form:input type="password" path="password" id="password" class="form-control" required="required"
                                placeholder="Password"/>
                    <form:errors path="password" cssClass="error"/>
                </div>
                <div class="form-group">
                    <input type="reset" name="submit" class="btn btn-reset" value="Clear">
                </div>
            </div>
            <div class="col-md-4">
                <div class="form-group">
                    <form:input type="email" path="email" id="email" class="form-control" required="required"
                                placeholder="Email"/>
                    <form:errors path="email" cssClass="error"/>
                </div>
                <div class="form-group">
                    <form:input path="lastName" id="lastName" class="form-control" required="required"
                                placeholder="Surname"/>
                    <form:errors path="lastName" cssClass="error"/>
                </div>
                <div class="form-group">
                    <form:input type="date" path="birthDate" id="birthDate" class="form-control" required="required"
                                placeholder="Birth date"/>
                    <form:errors path="birthDate" cssClass="error"/>
                </div>
                <div class="form-group">
                    <form:select path="country" id="country" class="form-control">
                        <c:forEach var="elem" items="${countries}">
                            <option value="${elem.code}"
                                    <c:if test="${elem.code.equals(appUser.country)}">
                                        selected
                                    </c:if>
                            >${elem.name} </option>
                        </c:forEach>
                    </form:select>
                </div>
                <div class="form-group">
                    <form:input type="password" path="rePassword" id="rePassword" class="form-control"
                                required="required" placeholder="Repeat password"/>
                    <form:errors path="rePassword" cssClass="error"/>
                </div>
                <div class="form-group">
                    <input type="submit" name="submit" class="btn btn-submit" value="Register">
                </div>
            </div>
            <div class="col-md-4 align-center">

                <c:choose>
                    <c:when test="${empty appUser.url}">
                        <img src="resources/images/blank-avatar.jpg" class="avatar">
                    </c:when>
                    <c:otherwise>
                        <img src="${appUser.url}" class="avatar">
                    </c:otherwise>
                </c:choose>
            </div>
        </form:form>
    </div>
    <div class="preloader"><i class="fa fa-sun-o fa-spin"></i></div>
</section>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>

</body>
</html>
