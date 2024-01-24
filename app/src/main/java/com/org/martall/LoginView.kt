package com.org.martall
interface LoginView {
    fun onLoginSuccess(code : Int, result : Result)
    fun onLoginFailure()
}