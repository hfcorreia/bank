<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<html>
<body>
    <h2> Users list <h2>
    <p> ${users_list} </p>
	<a href="<c:url value='/createuser' />">Create an user!</a>
	<a href="<c:url value='/createaccount' />">Create an account!</a>
</body>
</html>