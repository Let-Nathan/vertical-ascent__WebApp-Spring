document.addEventListener('DOMContentLoaded', function() {
    const viewCartButton = document.getElementById('viewCartBtn');
    if (viewCartButton) {
        viewCartButton.addEventListener('click', function(event) {
            event.preventDefault();
            const USERCART = JSON.parse(localStorage.getItem('userCart'));
            const CARTITEMDTO =
                USERCART != null ? USERCART.map(convertToProductDto) : null;

            if (redirectIfEmpty(CARTITEMDTO)) {
                return;
            }

            sendCartItems(CARTITEMDTO);
        });
    }

    /**
     * Object representation for a product dto.
     *
     * @param item cart item from local storage converted in dto representation
     * @return {{quantity: number, price: number, name: string, id: number}}
     */
    function convertToProductDto(item) {
        return {
            id: parseInt(item.productId),
            name: item.productName,
            price: parseInt(item.productPrice),
            quantity: parseInt(item.quantity),
        };
    }

    /**
     * If Cart Items (from local storage) is empty, we redirect user.
     *
     * @param cartItems localstorage cart items
     * @return {boolean}
     */
    function redirectIfEmpty(cartItems) {
        if (!cartItems) {
            window.location.href = '/pannier-vide';
            return true;
        }
        return false;
    }

    /**
     * Send to endpoint the current user local storage.
     *
     * @param cartItems
     */
    function sendCartItems(cartItems) {
        const userId = localStorage.getItem('userId');

        const requestBody = {
            cartItems: cartItems
        };

        if (userId) {
            requestBody.userId = userId;
        }
        fetch('/validate-cart', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(requestBody),
        })
            .then((response) => {
                if (!response) {
                    throw new Error(`Http error ==>  ${response.status}`);
                }
                return response.json();
            })
            .then((data) => {
                if (!localStorage.getItem('userId')) {
                    localStorage.setItem('userId', data.sessionId);
                }
                window.location.href = '/pannier?pannierId=' + data.sessionId;
            })
            .catch((error) => {
                console.log('Une erreur est survenue : ' + error);
            });
    }
});
