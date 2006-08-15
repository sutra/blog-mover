<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<fmt:bundle basename="com.redv.blogremover.bsps.messages-writers">
	<ol>
		<c:forEach var="writer" items="${writers}" varStatus="status">
			<li>
				<input type="radio" name="writerId" id="writerId${status.count}" accept="${status.count}" value="${writer[0]}" onclick="return selectWriter(this.value, $('writerClassName${status.count}').value)" />
				<input type="hidden" name="writerClassName" id="writerClassName${status.count}" value="${writer[1]}" />
				<label for="writerId${status.count}">
					<fmt:message key="${writer[0]}" />
				</label>
			</li>
		</c:forEach>
	</ol>
</fmt:bundle>
