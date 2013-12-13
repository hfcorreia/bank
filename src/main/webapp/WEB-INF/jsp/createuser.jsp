<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<html>
<head>
<!-- Javascript for password confirmation -->
<script>

</script>
</head>
<body>
<h2>Create User</h2>
<c:if test="${not empty error}">
		<div class="errorblock">
			<h1>${errorMsg}</h1>
		</div>
	</c:if>
<form method="post" action="<c:url value='/createuser/submit' />" onsubmit="return validate(this);">

    <table>
    <tr>
        <td><label>UserName</label></td>
        <td><input name="username" type="text" size="10" /></td>
    </tr>
    <tr>
        <td><label>Password</label></td>
        <td><input name="password" type="password" size="10"  /></td>
    </tr>
    <tr>
        <td><label>Confirm Password</label></td>
        <td><input name="passwordConfirm" type="password" size="10"  /></td>
    </tr>
    <tr>
        <td><label>Email</label></td>
        <td><input name="email" type="email" size ="10"/></td>
    </tr>
    <tr>
        <td><label>Role (user/admin)</label></td>
        <td><input name="role" type="text" size ="10"/></td>
        </tr>
    <tr>
        <td>
        	<input type="submit" value="submit"/>
        </td>
    </tr>
</table>
</form>

</body>
</html>