package jp.co.yumemi.android.code_check

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(
    private val itemClickListener: OnItemClickListener,
) : ListAdapter<item, CustomAdapter.ViewHolder>(diff_util){

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val itemview:TextView = view.findViewById(android.R.id.text1)
    }

    interface OnItemClickListener{
        fun itemClick(item: item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val view= LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_item, parent, false)
        return ViewHolder(view)
    }

    //検索結果表示
    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        val item= getItem(position)
        holder.itemview.text = item.name
        //検索結果をタップ
        holder.itemView.setOnClickListener{
            itemClickListener.itemClick(item)
        }
    }
}
