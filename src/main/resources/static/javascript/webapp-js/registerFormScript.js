document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('register-information-form');
    const confirmPasswordField = document.getElementById('input-confirmPassword');
    const errorSpan = document.getElementById('passwordError');
    let passwordMatch = true;

    confirmPasswordField.addEventListener('input', function() {
        const PASSWORD = document.getElementById('input-password').value;
        const CONFIRM_PASSWORD = this.value;

        if (PASSWORD === CONFIRM_PASSWORD) {
            errorSpan.textContent = '';
            errorSpan.classList.remove('text-danger');
            passwordMatch = true;
        } else {
            errorSpan.textContent = 'Les mots de passe ne correspondent pas';
            errorSpan.classList.add('text-danger');
            passwordMatch = false;
        }
    });

    form.addEventListener('submit', function(event) {
        if (!passwordMatch) {
            event.preventDefault();
        }
    });
});
