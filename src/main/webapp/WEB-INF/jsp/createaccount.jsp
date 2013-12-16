<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<html>
<head>
</head>
<body>
<h2>Create Account</h2>
<c:if test="${not empty error}">
		<div class="errorblock">
			<h1>${errorMsg}</h1>
		</div>
	</c:if>
<form method="post" action="<c:url value='/createaccount/submit' />" >

    <table>
    <tr>
        <td><label>Owner Username</label></td>
        <td><input name="owner" type="text" size="10" /></td>
    </tr>
    <tr>
        <td><label>Initial Balance</label></td>
        <td><input name="balance" type="number" size="10" step="any" min="1" /></td>
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