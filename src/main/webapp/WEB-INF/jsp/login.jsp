<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib tagdir='/WEB-INF/tags' prefix='sc'%>
<style>
.error {
        color: #ff0000;
        background-color: #ffEEEE;
        border: 3px solid #ff0000;
        padding: 8px;
        margin: 16px;
}
</style>
<script type="text/javascript" src="https://code.jquery.com/jquery.min.js"></script>
<script type="text/javascript">
   $(document).ready(function(){
       if (window.location.href.substring(window.location.href.length - 28) == '?param.error=bad_credentials') {
           alert("Your login information was incorrect. Try again");
       }
       if (window.location.href.substring(window.location.href.length - 24) == '?param.error=bad_captcha') {
           alert("Entered text was incorrect. Try again");
       }
   });
</script>
<div id="content">
    <div id="login_div">
        <div id="login_form">
        <div id="login-error">${param.error}</div>
        <c:url value="/j_spring_security_check" var="secureUrl"/>
        <form id="login" action="<c:url value="${secureUrl}" />" method="post">
            <div class="formInfo">
                <c:if test="${param.error} eq 'bad_credentials'">
                <div class="error">
                    Your sign in information was incorrect.
                    Please try again</a>.
                </div>
                <c:if test="${param.error} eq 'bad_captcha'">
                        <div class="error">
                            Entered text was incorrect.
                                      Please try again</a>.
                        </div>
                        </c:if>
                </c:if>
                <div class="login_text"><b>Login</b></div>
                <input id="login" name="j_username" type="text" class="input"/></br></br>
                <div class="login_text"><b>Password</b></div>
                <input id="password" name="j_password" type="password" class="input"/></br></br>
                <div class="captcha"><sc:captcha/></div>
                <input type="submit" src="../resources/images/login_button.png" value="Login" id="login_button" class="button"/>
            </form>
        </div>
    </div>
</div>
