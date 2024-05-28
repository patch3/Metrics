import 'whatwg-fetch';

// Генерация уникального токена
const token = document.querySelector('meta[name="token-metrics"]')?.getAttribute('content');

// Отправка данных о действии пользователя
function sendActivityData(action: string, target: string) {
    const data = {
        token: token,
        action: action,
        target: target
    };

    // Отправка данных на сервер
    fetch('http://localhost:8081/api/activity', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify(data)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            // провекряем есть ли тело
            if (response.headers.get('Content-Length') != '0') {
                return response.json();
            }
        })
        .then(data => {
            console.log('Activity data sent successfully:', data);
        })
        .catch(error => {
            console.error('There was a problem with the fetch operation:', error);
    });
}

document.addEventListener('click', (event) => {
    const target = (event.target as HTMLElement).tagName;
    sendActivityData('click', target);
});