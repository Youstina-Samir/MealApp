package com.example.myapplication.view.adapters

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings.Global.putString
import android.text.TextUtils.replace
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.MealByAreaFragment
import com.example.myapplication.Model.Areas
import com.example.myapplication.NewMainActivity
import com.example.myapplication.R

class CountiresAdapter (var Countireslist: ArrayList<Areas>, val context: Context): RecyclerView.Adapter<CountiresAdapter.CountiresViewHolder>() {
    class CountiresViewHolder(val row: View) : RecyclerView.ViewHolder(row) {
        val img = row.findViewById<ImageView>(R.id.imageView)
        val name = row.findViewById<TextView>(R.id.nameview)
        // val desc = row.findViewById<TextView>(R.id.descview)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountiresViewHolder {
        val inflator = LayoutInflater.from(parent.context)
        val rowView = inflator.inflate(R.layout.country_row, parent, false)
        return CountiresViewHolder(rowView)

    }

    override fun getItemCount(): Int {
        return Countireslist.size
    }

    override fun onBindViewHolder(holder: CountiresViewHolder, position: Int) {
        holder.name.text = Countireslist[position].strArea
        holder.img.setImageResource(R.drawable.world_food_nobg)
        holder.row.setOnClickListener({
            Toast.makeText(context, Countireslist[position].strArea, Toast.LENGTH_SHORT).show()

            val fragment = MealByAreaFragment()
            val bundle = Bundle().apply {
                putString("AreaName", Countireslist[position].strArea)
            }
            fragment.arguments = bundle
            val manager = (context as NewMainActivity).supportFragmentManager
            val transaction = manager.beginTransaction()
            transaction.replace(R.id.flFragment, fragment)
            transaction.commit()
        })
    }
}

