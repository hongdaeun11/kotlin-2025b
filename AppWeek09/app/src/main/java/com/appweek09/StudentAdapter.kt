package com.appweek09

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.appweek09.data.Student
import com.appweek09.databinding.ItemStudentBinding

class StudentAdapter(
    private val studentList: List<Student>,
    private val onItemClick: (Student, Int) -> Unit,
    private val onItemLongClick: (Int) -> Unit //뭘 지울 것 인지에 대한 인덱스 정보만 있으면 됨
) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>(){


    inner class StudentViewHolder(
        private val binding: ItemStudentBinding
    ) : RecyclerView.ViewHolder(binding.root){
        fun bind(student:Student){
            binding.apply { //findViewById()로 바인딩 하던 것을 이너 클래스로 묶은 것
                textViewName.text = student.name
                textViewDept.text = student.department
                textViewGrade.text = student.grade
                textViewEmail.text = student.email

                root.setOnClickListener{
                    onItemClick(student, adapterPosition)
                }
                root.setOnLongClickListener{
                    onItemLongClick(adapterPosition)
                    true
                }

            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val binding = ItemStudentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return StudentViewHolder(binding)
    }


    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        holder.bind(studentList[position])
    }

    override fun getItemCount() = studentList.size

}