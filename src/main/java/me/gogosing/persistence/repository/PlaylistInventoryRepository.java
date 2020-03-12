package me.gogosing.persistence.repository;

import me.gogosing.persistence.entity.PlaylistInventoryEntity;
import me.gogosing.persistence.repository.custom.PlaylistInventoryRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 플레이 리스트 수록곡 정보 플레이 리스트 수록곡 정보.
 * Created by JinBum Jeong on 2020/02/06.
 */
@Repository
public interface PlaylistInventoryRepository
        extends JpaRepository<PlaylistInventoryEntity, Long>, PlaylistInventoryRepositoryCustom {
}
