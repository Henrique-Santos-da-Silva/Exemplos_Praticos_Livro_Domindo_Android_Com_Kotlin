package dominando.android.multimedia

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_voice_recognition.*
import java.util.ArrayList

class VoiceRecognitionFragment: MultimediaFragment()  {
    private val voiceIntent: Intent by lazy {
        Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_PROMPT, "Fale alguma coisa!")
        }
    }
    private var isRecognizing = false
    private val speechRecognizer: SpeechRecognizer by lazy {
        SpeechRecognizer.createSpeechRecognizer(activity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (voiceIntent.resolveActivity(requireActivity().packageManager) == null) {
//            btnVoice.isEnabled = false
            Toast.makeText(activity, "Aparelho não suporta comando de voz.", Toast.LENGTH_SHORT).show()
            activity?.finish()
        } else {
            speechRecognizer.setRecognitionListener(recognitionListener)
        }
    }

    override fun onPause() {
        super.onPause()
        speechRecognizer.destroy()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_voice_recognition, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnVoice.setOnClickListener {
            openVoiceRecognitionIntent()
        }
    }

//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        if (voiceIntent.resolveActivity(requireActivity().packageManager) == null) {
//            btnVoice.isEnabled = false
//            Toast.makeText(activity, "Aparelho não suporta comando de voz.", Toast.LENGTH_SHORT).show()
//            activity?.finish()
//        }
//    }

    private fun openVoiceRecognitionIntent() {
        if (chkIntent.isChecked) {
            activityResult.launch(Intent(voiceIntent))
        } else {
            if (hasPermission()) {
                if (isRecognizing) {
                    stopRecognizing()
                } else {
                    startRecognizing()
                }
            } else {
                requestPermissions()
            }
        }

    }

    private fun startRecognizing() {
        pgrVoice.visibility = View.VISIBLE
        pgrVoice.isIndeterminate = true
        voiceIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5)
        speechRecognizer.startListening(voiceIntent)
        isRecognizing = true
    }


    private fun stopRecognizing() {
        pgrVoice.visibility = View.INVISIBLE
        pgrVoice.isIndeterminate = false
        speechRecognizer.stopListening()
        isRecognizing = false
    }

    private val activityResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val matches = result.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            updateResultsList(matches)
        }
    }

    private fun updateResultsList(results: ArrayList<String>?) {
        if (results != null) {
            lstResults.adapter = activity?.let { ArrayAdapter(it, android.R.layout.simple_list_item_1, results) }
        }
        pgrVoice.visibility = View.INVISIBLE
        isRecognizing = false
    }

    private val recognitionListener = object : RecognitionListener {
        override fun onBeginningOfSpeech() {
            pgrVoice.isIndeterminate = false
            pgrVoice.max = 10
        }

        override fun onEndOfSpeech() {
            pgrVoice.isIndeterminate = true
        }

        override fun onRmsChanged(rmsdB: Float) {
            pgrVoice.progress = rmsdB.toInt()
        }

        override fun onError(error: Int) {
            Toast.makeText(activity, "Problemas no comando de voz. Erro $error", Toast.LENGTH_SHORT).show()
            pgrVoice.visibility = View.INVISIBLE
        }

        override fun onResults(bundle: Bundle) {
            val results = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
            updateResultsList(results)
        }

        override fun onPartialResults(partialResults: Bundle?) {
        }

        override fun onReadyForSpeech(params: Bundle?) {
        }

        override fun onBufferReceived(buffer: ByteArray?) {
        }

        override fun onEvent(eventType: Int, params: Bundle?) {
        }
    }

}