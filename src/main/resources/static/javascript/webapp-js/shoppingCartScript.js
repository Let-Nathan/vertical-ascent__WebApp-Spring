document.addEventListener('DOMContentLoaded', function() {
    const viewCartButton = document.getElementById('viewCartBtn');
    if (viewCartButton) {
        viewCartButton.addEventListener('click', function(event) {
            event.preventDefault();
            const USERCART = JSON.parse(localStorage.getItem('userCart'));
            const CARTITEMDTO = USERCART != null ? USERCART.map(convertToProductDto) : null;

            if (redirectIfEmpty(CARTITEMDTO)) {
                return;
            }

            sendCartItems(CARTITEMDTO);
        });
    }

    function convertToProductDto(item) {
        return {
            id: parseInt(item.productId),
            name: item.productName,
            price: parseInt(item.productPrice),
            quantity: parseInt(item.quantity)
        };
    }

    function redirectIfEmpty(cartItems) {
        if (!cartItems) {
            window.location.href = '/pannier-vide';
            return true;
        }
        return false;
    }

    function sendCartItems(cartItems) {
        const CSRFTOKEN = document.cookie.replace(/(?:(?:^|.*;\s*)XSRF-TOKEN\s*\=\s*([^;]*).*$)|^.*$/, '$1');
        fetch('/validate-cart', {
            method: 'POST',
            headers: {
                "Content-Type": "application/json",
                'X-XSRF-TOKEN': CSRFTOKEN,
            },
            body: JSON.stringify(cartItems),
        })
            .then((response) => {
                if (!response) {
                    throw new Error(`Http error ==>  ${response.status}`);
                }
                return response.json();
            })
    }
});
