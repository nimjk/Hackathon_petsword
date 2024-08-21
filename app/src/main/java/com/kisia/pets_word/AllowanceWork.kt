package com.kisia.pets_word

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class AllowanceWork : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // qr_scan_notice.xml 레이아웃을 불러옴
        val inflater: LayoutInflater = layoutInflater
        val view = inflater.inflate(R.layout.qr_scan_notice, null)
        setContentView(view)

        // Firestore 인스턴스 가져오기
        db = FirebaseFirestore.getInstance()

        // check_ip 버튼 가져오기
        val checkIpButton: ImageButton = view.findViewById(R.id.check_ip)

        // check_ip 버튼 클릭 리스너 설정
        checkIpButton.setOnClickListener {
            showAllowanceDialog()
        }
    }

    // 허용/차단 선택 다이얼로그 표시
    private fun showAllowanceDialog() {
        val options = arrayOf("허용", "차단")
        val builder = AlertDialog.Builder(this)
        builder.setTitle("IP 주소를 허용 또는 차단하시겠습니까?")
        builder.setItems(options) { dialog, which ->
            when (which) {
                0 -> updateFirestore("allow")  // 허용 선택 시
                1 -> updateFirestore(null)  // 차단 선택 시
            }
        }
        builder.setOnDismissListener {
            updateTemporaryField()  // 다이얼로그가 닫힐 때 temporary 값을 no로 업데이트
        }
        builder.show()
    }

    // Firestore에서 allowance 값을 업데이트하는 함수
    private fun updateFirestore(allowance: String?) {
        val docRef = db.collection("35.223.237.205").document("106.101.131.142")
        val updates = if (allowance != null) {
            mapOf("allowance" to allowance, "temporary" to "N")
        } else {
            mapOf("temporary" to "N")
        }

        docRef.update(updates)
            .addOnSuccessListener {
                // 성공적으로 업데이트된 경우의 처리
            }
            .addOnFailureListener { e ->
                // 업데이트 실패 시 처리
            }
    }

    // temporary 필드를 no로 업데이트하는 함수
    private fun updateTemporaryField() {
        val docRef = db.collection("your_collection").document("your_document_id")
        docRef.update("temporary", "no")
            .addOnSuccessListener {
                // 성공적으로 업데이트된 경우의 처리
            }
            .addOnFailureListener { e ->
                // 업데이트 실패 시 처리
            }
    }
}