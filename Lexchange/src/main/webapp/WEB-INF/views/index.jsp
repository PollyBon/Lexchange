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
                    <h1>We Are Everywhere!</h1>
                    <p>You can find friends all around the world and learn their language no matter who are you and where are you from.
                    Let's start your personal Language Exchange now!</p>
                    <a href="#footer" class="btn btn-common">SIGN UP</a>
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
                    <h2>Real People</h2>
                    <p>Meet new people all around the world and make new friends.</p>
                </div>
            </div>
            <div class="col-sm-4 text-center padding wow fadeIn" data-wow-duration="1000ms" data-wow-delay="600ms">
                <div class="single-service">
                    <div class="wow scaleIn" data-wow-duration="500ms" data-wow-delay="600ms">
                        <img src="resources/images/home/icon2.png" >
                    </div>
                    <h2>Effective Studying</h2>
                    <p>Study language that you are interested with native speakers fo free!</p>
                </div>
            </div>
            <div class="col-sm-4 text-center padding wow fadeIn" data-wow-duration="1000ms" data-wow-delay="900ms">
                <div class="single-service">
                    <div class="wow scaleIn" data-wow-duration="500ms" data-wow-delay="900ms">
                        <img src="resources/images/home/icon3.png" >
                    </div>
                    <h2>You Are The Boss</h2>
                    <p>Only you can decide how often your lessons will be. Time is no longer a problem!</p>
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
                        <h1 class="title">Meet your friends in real</h1>
                        <p>Our clients can buy aircraft tickets with 25% off</p>
                    </div>
                    <div class="col-sm-5 text-center wow fadeInRight" data-wow-duration="500ms" data-wow-delay="300ms">
                        <div class="tour-button">
                            <a href="https://www.momondo.ua" class="btn btn-common">Buy Ticket</a>
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
                    <h1 class="title">Happy Clients</h1>
                    <p>Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. <br> Ut enim ad minim veniam, quis nostrud </p>
                </div>
                <div class="clients-logo wow fadeIn" data-wow-duration="1000ms" data-wow-delay="600ms">
                    <div class="col-xs-3 col-sm-2">
                        <a href="#"><img src="resources/images/home/client1.png" class="img-responsive" alt=""></a>
                    </div>
                    <div class="col-xs-3 col-sm-2">
                        <a href="#"><img src="resources/images/home/client2.png" class="img-responsive" alt=""></a>
                    </div>
                    <div class="col-xs-3 col-sm-2">
                        <a href="#"><img src="resources/images/home/client3.png" class="img-responsive" alt=""></a>
                    </div>
                    <div class="col-xs-3 col-sm-2">
                        <a href="#"><img src="resources/images/home/client4.png" class="img-responsive" alt=""></a>
                    </div>
                    <div class="col-xs-3 col-sm-2">
                        <a href="#"><img src="resources/images/home/client5.png" class="img-responsive" alt=""></a>
                    </div>
                    <div class="col-xs-3 col-sm-2">
                        <a href="#"><img src="resources/images/home/client6.png" class="img-responsive" alt=""></a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>

</body>
