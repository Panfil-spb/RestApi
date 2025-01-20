//функция для вывода информации авторизованного пользователя

const urlAuthInfo = 'http://localhost:8080/api/admin/auth';
const panel = document.getElementById('admin-panel');
const csrfToken = document.querySelector('meta[name="csrf-token"]').content;
const csrfHeader = document.querySelector('meta[name="csrf-header"]').content;


function userLineInfo() {
    fetch(urlAuthInfo)
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            return response.text();
        })
        .then(data => {
            if (!data) {
                throw new Error('Server returned empty response.');
            }
            panel.innerHTML = `<p><strong>User Info:</strong> ${data}</p>`;
        })
        .catch(error => {
            console.error('Error fetching user info:', error);
            panel.innerHTML = `<p style="color:red;">Error: ${error.message}</p>`;
        });
}

userLineInfo();


const url = 'http://localhost:8080/api/admin';

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

const urlGetUser = 'http://localhost:8080/api/admin/';

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
            document.getElementById('emailEdit').value = us.email;
        })
    });
}

async function editUser() {
    let idValue = document.getElementById("idEdit").value;
    let nameValue = document.getElementById("firstnameEdit").value;
    let surnameValue = document.getElementById("lastnameEdit").value;
    let emailValue = document.getElementById("emailEdit").value;
    let passwordValue = document.getElementById("passwordEdit").value;
    let roles = getRoles(Array.from(document.getElementById('rolesEdit').selectedOptions).map(role => role.value));

    let user = {
        id: idValue,
        name: nameValue,
        last_name: surnameValue,
        email: emailValue,
        password: passwordValue,
        roles: roles
    }

    await fetch(url, {
        method: "PUT",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json;charset=UTF-8',
            [csrfHeader]: csrfToken

        },
        body: JSON.stringify(user)
    });

    getAllUsers()
    document.getElementById("editCloseButton").click();
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
            document.getElementById('emailDelete').value = us.email;
        })
    });
}

async function deleteUser() {
    await fetch(url + "/" + document.getElementById('idDelete').value, {
        method: 'DELETE',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json;charset=UTF-8',
            [csrfHeader]: csrfToken
        },
    })

    getAllUsers()
    document.getElementById("deleteCloseButton").click();
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
            'Content-Type': 'application/json;charset=UTF-8',
            [csrfHeader]: csrfToken
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
        roles.push({
            "id": 1,
            "name": "ROLE_ADMIN"
        });
    }
    if (rols.indexOf("USER") >= 0) {
        roles.push({
            "id": 2,
            "name": "ROLE_USER"
        });
    }
    return roles;
}