package com.devileya.basicecommerce.utils.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.devileya.basicecommerce.R
import com.devileya.basicecommerce.network.model.ProductModel
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_item_product.*
import kotlinx.android.synthetic.main.list_item_product.view.*

/**
 * Created by Arif Fadly Siregar 2020-03-26.
 */
class ProductAdapter(private val products: List<ProductModel>, private val isLoveShowing: Boolean, private val listener: (ProductModel) -> Unit) : RecyclerView.Adapter<ProductAdapter.DataModViewHolder>(){
    private var ctx: Context?= null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataModViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_product, parent, false)
        ctx = parent.context
        return DataModViewHolder(view)
    }

    /**
     * @holder: PortViewHolder
     * @position: adapter position
     */
    override fun onBindViewHolder(holder: DataModViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        holder.bindItem(products[position], listener, isLoveShowing)
    }

    override fun getItemCount(): Int = products.size

    class DataModViewHolder(override val containerView: View): RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        fun bindItem(product: ProductModel, listener: (ProductModel) -> Unit, isLoveShowing: Boolean) {
            Glide.with(itemView.context)
                .load(product.imageUrl)
                .into(itemView.iv_product)
            tv_product.text = product.title
            tv_price.text = product.price
            if (!isLoveShowing) iv_heart.visibility = View.GONE
            else if (product.loved == 1) iv_heart.imageTintList = ContextCompat.getColorStateList(itemView.context, R.color.red)
            containerView.setOnClickListener { listener(product) }
        }
    }
}