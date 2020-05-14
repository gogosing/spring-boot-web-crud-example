package me.gogosing.service.impl;

import me.gogosing.component.GenerateIdComponent;
import me.gogosing.exception.NotFoundException;
import me.gogosing.model.Inventory;
import me.gogosing.model.InventoryRequest;
import me.gogosing.model.Song;
import me.gogosing.persistence.entity.PlaylistEntity;
import me.gogosing.persistence.entity.PlaylistInventoryEntity;
import me.gogosing.persistence.entity.SongEntity;
import me.gogosing.persistence.repository.AlbumRepository;
import me.gogosing.persistence.repository.PlaylistInventoryRepository;
import me.gogosing.persistence.repository.PlaylistRepository;
import me.gogosing.persistence.repository.SongRepository;
import me.gogosing.service.PlaylistInventoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by JinBum Jeong on 2020/02/07.
 */
@Service
public class PlaylistInventoryServiceImpl implements PlaylistInventoryService {

    private final GenerateIdComponent generateIdComponent;

    private final SongRepository songRepository;

    private final AlbumRepository albumRepository;

    private final PlaylistRepository playlistRepository;

    private final PlaylistInventoryRepository playlistInventoryRepository;

    public PlaylistInventoryServiceImpl(
            GenerateIdComponent generateIdComponent,
            SongRepository songRepository,
            AlbumRepository albumRepository,
            PlaylistRepository playlistRepository,
            PlaylistInventoryRepository playlistInventoryRepository
    ) {
        this.generateIdComponent = generateIdComponent;
        this.songRepository = songRepository;
        this.albumRepository = albumRepository;
        this.playlistRepository = playlistRepository;
        this.playlistInventoryRepository = playlistInventoryRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Inventory> getInventory(String userId, String id) {
        return getPlaylistEntityWithSongEntities(userId, id)
                .getPlaylistInventoryEntities().stream()
                .sorted(Comparator.comparing(PlaylistInventoryEntity::getCreateOn))
                .map(playlistInventoryEntity -> {
                    final SongEntity songEntity = playlistInventoryEntity.getSongEntity();
                    final Song song = new Song();
                    song.setId(songEntity.getId());
                    song.setTitle(songEntity.getTitle());
                    song.setTrack(songEntity.getTrackNo());
                    song.setLength(songEntity.getLength());

                    final Inventory inventory = new Inventory();
                    inventory.setId(playlistInventoryEntity.getId());
                    inventory.setSong(song);
                    inventory.setCreateOn(playlistInventoryEntity.getCreateOn());
                    return inventory;
                }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void addInventory(
            String userId,
            String id,
            String locale,
            InventoryRequest inventoryRequest
    ) {
        final PlaylistEntity playlistEntity = getPlaylistEntity(userId, id);

        final Set<SongEntity> songEntities = new LinkedHashSet<>();

        songEntities.addAll(
                songRepository.getSongEntities(locale, inventoryRequest.getSongs())
        );
        songEntities.addAll(
                albumRepository.getAlbumEntities(locale, inventoryRequest.getAlbums()).stream()
                .flatMap(albumEntity -> albumEntity.getSongEntities().stream())
                .collect(Collectors.toList())
        );

        final List<PlaylistInventoryEntity> inventoryEntities = new ArrayList<>();
        songEntities.forEach(songEntity -> {
            PlaylistInventoryEntity playlistInventoryEntity = new PlaylistInventoryEntity();
            playlistInventoryEntity.setId(generateIdComponent.inventoryId());
            playlistInventoryEntity.setPlaylistEntity(playlistEntity);
            playlistInventoryEntity.setSongEntity(songEntity);
            playlistInventoryEntity.setCreateOn(ZonedDateTime.now());
            inventoryEntities.add(playlistInventoryEntity);
        });

        playlistInventoryRepository.saveAll(inventoryEntities);
    }

    @Override
    @Transactional
    public void removeInventory(String userId, String id, List<String> inventoryId) {
        final List<PlaylistInventoryEntity> inventoryEntities =
                playlistInventoryRepository.getPlaylistInventoryEntities(userId, id, inventoryId);

        playlistInventoryRepository.deleteAll(inventoryEntities);
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

    /**
     * 특정 플레이리스트 정보 조회.(수록곡이 포함된)
     * @param userId 사용자 아이디.
     * @param id 플레이리스트 레코드 대체 식별자.
     * @return 플레이리스트 정보.
     */
    private PlaylistEntity getPlaylistEntityWithSongEntities(String userId, String id) {
        return playlistRepository.getPlaylistEntityWithSongEntities(userId, id).orElseThrow(() ->
                new NotFoundException(String.format("사용자[%s]의 플레이리스트[%s]는 존재하지 않거나 삭제된 상태입니다.", userId, id))
        );
    }
}
