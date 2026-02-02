<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<title>Create Field</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
	rel="stylesheet" />
</head>
<body class="p-4">
	<div class="container" style="max-width: 640px;">
		<h2 class="mb-3">Dodaj teren</h2>

		<form action="${pageContext.request.contextPath}/fields" method="post">
			<div class="mb-3">
				<label class="form-label">Naizv</label> <input class="form-control"
					name="name" required placeholder="Naizv terena">
			</div>

			<div class="mb-3">
				<label class="form-label">Lokacija</label> <input
					class="form-control" name="location" placeholder="Lokacija terena">
			</div>

			<div class="mb-3">
				<label class="form-label">Sport</label> <input class="form-control"
					name="type" placeholder="Tenis / Fudbal / Košarka" />
			</div>

			<!-- VRAĆENO: CENA PO SATU -->
			<div class="mb-3">
				<label class="form-label">Cena po satu</label>
				<div class="input-group">
					<input class="form-control" type="number" name="pricePerHour"
						required min="0" placeholder="0" /> <span class="input-group-text">RSD</span>
				</div>
			</div>

			<div class="mb-3 form-check">
				<input class="form-check-input" type="checkbox" name="indoor"
					id="indoorChk" /> <label class="form-check-label" for="indoorChk">indoor</label>
			</div>

			<div class="d-flex gap-2">
				<button class="btn btn-success" type="submit">Save</button>
				<a class="btn btn-secondary"
					href="${pageContext.request.contextPath}/">Cancel</a>
			</div>
		</form>
	</div>
</body>
</html>
