const urlAuthInfo = 'http://localhost:8080/api/admin/auth';
const panel = document.getElementById('admin-panel');
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
 userLineInfo();


const url = 'http://localhost:8080/api/admin/users';
function getAllUsers() {
    fetch(url)
        .then(res => res.json())
        .then(users => {
            const table = document.getElementById('user-data');
            table.innerHTML = '';
            users.forEach(user => {
                const row = document.createElement('tr');
                row.innerHTML = `<td>${user.id}</td>
                                <td id=${'name' + user.id}>${user.name}</td>
                                <td id=${'last_name' + user.id}>${user.last_name}</td>
                                <td id=${'email' + user.id}>${user.email}</td>
                                <td id=${'role' + user.id}>${user.stringRoles}</td>
                                <td> <button th:href="${'#modalEdit' + user.id}" type="button"
                                                        class="btn btn-info" data-toggle="modal"> Edit</button> </td>
                                <td> <button th:href="${'#modalDelete' + user.id}" type="button"
                                                        class="btn btn-danger" data-toggle="modal"> Delete</button> </td>`
                                ;
                table.appendChild(row);
            })
        })
}

getAllUsers();
