package com.example.thewitcher3wiki

import android.view.LayoutInflater
import android.view.OrientationEventListener
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class RecyclerViewAdapter(private val titles: List<String>,
                          private val imageLink:List<String>,
                            private val listener: OnItemClickListener
                          ): RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),View.OnClickListener{
        override fun onClick(p0: View?) {
            val position = adapterPosition
            if(position!=RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }

        var imageView: ImageView? = null
        var largeTextView: TextView? = null

        init {
            imageView = itemView?.findViewById(R.id.imageView)
            largeTextView = itemView?.findViewById(R.id.textViewLarge)
            itemView.setOnClickListener(this)
        }
    }
    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_view_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        Picasso.get().load(imageLink[position]).into(holder.imageView)
        holder.largeTextView?.text = titles[position]
    }

    override fun getItemCount(): Int {
        return titles.size
    }
}