<%@ page contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<title>Razgovor sa ${friend.username}</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
	rel="stylesheet" />
<style>
.chat-box {
	height: 400px;
	overflow-y: auto;
	background: #f8f9fa;
	border-radius: 10px;
	padding: 15px;
}

.message {
	margin-bottom: 12px;
}

.message .bubble {
	display: inline-block;
	padding: 10px 15px;
	border-radius: 15px;
	max-width: 70%;
	word-wrap: break-word;
}

.message.you .bubble {
	background: #0d6efd;
	color: white;
	margin-left: auto;
}

.message.them .bubble {
	background: #e9ecef;
	color: #212529;
}

.message small {
	display: block;
	font-size: 0.75rem;
	color: #6c757d;
	margin-top: 3px;
}
</style>
</head>

<body class="p-4 bg-light">
	<div class="container">

		<a class="btn btn-link mb-3"
			href="/friends">← Nazad</a>
		<h3 class="mb-3">
			Razgovor sa <b>${friend.username}</b>
		</h3>

		<!-- CHAT OKVIR -->
		<div class="chat-box mb-3 border">
			<c:forEach var="m" items="${messages}">
				<div
					class="message ${m.sender.id == sessionScope.user.id ? 'you text-end' : 'them text-start'}">
					<div class="bubble">${m.content}</div>
					<small>${fn:substring(m.createdAt.toLocalTime(), 0, 5)}</small>
				</div>
			</c:forEach>
		</div>

		<!-- FORMA ZA SLANJE PORUKE -->
		<form id="chatForm" method="post"
			action="/messages/${friend.id}"
			class="d-flex gap-2">
			<input type="text" name="content" class="form-control"
				placeholder="Napiši poruku..." required />
			<button class="btn btn-primary">Pošalji</button>
		</form>

		<!-- SKRIPTA KOJA SPRECAVA POVRATAK NA POCETAK CHAT-A NAKON SLANJA PORUKE -->
		<script>
			document.addEventListener("DOMContentLoaded", () => {
			  const chatBox = document.querySelector(".chat-box");
			  if (chatBox) chatBox.scrollTop = chatBox.scrollHeight;
			});
		</script>
	</div>
</body>
</html>
