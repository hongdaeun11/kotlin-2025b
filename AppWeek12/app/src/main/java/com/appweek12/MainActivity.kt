package com.appweek12

import android.graphics.Color
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.appweek12.databinding.ActivityMainBinding
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: CounterViewModel by viewModels() //by는 위임할 때 사용 delegation
    //위임하면 NPE날 가능성 줄어듦 lazy 초기화를 해주는 것
    



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater) //이 코드를 까먹으면 NullpointException이 날 수 있음
         setContentView(binding.root)

        setupObservers()

        setupListeners()

    }

    private fun setupObservers() {  //관측하고 있다가 변화가 되면 CounterViewModel 에 알림 코루틴의 flow와 같이
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.count.collect{
                    count -> binding.textViewCount.text = count.toString() //count변수에 담긴 integer을 스트링으로 변환

                    when{
                        count > 0 -> binding.textViewCount.setTextColor(Color.BLUE)
                        count < 0 -> binding.textViewCount.setTextColor(Color.RED)
                        else -> binding.textViewCount.setTextColor(Color.BLACK)
                    }
                }
            }
        }


    }


    private fun setupListeners(){
        binding.buttonPlus.setOnClickListener{
//            count++
//            updateCountDisplay()
            viewModel.increment()
        }
        binding.buttonMinus.setOnClickListener{
            viewModel.decrement()
        }
        binding.buttonReset.setOnClickListener{
            viewModel.reset()
        }
        binding.buttonPlus10.setOnClickListener{
            viewModel.incrementBy10()
        }
    }


}