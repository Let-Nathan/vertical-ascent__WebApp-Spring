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
                "Content-Type": "application/json",
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
                if(!localStorage.getItem("userId")) {
                    localStorage.setItem("userId", data.sessionId);
                }
                window.location.href = '/pannier?pannierId=' + data.sessionId;
            })
            .catch((error) => {
                console.log('Une erreur est survenue : ' + error);
            });
    }
});
