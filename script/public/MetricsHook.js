"use strict";
var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
const sendData = (data) => __awaiter(void 0, void 0, void 0, function* () {
    const token = localStorage.getItem('token');
    const response = yield fetch('http://localhost:8080/track', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify(data)
    });
    if (!response.ok) {
        throw new Error('Network response was not ok');
    }
    return response.json();
});
window.addEventListener('mousemove', (event) => {
    const data = {
        type: 'mousemove',
        x: event.clientX,
        y: event.clientY
    };
    sendData(data);
});
window.addEventListener('click', (event) => {
    const data = {
        type: 'click',
        target: event.target.tagName
    };
    sendData(data);
});
//# sourceMappingURL=MetricsHook.js.map