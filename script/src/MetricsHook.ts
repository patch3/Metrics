// Генерация уникального токена
const token = Math.random().toString(36).substring(2, 15) + Math.random().toString(36).substring(2, 15);

// Отправка данных о действии пользователя
function sendActivityData(action: string, target: string) {
    const data = {
        token: token,
        action: action,
        target: target
    };

    // Отправка данных на сервер
    fetch('/api/activity', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify(data)
    });
}

// Отслеживание действий пользователя
document.addEventListener('mousemove', (event) => {
    const target = (event.target as HTMLElement).tagName;
    sendActivityData('mousemove', target);
});

document.addEventListener('click', (event) => {
    const target = (event.target as HTMLElement).tagName;
    sendActivityData('click', target);
});