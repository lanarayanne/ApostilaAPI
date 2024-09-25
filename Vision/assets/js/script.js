var formCadastro = document.getElementById("cadastro");

formCadastro.addEventListener("submit", function (e) {
  e.preventDefault();

  var name = document.getElementById("name").value;
  var password = document.getElementById("password").value;
  var email = document.getElementById("email").value;

  fetch("http://localhost:8080/user/create", {
    method: "POST",
    body: JSON.stringify({
      name: name,
      password: password,
      email: email,
    }),
    headers: {
      "Content-Type": "application/json; charset=UTF-8",
    },
  })
    .then(function (response) {
      return response.json();
    })
    .then(function (data) {
      alert('Cadastro feito com sucesso! Vamos pra página principal :D');
      window.location.href = '../pages/main.html';
    })
    .catch((error) => console.error("Error:", error));
});