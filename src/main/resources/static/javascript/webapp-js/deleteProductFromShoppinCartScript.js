document.addEventListener('DOMContentLoaded', function() {
    const DELETEBTN = document.querySelectorAll('.deleteProductButton');
    DELETEBTN.forEach((button) => {
        button.addEventListener('click', function(event) {
            event.preventDefault();
            const PRODUCTID = this.getAttribute('data-product-id');

            // Supprimer l'élément correspondant du localStorage
            if (PRODUCTID) {
                removeProductFromLocalStorage(PRODUCTID);
                // Continuer avec la requête fetch après avoir mis à jour le local storage
                setTimeout(() => {
                    fetch(this.getAttribute('href'), {
                        method: 'DELETE',
                    })
                        .then(response => {
                            // Gérer la réponse
                        })
                        .catch(error => {
                            // Gérer les erreurs
                        });
                }, 100); // Ajoutez un délai pour garantir la mise à jour du local storage
            }
        });
    });

    function removeProductFromLocalStorage(productId) {
        const USERCART = JSON.parse(localStorage.getItem('userCart')) || [];
        const UPDATEDCART = USERCART.filter((item) => item.productId !== productId);
        localStorage.setItem('userCart', JSON.stringify(UPDATEDCART));
    }
});
