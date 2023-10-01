package com.ridhamsharma.musicplayer

data class MusicContent(
    var position: Int? = 0,
    var title: String? = null,
    var artistName: String? = null,
    var duration: String? = null,
    var storageLocation: String? = null,
    var isPlaying: String? = null
)
