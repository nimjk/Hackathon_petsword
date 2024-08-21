import subprocess
import firebase_admin
from firebase_admin import credentials, firestore

# Firebase 인증 정보 및 Firestore 초기화
cred = credentials.Certificate('pet-s-word-firebase-adminsdk-akby1-3d01e20ad4.json')
firebase_admin.initialize_app(cred)
db = firestore.client()

def get_last_login_ip():
    try:
        # 'last' 명령어 실행
        result = subprocess.run(['last'], stdout=subprocess.PIPE, text=True)
        lines = result.stdout.splitlines()
        
        # 최근 접속 IP 추출
        for line in lines:
            if 'pts' in line:  # 접속 기록에서 IP 주소를 포함한 라인 찾기
                parts = line.split()
                if len(parts) > 2 and parts[2].count('.') == 3:  # IP 주소가 포함된 라인 확인
                    return parts[2]
    except Exception as e:
        print(f"Error fetching last login IP: {e}")
    return None

def get_webcam_ip():
    try:
        # 'curl ifconfig.me' 명령어 실행하여 외부 IP 주소를 가져옴
        result = subprocess.run(['curl', 'ifconfig.me'], stdout=subprocess.PIPE, text=True)
        return result.stdout.strip()  # IP 주소 반환
    except Exception as e:
        print(f"Error fetching webcam IP: {e}")
    return None

def send_to_firebase(last_login_ip, webcam_ip):
    try:
        data = {
            'allowance': 'deny',
            'temporary': 'Y'
        }
        # Firestore에 데이터 추가
        db.collection(webcam_ip).document(last_login_ip).set(data)
        print("Data sent to Firebase successfully.")
    except Exception as e:
        print(f"Error sending data to Firebase: {e}")

if __name__ == "__main__":
    last_login_ip = get_last_login_ip()
    webcam_ip = get_webcam_ip()

    if db.collection(webcam_ip).document(last_login_ip).get().exists:
        print("already exists!")
    elif last_login_ip and webcam_ip:
        send_to_firebase(last_login_ip, webcam_ip)
    else:
        print("Could not fetch IP addresses.")
