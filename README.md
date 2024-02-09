![소개이미지.png](image%2F%EC%86%8C%EA%B0%9C%EC%9D%B4%EB%AF%B8%EC%A7%80.png)

# Yanabada_BackEnd
## 👋안녕하세요~ 야나바다 백엔드 팀입니다😀


|    [박주현](https://github.com/Programmer-may)    |       [안수지](https://github.com/deltawing71911)     |         [장성수](https://github.com/tjdtn0219)              |    [황규철](https://github.com/Hwang-Kyu-Cheol)      |
|:----------------------------------------------------------:|:----------------------------------------------------------:|:-----------------------:|:---------------------------------------------------------:|
| ![](https://avatars.githubusercontent.com/u/114227320?v=4) | ![](https://avatars.githubusercontent.com/u/137012201?v=4) | ![](https://avatars.githubusercontent.com/u/76704436?v=4) | ![](https://avatars.githubusercontent.com/u/67046364?v=4) |
<br>

### 🏨 프로젝트 소개
**프로젝트 내용**
 - 무료 예약 취소가 불가한 숙소의 양도/거래 플랫폼

**프로젝트 주제 및 필수 구현 기능 제안**
 - 야놀자

**프로젝트 제작 배경**
- 예약 취소에 대한 불만이 많음, 유저의 착각/실수도 있으나 천재지변, 개인적 사유로 인해 어쩔 수 없이 취소수수료가 발생하는 경우 유저의 불만 및 탈퇴로 이어짐
- 예약 취소의 부정적 경험으로 인해 유저가 탈퇴하는 것을 막을 수 있는 방안으로 예약 취소 불가 상품을 해결할 수 있는 플랫폼 또는 기능 제공
- 공급자와 기존 구매자, 양도자를 모두 고려한 안전하고 신뢰도 높은 예약 취소 거래 기능 구축
- 예약 취소 수수료가 아닌 취소 예약건을 온전히 체크인 완료하면 매출 증대에 기여할 것으로 기대함

**프로젝트 목적**
- 기획, 디자인, 프론트엔드, 백엔드 간의 협업
- RESTful API 개발

**프로젝트 기간**
- 서비스 기획 기간 : 2023년 12월 02일 ~ 2024년 01월 05일
- 서비스 개발 기간 : 2024년 01월 04일 ~ 2024년 01월 29일

**프로젝트 실행방법**

<details>
<summary>메뉴얼</summary>

### 배포 URL

**야나바다 홈페이지**

 - https://www.yanabada.com

**메인서버 배포 URL**

 - https://api.yanabada.com

**테스트 서버 배포 URL**
 - http://test.yanabada.com

**테스트 계정**
 - ID : test@naver.com
 - PW : password123!

### 로컬 실행환경 셋팅 

```shell
docker run -d -p 6379:6379 --name yanabada_redis redis
```

- 6379 포트로 Redis가 실행중이어야 프로젝트 로그인 기능이 정상 실행됩니다!

### 로컬 데이터베이스 H2 접속 경로

1. http://localhost:8080/h2-console 에 들어갑니다.
2. 아래 정보대로 입력 칸을 채우고 Connect를 누릅니다.

- Driver Class: org.h2.Driver
- JDBC URL: jdbc:h2:mem:testdb
- User Name: sa
- Password: (빈칸)

</details>

### 📚 프로젝트 기술스택
<div align=center> 
    <img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white"> 
    <img src="https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white">
    <img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white">
    <img src="https://img.shields.io/badge/SpringBoot-6DB33F?style=for-the-badge&logo=Springboot&logoColor=white">
    <img src="https://img.shields.io/badge/spring security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white">
    <br>
    <img src="https://img.shields.io/badge/json web tokens-000000?style=for-the-badge&logo=jsonwebtokens&logoColor=white">
    <img src="https://img.shields.io/badge/junit5-25A162?style=for-the-badge&logo=junit5&logoColor=white">
    <img src="https://img.shields.io/badge/Spring data jpa-6DB33F?style=for-the-badge&logo=Databricks&logoColor=white">
    <img src="https://img.shields.io/badge/QueryDSL-0389CF?style=for-the-badge&logo=SingleStore&logoColor=white">
    <img src="https://img.shields.io/badge/Nginx-009639?style=for-the-badge&logo=nginx&logoColor=white">
    <br>
    <img src="https://img.shields.io/badge/redis-DC382D?style=for-the-badge&logo=redis&logoColor=white">
    <img src="https://img.shields.io/badge/Mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white">
    <img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white">
    <img src="https://img.shields.io/badge/amazon aws-232F3E?style=for-the-badge&logo=amazon aws&logoColor=white">
    <img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white">
    <img src="https://img.shields.io/badge/github actions-2088FF?style=for-the-badge&logo=GitHubActions&logoColor=white">
    <img src="https://img.shields.io/badge/firebase-FFCA28?style=for-the-badge&logo=firebase&logoColor=white">
</div>

### 🗺️ 프로젝트 파이프라인
![프로젝트 파이프라인.jpg](image%2F%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8%20%ED%8C%8C%EC%9D%B4%ED%94%84%EB%9D%BC%EC%9D%B8.jpg)

### 🗄️서버 인프라
![백엔드 인프라 아키텍처.jpg](image%2F%EB%B0%B1%EC%97%94%EB%93%9C%20%EC%9D%B8%ED%94%84%EB%9D%BC%20%EC%95%84%ED%82%A4%ED%85%8D%EC%B2%98.jpg)

## 📝 프로젝트 기획
### ERD
![Yanabada-ERD.png](image%2FYanabada-ERD.png)

### API 명세서
![파이널 api 명세서.png](image%2F%ED%8C%8C%EC%9D%B4%EB%84%90%20api%20%EB%AA%85%EC%84%B8%EC%84%9C.png)


```
API 명세서에 없는 API는 더미데이터를 넣기위한 자체적인 API입니다.
```
### 컨벤션

### 1. 코딩 컨벤션

- 커스텀 구글 코딩 컨벤션을 사용합니다.
- 메소드 네이밍, 패키지, DTO 네이밍 등 기타 코딩 컨벤션은 노션 백엔드 아카이브 폴더를 확인합니다.

### 2. 브랜치 전략 - Git Flow

- 브랜치 전략으로 Git Flow를 사용합니다.
- API 구현 및 설계는 모든 팀원의 Approve를 받아야 Merge 할 수 있습니다.

<details>
<summary>브랜치별 역할</summary>

### `feature/#`

- 실제 작업을 하는 브랜치
- 이슈 번호가 1이라면 feature/1로 만들면 됩니다.
- 'develop'을 베이스 브랜치로 하여 만들어야 합니다.
    - ( 브랜치 생성은 베이스 브랜치[ 체크아웃되어있는 브랜치 ]를 기준으로 만들어진다.)
- 작업이 완료되면 develop으로 Pull Request를 날립니다.
- 본인을 제외한 조원의 Approve를 모두 받았다면 Merge 합니다.

### `develop`

- 테스트 서버에 자동 배포되는 브랜치
- 다음 버전 개발을 위해 release으로 가기 전 기능 코드들을 모아두는 브랜치
- 작성한 기능이 잘 작동되는지 확인하고, release으로 PR 및 Merge를 하면 됩니다.
- develop으로 Merge 하고 나서 자동 배포된 테스트 서버에서 자신의 API가 정상 작동하는지 꼭 테스트해야 합니다.

### `release`

- 실제 서비스를 운영할 수 있는 메인 서버 자동 배포되는 브랜치
- release으로 Merge 하고 나서 자동 배포된 메인 서버에서 자신의 API가 정상 작동하는지 꼭 테스트해야 합니다.


### `main`

- 최종본을 갖는 브랜치
</details>

### 3. 협의사항

- **협업 관련**
    - 매 회의시 회의록 작성
  ![파이널 BE 회의록.png](image%2F%ED%8C%8C%EC%9D%B4%EB%84%90%20BE%20%ED%9A%8C%EC%9D%98%EB%A1%9D.png)
      <br><br>
    - 백엔드 아카이브에 기술스택 사용법 정리
  ![BE참고자료.png](image%2FBE%EC%B0%B8%EA%B3%A0%EC%9E%90%EB%A3%8C.png)
      <br><br>
    - 데일리 스크럼: 매일 오전 10:00, 오후 14:00, 19:00 노션에 진행상황 공유
      ![데일리 스크럼.png](image%2F%EB%8D%B0%EC%9D%BC%EB%A6%AC%20%EC%8A%A4%ED%81%AC%EB%9F%BC.png)
      <br><br>
    - Notion 에서 PM, UIUX, FrontEnd 및 BackEnd 회의사항 공유, 서로간의 질문사항 & 건의사항 소통
      <br><br>
    - 매주 금요일 팀 전체회의
  ![파이널_전체회의.png](image%2F%ED%8C%8C%EC%9D%B4%EB%84%90_%EC%A0%84%EC%B2%B4%ED%9A%8C%EC%9D%98.png)
        <br><br>
- **커밋 메시지 관련**
    - 커밋 제목은 `prefix: 커밋 메시지` 형태로 합니다.
    - prefix의 목록과 각각의 용도는 IntelliJ 플러그인 commit message template 에 맞춰 작성
    - 커밋 내용을 자세하게 적습니다.


## 🤔 프로젝트 이슈

 - 실시간 채팅을 구현할 수 있는 방법은 여러가지가 있었습니다. 풀링, 롱풀링, 스트리밍 등 있었습니다. 하지만 HTTP 메세지를 주고 받으면서 통신하는 것은 메세지양이 너무 커, 다른 방법인 웹소켓으로 실시간 채팅을 구현하였습니다. 또한 STOMP라는 웹소켓의 서브 프로토콜을 이용하여 클라이언트와 서버가 서로 통신하는 데 있어 메시지 형식, 내용 등을 정의해 주어 통신을 수월하게 해주었습니다.


 - 서비스 계층에서 타 도메인의 의존성 주입을 무엇을 받을지 팀원들과 심도깊은 회의를 하였습니다. 타 도메인의 서비스계층 또는 레포지토리계층 둘중 어떤 것을 의존성 받았을 때 장단점이 있는지 회의하였고, 결국 레포지토리 계층을 주입받기로 결정하였습니다.


 - 리버스 프록시 서버를 사용했을시 각기 다른 URL 프록시 패스에 각각 다른 헤더를 set 해주어야한다는 걸 배웠고, 서버블록이 같은 포트가 두개 이상여서 프록시 서버가 작동하지 않는 이슈도 있었습니다. 포트당 한개의 블록을 갖게끔 하고 원하는 그리고 올바른 헤더를 세팅해주어 문제를 해결했습니다.

 - HTTPS 를 사용하는 본서버에서 쿠키 설정에서 문제를 겪었는데 바로, domain 을 따로 지정해줘야 하는 문제였습니다. 쿠키 설정 시 `domain`에 따로 값을 지정해주지 않으면,
 default로  `domain=<서버의 도메인 주소>`로 지정됩니다. 그리고 크롬 브라우저는 쿠키를 받았더라도, `domain`이 현재 크롬을 동작하는 `domain`과 일치해야만 쿠키를 사용할 수 있습니다.이 문제를 해결하기 위해, 서버의 domain(api.yanabada.com)과 클라이언트 배포 도메인(www.yanabada.com)의 공통 부분을 맞추어서 `domain= yanabada.com` 으로 설정하였습니다. 
 그 결과 클라이언트에서도 서버로부터 받은 쿠키를 사용할 수 있게 되었습니다.




## 🪄 프로젝트 회고
 
<details>
<summary>박주현</summary>


취직하기 전에 4분야의 직무가 함께 하나의 프로젝트에서 협업해보는 소중한 경험이었습니다.

HTTP 프로토콜만 사용하는 REST API를 만들었는데, 채팅 도메인을 구현하면서 HTTP 이외의 다른 프로토콜을 사용한 API도 만들어보고 그게 실제로 정상 작동했을땐 매우 행복했습니다.

이번 프로젝트때 개인적인 목표로 3가지가 있었는데, 리버스 프록시 서버는 구현했지만 시간이 부족하여 무중단 배포와 Source - Replica Architecture를 도입해보지 못한게 아쉽습니다. 
하지만 개인적으로 공부는 하였기에 다음번엔 도입해볼 수 있는 자신감도 얻었습니다.

1달이 넘는 시간동안 함께 고생한 PM분들, 디자이너분, 프론트엔드분들 그리고 백엔드 팀원들에게 감사함을 표합니다.



</details>

<details>
<summary>안수지</summary>

하나의 서비스가 완성되기까지 일련의 과정을 경험할 수 있어 아주 좋았습니다. 특히 산업디자인에 종사했던 사람으로서 UX/UI 디자인이 구체적으로 어떤식으로 진행되는지 개발하면서 지켜보는건 아주 재밌는 경험이었습니다.
(물론 디자이너가 딱 한 분이셔서 본인은 괴로우셨겠지만...)

다만 구현했던 기능이 미니 때보다 한정적이었던 만큼 다양한 이슈에 대응하지 못했던 점은 다소 아쉬웠습니다. 아무래도 리뷰만 하는 것보다 직접 부딫혀보고 문제를 해결하는건 전혀 다른 문제니까요.
다음 프로젝트 때는 보다 더 성장한 상태로 더 기여도를 늘릴 수 있기를 기대합니다. 

5조의 마법사들 환상적이었습니다!! 

</details>

<details>
<summary>장성수</summary>

PM, 디자인, 프론트, 백엔드 등 4분야가 하나의 팀이 되어, 아이디어를 제품화하는 좋은 경험이었습니다.
개발자 입장에서 하나의 프로덕트가 탄생하기 전, 기획부터 와이어프레임까지 만들어지는 과정을 두 눈으로 지켜보았고, 기획에서 설계, 설계에서 개발까지 이어지는 정교한 과정들을 참여하는 좋은 계기가 되었습니다.

그 과정에서 기획자-개발자 간의 배려 깊은 의사소통의 중요성을 깨달았습닌다. 퀄리티 높은 기능이 탄생하는 과정에는 4분야가 독립적이지 않고, 같이 고민하고 논의해야만 한다는 것을 느끼게 해준 경험이었습니다.

</details>

<details>
<summary>황규철</summary>

서로 다른 4가지 분야의 사람들과 만나서 팀 프로젝트를 해볼 수 있었던 좋은 경험이었습니다.

개발을 모르는 분들과의 협업도 처음 진행해볼 수 있었는데, 이 때 이전에는 몰랐던 소통의 중요성을
깨닫게 되었습니다. 이전에는 개발자들과만 협업을 진행하다보니 당연하게 여겼던 부분들은 대수롭지
않게 넘겼었는데, 다른 분야의 사람들과 협업하면서 나에게는 당연하던 것이 남에게는 아닐 수 있구나
라는 것을 알게되었습니다. 협업에서 소통이란 '남의 입장에서 한번 더 생각해보기' 라는 것을 알게
된 좋은 경험이었습니다.
</details>

## 🏆 수상 
### 우수 그룹 선정
<br>

![우수그룹1.png](image%2F%EC%9A%B0%EC%88%98%EA%B7%B8%EB%A3%B91.png)

<br>

![우수그릅2.png](image%2F%EC%9A%B0%EC%88%98%EA%B7%B8%EB%A6%852.png)





