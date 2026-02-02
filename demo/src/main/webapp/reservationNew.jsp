<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<title><c:out
		value="${reservation != null ? 'Edit Reservation' : 'New Reservation'}" />
</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
	rel="stylesheet" />
</head>
<body class="p-4">

	<!-- UČITAJ LISTU TERENA DIREKTNO IZ SPRING KONTEKSTA -->
	<spring:eval expression="@fieldService.findAll()" var="fields" />


	<div class="container" style="max-width: 640px;">

		<h2 class="mb-3">
			Nova rezervacija
		</h2>

		<c:if test="${not empty error}">
			<div class="alert alert-danger">
				<c:out value="${error}" />
			</div>
		</c:if>

		<!-- Action: /reservations (create) -->
		<form method="post"
			action="/reservations">

			<!-- IZBOR TERENA -->
			<div class="mb-3">
				<label class="form-label">Field</label> <select name="fieldId"
					class="form-select" required>
					<option value="" disabled ${empty selectedFieldId ? 'selected' : ''}>-- choose field --</option>

				<c:forEach var="f" items="${fields}">
				  <option value="${f.id}" ${selectedFieldId == f.id ? 'selected' : ''}>
				    <c:out value="${f.name}" />
				    <c:if test='${not empty f.location}'> — <c:out value="${f.location}" /></c:if>
				  </option>
				</c:forEach>
				</select>
				<div class="form-text">
					<small>Fields loaded: <c:out value="${fn:length(fields)}" /></small>
				</div>
			</div>

			<!-- DATUM -->
			<div class="mb-3">
				<label class="form-label">Date</label> <input class="form-control" type="date" name="date"
       				required min="${today}" value="${today}" />

			</div>

			<!-- START -->
			<div class="mb-3">
				<label class="form-label">Start time</label> <input class="form-control" type="time" 
				name="startTime" required value="${defaultStart}" />
			</div>

			<!-- END -->
			<div class="mb-3">
				<label class="form-label">End time</label> <input class="form-control" type="time" 
				name="endTime"   required value="${defaultEnd}" />
			</div>

			<!-- NAPOMENA -->
			<div class="mb-3">
				<label class="form-label">Notes</label>
				<textarea class="form-control" name="notes" rows="3"><c:out
						value="${reservation != null ? reservation.notes : ''}" /></textarea>
			</div>

			<button class="btn btn-success">
				Rezerviši
			</button>
			<a class="btn btn-secondary"
				href="${pageContext.request.contextPath}/">Otkaži</a>
		</form>
	</div>
</body>
</html>
