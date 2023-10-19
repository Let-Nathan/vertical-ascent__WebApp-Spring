document.getElementById("input-confirmPassword")
        .addEventListener("input", function() {
    const PASSWORD = document.getElementById('input-password').value;
    const CONFIRM_PASSWORD = this.value;
    const ERROR_SPAN = document.getElementById('passwordError');

    if (PASSWORD === CONFIRM_PASSWORD) {
        ERROR_SPAN.textContent = '';
    } else {
        ERROR_SPAN.textContent = 'Les mots de passe ne correspondent pas';
        ERROR_SPAN.class = 'text-danger';
    }
});
