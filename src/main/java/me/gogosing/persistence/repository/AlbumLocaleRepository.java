package me.gogosing.persistence.repository;

import me.gogosing.persistence.entity.AlbumLocaleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 앨범 서비스 가능 지역 Repository.
 * Created by JinBum Jeong on 2020/02/08.
 */
@Repository
public interface AlbumLocaleRepository
        extends JpaRepository<AlbumLocaleEntity, Long> {
}
