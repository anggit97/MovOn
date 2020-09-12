package com.anggitprayogo.movon.feature.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anggitprayogo.movon.data.remote.Movie
import com.anggitprayogo.movon.databinding.RowItemPopularMovieBinding
import com.eoa.tech.core.util.ext.load


/**
 * Created by Anggit Prayogo on 12,September,2020
 * GitHub : https://github.com/anggit97
 */
class PopularMovieAdapter : RecyclerView.Adapter<PopularMovieAdapter.ViewHolder>() {

    private var moviesList = mutableListOf<Movie>()

    fun setItems(movieList: MutableList<Movie>) {
        this.moviesList = movieList
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: RowItemPopularMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(movie: Movie) {
            with(binding) {
                ivBanner.load(movie.getBannerMovie())
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = RowItemPopularMovieBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(moviesList[position])
    }

    override fun getItemCount(): Int = moviesList.size
}