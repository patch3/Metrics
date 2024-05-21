"use strict";
document.addEventListener('mousemove', function (event) {
    const target = event.target;
    sendDataToServer('mousemove', target.tagName);
});
document.addEventListener('click', function (event) {
    const target = event.target;
    sendDataToServer('click', target.tagName);
});
function sendDataToServer(type, target) {
    const data = { type, target };
    fetch('/api/activity', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem('token')
        },
        body: JSON.stringify(data)
    });
}
//# sourceMappingURL=MetricsHook.js.map