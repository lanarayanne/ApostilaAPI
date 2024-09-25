// modal item no html para cadastrar
var openModalBtnItem = document.getElementById("openModalBtnItem");
var modal = document.getElementById("modalItem");
var closeBtnItem = document.getElementById("closeItem");

openModalBtnItem.onclick = function () {
  modal.style.display = "block";
};

closeBtnItem.onclick = function () {
  modal.style.display = "none";
};

window.onclick = function (event) {
  if (event.target == modal) {
    modal.style.display = "none";
  }
};

// criar pedido e recuperar no html
function enviarPedido() {

    // const pedido = {
    //     name: "Nome",
    // };


  fetch("http://localhost:8080/pedido/create?userId=1", {
    
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    // body:JSON.stringify(pedido)
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error("Erro ao enviar pedido");
      }
      return response.json();
    })
    .then((data) => {
      console.log("Pedido enviado com sucesso:", data);
      window.location.reload();
    })
    .catch((error) => {
      console.error("Erro ao enviar pedido:", error);
    });
}

function buscarPedido() {
  fetch(`http://localhost:8080/pedido/all`)
    .then((response) => {
      if (!response.ok) {
        throw new Error("Erro ao buscar pedido");
      }
      return response.json();
    })
    .then((data) => {
        console.log("Dados recebidos:",data);

        if (!Array.isArray(data)){
            throw new Error("A respsta não é uma lista");
        }

        if (data.length === 0) {
            document.getElementById("respostaPedido").innerHTML = "Nenhum pedido encontrado.";
            return;
        }

      const pedidoId = document.getElementById("respostaPedido");
      pedidoId.innerHTML = "";

      data.forEach((pedido) => {
        var pedidoItem = document.createElement("div");
        pedidoItem.classList.add("cardPedido");

        var itensSolicitados = "<ul>";
        pedido.itemList.forEach((item) => {
          itensSolicitados += `<li>Nome: <p>${item.name}</p> | Preço: <p> R$ ${item.price},00</p></li>`;
        });
        itensSolicitados += "</ul>";

        pedidoItem.innerHTML = `
        <div>
            <span>Pedido de número: <p> ${pedido.id} </p> </span>
            <span>Itens nesse pedido: <p> ${itensSolicitados} </p> </span>
        </div>
      `;
        pedidoId.appendChild(pedidoItem);
      });
    })
    .catch((error) => {
      console.error("Erro ao buscar pedido:", error);
      document.getElementById("respostaPedido").innerHTML =
        "Erro ao buscar pedido. Por favor, tente novamente.";
    });
}

buscarPedido();

// criar item e recuperar no html
document
  .getElementById("criarItem")
  .addEventListener("submit", function (event) {
    event.preventDefault();

    const formData = new FormData(this);
    const data = {
      name: formData.get("name"),
      price: parseFloat(formData.get("price")),
    };

    fetch("http://localhost:8080/item/item/create", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(data),
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error("Erro ao criar item");
        }
        return response.json();
      })
      .then((data) => {
        console.log("Item criado com sucesso:", data);
        window.location.reload();
      })
      .catch((error) => {
        console.error("Erro:", error);
      });
  });

// buscar itens
function buscarItens() {
  fetch("http://localhost:8080/item/item/all")
    .then((response) => {
      if (!response.ok) {
        throw new Error("Erro ao buscar itens");
      }
      return response.json();
    })
    .then((data) => {
      const container = document.getElementById("container-itens");
      container.innerHTML = "";

      data.forEach((item) => {
        const itemDiv = document.createElement("div");
        itemDiv.classList.add("item");
        itemDiv.innerHTML = `
              <p>Nome: ${item.name}</p>
              <p>Preço: R$ ${item.price},00</p>
              <button class="btnAssociacao" onclick="associarItemAoPedido(${item.id})">Associar ao Pedido</button>
            `;
        container.appendChild(itemDiv);
      });
    })
    .catch((error) => {
      console.error("Erro ao buscar itens:", error);
      document.getElementById("container-itens").innerHTML =
        "Erro ao buscar itens. Por favor, tente novamente.";
    });
}

buscarItens();

// associar itens a pedidos
function associarItemAoPedido(itemId) {
  const pedidoId = prompt("Informe o ID do pedido para associar o item:");
  if (pedidoId) {
    fetch(
      `http://localhost:8080/pedido/addItem?pedidoId=${pedidoId}&itemId=${itemId}`,
      {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ itemId: itemId }),
      }
    )
      .then((response) => {
        if (!response.ok) {
          throw new Error("Erro ao associar item ao pedido");
        }
        return response.json();
      })
      .then((data) => {
        console.log("Item associado com sucesso:", data);
        alert("Item associado com sucesso!");
        window.location.reload();
      })
      .catch((error) => {
        console.error("Erro ao associar item ao pedido:", error);
        alert("Erro ao associar item ao pedido. Por favor, tente novamente.");
      });
  }
}