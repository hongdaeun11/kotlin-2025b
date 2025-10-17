package com.appweek05

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    // UI component (widget)
    private lateinit var buttonAdd: Button
    private lateinit var buttonClear: Button
    private lateinit var editTextStudent: EditText
    private lateinit var textViewCount: TextView
    private lateinit var listView: ListView

    //Collection
    private lateinit var studentList: ArrayList<String>
    private lateinit var adapter: ArrayAdapter<String>


    companion object{
        private const val TAG = "KotlinWeek06App"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG,"onCreate: AppWeek05 started")

        setupViews()
        setupListView()
        setupListeners()
        addInitialData()
    }

    private fun setupViews(){ //컴포넌트 위젯 다섯개를 바인딩시켜줌
        listView = findViewById(R.id.listViewStudents) //여기서 바인딩
        editTextStudent = findViewById(R.id.editTextStudent)
        buttonClear = findViewById(R.id.buttonClear)
        buttonAdd = findViewById(R.id.buttonAdd)
        textViewCount = findViewById(R.id.textViewCount)

        studentList = ArrayList()
        Log.d(TAG, "Views initialized")
    }
    private fun setupListView(){ 
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, studentList)
        //adapter객체를 생성해서 연결한 것임(studentList와 연결)
        //ArrayList와 adapter을 먼저 연결
        listView.adapter = adapter  //adapter와 listview연결
        Log.d(TAG, "ListViews and Adapter setup completed")
    }
    private fun setupListeners(){
        buttonAdd.setOnClickListener{
            addStudent()
        }
        buttonClear.setOnClickListener{
            clearAllStudents()
        }
        listView.setOnItemLongClickListener{ _, _, position, _ ->  removeStudent(position)  //람다함수 작성
            true
        }
        listView.setOnItemClickListener { _, _, position, _ ->
            val studentName = studentList[position]
            Toast.makeText(
                this,
                "Selected: $studentName (Position: ${position+1}",
                Toast.LENGTH_SHORT
            ).show() //메서드 체이닝
            Log.d(TAG, "Selected: $studentName at position: $position") //logcat에서는 개발자가 보기 쉽게 그대로
        }
        Log.d(TAG, "Event listeners setup completed")
    }
    private fun addStudent(){
        val studentName = editTextStudent.text.toString().trim()

        if(studentName.isEmpty()){
            Toast.makeText(this, "Please enter a student name", Toast.LENGTH_SHORT).show()
            Log.d(TAG, "Attempted to add empty student")
            return //학생 추가하는 작업을 멈춰야함
        }
        if(studentList.contains(studentName)){ //학생 이름이 이미 존재하는 학생이면 입력 못하게
            Toast.makeText(this, "Student '$studentName' already exists", Toast.LENGTH_SHORT).show()
            Log.d(TAG, "Attempted to add duplicate student : $studentName")
            return
        }

        studentList.add(studentName)   //여기까지 온거면 공백도 아니고 중복도 아니라는 것
        adapter.notifyDataSetChanged() //내용이 바뀌었다고 알려주는 것
        editTextStudent.text.clear()   //입력한 위젯 초기화
        updateStudentCount()
        Toast.makeText(this, "Added: $studentName", Toast.LENGTH_SHORT).show()
        Log.d(TAG, "Added student: $studentName (Total: ${studentList.size})") //학생이름과 전체 학생 수 출력
    }
    private fun clearAllStudents(){
        if(studentList.isEmpty()){
            Toast.makeText(this, "List is already empty", Toast.LENGTH_SHORT).show()
            return
        }
        val count = studentList.size   //몇 명 지웠는지
        studentList.clear()
        adapter.notifyDataSetChanged()   //변경사항을 알려야 함
        updateStudentCount()
        Toast.makeText(this, "Cleared all $count students", Toast.LENGTH_SHORT).show()
        Log.d(TAG, "Cleared all students (Total cleared: $count)")
    }
    private fun removeStudent(position: Int){   //index로 받아도 되고 position으로 해도 되고
        if(position >= 0 && position < studentList.size){
            val removedStudent = studentList.removeAt(position)  //removeAt이 리스트로부터 삭제된 원소를 리턴함 => 리스트에 문자열을 담았기 때문에 문자열을 리턴함
            adapter.notifyDataSetChanged()
            updateStudentCount()

            Toast.makeText( this, "Removed: $removedStudent", Toast.LENGTH_SHORT).show()
            Log.d(TAG, "Removed student: $removedStudent (Remaining: ${studentList.size})")
        }
    }

    private fun updateStudentCount(){
        textViewCount.text = "Total Students : ${studentList.size}"
    }
    private fun addInitialData(){
        val initialStudents = listOf("Kim","Lee","Park")
        studentList.addAll(initialStudents) //add는 하나만 가능 addAll은 여러개 한번에 가능
        adapter.notifyDataSetChanged()
        updateStudentCount()
        Log.d(TAG, "Added initial data: $initialStudents")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: Current student count: ${studentList.size}")
    }
    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: Saving state with ${studentList.size} students")
    }
    //activity 가 멈추면 onPause 다시 재개되면 onResume
}