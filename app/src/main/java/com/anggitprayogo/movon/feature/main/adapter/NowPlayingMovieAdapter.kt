package com.anggitprayogo.movon.feature.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anggitprayogo.movon.data.remote.Movie
import com.anggitprayogo.movon.databinding.RowItemMovieWithTitleBinding
import com.eoa.tech.core.util.ext.load


/**
 * Created by Anggit Prayogo on 12,September,2020
 * GitHub : https://github.com/anggit97
 */
class NowPlayingMovieAdapter : RecyclerView.Adapter<NowPlayingMovieAdapter.ViewHolder>() {

    private var movieList = mutableListOf<Movie>()

    fun setItems(movieList: MutableList<Movie>) {
        this.movieList = movieList
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: RowItemMovieWithTitleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(movie: Movie) {
            with(binding) {
                ivBanner.load(movie.getBannerMovie())
                tvTitle.text = movie.title
                tvReleaseDate.text = movie.releaseDate
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowItemMovieWithTitleBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(movieList[position])
    }

    override fun getItemCount(): Int = movieList.size
}