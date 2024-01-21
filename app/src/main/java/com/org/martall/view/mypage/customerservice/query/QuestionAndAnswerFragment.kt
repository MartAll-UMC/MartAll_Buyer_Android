package com.org.martall.view.mypage.customerservice.query

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.org.martall.databinding.FragmentQuestionAndAnswerBinding

/*
 자주 묻는 질문 관련 -> 항목을 검색하거나 질문 사항을 클릭했을 때 나오는 화면
 */

class QuestionAndAnswerFragment: Fragment() {

    private lateinit var binding : FragmentQuestionAndAnswerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding=FragmentQuestionAndAnswerBinding.inflate(inflater,container,false)
        return binding.root
    }
}