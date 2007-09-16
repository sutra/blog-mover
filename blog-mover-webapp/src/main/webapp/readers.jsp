<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<fmt:bundle basename="com.redv.blogmover.bsps.messages-readers">
	<ol>
		<c:forEach var="reader" items="${readers}" varStatus="status">
			<li>
				<input type="radio" name="readerId" id="readerId${status.count}" accesskey="${status.count}" value="${reader[0]}" onclick="return selectReader(this.value, $('readerClassName${status.count}').value)" tabindex="${status.count}" />
				<input type="hidden" name="readerClassName" id="readerClassName${status.count}" value="${reader[1]}" />
				<label for="readerId${status.count}">
					<fmt:message key="${reader[0]}" />
				</label>
			</li>
		</c:forEach>
	</ol>
</fmt:bundle>
