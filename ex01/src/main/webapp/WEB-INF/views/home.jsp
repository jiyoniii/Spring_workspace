<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	Hello world!  
</h1>
<!-- home controller에서 attribute로 servertime을 건네줌  -->
<P>  The time on the server is ${serverTime}. </P>
</body>
</html>
