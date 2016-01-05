<!DOCTYPE html>
<html lang="${locale.language}">
    <head>
        <%-- The title should ideally be less than 64 characters in length (http://www.w3.org/Provider/Style/TITLE.html). --%>
        <title><content:gettitle /> | LiteracyApp.org</title>

        <meta charset="UTF-8" />

        <meta name="viewport" content="width=device-width, initial-scale=1.0" />

        <%-- Google will only display the first 150 characters of the meta description in SERPs. --%>
        <meta name="description" content="We develop open source and scalable software that will enable children in developing countries to teach themselves basic reading, writing and arithmetic." />

        <link rel="shortcut icon" href="<spring:url value='/img/favicon.ico' />" />
        
        <meta property="og:image" content="<spring:url value='/img/logo-208x208.png' />" />
        <meta property="twitter:image" content="<spring:url value='/img/logo-208x208.png' />" />

        <%-- CSS --%>
        <link href="http://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet" />
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.3/css/materialize.min.css" />
        <link rel="stylesheet" href="<spring:url value='/css/styles.css?version=' /><content:getversion />" />
    </head>

    <body>
        <nav class="black lighten-1" role="navigation">
            <div class="nav-wrapper container">
              <a id="logo-container" href="<spring:url value='/' />" class="brand-logo">
                  <img src="<spring:url value='/img/logo-208x208.png' />" alt="LiteracyApp" />
                  Literacy<span>App</span>
              </a>
              <ul class="right hide-on-med-and-down">
                  <li><a href="http://eepurl.com/bGihkr"><fmt:message key="sign.on" /></a></li>
              </ul>

              <ul id="nav-mobile" class="side-nav">
                <li><a href="http://eepurl.com/bGihkr"><fmt:message key="sign.on" /></a></li>
              </ul>
              <a href="#" data-activates="nav-mobile" class="button-collapse"><i class="material-icons">menu</i></a>
            </div>
        </nav>
        <div class="section no-pad-bot" id="index-banner">
          <div class="container">
            <br />
            <br />
            <br />
            <br />
            <div class="row center">
                <h1 class="header center white-text">Literacy<span>App</span></h1>
            </div>
            <div class="row center">
                <div class="col s2">&nbsp;</div>
                <h5 class="header col s8 light white-text"><fmt:message key="frontpage.subtitle" /></h5>
                <div class="col s2">&nbsp;</div>
            </div>
            <div class="row center">
              <a href="http://eepurl.com/bGihkr" id="join-button-top" class="btn-large waves-effect waves-light"><fmt:message key="join.now" /></a>
            </div>
            <br />
          </div>
        </div>


        <div id="${cssId}" class="container <c:if test="${cssClass != null}">${cssClass}</c:if>">
            <content:getsection />
        </div>

        <footer class="page-footer">
          <div class="container">
            <div class="row">
              <div class="col l6 s12">
                <h5 class="white-text"><fmt:message key="about" /> LiteracyApp</h5>
                <p class="grey-text text-lighten-4">We develop open source and scalable software that will enable children in developing countries to teach themselves basic reading, writing and arithmetic.</p>
                <p class="grey-text text-lighten-4"><fmt:message key="see.our" /> <a class="white-text" href="https://github.com/literacyapp-org"><fmt:message key="github.repository" /></a></p>
              </div>
              <div class="col l3 s12">
                <h5 class="white-text"><fmt:message key="join.the.community" /></h5>
                <p class="grey-text text-lighten-4">
                  Sign up to our mailing list to be informed about updates.
                </p>
                <a class="btn waves-effect waves-light red lighten-3" target="_blank" href="http://eepurl.com/bGihkr">
                    Subscribe<i class="material-icons right">mail</i>
                </a>
              </div>
              <div class="col l2 s12 right">
                <h5 class="white-text"><fmt:message key="connect" /></h5>
                <ul>
                    <li><a class="white-text" href="http://blog.literacyapp.org" target="_blank">Blog</a></li>
                    <li><a class="white-text" href="https://twitter.com/literacyapp" target="_blank">Twitter</a></li>
                    <li><a class="white-text" href="https://www.facebook.com/LiteracyApp-1517752825212045" target="_blank">Facebook</a></li>
                    <li><a class="white-text" href="https://plus.google.com/b/115775489057320481208/+LiteracyAppOrgCommunity" target="_blank">Google+</a></li>
                </ul>
              </div>
            </div>
          </div>
          <div class="footer-copyright">
            <div class="container">
                <div class="row">
                    <div class="col s6">
                        <a class="white-text" href="http://www.apache.org/licenses/LICENSE-2.0">
                            Apache License, 2.0
                        </a>
                    </div>
                    <div class="col s6">
                        <fmt:message key="switch.language" />: 
                        <a class="white-text" href="<spring:url value='/?lang=en' />">
                            <fmt:message key="language.en" />
                        </a> | 
                        <a class="white-text" href="<spring:url value='/?lang=es' />">
                            <fmt:message key="language.es" />
                        </a> | 
                        <a class="white-text" href="<spring:url value='/?lang=ar' />">
                            <fmt:message key="language.ar" />
                        </a> | 
                        <a class="white-text" href="<spring:url value='/?lang=sw' />">
                            <fmt:message key="language.sw" />
                        </a>
                    </div>
                </div>
            </div>
          </div>
        </footer>
        
        <%-- JavaScripts --%>
        <script src="https://code.jquery.com/jquery-2.1.4.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.3/js/materialize.min.js"></script>
        <script src="<spring:url value='/js/init.js' />"></script>
        <%@ include file="/WEB-INF/jsp/error/javascript-error.jsp" %>
        <%@ include file="/WEB-INF/jsp/google-analytics.jsp" %>
        
        <%-- HelpScout Beacon --%>
        <script>!function(e,o,n){window.HSCW=o,window.HS=n,n.beacon=n.beacon||{};var t=n.beacon;t.userConfig={},t.readyQueue=[],t.config=function(e){this.userConfig=e},t.ready=function(e){this.readyQueue.push(e)},o.config={docs:{enabled:!1,baseUrl:""},contact:{enabled:!0,formId:"317454f3-8c8e-11e5-9e75-0a7d6919297d"}};var r=e.getElementsByTagName("script")[0],c=e.createElement("script");c.type="text/javascript",c.async=!0,c.src="https://djtflbt20bdde.cloudfront.net/",r.parentNode.insertBefore(c,r)}(document,window.HSCW||{},window.HS||{});</script>
    </body>
</html>
