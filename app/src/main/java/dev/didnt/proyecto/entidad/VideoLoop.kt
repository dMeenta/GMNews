package dev.didnt.proyecto.entidad

import android.media.MediaPlayer

class VideoLoop: MediaPlayer.OnPreparedListener {
    override fun onPrepared(mp: MediaPlayer?) {
        mp?.isLooping = true
    }

}