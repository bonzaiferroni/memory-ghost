package com.bollwerks.memoryghost.utils.ezlisten

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import androidx.compose.ui.text.intl.Locale

class EzListener(
    context: Context
) {
    private val speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)
//    private val btAdapter: BluetoothAdapter
//    private var pairedDevices: Set<BluetoothDevice>? = null
//    private var btHeadset: BluetoothHeadset? = null

    init {
//        btAdapter = (context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager)
//            .adapter
//
//        pairedDevices = getBondedDevices()
//
//        var listener = object : BluetoothProfile.ServiceListener {
//            override fun onServiceConnected(profile: Int, proxy: BluetoothProfile) {
//                if (profile == BluetoothProfile.HEADSET) {
//                    btHeadset = proxy as BluetoothHeadset
//                }
//            }
//
//            override fun onServiceDisconnected(profile: Int) {
//                if (profile == BluetoothProfile.HEADSET) {
//                    btHeadset = null
//                }
//            }
//        }
//
//        btAdapter.getProfileProxy(context, listener, BluetoothProfile.HEADSET);
    }


    fun onResults(
        onResults: (String) -> Unit,
        onError: () -> Unit = {},
    ) {
        speechRecognizer.setRecognitionListener(EzRecognitionListener(onResults, onError))
    }

//    @SuppressLint("MissingPermission")
//    fun getBondedDevices(): Set<BluetoothDevice>? {
//        return btAdapter.bondedDevices
//    }

//    @SuppressLint("MissingPermission")
    fun startListening(
        biasingStrings: List<String>? = null,
        additionalTimeMs: Int? = null,
    ) {
//        val devices = pairedDevices
//        val headset = btHeadset
//        if (btAdapter.isEnabled && devices != null && headset != null) {
//            for (tryDevice in devices) {
//                //This loop tries to start VoiceRecognition mode on every paired device until
//                // it finds one that works(which will be the currently in use bluetooth headset)
//                if (headset.startVoiceRecognition(tryDevice)) {
//                    break
//                }
//            }
//        }
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.current.toString())
            putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak now...")
            if (biasingStrings != null && Build.VERSION.SDK_INT > Build.VERSION_CODES.TIRAMISU) {
                putExtra(RecognizerIntent.EXTRA_BIASING_STRINGS, biasingStrings.toTypedArray())
            }
            additionalTimeMs?.let {
                putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, it)
            }
        }
        speechRecognizer.startListening(intent)
    }

    fun destroy() {
        speechRecognizer.destroy()
    }
}

class EzRecognitionListener(
    private val onResults: (String) -> Unit,
    private val onError: () -> Unit,
) : RecognitionListener {

    override fun onResults(results: Bundle?) {
        val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
        if (!matches.isNullOrEmpty()) {
            onResults(matches[0])
        }
    }

    // Implement other necessary methods of RecognitionListener interface
    override fun onReadyForSpeech(params: Bundle?) {}
    override fun onBeginningOfSpeech() {}
    override fun onRmsChanged(rmsdB: Float) {}
    override fun onBufferReceived(buffer: ByteArray?) {}
    override fun onEndOfSpeech() {}

    override fun onError(error: Int) {
        onError()
    }

    override fun onPartialResults(partialResults: Bundle?) {}
    override fun onEvent(eventType: Int, params: Bundle?) {}
}