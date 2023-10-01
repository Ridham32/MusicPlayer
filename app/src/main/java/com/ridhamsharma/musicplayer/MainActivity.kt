package com.ridhamsharma.musicplayer

import android.content.pm.PackageManager
import android.database.Cursor
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.ridhamsharma.musicplayer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var navController: NavController
    lateinit var musicContent: MusicContent
    var musicList = ArrayList<MusicContent>()
    lateinit var musicViewModel: MusicViewModel
    var position = -1
    var mediaPlayer: MediaPlayer = MediaPlayer()
    var permission = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        if (it) {
            getSongs()
        } else {
            var dialog =  AlertDialog.Builder(this)
                .setTitle("Permission not Granted")
                .setMessage("Allow MusicPlayer to Access Local Storage..... go to settings")
            //alert.. cannot run app without permission
            //option pop-up ->> go to setting
            //exit finish
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        musicViewModel = ViewModelProvider(this)[MusicViewModel::class.java]
        navController = findNavController(R.id.playlist_Fragment)
        binding.bottomBar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.fragmentPlaylist -> {
                    navController.navigate(R.id.fragment_playlist)
                }

                R.id.fragmentPlayMusic -> {
                    navController.navigate(R.id.fragment_playMusic)
                }
            }
            return@setOnItemSelectedListener true
        }
        navController.addOnDestinationChangedListener { _, destination, argument ->
            when (destination.id) {
                R.id.fragmentPlaylist -> binding.bottomBar.menu.get(0).isChecked = true
                R.id.fragmentPlayMusic -> binding.bottomBar.menu.get(1).isChecked = true

            }
        }

    }

    //toget permission ... on resume
    override fun onResume() {
        super.onResume()
        //to check permission?
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ){
            getSongs()
        }else{
            permission.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)

        }
        //after allowed for permission
    }

    //populate list use getSong fun
    fun getSongs() {
        musicList.clear()
        var uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val selection = MediaStore.Audio.Media.IS_MUSIC
        val cursor: Cursor? = contentResolver?.query(uri, null, selection, null, null)
        // take cursor to data
        if (cursor != null) {
            if (cursor?.moveToFirst() == true) {
                while (cursor?.isLast == false) {
                    musicList.add(
                        MusicContent(
                            title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)),
                            duration = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)),
                            artistName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)),
                            storageLocation = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA)),
                        )
                    )
                    cursor.moveToNext()
                }
            }
            musicViewModel.musicContentList.value = musicList
        }
    }
}

