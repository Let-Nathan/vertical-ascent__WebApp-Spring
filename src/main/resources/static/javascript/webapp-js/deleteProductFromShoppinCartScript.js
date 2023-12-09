document.addEventListener('DOMContentLoaded', function() {
    const DELETEBTN = document.querySelectorAll('.deleteProductButton');
    DELETEBTN.forEach((button) => {
        button.addEventListener('click', function(event) {
            event.preventDefault();
            const PRODUCTID = this.getAttribute('data-product-id');

            // Supprimer l'élément correspondant du localStorage
            if(PRODUCTID) {
                removeProductFromLocalStorage(PRODUCTID);
            }
            // Rediriger vers l'URL de suppression du produit
            window.location.href = this.getAttribute('href');
        });
    });

    /**
     * Remove the given product id from the user local storage.
     *
     * @param productId {productId} data id product for removal
     */
    function removeProductFromLocalStorage(productId) {
        const USERCART =
            JSON.parse(localStorage.getItem('userCart')) || [];
        const UPDATEDCART=
            USERCART.filter((item) => item.productId !== productId);
        localStorage.setItem('userCart', JSON.stringify(UPDATEDCART));
    }
});
