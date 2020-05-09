package com.example.ecommerce.Model

import androidx.annotation.Keep
import java.io.Serializable


class Products:Serializable {

/*    companion object{
        public lateinit var category:String
        public lateinit var date:String
        public lateinit var description:String
        public lateinit var image:String
        public lateinit var pid:String
        public lateinit var pname:String
        public lateinit var price:String
        public lateinit var time:String
    }*/

    private lateinit var category:String
    private lateinit var date:String
    private lateinit var description:String
    private lateinit var image:String
    private lateinit var pid:String
    private lateinit var pname:String
    private lateinit var price:String
    private lateinit var time:String

    constructor(){}

    constructor(category: String, date: String, description: String, image: String, pid: String, pname: String, price: String, time: String) {
        this.category = category
        this.date = date
        this.description = description
        this.image = image
        this.pid = pid
        this.pname = pname
        this.price = price
        this.time = time
    }
    init {
        lateinit var category:String
        lateinit var date:String
        lateinit var description:String
        lateinit var image:String
        lateinit var pid:String
        lateinit var pname:String
        lateinit var price:String
        lateinit var time:String
    }
    fun getDescription():String {
        return description
    }

    fun getImage():String {
        return image
    }

    fun getPname():String {
        return pname
    }

    fun getPrice():String {
        return price
    }

    fun setDescription(description:String) {
        this.description = description
    }

    fun setImage(image:String) {
        this.image = image
    }

    fun setPname(pname:String) {
        this.pname = pname
    }

    fun setPrice(price:String) {
        this.price = price
    }


}