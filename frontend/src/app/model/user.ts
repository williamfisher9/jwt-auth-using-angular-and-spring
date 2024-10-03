export class User {
    username : string;
    firstName : string;
    lastName  : string;
    password : string;
    roles : string[]

    constructor(username : string, firstName : string, lastName : string, password : string, roles : string[]){
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.roles = roles;
    }
}
