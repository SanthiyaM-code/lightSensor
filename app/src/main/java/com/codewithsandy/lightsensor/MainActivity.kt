package com.codewithsandy.lightsensor

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import com.mikhaellopez.circularprogressbar.CircularProgressBar

class MainActivity : AppCompatActivity(),SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var brightness:Sensor?=null
    private lateinit var text:TextView
    private lateinit var cpb:CircularProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        text=findViewById(R.id.text)
        cpb=findViewById(R.id.cpb)

        setUpSensorStuff()


    }

    private fun setUpSensorStuff() {
        sensorManager=getSystemService(SENSOR_SERVICE) as SensorManager
        brightness=sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
    }

    private fun brightness(brightness:Float):String{
        return when (brightness.toInt())
        {
            0->"Pitch Black"
            in 1..10 ->"Dark"
            in 11..5000 ->"Normal"
            in 5001..25000->"Incredibly bright"
            else->"This Light will Blind you!!"
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {

        if (event?.sensor?.type==Sensor.TYPE_LIGHT){
            val light=event.values[0]

            text.text="Sensor: $light\n${brightness(light)}"
            cpb.setProgressWithAnimation(light)
        }

    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        return
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this,brightness,SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }
}