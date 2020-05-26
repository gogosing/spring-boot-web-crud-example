drop table if exists ALB_LOCALE cascade;

drop table if exists PL_INVENTORY cascade;

drop table if exists PLAYLIST cascade;

drop table if exists SONG cascade;

drop table if exists ALBUM cascade;

drop table if exists REVINFO cascade;

drop table if exists PLAYLIST_HISTORY cascade;

drop table if exists PL_INVENTORY_HISTORY cascade;

CREATE TABLE ALBUM (
	ALBUM_KEY            bigint  NOT NULL  AUTO_INCREMENT  PRIMARY KEY,
	ALBUM_ID             char(14)  NOT NULL    ,
	TITLE                varchar(150)  NOT NULL    ,
	DELETED              boolean  NOT NULL DEFAULT 0   ,
	CREATE_UTC           timestamp  NOT NULL DEFAULT CURRENT_TIMESTAMP   ,
	CONSTRAINT IDX_ALBUM_UNIQUE_ALBUM_ID UNIQUE ( ALBUM_ID ) 
 ) DEFAULT CHARSET=utf8mb4;

CREATE INDEX IDX_ALBUM_TITLE ON ALBUM ( TITLE );

ALTER TABLE ALBUM COMMENT '앨범 정보 테이블';

ALTER TABLE ALBUM MODIFY ALBUM_KEY bigint  NOT NULL  AUTO_INCREMENT COMMENT '레코드 식별자';

ALTER TABLE ALBUM MODIFY ALBUM_ID char(14)  NOT NULL   COMMENT '앨범 레코드 대체 식별자';

ALTER TABLE ALBUM MODIFY TITLE varchar(150)  NOT NULL   COMMENT '앨범 타이틀';

ALTER TABLE ALBUM MODIFY DELETED boolean  NOT NULL DEFAULT 0  COMMENT '삭제 여부';

ALTER TABLE ALBUM MODIFY CREATE_UTC timestamp  NOT NULL DEFAULT CURRENT_TIMESTAMP  COMMENT '레코드 생성일시';

CREATE TABLE ALB_LOCALE ( 
	LOCALE_KEY           bigint  NOT NULL  AUTO_INCREMENT  PRIMARY KEY,
	ALBUM_KEY            bigint  NOT NULL    ,
	LOCALE_CODE          char(3)  NOT NULL    ,
	CREATE_UTC           timestamp  NOT NULL DEFAULT CURRENT_TIMESTAMP   ,
	CONSTRAINT IDX_ALB_LOCALE_UNIQUE_LOCALE UNIQUE ( ALBUM_KEY, LOCALE_CODE ) ,
	CONSTRAINT IDX_ALB_LOCALE_FK_ALBUM_KEY FOREIGN KEY ( ALBUM_KEY ) REFERENCES ALBUM( ALBUM_KEY ) 
 ) DEFAULT CHARSET=utf8mb4;

ALTER TABLE ALB_LOCALE COMMENT '앨범 서비스 가능 지역 코드 정보 테이블';

ALTER TABLE ALB_LOCALE MODIFY LOCALE_KEY bigint  NOT NULL  AUTO_INCREMENT COMMENT '레코드 식별자';

ALTER TABLE ALB_LOCALE MODIFY ALBUM_KEY bigint  NOT NULL   COMMENT '앨범 레코드 식별자';

ALTER TABLE ALB_LOCALE MODIFY LOCALE_CODE char(3)  NOT NULL   COMMENT '서비스 지역 코드';

ALTER TABLE ALB_LOCALE MODIFY CREATE_UTC timestamp  NOT NULL DEFAULT CURRENT_TIMESTAMP  COMMENT '레코드 생성일시';

CREATE TABLE PLAYLIST ( 
	PLAYLIST_KEY         bigint  NOT NULL  AUTO_INCREMENT  PRIMARY KEY,
	PLAYLIST_ID          char(14)  NOT NULL    ,
	TITLE                varchar(150)  NOT NULL    ,
	CREATE_BY            char(14)  NOT NULL    ,
	CREATE_UTC           timestamp  NOT NULL DEFAULT CURRENT_TIMESTAMP   ,
	CONSTRAINT IDX_PLAYLIST_UNIQUE_PLAYLIST_ID UNIQUE ( PLAYLIST_ID ) 
 ) DEFAULT CHARSET=utf8mb4;

CREATE INDEX IDX_PLAYLIST_TITLE ON PLAYLIST ( TITLE );

CREATE INDEX IDX_PLAYLIST_CREATE_BY ON PLAYLIST ( CREATE_BY );

ALTER TABLE PLAYLIST COMMENT '사용자 플레이 리스트 정보 테이블';

ALTER TABLE PLAYLIST MODIFY PLAYLIST_KEY bigint  NOT NULL  AUTO_INCREMENT COMMENT '레코드 식별자';

ALTER TABLE PLAYLIST MODIFY PLAYLIST_ID char(14)  NOT NULL   COMMENT '레코드 대체 식별자';

ALTER TABLE PLAYLIST MODIFY TITLE varchar(150)  NOT NULL   COMMENT '플레이리스트 제목';

ALTER TABLE PLAYLIST MODIFY CREATE_BY char(14)  NOT NULL   COMMENT '레코드 생성 사용자 식별자';

ALTER TABLE PLAYLIST MODIFY CREATE_UTC timestamp  NOT NULL DEFAULT CURRENT_TIMESTAMP  COMMENT '레코드 생성일시';

CREATE TABLE SONG ( 
	SONG_KEY             bigint  NOT NULL  AUTO_INCREMENT  PRIMARY KEY,
	SONG_ID              char(14)  NOT NULL    ,
	ALBUM_KEY            bigint  NOT NULL    ,
	TITLE                varchar(150)  NOT NULL    ,
	`LENGTH`               int  NOT NULL    ,
	TRACK_NO             tinyint  NOT NULL    ,
	DELETED              boolean  NOT NULL DEFAULT 0   ,
	CREATE_UTC           timestamp  NOT NULL DEFAULT CURRENT_TIMESTAMP   ,
	CONSTRAINT IDX_SONG_UNIQUE_SONG_ID UNIQUE ( SONG_ID ) ,
	CONSTRAINT IDX_SONG_FK_ALBUM_KEY FOREIGN KEY ( ALBUM_KEY ) REFERENCES ALBUM( ALBUM_KEY ) 
 ) DEFAULT CHARSET=utf8mb4;

CREATE INDEX IDX_SONG_FK_ALBUM_KEY ON SONG ( ALBUM_KEY );

CREATE INDEX IDX_SONG_TITLE ON SONG ( TITLE );

ALTER TABLE SONG COMMENT '곡 정보 테이블';

ALTER TABLE SONG MODIFY SONG_KEY bigint  NOT NULL  AUTO_INCREMENT COMMENT '레코드 식별자';

ALTER TABLE SONG MODIFY SONG_ID char(14)  NOT NULL   COMMENT '레코드 대체 식별자';

ALTER TABLE SONG MODIFY ALBUM_KEY bigint  NOT NULL   COMMENT '소속 앨범 레코드 식별자';

ALTER TABLE SONG MODIFY TITLE varchar(150)  NOT NULL   COMMENT '제목';

ALTER TABLE SONG MODIFY LENGTH int  NOT NULL   COMMENT '곡 길이';

ALTER TABLE SONG MODIFY TRACK_NO tinyint  NOT NULL   COMMENT '트랙 번호';

ALTER TABLE SONG MODIFY DELETED boolean  NOT NULL DEFAULT 0  COMMENT '삭제 여부';

ALTER TABLE SONG MODIFY CREATE_UTC timestamp  NOT NULL DEFAULT CURRENT_TIMESTAMP  COMMENT '레코드 생성일시';

CREATE TABLE PL_INVENTORY ( 
	INVENTORY_KEY        bigint  NOT NULL  AUTO_INCREMENT  PRIMARY KEY,
	INVENTORY_ID         char(14)  NOT NULL    ,
	PLAYLIST_KEY         bigint  NOT NULL    ,
	SONG_KEY             bigint  NOT NULL    ,
	CREATE_UTC           timestamp  NOT NULL DEFAULT CURRENT_TIMESTAMP   ,
	CONSTRAINT IDX_PL_INVENTORY_UNIQUE_INVENTORY_ID UNIQUE ( INVENTORY_ID ) ,
	CONSTRAINT IDX_PL_INVENTORY_FK_PLAYLIST_KEY FOREIGN KEY ( PLAYLIST_KEY ) REFERENCES PLAYLIST( PLAYLIST_KEY ) ,
	CONSTRAINT IDX_PL_INVENTORY_FK_SONG_KEY FOREIGN KEY ( SONG_KEY ) REFERENCES SONG( SONG_KEY ) 
 ) DEFAULT CHARSET=utf8mb4;

ALTER TABLE PL_INVENTORY COMMENT '사용자 플레이리스트 수록곡 정보 테이블';

ALTER TABLE PL_INVENTORY MODIFY INVENTORY_KEY bigint  NOT NULL  AUTO_INCREMENT COMMENT '레코드 식별자';

ALTER TABLE PL_INVENTORY MODIFY INVENTORY_ID char(14)  NOT NULL   COMMENT '레코드 대체 식별자';

ALTER TABLE PL_INVENTORY MODIFY PLAYLIST_KEY bigint  NOT NULL   COMMENT '플레이리스트 레코드 식별자';

ALTER TABLE PL_INVENTORY MODIFY SONG_KEY bigint  NOT NULL   COMMENT '곡 레코드 식별자';

ALTER TABLE PL_INVENTORY MODIFY CREATE_UTC timestamp  NOT NULL DEFAULT CURRENT_TIMESTAMP  COMMENT '레코드 생성일시';

---

create table REVINFO
(
	REV bigint AUTO_INCREMENT PRIMARY KEY,
	REVTSTMP bigint NULL
) DEFAULT CHARSET=utf8mb4;

CREATE TABLE PLAYLIST_HISTORY (
	PLAYLIST_KEY         bigint  NOT NULL,
	REV                  bigint NOT NULL,
	REVTYPE              tinyint NULL,
	PLAYLIST_ID          char(14)  NOT NULL    ,
	TITLE                varchar(150)  NOT NULL    ,
	CREATE_BY            char(14)  NOT NULL    ,
	CREATE_UTC           timestamp  NOT NULL DEFAULT CURRENT_TIMESTAMP   ,
	PRIMARY KEY (PLAYLIST_KEY, REV),
	CONSTRAINT IDX_PLAYLIST_AUD_FK_REV FOREIGN KEY (REV) REFERENCES REVINFO (REV)
) DEFAULT CHARSET=utf8mb4;

CREATE TABLE PL_INVENTORY_HISTORY (
	INVENTORY_KEY        bigint  NOT NULL,
	REV                  bigint NOT NULL,
	REVTYPE              tinyint NULL,
	INVENTORY_ID         char(14)  NOT NULL    ,
	PLAYLIST_KEY         bigint  NOT NULL    ,
	SONG_KEY             bigint  NOT NULL    ,
	CREATE_UTC           timestamp  NOT NULL DEFAULT CURRENT_TIMESTAMP   ,
	PRIMARY KEY (INVENTORY_KEY, REV),
	CONSTRAINT IDX_PL_INVENTORY_AUD_FK_REV FOREIGN KEY (REV) REFERENCES REVINFO (REV)
) DEFAULT CHARSET=utf8mb4;