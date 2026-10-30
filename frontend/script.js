document.addEventListener("DOMContentLoaded", () => {
    const loginForm = document.getElementById("login-form");
    const message = document.getElementById("message");

    console.log("Formulário encontrado:", loginForm);

    if (!loginForm) {
        console.error("Não encontrei o formulário login-form!");
        return;
    }

    loginForm.addEventListener("submit", async (event) => {
        event.preventDefault();

        const email = document.getElementById("email").value.trim();
        const password = document.getElementById("password").value;

        const loginData = {
            email: email,
            password: password
        };

        message.textContent = "Realizando login...";

        try {
            const response = await fetch("http://localhost:8080/auth/login", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(loginData)
            });

            const data = await response.json();

            if (!response.ok) {
                message.textContent = data.message || "E-mail ou senha inválidos.";
                return;
            }

            if (!data.token) {
                message.textContent = "O servidor não retornou o token.";
                return;
            }

            sessionStorage.setItem("token", data.token);
            sessionStorage.setItem("expiration", data.expiration);

            message.textContent = "Login realizado com sucesso!";

            window.location.href = "pages/dashboard.html";

        } catch (error) {
            message.textContent = "Não foi possível conectar ao servidor.";
            console.error("Erro na requisição:", error);
        }
    });
});