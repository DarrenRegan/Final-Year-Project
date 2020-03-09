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

    fun getName():String {
        return name
    }

    fun getPassword():String {
        return password
    }

    fun getPhone():String {
        return phone
    }

    fun setName(name:String) {
        this.name = name
    }

    fun setPassword(password: String) {
        this.password = password
    }

    fun setPhone(phone:String) {
        this.phone = phone
    }

}