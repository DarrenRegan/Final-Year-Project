package com.example.ecommerce.views

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerce.R
import com.example.ecommerce.interfaces.ItemClickListener


class ProductView:RecyclerView.ViewHolder, View.OnClickListener{
    var txtProdName:TextView
    var txtProdDesc:TextView
    var txtProdPrice:TextView
    var txtImageView:ImageView
    lateinit var listener: ItemClickListener
    constructor(itemView: View) : super(itemView)

    fun ProductView(itemView:View){
        super.itemView
        txtImageView = itemView.findViewById(R.id.product_card_img)
        txtProdName = itemView.findViewById(R.id.product_card_name)
        txtProdDesc = itemView.findViewById(R.id.product_card_desc)
        txtProdPrice = itemView.findViewById(R.id.product_card_price)
    }

    init {
        txtImageView = itemView.findViewById(R.id.product_card_img)
        txtProdName = itemView.findViewById(R.id.product_card_name)
        txtProdDesc = itemView.findViewById(R.id.product_card_desc)
        txtProdPrice = itemView.findViewById(R.id.product_card_price)
    }

    fun setItemClickListener(listener: ItemClickListener){
        this.listener = listener
    }


    override fun onClick(v: View?) {
        listener.onClick(v!!, adapterPosition, false)
    }
}