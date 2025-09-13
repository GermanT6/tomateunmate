function cargarDatosParaEditar(button) {
    const id = button.getAttribute('data-id');
    const nombre = button.getAttribute('data-nombre');
    const descripcion = button.getAttribute('data-descripcion');
    const precioCompra = button.getAttribute('data-preciocompra');
    const precioVenta = button.getAttribute('data-precioventa');
    const stock = button.getAttribute('data-stock');
    
    document.getElementById('productoId').value = id;
    document.getElementById('nombre').value = nombre;
    document.getElementById('descripcion').value = descripcion;
    document.getElementById('precioCompra').value = precioCompra;
    document.getElementById('precioVenta').value = precioVenta;
    document.getElementById('stock').value = stock;
    
    // Cambiar el título del modal
    document.getElementById('modal-producto-label').textContent = 'Editar Producto';
}

function limpiarFormulario() {
    document.getElementById('form-producto').reset();
    document.getElementById('productoId').value = '';
    document.getElementById('modal-producto-label').textContent = 'Agregar Producto';
}

        // Inicializar toasts de Bootstrap
        document.addEventListener('DOMContentLoaded', function() {
            const toastElList = [].slice.call(document.querySelectorAll('.toast'));
            const toastList = toastElList.map(function(toastEl) {
                const toast = new bootstrap.Toast(toastEl, {
                    autohide: true,
                    delay: 2000
                });
                toast.show();
                return toast;
            });
            
            // Opcional: Animación de entrada
            const toastElements = document.querySelectorAll('.toast');
            toastElements.forEach(toast => {
                toast.style.transition = 'all 0.3s ease';
                setTimeout(() => {
                    toast.style.opacity = '1';
                    toast.style.transform = 'translateX(0)';
                }, 100);
            });
        });