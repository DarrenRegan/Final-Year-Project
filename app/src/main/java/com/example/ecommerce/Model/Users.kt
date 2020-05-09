package com.example.ecommerce.Model

class Users {

    private lateinit var name: String
    private lateinit var password: String
    private lateinit var phone: String
    private lateinit var image: String
    private lateinit var address: String
    constructor()

    constructor(name: String, password: String, phone: String, image:String, address:String) {
        this.name = name
        this.password = password
        this.phone = phone
        this.image = image
        this.address = address
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

    fun getImage():String {
        return image
    }

    fun getAddress():String {
        return address
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

    fun setImage(image:String) {
        this.image = image
    }

    fun setAddress(address:String) {
        this.address = address
    }

}