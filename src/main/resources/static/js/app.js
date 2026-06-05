// app.js – TaskManager Vanilla JS

// Flash-Messages automatisch ausblenden
document.addEventListener('DOMContentLoaded', () => {
    const alerts = document.querySelectorAll('.alert');
    alerts.forEach(alert => {
        setTimeout(() => {
            alert.style.transition = 'opacity .5s';
            alert.style.opacity = '0';
            setTimeout(() => alert.remove(), 500);
        }, 4000);
    });

    // Heute als Standard für neue Fälligkeitsdaten setzen (optional)
    const dueDateInputs = document.querySelectorAll('input[type="date"]');
    dueDateInputs.forEach(input => {
        if (!input.value) {
            // Kein Default setzen – leer lassen ist besser
        }
    });

    console.log('✅ TaskManager geladen');
});
