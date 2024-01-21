package com.org.martall.view.mypage.customerservice.query

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.org.martall.databinding.FragmentQuestionBinding

/*
 자주 묻는 질문 Fragment
 이 부분의 항목 들은 우선 리사이클러 뷰로 만들었습니다
 하드 코딩이 될지 내용이 추가될 지 몰라 adapter는 따로 만들지 않았습니다.
 나머지 Fragment 도 동일
 */
class QuestionFragment: Fragment() {

    private lateinit var binding : FragmentQuestionBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding=FragmentQuestionBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

}