package com.appweek12

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.appweek12.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var count = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
    }

    private fun setupListeners(){
        binding.buttonPlus.setOnClickListener{
            count++
            updateCountDisplay()
        }
        binding.buttonMinus.setOnClickListener{
            count--
            updateCountDisplay()
        }
        binding.buttonReset.setOnClickListener{
            count = 0
            updateCountDisplay()
        }
        binding.buttonPlus10.setOnClickListener{
            count+=10 //count = count + 10
            updateCountDisplay()
        }
    }

    private fun updateCountDisplay() {
        binding.textViewCount.text = count.toString() //count변수에 담긴 integer을 스트링으로 변환

        when{
            count > 0 -> binding.textViewCount.setTextColor(Color.BLUE)
            count < 0 -> binding.textViewCount.setTextColor(Color.RED)
            else -> binding.textViewCount.setTextColor(Color.BLACK)
        }

    }
}