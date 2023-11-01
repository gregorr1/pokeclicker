package com.example.pokeclicker

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.pokeclicker.network.Pokemon
import com.example.pokeclicker.network.Sprites
import com.example.pokeclicker.overview.ImageGridAdapter
import com.example.pokeclicker.overview.PokeApiStatus

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Pokemon>?) {
    val adapter = recyclerView.adapter as ImageGridAdapter
    adapter.submitList(data)
}

// Uses the Coil library to load an image by URL into an [ImageView]
@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, sprite: Sprites?) {
    sprite?.version?.generationIII?.rubySapphire?.frontDefault?.let { imgUrl ->
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        imgView.load(imgUri) {
            placeholder(R.drawable.loading_animation)
            error(R.drawable.ic_broken_image)
        }
    }
}

@BindingAdapter("pokemonStatus")
fun bindStatus(statusImageView: ImageView, status: PokeApiStatus) {
    when (status) {
        PokeApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        PokeApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        PokeApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}