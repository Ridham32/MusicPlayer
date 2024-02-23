package com.ridhamsharma.musicplayer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


interface MusicClick{
    fun onSongPlayClick(musicContent: MusicContent, position: Int)
}

class RecyclerViewAdapter( var musicClick: MusicClick): RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    var musicContent: ArrayList<MusicContent> = arrayListOf()
    lateinit var musicClick1: MusicClick

    class ViewHolder(view : View):RecyclerView.ViewHolder(view) {
        var tvMusicName = view.findViewById<TextView>(R.id.tvMusicName)
       // var btnPlayMusic = view.findViewById<ImageView>(R.id.btnPlayMusic)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerlistview,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return musicContent.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvMusicName.setText(musicContent[position].title)
        musicClick1 = musicClick
        holder.itemView.setOnClickListener {
            musicClick1.onSongPlayClick(musicContent[position], position)
        }




    }

    fun updateList( musicContent: ArrayList<MusicContent>){
        System.out.println("musicContent ${musicContent.size}")
        this.musicContent.clear()
        this.musicContent.addAll(musicContent)
        notifyDataSetChanged()
    }


}