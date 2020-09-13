package com.anggitprayogo.movon.feature.favourite.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import com.anggitprayogo.movon.R
import com.anggitprayogo.movon.data.local.entity.MovieEntity
import com.anggitprayogo.movon.feature.favourite.FavouriteActivity
import com.eoa.tech.core.util.ext.load
import kotlinx.android.synthetic.main.row_item_favourite_movie.view.*


/**
 * Created by Anggit Prayogo on 13,September,2020
 * GitHub : https://github.com/anggit97
 */
class FavouriteListAdapter : RecyclerView.Adapter<FavouriteListAdapter.ViewHolder>() {

    private var items: MutableList<MovieEntity> = mutableListOf()
    private lateinit var activity: FavouriteActivity

    fun setItems(items: MutableList<MovieEntity>) {
        this.items = items
        notifyDataSetChanged()
    }

    fun setActivity(activity: FavouriteActivity) {
        this.activity = activity
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.inflate(parent)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(items[position], activity)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        companion object {
            fun inflate(parent: ViewGroup): ViewHolder {
                return ViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.row_item_favourite_movie, parent, false)
                )
            }
        }

        fun bindItem(
            movie: MovieEntity,
            activity: FavouriteActivity
        ) {
            with(itemView) {
                ivPoster.load(movie.posterUrl ?: "")
                tvTitle.text = movie.title
                tvReleaseDate.text = movie.releaseDate
                tvOverview.text = movie.overview
            }

            itemView.setOnClickListener {
                val activityOptionsCompat: ActivityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        activity,
                        itemView.ivPoster,
                        "imageMain"
                    )

//                val intent = Intent(itemView.context, FavouriteDetailActivity::class.java).apply {
//                    putExtra(MovieDetailActivity.MOVIE_ID_KEY, movie.movieId.toString())
//                }
//                itemView.context.startActivity(intent, activityOptionsCompat.toBundle())
            }
        }
    }
}