<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib tagdir='/WEB-INF/tags' prefix='sc'%>
<html>
<head>
<title>Login Page</title>
<style>
.errorblock {
	color: #ff0000;
	background-color: #ffEEEE;
	border: 3px solid #ff0000;
	padding: 8px;
	margin: 16px;
}
</style>
</head>
<body onload='document.f.j_username.focus();'>
<h1>Login</h1>
<div id="login-error">${error}</div>
<c:url value="/j_spring_security_check" var="secureUrl"/>
<form action="${secureUrl}" method="post" >
    <table>
			<tr>
				<td>User:</td>
				<td><input type='text' name='j_username' value=''>
				</td>
			</tr>
			<tr>
				<td>Password:</td>
				<td><input type='password' name='j_password' />
				</td>
			</tr>
			<tr>
                <td colspan='2'>
                    <sc:captcha/>
                </tr>
			<tr>
				<td colspan='2'><input name="submit" type="submit"
					value=" Submit " />
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
