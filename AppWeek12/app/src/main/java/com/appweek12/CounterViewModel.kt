package com.appweek12

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.lifecycle.ViewModel

class CounterViewModel : ViewModel() { //기본 생성자 필요 매개 변수 없기 때문에 괄호 필요
    private val _count = MutableStateFlow(0)
    val count: StateFlow<Int> = _count.asStateFlow() //asStateFlow()가 뭐냐면 바로 위에서 MutableStateFlow를 선엉ㄴ
    // asStateFlow()는 읽기 전용으로 바꾸는 함수 _count를 읽기 전용으로 변경

    fun increment(){
       // _count.value = (_count.value ?: 0) + 1   //?: 앨비스 연산자(널값을 체크)  / stateFlow는 널값을 체크하지 않음
        _count.value += 1

    }
    fun decrement(){
        _count.value -= 1
    }
    fun reset(){
        _count.value = 0
    }
    fun incrementBy10(){
        _count.value = _count.value + 10
    }

}