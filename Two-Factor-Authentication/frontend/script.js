// DOM Elements
const signupContainer = document.getElementById('signupContainer');
const loginContainer = document.getElementById('loginContainer');
const otpModal = document.getElementById('otpModal');
const showLogin = document.getElementById('showLogin');
const showSignup = document.getElementById('showSignup');
const verifyOtpBtn = document.getElementById('verifyOtpBtn');
let currentUserEmail = '';

// Toggle between signup and login forms
showLogin.addEventListener('click', (e) => {
    e.preventDefault();
    signupContainer.style.display = 'none';
    loginContainer.style.display = 'block';
});

showSignup.addEventListener('click', (e) => {
    e.preventDefault();
    loginContainer.style.display = 'none';
    signupContainer.style.display = 'block';
});

// Password visibility toggle
document.getElementById('togglePassword').addEventListener('click', function() {
    togglePasswordVisibility('password', this);
});

document.getElementById('toggleLoginPassword').addEventListener('click', function() {
    togglePasswordVisibility('loginPassword', this);
});

function togglePasswordVisibility(fieldId, icon) {
    const passwordInput = document.getElementById(fieldId);
    if (passwordInput.type === 'password') {
        passwordInput.type = 'text';
        icon.classList.replace('fa-eye', 'fa-eye-slash');
    } else {
        passwordInput.type = 'password';
        icon.classList.replace('fa-eye-slash', 'fa-eye');
    }
}

// Signup Form Submission
document.getElementById('signupForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    const submitBtn = document.getElementById('submitBtn');
    
    try {
        submitBtn.disabled = true;
        submitBtn.innerHTML = '<span class="loading-spinner"></span>';
        
        const formData = {
            firstName: document.getElementById('firstName').value,
            lastName: document.getElementById('lastName').value,
            email: document.getElementById('email').value,
            password: document.getElementById('password').value,
            phone: document.getElementById('phoneNumber').value
        };

        const response = await fetch('http://localhost:9090/auth/signup', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(formData)
        });

        const data = await response.text();
        
        if (!response.ok) throw new Error(data || 'Signup failed');
        
        alert('Signup successful! Please login.');
        signupContainer.style.display = 'none';
        loginContainer.style.display = 'block';
        resetForm('signupForm');
    } catch (error) {
        console.error('Signup Error:', error);
        alert(error.message);
    } finally {
        submitBtn.disabled = false;
        submitBtn.innerHTML = '<span class="btn-text">Sign Up</span>';
    }
});

// Login Form Submission
document.getElementById('loginForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    const loginBtn = document.getElementById('loginBtn');
    
    try {
        loginBtn.disabled = true;
        loginBtn.innerHTML = '<span class="loading-spinner"></span>';
        
        const loginData = {
            email: document.getElementById('loginEmail').value,
            password: document.getElementById('loginPassword').value
        };

        currentUserEmail = loginData.email;
        
        const response = await fetch('http://localhost:9090/auth/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(loginData)
        });

        const data = await response.text();
        
        if (!response.ok) throw new Error(data || 'Login failed');
        
        // Show OTP modal on successful login
        showOtpModal();
    } catch (error) {
        console.error('Login Error:', error);
        alert(error.message);
    } finally {
        loginBtn.disabled = false;
        loginBtn.innerHTML = '<span class="btn-text">Login</span>';
    }
});

// OTP Verification
verifyOtpBtn.addEventListener('click', async () => {
    const otpCode = document.getElementById('otpCode').value;
    const otpMessage = document.getElementById('otpMessage');
    
    if (!otpCode || otpCode.length !== 6) {
        otpMessage.textContent = 'Please enter a valid 6-digit OTP';
        otpMessage.className = 'message error';
        return;
    }

    try {
        verifyOtpBtn.disabled = true;
        verifyOtpBtn.textContent = 'Verifying...';
        
        const response = await fetch('http://localhost:9090/otp/verify', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                email: currentUserEmail,
                otpCode: otpCode
            })
        });

        const data = await response.text();
        
        if (!response.ok) throw new Error(data || 'OTP verification failed');
        
        otpMessage.textContent = 'Verification successful! Redirecting...';
        otpMessage.className = 'message success';
        
        // Redirect to dashboard after 2 seconds
        setTimeout(() => {
            window.location.href = '/dashboard.html';
        }, 2000);
    } catch (error) {
        console.error('OTP Error:', error);
        otpMessage.textContent = error.message;
        otpMessage.className = 'message error';
    } finally {
        verifyOtpBtn.disabled = false;
        verifyOtpBtn.textContent = 'Verify';
    }
});

// Helper Functions
function showOtpModal() {
    otpModal.style.display = 'flex';
    document.getElementById('otpMessage').textContent = '';
    document.getElementById('otpCode').value = '';
}

function resetForm(formId) {
    document.getElementById(formId).reset();
}

// Close modal when clicking outside
window.addEventListener('click', (e) => {
    if (e.target === otpModal) {
        otpModal.style.display = 'none';
    }
});