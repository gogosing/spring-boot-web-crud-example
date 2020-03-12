package me.gogosing.service.impl;

import me.gogosing.component.GenerateIdComponent;
import me.gogosing.model.Playlist;
import me.gogosing.persistence.entity.PlaylistEntity;
import me.gogosing.persistence.repository.PlaylistRepository;
import me.gogosing.service.PlaylistService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by JinBum Jeong on 2020/02/08.
 */
@ExtendWith(SpringExtension.class)
public class PlaylistServiceImplTests {

    @MockBean
    private GenerateIdComponent generateIdComponent;

    @MockBean
    private PlaylistRepository playlistRepository;

    private PlaylistService playlistService;

    @BeforeEach
    public void init() {
        playlistService = new PlaylistServiceImpl(
                generateIdComponent, playlistRepository
        );
    }

    @Test
    public void testCreatePlaylist() {
        String expectedUserId = "FU200205000001";
        String expectedPlaylistId = "FP20200208NZRH";

        when(generateIdComponent.playlistId())
            .thenReturn(expectedPlaylistId);

        PlaylistEntity playlistEntity = new PlaylistEntity();
        playlistEntity.setId(expectedPlaylistId);
        playlistEntity.setTitle("playlist title");
        playlistEntity.setCreateBy(expectedUserId);
        playlistEntity.setCreateOn(ZonedDateTime.now());

        ArgumentCaptor<PlaylistEntity> playlistEntityArgumentCaptor =
                ArgumentCaptor.forClass(PlaylistEntity.class);

        when(playlistRepository.save(playlistEntityArgumentCaptor.capture()))
            .thenReturn(playlistEntity);

        String actualResult = playlistService
                .createPlaylist(expectedUserId, playlistEntity.getTitle());

        assertThat(actualResult)
                .as("플레이리스트 생성 테스트")
                .isNotBlank()
                .isEqualTo(expectedPlaylistId);
    }

    @Test
    public void testUpdatePlaylist() {
        String expectedUserId = "FU200205000001";
        String expectedPlaylistId = "FP20200208NZRH";

        PlaylistEntity playlistEntity = new PlaylistEntity();
        playlistEntity.setId(expectedPlaylistId);
        playlistEntity.setTitle("playlist title");
        playlistEntity.setCreateBy(expectedUserId);
        playlistEntity.setCreateOn(ZonedDateTime.now());

        ArgumentCaptor<String> userIdArgumentCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> playlistIdArgumentCaptor = ArgumentCaptor.forClass(String.class);

        when(
            playlistRepository.getPlaylistEntity(
                userIdArgumentCaptor.capture(), playlistIdArgumentCaptor.capture()
            )
        ).thenReturn(Optional.of(playlistEntity));

        playlistEntity.setTitle("playlist title modified");

        when(playlistRepository.save(playlistEntity))
            .thenReturn(playlistEntity);

        playlistService.updatePlaylist(
            expectedUserId, expectedPlaylistId, "playlist title modified"
        );

        verify(playlistRepository, times(1))
                .getPlaylistEntity(any(String.class), any(String.class));

        verify(playlistRepository, times(1))
                .save(any(PlaylistEntity.class));
    }

    @Test
    public void testDeletePlaylist() {
        String expectedUserId = "FU200205000001";
        String expectedPlaylistId = "FP20200208NZRH";

        PlaylistEntity playlistEntity = new PlaylistEntity();
        playlistEntity.setId(expectedPlaylistId);
        playlistEntity.setTitle("playlist title");
        playlistEntity.setCreateBy(expectedUserId);
        playlistEntity.setCreateOn(ZonedDateTime.now());

        ArgumentCaptor<String> userIdArgumentCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> playlistIdArgumentCaptor = ArgumentCaptor.forClass(String.class);

        when(
            playlistRepository.getPlaylistEntity(
                userIdArgumentCaptor.capture(), playlistIdArgumentCaptor.capture()
            )
        ).thenReturn(Optional.of(playlistEntity));

        doNothing()
            .when(playlistRepository).delete(playlistEntity);

        playlistService.deletePlaylist(expectedUserId, expectedPlaylistId);

        verify(playlistRepository, times(1))
                .getPlaylistEntity(any(String.class), any(String.class));

        verify(playlistRepository, times(1))
                .delete(any(PlaylistEntity.class));
    }

    @Test
    public void testGetPlaylist() {
        String expectedUserId = "FU200205000001";

        PlaylistEntity playlistEntity1 = new PlaylistEntity();
        playlistEntity1.setId("FP20200208NZR1");
        playlistEntity1.setTitle("playlist title 1");
        playlistEntity1.setCreateBy(expectedUserId);
        playlistEntity1.setCreateOn(ZonedDateTime.now());

        PlaylistEntity playlistEntity2 = new PlaylistEntity();
        playlistEntity2.setId("FP20200208NZR2");
        playlistEntity2.setTitle("playlist title 2");
        playlistEntity2.setCreateBy(expectedUserId);
        playlistEntity2.setCreateOn(ZonedDateTime.now().plusDays(1));

        List<PlaylistEntity> playlistEntities = Stream.of(playlistEntity1, playlistEntity2)
                .sorted(Comparator.comparing(PlaylistEntity::getCreateOn).reversed())
                .collect(Collectors.toList());

        ArgumentCaptor<String> userIdArgumentCaptor = ArgumentCaptor.forClass(String.class);

        when(
            playlistRepository.getPlaylistEntities(userIdArgumentCaptor.capture())
        ).thenReturn(playlistEntities);

        List<Playlist> actualResult = playlistService.getPlaylist(expectedUserId);

        assertThat(actualResult)
                .as("플레이리스트 목록 조회 검증")
                .isNotNull()
                .isNotEmpty()
                .hasSize(playlistEntities.size())
                .extracting("id")
                .containsExactly(playlistEntity2.getId(), playlistEntity1.getId());
    }
}
