document.getElementById("input-confirmPassword").addEventListener("input", function() {
    let password = document.getElementById("input-password").value;
    let confirmPassword = this.value;
    let errorSpan = document.getElementById("passwordError");

    if (password === confirmPassword) {
    errorSpan.textContent = "";
    } else {
        errorSpan.textContent = "Les mots de passe ne correspondent pas";
        errorSpan.class = "text-danger";
    }
});
