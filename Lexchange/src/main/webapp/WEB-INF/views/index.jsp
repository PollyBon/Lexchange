<%@ include file="/WEB-INF/jspf/taglib.jspf" %>
<c:set var="title" value="Home" />
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>

<%@ include file="/WEB-INF/jspf/header.jspf" %>

<section id="home-slider">
    <div class="container">
        <div class="row">
            <div class="main-slider">
                <div class="slide-text">
                    <h1><spring:message code="we_everywhere"/></h1>
                    <p><spring:message code="we_everywhere_text"/></p>
                    <a href="#footer" class="btn btn-common"><spring:message code="sign_up"/></a>
                </div>
                <img src="resources/images/home/slider/hill.png" class="slider-hill">
                <img src="resources/images/home/slider/house.png" class="slider-house">
                <img src="resources/images/home/slider/sun.png" class="slider-sun">
                <img src="resources/images/home/slider/birds1.png" class="slider-birds1">
                <img src="resources/images/home/slider/birds2.png" class="slider-birds2">
            </div>
        </div>
    </div>
    <div class="preloader"><i class="fa fa-sun-o fa-spin"></i></div>
</section>

<section id="services">
    <div class="container">
        <div class="row">
            <div class="col-sm-4 text-center padding wow fadeIn" data-wow-duration="1000ms" data-wow-delay="300ms">
                <div class="single-service">
                    <div class="wow scaleIn" data-wow-duration="500ms" data-wow-delay="300ms">
                        <img src="resources/images/home/icon1.png" >
                    </div>
                    <h2><spring:message code="real_people"/></h2>
                    <p><spring:message code="real_people_text"/></p>
                </div>
            </div>
            <div class="col-sm-4 text-center padding wow fadeIn" data-wow-duration="1000ms" data-wow-delay="600ms">
                <div class="single-service">
                    <div class="wow scaleIn" data-wow-duration="500ms" data-wow-delay="600ms">
                        <img src="resources/images/home/icon2.png" >
                    </div>
                    <h2><spring:message code="effective_studying"/></h2>
                    <p><spring:message code="effective_studying_text"/></p>
                </div>
            </div>
            <div class="col-sm-4 text-center padding wow fadeIn" data-wow-duration="1000ms" data-wow-delay="900ms">
                <div class="single-service">
                    <div class="wow scaleIn" data-wow-duration="500ms" data-wow-delay="900ms">
                        <img src="resources/images/home/icon3.png" >
                    </div>
                    <h2><spring:message code="you_boss"/></h2>
                    <p><spring:message code="you_boss_text"/></p>
                </div>
            </div>
        </div>
    </div>
</section>

<section id="action">
    <div class="vertical-center">
        <div class="container">
            <div class="row">
                <div class="action count">
                    <div class="col-sm-4 text-center wow bounceIn" data-wow-duration="5000ms" data-wow-delay="300ms">
                        <h1 class="timer bold" data-to="${members}" data-speed="5000" data-from="0"></h1>
                        <h3><spring:message code="members"/></h3>
                    </div>
                    <div class="col-sm-4 text-center wow bounceIn" data-wow-duration="5000ms" data-wow-delay="300ms">
                        <h1 class="timer bold" data-to="${fn:length(countries)}" data-speed="5000" data-from="0"></h1>
                        <h3><spring:message code="countries"/></h3>
                    </div>
                    <div class="col-sm-4 text-center wow bounceIn" data-wow-duration="5000ms" data-wow-delay="300ms">
                        <h1 class="timer bold" data-to="${fn:length(languages)}" data-speed="5000" data-from="0"></h1>
                        <h3><spring:message code="languages"/></h3>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

<section id="services">
    <div class="container">
        <div class="row">
            <div id="regions_div" class="col-sm-12 text-center padding wow fadeIn" data-wow-duration="1000ms"
                 data-wow-delay="300ms"></div>
        </div>
    </div>
</section>

<section id="action" class="responsive">
    <div class="vertical-center">
        <div class="container">
            <div class="row">
                <div class="action take-tour">
                    <div class="col-sm-7 wow fadeInLeft" data-wow-duration="500ms" data-wow-delay="300ms">
                        <h1 class="title"><spring:message code="meet_friends"/></h1>
                        <p><spring:message code="meet_friends_text"/></p>
                    </div>
                    <div class="col-sm-5 text-center wow fadeInRight" data-wow-duration="500ms" data-wow-delay="300ms">
                        <div class="tour-button">
                            <a href="https://www.momondo.ua" class="btn btn-common"><spring:message code="buy_ticket"/></a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

<section id="clients">
    <div class="container">
        <div class="row">
            <div class="col-sm-12">
                <div class="clients text-center wow fadeInUp" data-wow-duration="500ms" data-wow-delay="300ms">
                    <p><img src="resources/images/home/clients.png" class="img-responsive" alt=""></p>
                    <h1 class="title"><spring:message code="happy_clients"/></h1>
                    <p><spring:message code="happy_clients_text1"/><br><spring:message code="happy_clients_text2"/></p>
                </div>
            </div>
        </div>
    </div>
</section>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>

</body>
