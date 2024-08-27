package com.example.myapplication.view.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.Model.Meals
import com.example.myapplication.R
import com.example.myapplication.view.MealDescriptionActivity
import com.example.myapplication.view.OnButtonClick
import com.google.firebase.auth.FirebaseAuth

class MealAdapter(var meallist: List<Meals>, val context: Context, val OnButtonClick: OnButtonClick): RecyclerView.Adapter<MealAdapter.MealViewHolder>() {
    class MealViewHolder (val row: View): RecyclerView.ViewHolder(row){
        val img =row.findViewById<ImageView>(R.id.mealimg)
        val name = row.findViewById<TextView>(R.id.mealName)
        val desc = row.findViewById<TextView>(R.id.mealdesc)
        val favbtn = row.findViewById<Button>(R.id.addTOFavBtn)
        val deletebtn = row.findViewById<Button>(R.id.deletebtn)
        var userID= FirebaseAuth.getInstance().currentUser?.uid

    }

    override fun onCreateViewHolder(recycler: ViewGroup, viewType: Int): MealViewHolder {
        val inflator = LayoutInflater.from(recycler.context)
        val rowView = inflator.inflate(R.layout.meal_row,recycler,false)
        return MealViewHolder(rowView)
    }

    override fun getItemCount(): Int {
      return  meallist.size
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {

        Glide.with(context)
            .load(meallist[position].strMealThumb)
            .centerCrop()
            .into(holder.img)


        holder.name.text=meallist[position].strMeal
        holder.desc.text=meallist[position].idMeal
        holder.favbtn.setOnClickListener({
         //   OnButtonClick.favbtnclick(ConvertMealsToFavmeal(meallist[position],holder.userID!!))
            OnButtonClick.favbtnclick(meallist[position])
        })
        holder.deletebtn.setOnClickListener({
           // OnButtonClick.deletebtnclick(ConvertMealsToFavmeal(meallist[position],holder.userID!!))
            OnButtonClick.deletebtnclick(meallist[position])
        })
       holder.row.setOnClickListener({
            Toast.makeText(context,meallist[position].strMeal, Toast.LENGTH_SHORT ).show()
            val outIntent = Intent (context ,   MealDescriptionActivity::class.java);
            outIntent.putExtra("MealName" ,meallist[position].strMeal)
            outIntent.putExtra("MealImg",meallist[position].strMealThumb )
            context.startActivity(outIntent)
        })


    }
}