<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Transfer Page</title>
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
<body>
<h2>Transfer Money</h2>
<c:if test="${not empty error}">
		<div class="errorblock">
			<h1>Error Transferring money</h1>
		</div>
	</c:if>
<form method="post" action="<c:url value='/transfer/submit' />" >
   
    <table>
    <tr>
        <td><label path="accountNumber">Account Number</label></td>
        <td><input path="accountNumber" type="text" size="10" /></td> 
    </tr>
    <tr>
        <td><label path="accountNumber2">Account Number to Transfer</label></td>
        <td><input path="accountNumber2" type="text" size="10"  /></td> 
    </tr>
    <tr>
        <td><label path="amount">Amount</label></td>
        <td><input path="amount" type="text" size ="10"/></td>
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