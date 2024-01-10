package com.example.automotive_example

import com.example.automotive_example.R
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import androidx.car.app.CarContext
import androidx.car.app.Screen
import androidx.car.app.model.Action
import androidx.car.app.model.CarIcon
import androidx.car.app.model.GridItem
import androidx.car.app.model.Pane
import androidx.car.app.model.PaneTemplate
import androidx.car.app.model.Row
import androidx.car.app.model.Template
import androidx.core.graphics.drawable.IconCompat


class CarHomeScreen(carContext: CarContext) : Screen(carContext) {

    private var text = "--"

    override fun onGetTemplate(): Template {

        val mGridIcon = IconCompat.createWithResource(carContext, R.drawable.ic_car)
        val mAlertIcon = IconCompat.createWithResource(carContext, R.drawable.ic_alert)
        val mSearchIcon = IconCompat.createWithResource(carContext, R.drawable.ic_search)
        val mMic = IconCompat.createWithResource(carContext, R.drawable.ic_mic)

        val gridItemCar = GridItem.Builder()
            .setTitle("Speech for Task")
            .setImage(CarIcon.Builder(mMic).build(), GridItem.IMAGE_TYPE_LARGE)
            .setOnClickListener { speechReco() }
            .build()

        val row = Row.Builder().setTitle(text).build()
        val action = Action.Builder().setTitle("Tap to add task").setOnClickListener {
            speechReco()
        }.build()

        val pane = Pane.Builder()
            .addRow(row)
            .addAction(action)
            .build()

        return PaneTemplate.Builder(
            pane
        ).setTitle("Co Tasker").build()

//        val gridItemCar2 = GridItem.Builder()
//            .setTitle(text)
//            .setImage(CarIcon.Builder(mMic).build(),GridItem.IMAGE_TYPE_LARGE)
//            .setOnClickListener {  }
//            .build()
//
////        row = Row.Builder().setTitle(text).build()
//
//        val row  = Row.Builder().setTitle("Record Audio").setImage(CarIcon.Builder(mMic).build(), Row.IMAGE_TYPE_ICON)
//            .setOnClickListener { speechReco() }.build()
//
//        val row2 = Row.Builder().setTitle(text).build()
//
//////        val gridItemAlert = GridItem.Builder()
////            .setTitle("Alert")
////            .setImage(CarIcon.Builder(mAlertIcon).build(),GridItem.IMAGE_TYPE_LARGE)
////            .setOnClickListener { screenManager.push(AlertScreen(carContext)) }
////            .build()
////        val gridItemSearch = GridItem.Builder()
////            .setTitle("Search")
////            .setImage(CarIcon.Builder(mSearchIcon).build(),GridItem.IMAGE_TYPE_LARGE)
////            .setOnClickListener {  }
////            .build()
//
//        val gridList = ItemList.Builder()
//            .addItem(gridItemCar)
////            .addItem(row)
//            .addItem(gridItemCar2)
////            .addItem(gridItemSearch)
//            .build()
//
//        carContext.requestPermissions(listOf(Manifest.permission.RECORD_AUDIO)
//        ) { grantedPermissions, rejectedPermissions -> }
////
//        return GridTemplate.Builder()
//            .setTitle("Co-Tasker Auto App")
//            .setSingleList(gridList)
//            .build()


//        val row = Row.Builder().setTitle("Speech").setImage(CarIcon.Builder(mMic).build(),
//            Row.IMAGE_TYPE_ICON
//        ).setOnClickListener { speechReco() }
//            .build()
//        val pane = Pane.Builder()
//            .addRow(row)
//            .addRow(row2)
//            .build()
////
////
////
//        return PaneTemplate.Builder(pane)
//            .setTitle("Co-Tasker App")
//            .build()


    }

    private fun speechReco() {
        var intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, carContext.packageName)
        val recognizer = SpeechRecognizer.createSpeechRecognizer(this.carContext)
        val listner = object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {
                Log.e("onReadyForSpeech", "onReadyForSpeech")
            }

            override fun onBeginningOfSpeech() {

            }

            override fun onRmsChanged(rmsdB: Float) {

            }

            override fun onBufferReceived(buffer: ByteArray?) {

            }

            override fun onEndOfSpeech() {

            }

            override fun onError(error: Int) {
                var mError = ""
                var mStatus = "Error detected"
                when (error) {
                    SpeechRecognizer.ERROR_NETWORK_TIMEOUT -> {
                        mError = " network timeout"
                    }

                    SpeechRecognizer.ERROR_NETWORK -> {
                        mError = " network"
                        //toast("Please check data bundle or network settings");
                        return
                    }

                    SpeechRecognizer.ERROR_AUDIO -> mError = " audio"
                    SpeechRecognizer.ERROR_SERVER -> {
                        mError = " server"
                    }

                    SpeechRecognizer.ERROR_CLIENT -> mError = " client"
                    SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> mError = " speech time out"
                    SpeechRecognizer.ERROR_NO_MATCH -> {
                        mError = " no match"
                    }

                    SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> mError = " recogniser busy"
                    SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> mError =
                        " insufficient permissions"
                }
                Log.i("TAG", "Error: $error - $mError")
            }

            override fun onResults(results: Bundle?) {
                val voiceResult = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                if (voiceResult == null) {
                    Log.e("SpeechRecoEmpty", "SpeechRecoEmpty")
                } else {
                    Log.e("SpeechRecoMatch", "SpeechRecoMatch")
                    Log.e("Specch", "SpecchLis : ${voiceResult}")
                    text = voiceResult.joinToString(" ")
                    invalidate()
                }
            }

            override fun onPartialResults(partialResults: Bundle?) {

            }

            override fun onEvent(eventType: Int, params: Bundle?) {

            }


        }
        recognizer.setRecognitionListener(listner)
        recognizer.startListening(intent)
    }

}
