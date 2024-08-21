package com.example.myapplication.view

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.Model.Category
import com.example.myapplication.R

class CategoriesAdapter(var categorylist: ArrayList<Category>, val context: Context):RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {
    class CategoryViewHolder(val row:View): RecyclerView.ViewHolder(row) {
        val img =row.findViewById<ImageView>(R.id.imageView)
        val name = row.findViewById<TextView>(R.id.nameview)
        val desc = row.findViewById<TextView>(R.id.descview)
    }
//inflate row
    override fun onCreateViewHolder(recycler: ViewGroup, viewType: Int): CategoryViewHolder {
        val inflator = LayoutInflater.from(recycler.context)
        val rowView = inflator.inflate(R.layout.dessert_row,recycler,false)
        return CategoryViewHolder(rowView)     //a7na bnreturn elDessertViewHolder in line 17
    }

    override fun getItemCount(): Int {
        return categorylist.size
    }
//set the data

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.name.text=categorylist[position].strCategory
        holder.desc.text=categorylist[position].strCategoryDescription

        Glide.with(context)
            .load(categorylist[position].strCategoryThumb)
            .into(holder.img)

      //  holder.img.setImageResource(categorylist[position].strCategoryThumb!!.toInt())
     holder.row.setOnClickListener(View.OnClickListener { Toast.makeText(context,categorylist[position].strCategory, Toast.LENGTH_SHORT ).show()
    val outIntent = Intent (context , MealsByCategory::class.java);
     outIntent.putExtra("categoryName",categorylist[position].strCategory)
    context.startActivity(outIntent)
     })
}
}

