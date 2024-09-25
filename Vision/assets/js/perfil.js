function getUserNameFromServer() {
    fetch("http://localhost:8080/1")
      .then((response) => response.json())
      .then((data) => {
        document.getElementById("userName").innerText = data.name;
  
        document.getElementById("id").value = data.id;
        document.getElementById("name").value = data.name;
        document.getElementById("email").value = data.email;
      })
      .catch((error) => {
        console.error("Erro ao obter o nome do usuário:", error);
      });
  }
  
  document.addEventListener("DOMContentLoaded", getUserNameFromServer);
  
  document.getElementById("alterar").addEventListener("submit", function (event) {
    event.preventDefault();
  
    var id = document.getElementById("id").value;
    var name = document.getElementById("name").value;
    var email = document.getElementById("email").value;
    var password = document.getElementById("password").value;
    var passwordNew = document.getElementById("passwordNew").value;
    var passwordConfirmation = document.getElementById(
      "passwordConfirmation"
    ).value;
  
    if (passwordNew !== passwordConfirmation) {
      alert("A nova senha e a confirmação não coincidem.");
      return;
    }
  
    var userData = {
      id: id,
      name: name,
      email: email,
      password: passwordNew,
    };
  
    fetch("http://localhost:8080/update", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(userData),
    })
      .then((response) => {
        if (response.ok) {
          alert("Dados alterados com sucesso! :D");
          window.location.href = "../pages/main.html";
        } else {
          alert("Erro ao alterar os dados. Por favor, tente novamente. D:");
        }
      })
      .catch((error) => {
        console.error("Erro ao fazer requisição:", error);
        alert("Erro ao fazer requisição. Por favor, tente novamente mais tarde.");
      });
  });
  
  document.getElementById("deletar").addEventListener("submit", function (event) {
    event.preventDefault();
  
    fetch("http://localhost:8080/1", {
      method: "DELETE",
      headers: {
        "Content-Type": "application/json",
      }
    })
      .then((response) => {
        if (response.ok) {
          alert("Conta deletada com sucesso.");
          window.location.href = "../../index.html";
        } else {
          alert("Erro ao deletar conta.");
        }
      })
      .catch((error) => {
        console.error("Erro ao fazer requisição:", error);
        alert("Erro ao fazer requisição. Por favor, tente novamente mais tarde.");
      });
  });