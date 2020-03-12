## [3] Playlist 관련 API

### [3.1] Playlist 생성 API
* 해당 사용자의 playlist를 생성할 수 있습니다.
* 한 사용자는 여러 개의 playlist를 가질 수 있습니다.
* playlist 이름을 지정할 수 있습니다.
```
POST /playlist/{userId}
```
#### Path Parameters
| Variable | Required | Type | Description | Example |
|---|:---:|---:|---:|---:|
| userId | O | String | 사용자 아이디 | FU200205000001 |

#### Request Body Schema (application/json)
| Variable | Required | Type | Description | Example |
|---|:---:|---:|---:|---:|
| title | O | String | 플레이리스트 제목 | 첫번째 플레이리스트 |

#### Request Sample
```
POST /playlist/FU200205000001
```
```
{
    "title": "첫번째 플레이리스트"
}
```

#### Response Body Schema (application/json)
| Variable | Required | Type | Description | Example |
|---|:---:|---:|---:|---:|
| id | O | String | 생성된 플레이리스트 레코드 대체 식별자 | FP20200208MZ2W |

#### Response Sample
- `200` 정상적으로 등록되었을 경우
```
{
    "id": "FP20200208MZ2W"
}
```
- `500` 등록 처리 중 서버 오류가 발생하였을 경우

### [3.2] Playlist 노래, 앨범 추가 API
* 노래를 playlist에 추가할 수 있습니다.
* 특정 앨범에 포함된 모든 노래를 playlist에 추가할 수 있습니다.
```
POST /playlist/{userId}/{id}/inventory
```
#### Path Parameters
| Variable | Required | Type | Description | Example |
|---|:---:|---:|---:|---:|
| userId | O | String | 사용자 아이디 | FU200205000001 |
| id | O | String | 플레이리스트 레코드 대체 식별자 | FP20200208MZ2W |

#### Header Parameters
| Variable | Required | Type | Description | Example |
|---|:---:|---:|---:|---:|
| X-Locale-With | O | String | 사용자 서비스 지역 | ko |

#### Request Body Schema (application/json)
| Variable | Required | Type | Description | Example |
|---|:---:|---:|---:|---:|
| albums | X | Array`<String>` | 앨범 레코드 대체 식별자 목록 |  |
| songs | X | Array`<String>` | 곡 레코드 대체 식별자 목록 |  |

#### Request Sample
```
POST /playlist/FU200205000001/FP20200208MZ2W/inventory
```
```
{
    "albums": [
        "FA200205000003",
        "FA200205000005"
    ],
    "songs": [
        "FS200205000023",
        "FS200205000024"
    ]
}
```
#### Responses
- `200` 정상적으로 등록되었을 경우
- `404` 플레이리스트가 존재하지 않을 경우
- `500` 등록 처리 중 서버 오류가 발생하였을 경우

### [3.3] Playlist 목록 API
* 특정 사용자의 playlist 목록을 조회할 수 있습니다.
* pagination은 하지 않습니다.
```
GET /playlist/{userId}
```
#### Path Parameters
| Variable | Required | Type | Description | Example |
|---|:---:|---:|---:|---:|
| userId | O | String | 사용자 아이디 | FU200205000001 |

#### Request Sample
```
GET /playlist/FU200205000001
```

#### Response Body Schema (application/json)
#### Root
| Variable | Required | Type | Description | Example |
|---|:---:|---:|---:|---:|
|  | O | Array`<Playlist>` | 플레이리스트 목록 |  |

#### Playlist
| Variable | Required | Type | Description | Example |
|---|:---:|---:|---:|---:|
| id | O | String | 플레이리스트 레코드 대체 식별자 | FP20200208XWPV |
| title | O | String | 플레이리스트 제목 | 첫번째 플레이리스트 |
| createOn | O | datetime | 플레이리스트 생성일시 | 2020-02-08T15:12:20+09:00 |

#### Response Sample
- `200` 정상적으로 목록이 조회되었을 경우
```
[
    {
        "id": "FP20200208XWPV",
        "title": "첫번째 플레이리스트",
        "createOn": "2020-02-08T15:12:20+09:00"
    },
    {
        "id": "FP20200208XWP2",
        "title": "두번째 플레이리스트",
        "createOn": "2020-02-09T15:12:20+09:00"
    }
]
```
- `500` 목록 조회 중 서버 오류가 발생하였을 경우

### [3.4] Playlist 삭제 API
* 사용자의 특정 playlist 1개를 삭제합니다.
```
DELETE /playlist/{userId}/{id}
```
#### Path Parameters
| Variable | Required | Type | Description | Example |
|---|:---:|---:|---:|---:|
| userId | O | String | 사용자 아이디 | FU200205000001 |
| id | O | String | 플레이리스트 레코드 대체 식별자 | FP20200208MZ2W |

#### Request Sample
```
DELETE /playlist/FU200205000001
```
#### Responses
- `200` 정상적으로 삭제되었을 경우
- `404` 플레이리스트가 존재하지 않을 경우
- `500` 삭제 처리 중 서버 오류가 발생하였을 경우