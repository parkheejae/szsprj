# SZS ���� ������Ʈ

------------

## STACK
+ ��� : JAVA
+ FrameWork : SPRING BOOT 2.5.7
+ Build-Tool : GRADLE
+ DB : H2 Embeded
+ JUNIT, SWAGGER3, LOMBOK, JWT

------------

## ����
+ type : application/json
+ ���信 code�� �߰� 
    + ���� �� ���� ���ο� ���� code���� SUCCESS / ERROR
    + ERROR�� ��� message�� ���� ���� �߰�
    
------------

## ���� API
1. ȸ�� ���
    + ȸ�� ��й�ȣ, �ֹ� ��� ��ȣ ��ȣȭ(AES256)
2. ȸ�� �α���
    + JWT ��ū ���� �� ���� ���
3. ȸ�� ���� ��ȸ(JWT ��ū)
    + JWT ��ū���� ȸ�� ID ȹ��
4. ȸ�� �α� �ƿ�
5. ȸ�� ���� ��ũ��
    + JWT ��ū���� ȸ�� ID ȹ��
    + HTTPCLIENT BUILDER
6. ȯ�޾� ���
    + JWT ��ū���� ȸ�� ID && ȸ�� �̸� ��ȸ
    + ȯ�޾� ���
    
------------

##### ���� ���

###### 1.ȸ�� ���

+ REQUEST BODY
    + {
        "userId": "DAECADID",
        "password": "test1234",
        "userName": "ȫ�浿",
        "regNo": "860824-1655068"
      }

+ RESPONSE BODY
    + ��������
        + {
            "code": "SUCCESS"
          }

    + ��������
        + {
			  "code": "ERROR",
			  "message": "���̵� �ߺ��Դϴ�. �ٸ� ���̵�� �õ��� �ּ���."
          }
        + {
            "code": "ERROR",
            "message": "���Ե� �ֹ� ��� ��ȣ�Դϴ�. ���Ե� ȸ�� ������ Ȯ�� ���ּ���."
          }
        + {
            "code": "ERROR",
            "message": "�ʼ��Ķ���Ͱ� �����Ǿ����ϴ�."
          }
        + {
            "code": "ERROR",
            "message": "ȸ�� ��Ͽ� �����Ͽ����ϴ�."
          }

###### 2. ȸ�� �α���

+ REQUEST BODY
    + {
        "userId": "DAECADID",
        "password": "test1234"
      }

+ RESPONSE BODY
    + ��������
        + {
            "code": "SUCCESS"
          }
    + ��������
        + {
            "code": "ERROR",
            "message": "��ϵ��� ���� ID�Դϴ�."
          }
      + {
            "code": "ERROR",
            "message": "��й�ȣ ���� �Դϴ�."
          }


###### 3. ȸ�� ���� ��ȸ

+ REQUEST BODY

+ RESPONSE BODY
    + ��������
      + {
          "code": "SUCCESS",
          "userId": "DAECADID",
          "userName": "ȫ�浿",
          "createdAt": "2021-12-24T15:00:00.000+00:00"
        }
    + ��������
      + {
          "code": "ERROR",
          "message": "��ūŰ�� �������� �ʽ��ϴ�."
        }
      + {
          "code": "ERROR",
          "message": "��ūŰ�� ����Ǿ����ϴ�."
        }


###### 4. ȸ�� �α׾ƿ�

+ REQUEST BODY

+ RESPONSE BODY
    + ��������
      + {
  "code": "SUCCESS",
  "userId": "DAECADID",
  "userName": "ȫ�浿",
  "createdAt": "2021-12-24T15:00:00.000+00:00"
}
    + ��������
      + {
          "code": "ERROR",
          "message": "��ūŰ�� �������� �ʽ��ϴ�."
        }
      + {
          "code": "ERROR",
          "message": "��ūŰ�� ����Ǿ����ϴ�."
        }



###### 5. ȸ�� ���� ��ũ��

+ REQUEST BODY

+ RESPONSE BODY
    + ��������
     + {
        "code": "SUCCESS",
        "jsonList": {
          "scrap001": [
            {
              "�ҵ泻��": "�޿�",
              "�����޾�": "24000000",
              "����������": "2020.10.03",
              "�����": "(��)Ȱ���",
              "������": "2020.11.02",
              "�̸�": "ȫ�浿",
              "����������": "2020.11.02",
              "�ֹε�Ϲ�ȣ": "860824-1655068",
              "�ҵ汸��": "�ٷμҵ�(����)",
              "����ڵ�Ϲ�ȣ": "012-34-56789"
            }
          ],
          "scrap002": [
            {
              "�ҵ泻��": "���⼼��",
              "�ѻ��ݾ�": "2000000"
            }
          ],
          "errMSg": "",
          "company": "������",
          "svcCd": "test01",
          "userId": "1"
        },
        "appVer": "2021112501",
        "hostNm": "jobis-codetest",
        "workerResDt": "2021-12-26T05:57:41.502248",
        "workerReqDt": "2021-12-26T05:57:41.502248"
      }
    + ��������
      + {
          "code": "ERROR",
          "message": "��ūŰ�� �������� �ʽ��ϴ�."
        }
      + {
          "code": "ERROR",
          "message": "��ūŰ�� ����Ǿ����ϴ�."
        }
      + {
          "code": "ERROR",
          "message": "ȸ�� ���� ��ȸ�� �����߽��ϴ�."
        }
      + {
          "code": "ERROR",
          "message": "������ ��ȸ�� �����޽��ϴ�."
        }


###### 6. ȯ�޾� ���
+ REQUEST BODY

+ RESPONSE BODY
    + ��������
      + {
          "�ѵ�": "74����",
          "code": "SUCCESS",
          "�̸�": "ȫ�浿",
          "������": "50��5õ��",
          "ȯ�޾�": "50��5õ��"
        }
    + ��������
      + {
          "code": "ERROR",
          "message": "��ūŰ�� �������� �ʽ��ϴ�."
        }
      + {
          "code": "ERROR",
          "message": "��ūŰ�� ����Ǿ����ϴ�."
        }

