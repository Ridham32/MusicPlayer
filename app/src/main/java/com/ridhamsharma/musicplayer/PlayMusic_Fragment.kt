package com.ridhamsharma.musicplayer

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.ridhamsharma.musicplayer.databinding.FragmentPlayMusicBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PlayMusic_Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PlayMusic_Fragment : Fragment() {
    lateinit var binding : FragmentPlayMusicBinding
    lateinit var mainActivity: MainActivity
     var indexCount :Int = 0
    private var param1: String? = null
    private var param2: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        mainActivity = activity as MainActivity
        super.onCreate(savedInstanceState)
        arguments?.let {
            mainActivity.position = it.getInt("position")
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPlayMusicBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        PlaySong()
       binding.btnForward.setOnClickListener {
           if(indexCount == mainActivity.musicList.size -1){
               indexCount = 0
               mainActivity.mediaPlayer.stop()
               mainActivity.mediaPlayer.reset()
               mainActivity.musicContent = mainActivity.musicList[indexCount]
               mainActivity.mediaPlayer.setDataSource(mainActivity.musicList[indexCount].storageLocation)
               mainActivity.mediaPlayer.prepare()
               mainActivity.mediaPlayer.start()
               binding.tvChoosenSongName.setText(mainActivity.musicList[indexCount].title)
           } else{
               indexCount++
               mainActivity.mediaPlayer.stop()
               mainActivity.mediaPlayer.reset()
               mainActivity.musicContent = mainActivity.musicList[indexCount]
               mainActivity.mediaPlayer.setDataSource(mainActivity.musicList[indexCount].storageLocation)
               mainActivity.mediaPlayer.prepare()
               mainActivity.mediaPlayer.start()
               binding.tvChoosenSongName.setText(mainActivity.musicList[indexCount].title)

           }
           binding.btnBackward.setOnClickListener {
               if(indexCount == 0){
                   indexCount = mainActivity.musicList.size -1
                   mainActivity.mediaPlayer.stop()
                   mainActivity.mediaPlayer.reset()
                   mainActivity.musicContent = mainActivity.musicList[indexCount]
                   mainActivity.mediaPlayer.setDataSource(mainActivity.musicList[indexCount].storageLocation)
                   mainActivity.mediaPlayer.prepare()
                   mainActivity.mediaPlayer.start()
                   binding.tvChoosenSongName.setText(mainActivity.musicList[indexCount].title)

               } else{
                   indexCount--
                   mainActivity.mediaPlayer.stop()
                   mainActivity.mediaPlayer.reset()
                   mainActivity.musicContent = mainActivity.musicList[indexCount]
                   mainActivity.mediaPlayer.setDataSource(mainActivity.musicList[indexCount].storageLocation)
                   mainActivity.mediaPlayer.prepare()
                   mainActivity.mediaPlayer.start()
                   binding.tvChoosenSongName.setText(mainActivity.musicList[indexCount].title)
               }
           }
       }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PlayMusic_Fragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PlayMusic_Fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    fun PlaySong(){
        System.out.println("music on")
        if(mainActivity.mediaPlayer.isPlaying){
            binding.tvChoosenSongName.setText(mainActivity.musicContent.title)
            mainActivity.mediaPlayer.pause()
        } else{
            mainActivity.mediaPlayer.start()

        }
    }
}