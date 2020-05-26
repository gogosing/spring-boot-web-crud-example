#!/usr/bin/env bash
docker run -d `# Daemon 모드로 MySQL Docker Container 실행` \
--name mysql `# Docker Container 이름 설정` \
-p 3306:3306 `# Docker Container 3306 포트를 Docker Host 의 3306 로 연결` \
-e "MYSQL_ROOT_PASSWORD=password" `# Database ROOT 비밀번호 설정` \
-e "MYSQL_DATABASE=my_database" `# Database 생성` \
-e "MYSQL_USER=root" `# Database 접속 계정 설정` \
-e "MYSQL_PASSWORD=password" `# Database 접속 계정의 비밀번호 설정` \
mysql:latest `# MySQL Docker Container Image를 최신 버젼으로 사용` \
--character-set-server=utf8mb4 `# Database 기본 CharacterSet 을 UTF8 로 설정` \
--collation-server=utf8mb4_unicode_ci `# Database 기본 Collation 을 UTF8 로 설정`