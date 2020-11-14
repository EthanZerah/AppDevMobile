package com.example.testmenu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_and_version.*

class AndVersionAdapter(val items: Array<AndVersion>) : RecyclerView.Adapter<AndVersionAdapter.ViewHolder>() {
    override fun getItemCount(): Int = items.size
    fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
        return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AndVersionAdapter.ViewHolder {
        return ViewHolder(parent.inflate(R.layout.item_and_version))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindAndVersion(items[position])
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view), LayoutContainer{
        override val containerView: View?
            get() = itemView
        fun bindAndVersion(andVersion: AndVersion) {
            with(andVersion) {
                andVersionName.text = name
            }
        }
    }
}