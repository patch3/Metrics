document.addEventListener('mousemove', function (event) {
    const target = event.target as HTMLElement;
    sendDataToServer('mousemove', target.tagName);
});

document.addEventListener('click', function (event) {
    const target = event.target as HTMLElement;
    sendDataToServer('click', target.tagName);
});

function sendDataToServer(type: string, target: string) {
    const data = {type, target};
    fetch('/api/activity', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem('token')
        },
        body: JSON.stringify(data)
    });
}