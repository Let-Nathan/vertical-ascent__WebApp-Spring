document.addEventListener('DOMContentLoaded', function() {
    let deleteButtons = document.querySelectorAll('.deleteProductButton');
    deleteButtons.forEach(button => {
        button.addEventListener('click', function(event) {
            event.preventDefault();
            let productId = this.getAttribute('data-product-id');

            // Supprimer l'élément correspondant du localStorage
            removeProductFromLocalStorage(productId);

            // Rediriger vers l'URL de suppression du produit
            window.location.href = this.getAttribute('href');
        });
    });

    function removeProductFromLocalStorage(productId) {
        let userCart = JSON.parse(localStorage.getItem('userCart')) || [];
        let updatedCart = userCart.filter(item => item.productId !== productId);
        localStorage.setItem('userCart', JSON.stringify(updatedCart));
    }
});
