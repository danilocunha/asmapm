<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<sql:query var="rs" dataSource="jdbc/SisnewsInternetDS1">
select top 10 from enquete
</sql:query>

<html>
  <head>
    <title>DB Test</title>
  </head>
  <body>

  <h2>Results</h2>
  
<c:forEach var="row" items="${rs.rows}">
    Foo ${row.ideEnquete}<br/>
    Bar ${row.texTitulo}<br/>
</c:forEach>

  </body>
</html>