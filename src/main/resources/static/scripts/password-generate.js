function generatePassword() {
    const uppercaseChars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ';
    const lowercaseChars = 'abcdefghijklmnopqrstuvwxyz';
    const numericChars = '0123456789';
    const symbolChars = '!@#$%^&*()-=_+[]{}|;:,.<>?';

    const allChars = uppercaseChars + lowercaseChars + numericChars + symbolChars;

    // Ensure at least one character from each category
    const passwordArray = [
        getRandomChar(uppercaseChars),
        getRandomChar(lowercaseChars),
        getRandomChar(numericChars),
        getRandomChar(symbolChars)
    ];

    const passwordLength = 12;
    // Fill the remaining characters randomly
    const remainingLength = passwordLength - passwordArray.length;
    for (let i = 0; i < remainingLength; i++) {
        passwordArray.push(getRandomChar(allChars));
    }

    // Shuffle the password array to randomize the order
    passwordArray.sort(() => Math.random() - 0.5);

    // Convert the array of characters to a string
    return passwordArray.join('');
}

function getRandomChar(characters) {
    const randomIndex = Math.floor(Math.random() * characters.length);
    return characters.charAt(randomIndex);
}

function generateAndFillPassword() {
    const passwordInput = document.getElementById('password');
    passwordInput.value = generatePassword();
}
