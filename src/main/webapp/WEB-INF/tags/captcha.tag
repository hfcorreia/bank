
<%@ tag import="net.tanesha.recaptcha.ReCaptcha" %>
<%@ tag import="net.tanesha.recaptcha.ReCaptchaFactory" %>

<script type="text/javascript">var RecaptchaOptions = {theme : 'clean'};</script>
<%
    ReCaptcha reCaptcha = ReCaptchaFactory.newReCaptcha("6LfCqOsSAAAAAFw_B-OvMbsvEQJ4_BH-sndsu9E1",
                                                                "6LfCqOsSAAAAAN7_zHT3mPU_zd0hkcBJI9CG7zux", false);
    out.print(reCaptcha.createRecaptchaHtml(null, null));
%>
