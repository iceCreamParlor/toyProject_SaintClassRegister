20130573 김희재 수강신청 소프트웨어 실행 방법

** 개발 환경 : JAVA 1.8 

** 소스 코드 위치 : bin 디렉토리
** 클래스 파일 위치 : src 디렉토리
** PastSubject.txt, TotalSubject.txt를 변경시키면 프로그램이 오작동 할 수 있습니다.

1. 서강대학교 로고가 달린 수강신청.exe 파일을 실행시킨다.(더블클릭)

2. 로그인 화면이 띄워지면, 아이디 : admin   ,   비밀번호  :  1234  입력 후 로그인
		-> 수강신청 화면이 띄워진다.

3. 메뉴바의 '과목' 메뉴를 클릭한다. '개설 교과목 정보' 를 클릭하면 2018학년도 1학기에 개설되는 과목들을 열람할 수 있다.

4. '과목' 메뉴의 '과거 수강 기록'을 선택하면, 2017학년도 1학기, 2017학년도 2학기에 수강한 과목들의 과목 코드, 과목명을 열람할 수 있다.

5. 수강 신청은 두 가지 방법으로 가능하다.
    5-1. '과목명으로 수강신청'에 과목 이름을 입력한 후 '저장'을 누르면, 입력한 과목이 수강신청된다. (단, 자바 textField에서 한글을 잘 인식하지 않을 수 있으니 주의 !)
    5-2. '과목 코드로 수강신청'에 과목 코드를 입력(최대 7개)한 후 '저장'을 누르면, 입력한 과목들이 수강신청된다.

6. 만약 수강 신청한 과목들이 다른 과목들과 시간이 겹치거나, 선수 과목을 이수하지 않은 경우, 해당하는 메세지가 안내 창에 뜨게 된다(이 경우, 수강신청 실패). 만약 성공적으로 수강신청 된 경우, 이에 해당하는 메세지가 안내 창에 출력된다.

7. 과목 메뉴바에서 '내 시간표'를 클릭하면, 수강 신청한 과목들이 시간표의 형태로 출력된다.

8. 수강 취소를 하고 싶은 경우, 메뉴바의 '변경'에서 '수강신청 정정'을 누르면 된다. '수강신청 정정' 버튼을 누르면, 수강 신청한 과목 정보와 '삭제' 버튼이 나타난다. '삭제'버튼을 클릭하면, 해당 과목이 수강 취소된다.

9. 수강 신청을 정상적으로 마치기 위해서는, 메뉴바의 '계정'에서 '저장' 버튼을 눌러야 한다. 만약 신청 학점이 9학점 미만이거나, 24학점 초과라면, 이에 해당하는 안내 창이 출력되고 수강신청 결과가 저장되지 않는다.
정상적으로 수강 신청 저장이 이루어진 경우, 수강 신청 내역이 새로운 화면 창에 출력된다. 또한, '수강신청.exe'가 속해 있는 작업 디렉토리에 '수강신청내역.txt' 파일이 생성된다.

10. 수강 신청을 종료하기 위해서는, 메뉴바의 '계정'에서 '로그아웃'을 클릭하면 된다. 정상적으로 수강신청이 저장되었다면, 수강신청 내역이 다시 출력된다. 이 창을 닫게 되면, 수강신청 프로그램이 종료된다.


*** 실행 파일이 실행되지 않을 경우
만약 수강신청.exe가 실행되지 않는다면, bin 폴더에 작업 디렉토리를 위치시킨 다음 명령 프롬프트나 터미널에서 main package의 Main.java를 실행시킨다.(java main/Main)
