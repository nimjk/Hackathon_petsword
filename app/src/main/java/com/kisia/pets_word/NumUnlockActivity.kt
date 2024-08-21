package com.kisia.pets_word

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class NumUnlockActivity : AppCompatActivity() {

    private lateinit var passwordInput: EditText
    private lateinit var ellipses: List<ImageView>
    private val correctPassword = "1234"  // 비밀번호는 실제로 보안이 요구되는 저장소에 보관해야 함

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.num_unlock)

        passwordInput = findViewById(R.id.password_input)

        ellipses = listOf(
            findViewById(R.id.firstnum),
            findViewById(R.id.secondnum),
            findViewById(R.id.thirdnum),
            findViewById(R.id.fourthnum)
        )

        // EditText의 텍스트 변화 감지
        passwordInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                updateEllipses(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        // 자동으로 소프트 키보드 띄우기
        showKeyboard()
    }

    private fun showKeyboard() {
        // EditText에 포커스 주기
        passwordInput.requestFocus()
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(passwordInput, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun updateEllipses(password: String) {
        for (i in password.indices) {
            ellipses[i].setImageResource(R.drawable.ellipse_11)  // 입력된 부분 변경
        }
        for (i in password.length until ellipses.size) {
            ellipses[i].setImageResource(R.drawable.ellipse_7)  // 남은 부분은 기본 상태
        }
        if (password.length == 4) {
            checkPassword(password)
        }
    }

    private fun checkPassword(password: String) {
        if (password == correctPassword) {
            // 비밀번호가 맞으면 alarm_list로 이동
            startActivity(Intent(this, AlarmListActivity::class.java))
            finish()
        } else {
            // 비밀번호가 틀리면 팝업창 출력 후 비밀번호 초기화
            Toast.makeText(this, "비밀번호가 틀렸습니다. 다시 입력하세요.", Toast.LENGTH_SHORT).show()
            passwordInput.text.clear()
            resetEllipses()
        }
    }

    private fun resetEllipses() {
        ellipses.forEach { it.setImageResource(R.drawable.ellipse_7) }
    }
}