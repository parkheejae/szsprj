# SZS 과제 프로젝트

------------

## STACK
+ 언어 : JAVA
+ FrameWork : SPRING BOOT 2.5.7
+ Build-Tool : GRADLE
+ DB : H2 Embeded
+ JUNIT, SWAGGER3, LOMBOK, JWT

------------

## 응답
+ type : application/json
+ 응답에 code를 추가 
    + 성공 및 실패 여부에 따라 code값이 SUCCESS / ERROR
    + ERROR인 경우 message에 오류 내용 추가
    
------------

## 구현 API
1. 회원 등록
    + 회원 비밀번호, 주민 등록 번호 암호화(AES256)
2. 회원 로그인
    + JWT 토큰 생성 및 세션 등록
3. 회원 정보 조회(JWT 토큰)
    + JWT 토큰에서 회원 ID 획득
4. 회원 로그 아웃
5. 회원 정보 스크랩
    + JWT 토큰에서 회원 ID 획득
    + HTTPCLIENT BUILDER
6. 환급액 계산
    + JWT 토큰에서 회원 ID && 회원 이름 조회
    + 환급액 계산
    
------------

##### 검증 결과

###### 1.회원 등록

+ REQUEST BODY
    + {
        "userId": "DAECADID",
        "password": "test1234",
        "userName": "홍길동",
        "regNo": "860824-1655068"
      }

+ RESPONSE BODY
    + 정상응답
        + {
            "code": "SUCCESS"
          }

    + 오류응답
        + {
			  "code": "ERROR",
			  "message": "아이디 중복입니다. 다른 아이디로 시도해 주세요."
          }
        + {
            "code": "ERROR",
            "message": "가입된 주민 등록 번호입니다. 가입된 회원 정보를 확인 해주세요."
          }
        + {
            "code": "ERROR",
            "message": "필수파라미터가 누락되었습니다."
          }
        + {
            "code": "ERROR",
            "message": "회원 등록에 실패하였습니다."
          }

###### 2. 회원 로그인

+ REQUEST BODY
    + {
        "userId": "DAECADID",
        "password": "test1234"
      }

+ RESPONSE BODY
    + 정상응답
        + {
            "code": "SUCCESS"
          }
    + 오류응답
        + {
            "code": "ERROR",
            "message": "등록되지 않은 ID입니다."
          }
      + {
            "code": "ERROR",
            "message": "비밀번호 오류 입니다."
          }


###### 3. 회원 정보 조회

+ REQUEST BODY

+ RESPONSE BODY
    + 정상응답
      + {
          "code": "SUCCESS",
          "userId": "DAECADID",
          "userName": "홍길동",
          "createdAt": "2021-12-24T15:00:00.000+00:00"
        }
    + 오류응답
      + {
          "code": "ERROR",
          "message": "토큰키가 존재하지 않습니다."
        }
      + {
          "code": "ERROR",
          "message": "토큰키가 만료되었습니다."
        }


###### 4. 회원 로그아웃

+ REQUEST BODY

+ RESPONSE BODY
    + 정상응답
      + {
  "code": "SUCCESS",
  "userId": "DAECADID",
  "userName": "홍길동",
  "createdAt": "2021-12-24T15:00:00.000+00:00"
}
    + 오류응답
      + {
          "code": "ERROR",
          "message": "토큰키가 존재하지 않습니다."
        }
      + {
          "code": "ERROR",
          "message": "토큰키가 만료되었습니다."
        }



###### 5. 회원 정보 스크랩

+ REQUEST BODY

+ RESPONSE BODY
    + 정상응답
     + {
        "code": "SUCCESS",
        "jsonList": {
          "scrap001": [
            {
              "소득내역": "급여",
              "총지급액": "24000000",
              "업무시작일": "2020.10.03",
              "기업명": "(주)활빈당",
              "지급일": "2020.11.02",
              "이름": "홍길동",
              "업무종료일": "2020.11.02",
              "주민등록번호": "860824-1655068",
              "소득구분": "근로소득(연간)",
              "사업자등록번호": "012-34-56789"
            }
          ],
          "scrap002": [
            {
              "소득내역": "산출세액",
              "총사용금액": "2000000"
            }
          ],
          "errMSg": "",
          "company": "삼쩜삼",
          "svcCd": "test01",
          "userId": "1"
        },
        "appVer": "2021112501",
        "hostNm": "jobis-codetest",
        "workerResDt": "2021-12-26T05:57:41.502248",
        "workerReqDt": "2021-12-26T05:57:41.502248"
      }
    + 오류응답
      + {
          "code": "ERROR",
          "message": "토큰키가 존재하지 않습니다."
        }
      + {
          "code": "ERROR",
          "message": "토큰키가 만료되었습니다."
        }
      + {
          "code": "ERROR",
          "message": "회원 정보 조회에 실패했습니다."
        }
      + {
          "code": "ERROR",
          "message": "데이터 조회에 실패햇습니다."
        }


###### 6. 환급액 계산
+ REQUEST BODY

+ RESPONSE BODY
    + 정상응답
      + {
          "한도": "74만원",
          "code": "SUCCESS",
          "이름": "홍길동",
          "공제액": "50만5천원",
          "환급액": "50만5천원"
        }
    + 오류응답
      + {
          "code": "ERROR",
          "message": "토큰키가 존재하지 않습니다."
        }
      + {
          "code": "ERROR",
          "message": "토큰키가 만료되었습니다."
        }

