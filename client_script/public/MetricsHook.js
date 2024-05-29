"use strict";
var _a;
Object.defineProperty(exports, "__esModule", { value: true });
require("whatwg-fetch");
const token = (_a = document.querySelector('meta[name="token-metrics"]')) === null || _a === void 0 ? void 0 : _a.getAttribute('content');
function sendActivityData(action, target) {
    const data = {
        token: token,
        action: action,
        target: target
    };
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
    const target = event.target.tagName;
    sendActivityData('click', target);
});
//# sourceMappingURL=MetricsHook.js.map