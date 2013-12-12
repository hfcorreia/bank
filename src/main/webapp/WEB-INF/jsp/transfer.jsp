<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

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
			<h1>Error ${errorMSG}</h1>
		</div>
</c:if>
<form method="post" action="<c:url value='/transfer/submit' />" >
   
    <table>
    <tr>
        <td><label>Account Number</label></td>
        <td><input name="accountNumber" type="text" size="10" /></td> 
    </tr>
    <tr>
        <td><label>Account Number to Transfer</label></td>
        <td><input name="accountNumber2" type="text" size="10"  /></td> 
    </tr>
    <tr>
        <td><label>Amount</label></td>
        <td><input name="amount" type="text" size ="10"/></td>
    </tr> 
     <tr>
        <td><label>${ROW1}${COL1} Number: ${NUM1}</label></td>
        <td><input name="matrix1" type="password" size="1" /></td> 
    </tr>
    <tr>
        <td><label>${ROW2}${COL2} Number: ${NUM2}</label></td>
        <td><input name="matrix2" type="password" size="1"  /></td> 
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