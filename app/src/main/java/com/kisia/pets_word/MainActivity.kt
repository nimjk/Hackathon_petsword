package com.kisia.pets_word

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.kisia.pets_word.ui.theme.Pets_wordTheme
import java.util.concurrent.Executor

class MainActivity : AppCompatActivity() {

    private var isLoginPage = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_page)
        setupButtons()
    }

    private fun setupButtons() {
        // 버튼 참조
        val fingerprintButton: ImageButton = findViewById(R.id.fingerprint)
        val passwordButton: ImageButton = findViewById(R.id.password)
        val addEquipButton: ImageButton = findViewById(R.id.add_equip)

        // Fingerprint 버튼 클릭 시 unlock.xml 호출 후 지문 인식 수행
        fingerprintButton.setOnClickListener {
            setContentView(R.layout.unlock)
            isLoginPage = false  // 다른 페이지로 전환했으므로 상태를 변경
            authenticateBiometric()  // 지문 인식 실행
        }

        // Password 버튼 클릭 시 num_unlock.xml 호출
        passwordButton.setOnClickListener {
            isLoginPage = false
            val intent = Intent(this, NumUnlockActivity::class.java)
            startActivity(intent)
              // 다른 페이지로 전환했으므로 상태를 변경
        }

        // Add Equip 버튼 클릭 시 join_1.xml 호출
        addEquipButton.setOnClickListener {
            setContentView(R.layout.join_1)
            isLoginPage = false  // 다른 페이지로 전환했으므로 상태를 변경
        }
    }

    override fun onBackPressed() {
        if (!isLoginPage) {
            // 현재 login_page가 아닌 경우 login_page로 돌아감
            setContentView(R.layout.login_page)
            isLoginPage = true  // login_page로 돌아갔으므로 상태를 변경
            setupButtons()
        } else {
            // login_page인 경우 기본 뒤로가기 동작 수행 (앱 종료)
            super.onBackPressed()
        }
    }


    private fun authenticateBiometric() {
        val executor: Executor = ContextCompat.getMainExecutor(this)
        val biometricPrompt = androidx.biometric.BiometricPrompt(
            this,
            executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(
                    errorCode: Int,
                    errString: CharSequence
                ) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(
                        applicationContext,
                        "Authentication error: $errString",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    Toast.makeText(
                        applicationContext,
                        "Authentication succeeded!",
                        Toast.LENGTH_SHORT
                    ).show()
                    // 지문 인식 성공 시 alarm_list.xml로 전환
                    startActivity(Intent(this@MainActivity, AlarmListActivity::class.java))
                    finish()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(
                        applicationContext,
                        "Authentication failed",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            })

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric authentication")
            .setSubtitle("Use your fingerprint to unlock")
            .setNegativeButtonText("Cancel")
            .build()

        biometricPrompt.authenticate(promptInfo)
    }
}


