document.addEventListener('DOMContentLoaded', function() {
    const PAYMENTBTN = document.getElementById('payment-btn');
    console.log(PAYMENTBTN);
    if(PAYMENTBTN) {
        PAYMENTBTN.addEventListener('click', function(ev) {
            console.log('click');
            ev.preventDefault();
            const currLOCALSTORAGE = localStorage.getItem('userCart');
            if(currLOCALSTORAGE != null) {
                localStorage.clear();
            }
        })
    }
})
