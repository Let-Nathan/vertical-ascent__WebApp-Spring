document.addEventListener('DOMContentLoaded', function() {
    const DELETEBTN = document.querySelectorAll('.deleteProductButton');
    DELETEBTN.forEach((button) => {
        button.addEventListener('click', function(event) {
            event.preventDefault();
            const PRODUCTID = this.getAttribute('data-product-id');

            if (PRODUCTID) {
                removeProductFromLocalStorage(PRODUCTID);
                setTimeout(() => {
                    fetch(this.getAttribute('href'), {
                        method: 'DELETE',
                    })
                        .then(response => {

                        })

                }, 100);
            }
        });
    });

    function removeProductFromLocalStorage(productId) {
        const USERCART = JSON.parse(localStorage.getItem('userCart')) || [];
        const UPDATEDCART = USERCART.filter((item) => item.productId !== productId);
        localStorage.setItem('userCart', JSON.stringify(UPDATEDCART));
    }
});
