"use strict";
var _a;
Object.defineProperty(exports, "__esModule", { value: true });
require("whatwg-fetch");
const token = (_a = document.querySelector('meta[name="token-metrics"]')) === null || _a === void 0 ? void 0 : _a.getAttribute('content');
if (!(token === null || token === undefined)) {
    let ActivityType;
    (function (ActivityType) {
        ActivityType["VISIT"] = "visit";
        ActivityType["CLICK"] = "click";
    })(ActivityType || (ActivityType = {}));
    function sendActivityData(action, elements) {
        const data = Object.assign({ token: token }, elements);
        let url;
        if (action === ActivityType.VISIT) {
            url = 'http://localhost:8081/api/activity/visit';
        }
        else if (action === ActivityType.CLICK) {
            url = 'http://localhost:8081/api/activity/click';
        }
        else {
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
    window.addEventListener('load', () => {
        sendActivityData(ActivityType.VISIT, {
            pageUrl: window.location.href,
            timestamp: Date.now()
        });
    });
    document.addEventListener('click', (event) => {
        const target = event.target;
        sendActivityData(ActivityType.CLICK, {
            elementName: target.tagName,
            elementId: target.id,
            classes: Array.from(target.classList).join(' '),
            timestamp: Date.now()
        });
    });
}
else {
    console.error("Токен не определен", token);
}
//# sourceMappingURL=MetricsHook.js.map