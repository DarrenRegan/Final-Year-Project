package com.example.ecommerce.Model

class Users {

    private lateinit var name: String
    private lateinit var password: String
    private lateinit var phone: String

    constructor()

    constructor(name: String, password: String, phone: String) {
        this.name = name
        this.password = password
        this.phone = phone
    }




}