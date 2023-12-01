function onChange() {
    const password = document.querySelector('input[name=newPassword]');
    const confirm = document.querySelector('input[name=passwordConfirmation]');
    if (confirm.value === password.value) {
        confirm.setCustomValidity('');
    } else {
        confirm.setCustomValidity('Passwords do not match');
    }
}