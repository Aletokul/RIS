<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<title>Izmeni teren</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
	rel="stylesheet" />
</head>
<body class="p-4">
	<div class="container" style="max-width: 640px;">

		<div class="d-flex justify-content-between align-items-center mb-3">
			<h2 class="mb-0">Izmeni teren</h2>
			<a class="btn btn-link" href="/fields">← Nazad na listu</a>
		</div>

		<c:if test="${not empty error}">
			<div class="alert alert-danger">${error}</div>
		</c:if>

		<!-- Akcija IDE NA /fields/{id}/edit (POST) -->
		<form method="post" action="/fields/${field.id}/edit" class="row g-3">

			<div class="col-12">
				<label class="form-label">Naziv</label> <input class="form-control"
					name="name" value="${field.name}" required />
			</div>

			<div class="col-12">
				<label class="form-label">Lokacija</label> <input
					class="form-control" name="location" value="${field.location}" />
			</div>

			<div class="col-md-6">
				<label class="form-label">Sport (tip)</label> <input
					class="form-control" name="type" value="${field.type}"
					placeholder="fudbal / kosarka / basket / tenis" />
			</div>

			<div class="col-md-6">
				<label class="form-label">Cena po satu</label>
				<div class="input-group">
					<input class="form-control" type="number" step="0.01" min="0"
						name="pricePerHour" value="${field.pricePerHour}" required /> <span
						class="input-group-text">RSD</span>
				</div>
			</div>

			<!-- Checkbox: pošalji i false i true da bind bude pouzdan -->
			<div class="col-12">
				<div class="form-check">
					<input class="form-check-input" type="checkbox" id="indoorChk"
						name="indoor" value="true"
						<c:if test="${field.indoor}">checked</c:if> /> <label
						class="form-check-label" for="indoorChk">U zatvorenom</label>
				</div>
			</div>

			<div class="col-12 d-flex gap-2">
				<button class="btn btn-primary" type="submit">Sačuvaj
					izmene</button>
				<a class="btn btn-secondary" href="/fields">Otkaži</a>
			</div>
		</form>

	</div>
</body>
</html>
