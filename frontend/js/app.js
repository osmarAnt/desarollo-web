const API_URL = "/api";

// Referencias Auth
const authSection = document.getElementById("auth-section");
const dashboardSection = document.getElementById("dashboard-section");
const loginForm = document.getElementById("login-form");
const registerForm = document.getElementById("register-form");
const tabLogin = document.getElementById("tab-login");
const tabRegister = document.getElementById("tab-register");
const authAlert = document.getElementById("auth-alert");
const loginEmailInput = document.getElementById("login-email");
const regEmailInput = document.getElementById("reg-email");

// Referencias Dashboard
const userGreeting = document.getElementById("user-greeting");
const btnLogout = document.getElementById("btn-logout");
const tareaForm = document.getElementById("tarea-form");
const listaTareas = document.getElementById("lista-tareas");
const busquedaInput = document.getElementById("busqueda");
const filtroMateria = document.getElementById("filtro-materia");
const filtroEstado = document.getElementById("filtro-estado");

// Referencias Modal y Toast
const editModal = document.getElementById("edit-modal");
const editForm = document.getElementById("edit-form");
const btnCancelEdit = document.getElementById("btn-cancel-edit");
const toastEl = document.getElementById("toast");

let usuarioActual = null;
let tareasCache = [];
let toastTimer = null;

// --- UTILIDADES ---

function mostrarToast(mensaje) {
  toastEl.textContent = mensaje;
  toastEl.classList.remove("hidden");
  clearTimeout(toastTimer);
  toastTimer = setTimeout(() => toastEl.classList.add("hidden"), 3000);
}

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
    mostrarAlerta(
      "Correo inválido: asegúrate de incluir '@' y un dominio completo (ej. osmar@uv.mx).",
      "warning",
    );
    return false;
  }
  input.classList.remove("input-invalid");
  return true;
}

function estaVencida(tarea) {
  if (tarea.estado === "completada" || !tarea.fecha_limite) return false;
  return new Date(tarea.fecha_limite) < new Date();
}

function escapeHtml(text) {
  if (!text) return "";
  return text
    .replace(/&/g, "&amp;")
    .replace(/</g, "&lt;")
    .replace(/>/g, "&gt;")
    .replace(/"/g, "&quot;");
}

// --- AUTENTICACIÓN ---

loginEmailInput.addEventListener("blur", () =>
  validarCampoCorreo(loginEmailInput),
);
regEmailInput.addEventListener("blur", () => validarCampoCorreo(regEmailInput));

document.addEventListener("DOMContentLoaded", async () => {
  try {
    const res = await fetch(`${API_URL}/auth/me`);
    if (res.ok) {
      usuarioActual = await res.json();
      mostrarDashboard();
      return;
    }
  } catch (err) {}
  mostrarAuth();
});

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

loginForm.addEventListener("submit", async (e) => {
  e.preventDefault();
  ocultarAlerta();

  const email = loginEmailInput.value.trim();
  const password = document.getElementById("login-password").value;

  if (!email || !esCorreoValido(email)) {
    loginEmailInput.classList.add("input-invalid");
    mostrarAlerta(
      "Ingresa un correo electrónico válido (ej. osmar@uv.mx).",
      "warning",
    );
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
      body: JSON.stringify({ email, password }),
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
    mostrarAlerta(
      "El correo no tiene un formato válido (ej. osmar@uv.mx).",
      "warning",
    );
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
      body: JSON.stringify({ nombre, email, password_hash: password }),
    });
    const data = await res.json();

    if (!res.ok) {
      mostrarAlerta(data.error || "No se pudo registrar el usuario.", "error");
      return;
    }

    const loginRes = await fetch(`${API_URL}/auth/login`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ email, password }),
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

btnLogout.addEventListener("click", async () => {
  try {
    await fetch(`${API_URL}/auth/logout`, { method: "POST" });
  } catch (err) {
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

// --- GESTIÓN DE TAREAS ---

async function cargarTareas() {
  try {
    const res = await fetch(`${API_URL}/tareas`);
    if (res.status === 401) {
      mostrarAuth();
      return;
    }
    tareasCache = await res.json();
    actualizarEstadisticas();
    actualizarSelectorMaterias();
    renderizarTareas();
  } catch (err) {
    listaTareas.innerHTML = `<p class="alert alert-error">Error al cargar las tareas.</p>`;
  }
}

function actualizarEstadisticas() {
  const total = tareasCache.length;
  const completadas = tareasCache.filter(
    (t) => t.estado === "completada",
  ).length;
  const vencidas = tareasCache.filter((t) => estaVencida(t)).length;
  const pendientes = total - completadas;

  document.getElementById("stat-total").textContent = total;
  document.getElementById("stat-pendientes").textContent = pendientes;
  document.getElementById("stat-vencidas").textContent = vencidas;
  document.getElementById("stat-completadas").textContent = completadas;
}

function actualizarSelectorMaterias() {
  const seleccionActual = filtroMateria.value;
  const materiasUnicas = [
    ...new Set(tareasCache.map((t) => t.materia).filter(Boolean)),
  ].sort();

  filtroMateria.innerHTML =
    `<option value="todas">Todas las materias</option>` +
    materiasUnicas
      .map((m) => `<option value="${escapeHtml(m)}">${escapeHtml(m)}</option>`)
      .join("");

  if (materiasUnicas.includes(seleccionActual)) {
    filtroMateria.value = seleccionActual;
  }

  const datalistMaterias = document.getElementById("materias-sugeridas");
  if (datalistMaterias) {
    datalistMaterias.innerHTML = materiasUnicas
      .map((m) => `<option value="${escapeHtml(m)}"></option>`)
      .join("");
  }
}

function renderizarTareas() {
  const texto = busquedaInput.value.trim().toLowerCase();
  const mat = filtroMateria.value;
  const est = filtroEstado.value;

  const filtradas = tareasCache
    .filter((t) => {
      const vencida = estaVencida(t);
      const coincideTexto =
        !texto ||
        t.titulo.toLowerCase().includes(texto) ||
        t.materia.toLowerCase().includes(texto);
      const coincideMateria = mat === "todas" || t.materia === mat;
      const coincideEstado =
        est === "todas" ||
        (est === "vencida" && vencida) ||
        (est === "pendiente" && t.estado === "pendiente") ||
        (est === "completada" && t.estado === "completada");
      return coincideTexto && coincideMateria && coincideEstado;
    })
    // Ordenar por fecha límite más próxima
    .sort(
      (a, b) => new Date(a.fecha_limite || 0) - new Date(b.fecha_limite || 0),
    );

  if (filtradas.length === 0) {
    listaTareas.innerHTML = `<p style="color: var(--text-muted);">No hay tareas que coincidan con los filtros.</p>`;
    return;
  }

  listaTareas.innerHTML = filtradas
    .map((t) => {
      const id = t.id_tarea;
      const fechaFormateada = t.fecha_limite
        ? t.fecha_limite.replace("T", " ")
        : "Sin fecha";
      const esCompletada = t.estado === "completada";
      const vencida = estaVencida(t);

      const claseItem = esCompletada ? "completada" : vencida ? "vencida" : "";
      const claseBadge = esCompletada
        ? "badge-completada"
        : vencida
          ? "badge-vencida"
          : "badge-pendiente";
      const textoBadge = esCompletada
        ? "completada"
        : vencida
          ? "vencida"
          : "pendiente";

      return `
            <div class="tarea-item ${claseItem}">
                <div class="tarea-info">
                    <span class="tarea-materia">${escapeHtml(t.materia)}</span>
                    <span class="badge ${claseBadge}">${textoBadge}</span>
                    <h3>${escapeHtml(t.titulo)}</h3>
                    <span class="tarea-fecha">Entrega: ${escapeHtml(fechaFormateada)}</span>
                </div>
                <div class="tarea-actions">
                    <button class="btn btn-success" onclick="alternarEstado('${id}', '${t.estado}')">
                        ${esCompletada ? "Reabrir" : "Completar"}
                    </button>
                    <button class="btn btn-warning" onclick="abrirModalEdicion('${id}')">
                        Editar
                    </button>
                    <button class="btn btn-danger" onclick="eliminarTarea('${id}')">
                        Eliminar
                    </button>
                </div>
            </div>
        `;
    })
    .join("");
}

busquedaInput.addEventListener("input", renderizarTareas);
filtroMateria.addEventListener("change", renderizarTareas);
filtroEstado.addEventListener("change", renderizarTareas);

// Crear Tarea (POST)
tareaForm.addEventListener("submit", async (e) => {
  e.preventDefault();

  const nuevaTarea = {
    materia: document.getElementById("materia").value.trim(),
    titulo: document.getElementById("titulo").value.trim(),
    fecha_limite: document.getElementById("fecha_limite").value,
    estado: "pendiente",
  };

  try {
    const res = await fetch(`${API_URL}/tareas`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(nuevaTarea),
    });

    if (res.status === 401) return mostrarAuth();

    if (res.ok) {
      tareaForm.reset();
      mostrarToast("Tarea agregada");
      cargarTareas();
    } else {
      const err = await res.json();
      mostrarToast(err.error || "Error al crear tarea");
    }
  } catch (err) {
    mostrarToast("Error de conexión");
  }
});

// Modal de Edición (PUT completo)
function abrirModalEdicion(id) {
  const tarea = tareasCache.find((t) => t.id_tarea === id);
  if (!tarea) return;

  document.getElementById("edit-id").value = tarea.id_tarea;
  document.getElementById("edit-materia").value = tarea.materia;
  document.getElementById("edit-titulo").value = tarea.titulo;
  document.getElementById("edit-fecha_limite").value = tarea.fecha_limite || "";
  editModal.classList.remove("hidden");
}

btnCancelEdit.addEventListener("click", () => {
  editModal.classList.add("hidden");
});

editForm.addEventListener("submit", async (e) => {
  e.preventDefault();
  const id = document.getElementById("edit-id").value;

  const datosActualizados = {
    materia: document.getElementById("edit-materia").value.trim(),
    titulo: document.getElementById("edit-titulo").value.trim(),
    fecha_limite: document.getElementById("edit-fecha_limite").value,
  };

  try {
    const res = await fetch(`${API_URL}/tareas/${id}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(datosActualizados),
    });

    if (res.ok) {
      editModal.classList.add("hidden");
      mostrarToast("Tarea actualizada");
      cargarTareas();
    }
  } catch (err) {
    mostrarToast("Error al actualizar");
  }
});

// Cambiar Estado (PUT parcial)
async function alternarEstado(id, estadoActual) {
  const nuevoEstado = estadoActual === "pendiente" ? "completada" : "pendiente";

  try {
    const res = await fetch(`${API_URL}/tareas/${id}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ estado: nuevoEstado }),
    });

    if (res.ok) {
      mostrarToast(
        nuevoEstado === "completada" ? "Tarea completada" : "Tarea reabierta",
      );
      cargarTareas();
    }
  } catch (err) {
    mostrarToast("Error al cambiar estado");
  }
}

// Eliminar Tarea (DELETE)
async function eliminarTarea(id) {
  try {
    const res = await fetch(`${API_URL}/tareas/${id}`, {
      method: "DELETE",
    });

    if (res.ok) {
      mostrarToast("Tarea eliminada");
      cargarTareas();
    }
  } catch (err) {
    mostrarToast("Error al eliminar");
  }
}
