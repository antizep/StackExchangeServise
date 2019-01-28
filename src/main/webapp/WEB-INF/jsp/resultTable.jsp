<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Result Table</title>
</head>
<body>
	
	<c:choose>
	   <c:when test="${empty error}">
			<table border="1">
		        <tr>
		          <th>Date Question</th>
		          <th>Title</th>
		          <th>Who Posted</th>
		          <th>link</th>
		        </tr>
		                
		        <c:forEach var ="res"  items="${results}" >
			        <tr>
			          <td>${res.dateQuestion}</td>
			          <td>${res.title}</td>
			          <td>${res.whoPosted}</td>
			          <td><a href="${res.link}">${res.link}</a></td>
			        </tr>
		        </c:forEach>
      		</table>	   		
	   </c:when> 

   		<c:otherwise>
			Error: ${error}
		</c:otherwise>   
	</c:choose>
</body>
</html>