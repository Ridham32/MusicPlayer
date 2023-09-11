package com.ridhamsharma.musicplayer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


interface MusicClick{
    fun onSongPlayClick(musicContent: MusicContent)
}

class RecyclerViewAdapter( var musicClick: MusicClick): RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    var musicContent: ArrayList<MusicContent> = arrayListOf()
    class ViewHolder(var view : View):RecyclerView.ViewHolder(view) {
        var tvMusicName = view.findViewById<TextView>(R.id.tvMusicName)


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
        //holder.musicClick= musicClick
        holder.view.setOnClickListener {
            musicClick.onSongPlayClick(musicContent[position])
        }
    }

    fun updateList( musicContent: ArrayList<MusicContent>){
        System.out.println("musicContent ${musicContent.size}")
        this.musicContent.clear()
        this.musicContent.addAll(musicContent)
        notifyDataSetChanged()
    }


}