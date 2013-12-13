<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<body>
    <h2> Users list <h2>
    <p> ${accounts_list} </p>
    
	<a href="<c:url value='/transfer' />">Go to Transfer</a>

</body>
</html>