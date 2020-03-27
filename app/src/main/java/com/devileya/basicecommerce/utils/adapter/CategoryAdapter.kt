package com.devileya.basicecommerce.utils.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.devileya.basicecommerce.R
import com.devileya.basicecommerce.network.model.CategoryModel
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_item_category.*
import kotlinx.android.synthetic.main.list_item_category.view.*

/**
 * Created by Arif Fadly Siregar 2020-03-26.
 */
class CategoryAdapter(private val categories: List<CategoryModel>) : RecyclerView.Adapter<CategoryAdapter.DataModViewHolder>(){
    private var ctx: Context?= null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataModViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_category, parent, false)
        ctx = parent.context
        return DataModViewHolder(view)
    }

    /**
     * @holder: PortViewHolder
     * @position: adapter position
     */
    override fun onBindViewHolder(holder: DataModViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        holder.bindItem(categories[position])
    }

    override fun getItemCount(): Int = categories.size

    class DataModViewHolder(override val containerView: View): RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        fun bindItem(category: CategoryModel) {
            Glide.with(itemView.context)
                .load(category.imageUrl)
                .into(itemView.iv_category)
            tv_category.text = category.name
        }
    }
}