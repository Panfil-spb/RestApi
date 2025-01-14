//функция для вывода информации авторизованного пользователя

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
                                <td> <button data-target="#modalEdit" type="button"
                                                        class="btn btn-info" data-toggle="modal"
                                                        onclick="editModal(${user.id})"> Edit</button> </td>
                                <td> <button data-target="#modalDelete" type="button"
                                                        class="btn btn-danger" data-toggle="modal"
                                                         onclick="deleteModal(${user.id})"> Delete</button> </td>`
                ;
                table.appendChild(row);
            })
        })
}

getAllUsers();

const urlGetUser = 'http://localhost:8080/api/admin/users/';

function editModal(id) {
    fetch(urlGetUser + id, {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json;charset=UTF-8'
        }
    }).then(res => {
        res.json().then(us => {

            document.getElementById('idEdit').value = us.id;
            document.getElementById('firstnameEdit').value = us.name;
            document.getElementById('lastnameEdit').value = us.last_name;
            ;
            document.getElementById('emailEdit').value = us.email;
            document.getElementById('passwordEdit').value = us.password;

        })
    });
}

async function editUser() {
    let idValue = document.getElementById("idEdit").value;
    let nameValue = document.getElementById("firstnameEdit").value;
    let surnameValue = document.getElementById("lastnameEdit").value;
    let emailValue = document.getElementById("emailEdit").value;
    let passwordValue = document.getElementById("passwordEdit").value;
    let roles = getRoles(Array.from(document.getElementById("rolesEdit").selectedOptions).map(role => role.value));

    let user = {
        id: idValue,
        name: nameValue,
        surname: surnameValue,
        email: emailValue,
        password: passwordValue,
        roles: roles
    }

    await fetch(url, {
        method: "PUT",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json;charset=UTF-8'
        },
        body: JSON.stringify(user)
    });
}

function deleteModal(id) {
    fetch(urlGetUser + id, {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json;charset=UTF-8'
        }
    }).then(res => {
        res.json().then(us => {

            document.getElementById('idDelete').value = us.id;
            document.getElementById('firstnameDelete').value = us.name;
            document.getElementById('lastnameDelete').value = us.last_name;
            ;
            document.getElementById('emailDelete').value = us.email;
            document.getElementById('passwordDelete').value = us.password;

        })
    });
}

async function deleteUser() {
    await fetch(url + document.getElementById('idDelete').value, {
        method: 'DELETE',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json;charset=UTF-8'
        },
    })

    getAllUsers()
    document.getElementById("deleteButton").click();
}

const addForm = document.getElementById('add-form');

addForm.addEventListener('submit', addUser)


function addUser(event) {

    event.preventDefault();
    let nameValue = document.getElementById('firstnameAdd').value;
    let lastName = document.getElementById('lastnameAdd').value;
    let emailValue = document.getElementById('emailAdd').value;
    let passwordValue = document.getElementById('passwordAdd').value;
    let roles = getRoles(Array.from(document.getElementById('rolesAdd').selectedOptions).map(role => role.value));


    let newUser = {
        name: nameValue,
        last_name: lastName,
        email: emailValue,
        password: passwordValue,
        roles: roles
    }
    const url = 'http://localhost:8080/api/admin/users';
    fetch(url, {
        method: "POST",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json;charset=UTF-8'
        },
        body: JSON.stringify(newUser)
    })
        .then(() => {
            document.getElementById('usersTable').click();
            getAllUsers();
        })
}

function getRoles(rols) {
    let roles = [];
    if (rols.indexOf("ADMIN") >= 0) {
        roles.push(1);
    }
    if (rols.indexOf("USER") >= 0) {
        roles.push(2);
    }
    return roles;
}