<%@ page contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<title>Tereni</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
	rel="stylesheet" />
</head>
<body class="p-4">

	<div class="container">
		<!-- HEADER: dodato + Novi teren (samo ADMIN) -->
		<div class="d-flex justify-content-between align-items-center mb-3">
			<h2 class="mb-0">Dostupni tereni</h2>
			<div class="d-flex gap-2">
				<a class="btn btn-link" href="/">← Početna</a>
				<c:if
					test="${not empty sessionScope.user and sessionScope.user.role == 'ADMIN'}">
					<a class="btn btn-primary" href="/fields/new">+ Novi teren</a>
				</c:if>
			</div>
		</div>

		<form method="get"
			class="d-flex align-items-center gap-2 mb-3 flex-wrap">
			<label for="type" class="mb-0">Filtriraj po sportu:</label> <select
				id="type" name="type" class="form-select w-auto">
				<option value="">Svi tereni</option>
				<option value="fudbal" ${param.type == 'fudbal' ? 'selected' : ''}>Fudbal</option>
				<option value="košarka" ${param.type == 'košarka' ? 'selected' : ''}>Košarka
					5v5</option>
				<option value="basket" ${param.type == 'basket' ? 'selected' : ''}>Basket
					3x3</option>
				<option value="tenis" ${param.type == 'tenis' ? 'selected' : ''}>Tenis</option>
			</select>
			<button type="submit" class="btn btn-primary">Filtriraj</button>
		</form>

		<c:choose>
			<c:when test="${empty fields}">
				<div class="alert alert-info">Trenutno nema definisanih
					terena.</div>
			</c:when>
			<c:otherwise>
				<div class="row g-3">
					<c:forEach var="f" items="${fields}">
						<div class="col-12 col-md-6 col-lg-4">
							<div class="card h-100">
								<div class="card-body">
									<h5 class="card-title mb-1">
										<c:out value="${f.name}" />
									</h5>

									<p class="text-muted mb-2">
										<c:out value="${empty f.location ? 'Nepoznato' : f.location}" />
									</p>

									<p class="mb-2">
										Cena po satu: <strong><c:out
												value="${f.pricePerHour}" /></strong>
									</p>

									<p class="mb-3">
										<small class="text-muted"> <c:out
												value="${empty f.type ? '' : f.type}" /> <c:if
												test="${f.indoor}"> — U zatvorenom</c:if> <c:if
												test="${not f.indoor}"> — Na otvorenom</c:if>
										</small>
									</p>

									<!-- Rezervacija ili login -->
									<div class="d-flex gap-2 mb-2">
										<c:choose>
											<c:when test="${empty sessionScope.user}">
												<a class="btn btn-outline-success" href="/login">
													Prijavi se da bi rezervisao </a>
											</c:when>
											<c:otherwise>
												<a class="btn btn-success"
													href="/reservations/new?fieldId=${f.id}"> Rezerviši </a>
											</c:otherwise>
										</c:choose>
									</div>

									<!-- ADMIN-only: Izmeni / Obriši -->
									<c:if
										test="${not empty sessionScope.user and sessionScope.user.role == 'ADMIN'}">
										<div class="d-flex gap-2 mb-2">
											<a class="btn btn-sm btn-outline-secondary"
												href="/fields/${f.id}/edit">Izmeni</a>

											<form action="/fields/${f.id}/delete" method="post"
												style="display: inline"
												onsubmit="return confirm('Obrisati teren #${f.id}?');">
												<button class="btn btn-sm btn-outline-danger" type="submit">Obriši</button>
											</form>
										</div>
									</c:if>

									<!-- ================== RECENZIJE ================== -->
									<spring:eval
										expression="@fieldReviewService.getReviewsForField(f.id)"
										var="reviews" />

									<h6 class="text-muted mb-2">Recenzije</h6>

									<c:if test="${empty reviews}">
										<div class="alert alert-light border small mb-3">Nema
											recenzija za ovaj teren.</div>
									</c:if>

									<!-- prikaz recenzija bez korisnika -->

									<c:forEach var="review" items="${reviews}">
										<div class="border rounded p-2 mb-2">
											<div
												class="d-flex justify-content-between align-items-center">
												<strong>${review.user.username}</strong> <span class="small">
													<c:forEach var="i" begin="1" end="5">
														<c:choose>
															<c:when test="${i <= review.rating}">★</c:when>
															<c:otherwise>☆</c:otherwise>
														</c:choose>
													</c:forEach> <span class="ms-1">${review.rating}/5</span>
												</span>
											</div>
											<c:if test="${not empty review.comment}">
												<div class="text-muted small mt-1">
													<em>${review.comment}</em>
												</div>
											</c:if>
										</div>
									</c:forEach>

									<c:if
										test="${not empty sessionScope.user and sessionScope.user.role.toString() == 'USER'}">

										<form action="/reviews/add" method="post" class="mt-2">
											<input type="hidden" name="fieldId" value="${f.id}" />

											<div class="row g-2 align-items-center">
												<div class="col-auto">
													<label class="form-label mb-0">Ocena:</label>
												</div>
												<div class="col-auto">
													<select name="rating"
														class="form-select form-select-sm w-auto" required>
														<c:forEach var="i" begin="1" end="5">
															<option value="${i}">${i}</option>
														</c:forEach>
													</select>
												</div>
											</div>

											<label class="form-label mt-2 mb-1">Komentar
												(opciono):</label>
											<textarea name="comment" rows="2"
												class="form-control form-control-sm mb-2"
												placeholder="Napiši kratak utisak..."></textarea>

											<button type="submit" class="btn btn-sm btn-outline-primary">Dodaj
												recenziju</button>
										</form>
									</c:if>

								</div>
							</div>
						</div>
					</c:forEach>
				</div>
			</c:otherwise>
		</c:choose>
	</div>

</body>
</html>
