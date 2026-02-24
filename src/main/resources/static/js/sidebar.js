// Функция открытия/закрытия сайдбара
function toggleSidebar() {
    const sidebar = document.getElementById('sidebar');
    const overlay = document.getElementById('overlay');
    sidebar.classList.toggle('open');
    overlay.classList.toggle('show');
}

// Закрытие по Esc
document.addEventListener('keydown', function(e) {
    if (e.key === 'Escape') {
        const sidebar = document.getElementById('sidebar');
        if (sidebar.classList.contains('open')) {
            toggleSidebar();
        }
    }
});

// Закрытие при клике вне сайдбара
document.addEventListener('click', function(e) {
    const sidebar = document.getElementById('sidebar');
    const profileIcon = document.querySelector('.profile-icon');
    
    if (sidebar && profileIcon && sidebar.classList.contains('open')) {
        const isClickInsideSidebar = sidebar.contains(e.target);
        const isClickOnIcon = profileIcon.contains(e.target);
        
        if (!isClickInsideSidebar && !isClickOnIcon) {
            toggleSidebar();
        }
    }
});

// Инициализация: проверка наличия элементов
document.addEventListener('DOMContentLoaded', function() {
    const sidebar = document.getElementById('sidebar');
    const overlay = document.getElementById('overlay');
    const profileIcon = document.querySelector('.profile-icon');
    
    if (!sidebar || !overlay || !profileIcon) {
        console.log('Элементы сайдбара не найдены');
    }
});