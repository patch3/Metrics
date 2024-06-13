import 'whatwg-fetch';

// Генерация уникального токена
const token = document.querySelector('meta[name="token-metrics"]')
    ?.getAttribute('content');

if (!(token === null || token === undefined)) {
    // Тип действия пользователя
    enum ActivityType {
        VISIT = 'visit',
        CLICK = 'click'
    }

    // Отправка данных о действии пользователя
    function sendActivityData(action: ActivityType, elements: { [key: string]: any }) {
        const data = {
            token: token,
            ...elements
        };

        let url;
        if (action === ActivityType.VISIT) {
            url = 'http://localhost:8081/api/activity/visit';
        } else if (action === ActivityType.CLICK) {
            url = 'http://localhost:8081/api/activity/click';
        } else {
            return;
        }

        fetch(url, {
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


// Привязка обработчика события на загрузку страницы
    window.addEventListener('load', () => {
        sendActivityData(ActivityType.VISIT, {
            pageUrl: window.location.href,
            timestamp: Date.now()
        });
    });

// Привязка обработчика события на клики на элементах
    document.addEventListener('click', (event) => {
        const target = event.target as HTMLElement;

        sendActivityData(ActivityType.CLICK, {
            pageUrl: window.location.href,
            elementName: target.tagName,
            elementId: target.id,
            classes: Array.from(target.classList).join(' '),
            timestamp: Date.now()
        });
    });

} else {
    console.error("Токен не определен", token)
}