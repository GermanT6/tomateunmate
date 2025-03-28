
document.addEventListener("DOMContentLoaded", function() {
// Obtén la URL actual
const currentUrl = window.location.pathname;

// Obtén todos los enlaces de la barra de navegación
const navLinks = document.querySelectorAll('.nav-link');

// Recorre todos los enlaces y agrega la clase 'active' al que coincida con la URL
navLinks.forEach(link => {
if (link.getAttribute('href') === currentUrl) {
    link.classList.add('active');
}
});
});

