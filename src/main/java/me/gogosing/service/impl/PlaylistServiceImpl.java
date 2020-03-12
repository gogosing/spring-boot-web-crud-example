package me.gogosing.service.impl;

import me.gogosing.component.GenerateIdComponent;
import me.gogosing.exception.NotFoundException;
import me.gogosing.model.Playlist;
import me.gogosing.model.Song;
import me.gogosing.persistence.entity.PlaylistEntity;
import me.gogosing.persistence.entity.SongEntity;
import me.gogosing.persistence.repository.PlaylistRepository;
import me.gogosing.service.PlaylistService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by JinBum Jeong on 2020/02/07.
 */
@Service
public class PlaylistServiceImpl implements PlaylistService {

    private final GenerateIdComponent generateIdComponent;

    private final PlaylistRepository playlistRepository;

    public PlaylistServiceImpl(
        GenerateIdComponent generateIdComponent,
        PlaylistRepository playlistRepository
    ) {
        this.generateIdComponent = generateIdComponent;
        this.playlistRepository = playlistRepository;
    }

    @Override
    @Transactional
    public String createPlaylist(String userId, String title) {
        final PlaylistEntity playlistEntity = new PlaylistEntity();

        playlistEntity.setId(generateIdComponent.playlistId());
        playlistEntity.setTitle(title);
        playlistEntity.setCreateBy(userId);
        playlistEntity.setCreateOn(ZonedDateTime.now());

        return playlistRepository.save(playlistEntity).getId();
    }

    @Override
    @Transactional
    public void updatePlaylist(String userId, String id, String title) {
        final PlaylistEntity playlistEntity = getPlaylistEntity(userId, id);

        playlistEntity.setTitle(title);

        playlistRepository.save(playlistEntity);
    }

    @Override
    @Transactional
    public void deletePlaylist(String userId, String id) {
        playlistRepository.delete(getPlaylistEntity(userId, id));
    }

    @Override
    public List<Playlist> getPlaylist(String userId) {
        return playlistRepository.getPlaylistEntities(userId).stream().map(playlistEntity -> {
            final Playlist playlist = new Playlist();
            playlist.setId(playlistEntity.getId());
            playlist.setTitle(playlistEntity.getTitle());
            playlist.setSongs(playlistEntity.getPlaylistInventoryEntities().stream().map(inventoryEntity -> {
                final SongEntity songEntity = inventoryEntity.getSongEntity();
                final Song song = new Song();
                song.setId(songEntity.getId());
                song.setTitle(songEntity.getTitle());
                song.setTrack(songEntity.getTrackNo());
                song.setLength(songEntity.getLength());
                return song;
            }).collect(Collectors.toList()));
            playlist.setCreateOn(playlistEntity.getCreateOn());
            return playlist;
        }).collect(Collectors.toList());
    }

    /**
     * 특정 플레이리스트 정보 조회.
     * @param userId 사용자 아이디.
     * @param id 플레이리스트 레코드 대체 식별자.
     * @return 플레이리스트 정보.
     */
    private PlaylistEntity getPlaylistEntity(String userId, String id) {
        return playlistRepository.getPlaylistEntity(userId, id).orElseThrow(() ->
            new NotFoundException(String.format("사용자[%s]의 플레이리스트[%s]는 존재하지 않거나 삭제된 상태입니다.", userId, id))
        );
    }
}
