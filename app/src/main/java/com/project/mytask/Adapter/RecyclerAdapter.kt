package com.project.mytask.Adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.project.mytask.Fragment.FullScreenFragmentDialog
import com.project.mytask.Pojo.Data
import com.project.mytask.R


class RecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder> {

    var mItems: List<Data> = ArrayList()
    var context: Context? = null

    constructor(mItems: List<Data>, context: Context?) : super() {
        this.mItems = mItems
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.adapter_class, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = mItems[position]

        if(mItems.get(position).title != null) {
            println("items:" + item.toString())
            (holder as ViewHolder).txt_name.setText(mItems.get(position).title)
        }

        holder.itemView.setOnClickListener({
           // println("Val:" + mItems.get(position).title)
           // println("id:" + mItems.get(position).id)

           /* val activity = context as FragmentActivity
            val fm = activity.supportFragmentManager
            val alertDialog = FullScreenFragmentDialog()
           // alertDialog.show(fm, "fragment_alert")
            alertDialog.show(fm,item.toString())*/

            val activity = context as FragmentActivity
            val bundle = Bundle()
            bundle.putString("userId",mItems.get(position).userId)
            bundle.putString("id",mItems.get(position).id)
            bundle.putString("title",mItems.get(position).title)
            bundle.putString("body",mItems.get(position).body)

            val dialogFragment = FullScreenFragmentDialog()
            dialogFragment.arguments = bundle
            dialogFragment.show(activity.supportFragmentManager, "dialog_event")
        })
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    fun Update(list: List<Data>) {
        mItems = list
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var txt_name: TextView
       // var image: ImageView

        init {
            txt_name = view.findViewById(R.id.txt1)
           // image = view.findViewById(R.id.image);
        }
        /* val image: ImageView = itemView!!.findViewById(R.id.image)
         fun bind(str: String, position: Int){
             tvMovieName.text = str
         }*/
    }
}