const API_URL = "/api";

// Referencias al DOM
const authSection = document.getElementById("auth-section");
const dashboardSection = document.getElementById("dashboard-section");
const loginForm = document.getElementById("login-form");
const registerForm = document.getElementById("register-form");
const tabLogin = document.getElementById("tab-login");
const tabRegister = document.getElementById("tab-register");
const authAlert = document.getElementById("auth-alert");

const loginEmailInput = document.getElementById("login-email");
const regEmailInput = document.getElementById("reg-email");

const userGreeting = document.getElementById("user-greeting");
const btnLogout = document.getElementById("btn-logout");
const tareaForm = document.getElementById("tarea-form");
const listaTareas = document.getElementById("lista-tareas");
const filtroEstado = document.getElementById("filtro-estado");

let usuarioActual = null;
let tareasCache = [];

// Helpers para anuncios y validación de correo
function mostrarAlerta(mensaje, tipo = "error") {
    authAlert.textContent = mensaje;
    authAlert.className = `alert alert-${tipo}`;
}

function ocultarAlerta() {
    authAlert.textContent = "";
    authAlert.className = "alert hidden";
    loginEmailInput.classList.remove("input-invalid");
    regEmailInput.classList.remove("input-invalid");
}

function esCorreoValido(email) {
    return /^[^\s@]+@[^\s@]+\.[^\s@]{2,}$/.test(email);
}

function validarCampoCorreo(input) {
    const valor = input.value.trim();
    if (valor.length > 0 && !esCorreoValido(valor)) {
        input.classList.add("input-invalid");
        mostrarAlerta("Correo inválido: asegúrate de incluir '@' y un dominio completo (ej. osmar@uv.mx).", "warning");
        return false;
    }
    input.classList.remove("input-invalid");
    return true;
}

// Aviso inmediato si el usuario escribe mal el correo y cambia de campo
loginEmailInput.addEventListener("blur", () => validarCampoCorreo(loginEmailInput));
regEmailInput.addEventListener("blur", () => validarCampoCorreo(regEmailInput));

// Verificar sesión activa al cargar
document.addEventListener("DOMContentLoaded", async () => {
    try {
        const res = await fetch(`${API_URL}/auth/me`);
        if (res.ok) {
            usuarioActual = await res.json();
            mostrarDashboard();
            return;
        }
    } catch (err) {
        // Sin sesión previa
    }
    mostrarAuth();
});

// Alternar pestañas Login / Registro
tabLogin.addEventListener("click", () => {
    tabLogin.classList.add("active");
    tabRegister.classList.remove("active");
    loginForm.classList.remove("hidden");
    registerForm.classList.add("hidden");
    ocultarAlerta();
});

tabRegister.addEventListener("click", () => {
    tabRegister.classList.add("active");
    tabLogin.classList.remove("active");
    registerForm.classList.remove("hidden");
    loginForm.classList.add("hidden");
    ocultarAlerta();
});

// Iniciar Sesión
loginForm.addEventListener("submit", async (e) => {
    e.preventDefault();
    ocultarAlerta();

    const email = loginEmailInput.value.trim();
    const password = document.getElementById("login-password").value;

    if (!email) {
        loginEmailInput.classList.add("input-invalid");
        mostrarAlerta("Ingresa tu correo electrónico para continuar.", "warning");
        return;
    }

    if (!esCorreoValido(email)) {
        loginEmailInput.classList.add("input-invalid");
        mostrarAlerta("Formato de correo incorrecto. Usa una dirección válida como osmar@uv.mx.", "warning");
        return;
    }

    if (!password) {
        mostrarAlerta("Ingresa tu contraseña.", "warning");
        return;
    }

    try {
        const res = await fetch(`${API_URL}/auth/login`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ email, password })
        });
        const data = await res.json();

        if (!res.ok) {
            mostrarAlerta(data.error || "Correo o contraseña incorrectos.", "error");
            return;
        }

        usuarioActual = data;
        loginForm.reset();
        mostrarDashboard();
    } catch (err) {
        mostrarAlerta("No se pudo conectar con el servidor.", "error");
    }
});

// Registrar Usuario
registerForm.addEventListener("submit", async (e) => {
    e.preventDefault();
    ocultarAlerta();

    const nombre = document.getElementById("reg-nombre").value.trim();
    const email = regEmailInput.value.trim();
    const password = document.getElementById("reg-password").value;

    if (!nombre) {
        mostrarAlerta("Escribe tu nombre para crear la cuenta.", "warning");
        return;
    }

    if (!esCorreoValido(email)) {
        regEmailInput.classList.add("input-invalid");
        mostrarAlerta("El correo no tiene un formato válido (ej. osmar@uv.mx).", "warning");
        return;
    }

    if (password.length < 4) {
        mostrarAlerta("La contraseña debe tener al menos 4 caracteres.", "warning");
        return;
    }

    try {
        const res = await fetch(`${API_URL}/auth/register`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({
                nombre,
                email,
                password_hash: password
            })
        });
        const data = await res.json();

        if (!res.ok) {
            mostrarAlerta(data.error || "No se pudo registrar el usuario.", "error");
            return;
        }

        // Iniciar sesión automáticamente para generar la cookie JSESSIONID
        const loginRes = await fetch(`${API_URL}/auth/login`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ email, password })
        });

        if (loginRes.ok) {
            usuarioActual = await loginRes.json();
            registerForm.reset();
            mostrarDashboard();
        }
    } catch (err) {
        mostrarAlerta("Error de conexión al registrar.", "error");
    }
});

// Cerrar Sesión (corregido sin referencias indefinidas)
btnLogout.addEventListener("click", async () => {
    try {
        await fetch(`${API_URL}/auth/logout`, { method: "POST" });
    } catch (err) {
        // Ignorar fallo de red al salir
    } finally {
        usuarioActual = null;
        tareasCache = [];
        ocultarAlerta();
        mostrarAuth();
    }
});

function mostrarAuth() {
    dashboardSection.classList.add("hidden");
    authSection.classList.remove("hidden");
}

function mostrarDashboard() {
    ocultarAlerta();
    authSection.classList.add("hidden");
    dashboardSection.classList.remove("hidden");
    userGreeting.textContent = `Hola, ${usuarioActual.nombre}`;
    cargarTareas();
}

// Obtener Tareas (GET)
async function cargarTareas() {
    try {
        const res = await fetch(`${API_URL}/tareas`);
        if (res.status === 401) {
            mostrarAuth();
            return;
        }
        tareasCache = await res.json();
        renderizarTareas();
    } catch (err) {
        listaTareas.innerHTML = `<p class="alert alert-error">Error al cargar las tareas.</p>`;
    }
}

// Renderizar Tareas
function renderizarTareas() {
    const filtro = filtroEstado.value;
    const filtradas = tareasCache.filter(t => filtro === "todas" || t.estado === filtro);

    if (filtradas.length === 0) {
        listaTareas.innerHTML = `<p style="color: var(--text-muted);">No hay tareas para mostrar.</p>`;
        return;
    }

    listaTareas.innerHTML = filtradas.map(t => {
        const id = t.id_tarea || t.idTarea;
        const fechaRaw = t.fecha_limite || t.fechaLimite || "";
        const fechaFormateada = fechaRaw ? fechaRaw.replace("T", " ") : "Sin fecha";
        const esCompletada = t.estado === "completada";

        return `
            <div class="tarea-item ${esCompletada ? "completada" : ""}">
                <div class="tarea-info">
                    <span class="tarea-materia">${escapeHtml(t.materia)}</span>
                    <span class="badge ${esCompletada ? "badge-completada" : "badge-pendiente"}">
                        ${t.estado}
                    </span>
                    <h3>${escapeHtml(t.titulo)}</h3>
                    <span class="tarea-fecha">Entrega: ${escapeHtml(fechaFormateada)}</span>
                </div>
                <div class="tarea-actions">
                    <button class="btn btn-success" onclick="alternarEstado('${id}', '${t.estado}')">
                        ${esCompletada ? "Reabrir" : "Completar"}
                    </button>
                    <button class="btn btn-danger" onclick="eliminarTarea('${id}')">
                        Eliminar
                    </button>
                </div>
            </div>
        `;
    }).join("");
}

filtroEstado.addEventListener("change", renderizarTareas);

// Crear Tarea (POST)
tareaForm.addEventListener("submit", async (e) => {
    e.preventDefault();

    const nuevaTarea = {
        materia: document.getElementById("materia").value.trim(),
        titulo: document.getElementById("titulo").value.trim(),
        fecha_limite: document.getElementById("fecha_limite").value,
        estado: "pendiente"
    };

    try {
        const res = await fetch(`${API_URL}/tareas`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(nuevaTarea)
        });

        if (res.status === 401) {
            mostrarAuth();
            return;
        }

        if (res.ok) {
            tareaForm.reset();
            cargarTareas();
        }
    } catch (err) {
        alert("No se pudo guardar la tarea.");
    }
});

// Actualizar Estado (PUT)
async function alternarEstado(id, estadoActual) {
    const nuevoEstado = estadoActual === "pendiente" ? "completada" : "pendiente";

    try {
        const res = await fetch(`${API_URL}/tareas/${id}`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ estado: nuevoEstado })
        });

        if (res.ok) {
            cargarTareas();
        }
    } catch (err) {
        alert("Error al actualizar la tarea.");
    }
}

// Eliminar Tarea (DELETE)
async function eliminarTarea(id) {
    try {
        const res = await fetch(`${API_URL}/tareas/${id}`, {
            method: "DELETE"
        });

        if (res.ok) {
            cargarTareas();
        }
    } catch (err) {
        alert("Error al eliminar la tarea.");
    }
}

function escapeHtml(text) {
    if (!text) return "";
    return text
        .replace(/&/g, "&amp;")
        .replace(/</g, "&lt;")
        .replace(/>/g, "&gt;")
        .replace(/"/g, "&quot;");
}