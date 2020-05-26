package me.gogosing.persistence.repository;

import me.gogosing.persistence.entity.PlaylistEntity;
import me.gogosing.persistence.repository.custom.PlaylistRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

/**
 * 플레이리스트 Repository.
 * Created by JinBum Jeong on 2020/02/06.
 */
@Repository
public interface PlaylistRepository extends
        JpaRepository<PlaylistEntity, Long>,
        PlaylistRepositoryCustom,
        RevisionRepository<PlaylistEntity, Long, Integer> {
}
