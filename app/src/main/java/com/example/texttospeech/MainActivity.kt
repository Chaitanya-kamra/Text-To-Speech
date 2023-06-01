package com.example.texttospeech

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Toast
import com.example.texttospeech.databinding.ActivityMainBinding
import java.util.Locale

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private var tts :TextToSpeech? = null
    private var binding:ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        tts = TextToSpeech(this,this)

        binding?.button?.setOnClickListener { view ->
            if (binding?.editTextText?.text!!.isEmpty()){
                Toast.makeText(this@MainActivity ,"Type Some Text", Toast.LENGTH_SHORT).show()
            }else{
                speakOut(binding?.editTextText?.text.toString())
            }
        }


    }

    private fun speakOut(text:String){
        tts?.speak(text,TextToSpeech.QUEUE_FLUSH,null,"")

    }

    override fun onInit(status: Int) {
            if (status == TextToSpeech.SUCCESS){
                val result = tts!!.setLanguage(Locale.UK)
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                    Log.e("TTS","not Supported")
                }
            }else{
                Log.e("TTS","init failed")
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (tts != null){
            tts?.stop()
            tts?.shutdown()
        }
        binding = null
    }
}