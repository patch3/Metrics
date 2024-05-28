"use strict";
const token = Math.random().toString(36).substring(2, 15) + Math.random().toString(36).substring(2, 15);

function sendActivityData(action, target) {
    const data = {
        token: token,
        action: action,
        target: target
    };
    fetch('/api/activity', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify(data)
    });
}

document.addEventListener('mousemove', (event) => {
    const target = event.target.tagName;
    sendActivityData('mousemove', target);
});
document.addEventListener('click', (event) => {
    const target = event.target.tagName;
    sendActivityData('click', target);
});
//# sourceMappingURL=MetricsHook.js.map