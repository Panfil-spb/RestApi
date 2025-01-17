const urlAuthInfo = 'http://localhost:8080/api/user/auth';
const panel = document.getElementById('user-panel');
const csrfToken = document.querySelector('meta[name="csrf-token"]').content;
const csrfHeader = document.querySelector('meta[name="csrf-header"]').content;


function userLineInfo() {
    fetch(urlAuthInfo)
        .then(response => {
            // Проверяем успешность HTTP-ответа
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }

            // Получаем содержимое ответа как текст
            return response.text();
        })
        .then(data => {
            // Обрабатываем текстовый ответ
            console.log('Response data:', data); // Отладочная информация
            if (!data) {
                throw new Error('Server returned empty response.');
            }

            // Вставляем данные в панель
            panel.innerHTML = `<p><strong>User Info:</strong> ${data}</p>`;
        })
        .catch(error => {
            // Обрабатываем ошибки
            console.error('Error fetching user info:', error);
            panel.innerHTML = `<p style="color:red;">Error: ${error.message}</p>`;
        });
}


const url = 'http://localhost:8080/api/user';

function getUserTable() {
    fetch(url)
        .then(res => res.json())
        .then(user => {
            const table = document.getElementById('user-data');
            table.innerHTML = '';
            const row = document.createElement('tr');
            row.innerHTML = `<td>${user.id}</td>
                                <td id=${'name' + user.id}>${user.name}</td>
                                <td id=${'last_name' + user.id}>${user.last_name}</td>
                                <td id=${'email' + user.id}>${user.email}</td>
                                <td id=${'role' + user.id}>${user.stringRoles}</td>
                                `
            ;
            table.appendChild(row);
        })


}
getUserTable();
userLineInfo();
