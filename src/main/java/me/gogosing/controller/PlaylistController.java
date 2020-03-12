package me.gogosing.controller;

import me.gogosing.model.Inventory;
import me.gogosing.model.InventoryRequest;
import me.gogosing.model.Playlist;
import me.gogosing.model.PlaylistRequest;
import me.gogosing.service.PlaylistInventoryService;
import me.gogosing.service.PlaylistService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.AbstractMap.SimpleEntry;

/**
 * 플레이리스트 Controller.
 * Created by JinBum Jeong on 2020/02/05.
 */
@Validated
@RestController
public class PlaylistController {

    private final PlaylistService playlistService;

    private final PlaylistInventoryService playlistInventoryService;

    public PlaylistController(
            PlaylistService playlistService,
            PlaylistInventoryService playlistInventoryService
    ) {
        this.playlistService = playlistService;
        this.playlistInventoryService = playlistInventoryService;
    }

    /**
     * [3.1] Playlist 생성 API.
     * @param userId 특정 사용자의 ID.
     * @param playlistRequest 플레이리스트 생성 요청 정보.
     * @return 생성된 플레이리스트의 레코드 대체 식별자.
     */
    @PostMapping("/playlist/{userId}")
    public Map<String, String> postPlaylist(
            @PathVariable("userId") String userId,
            @RequestBody @Valid PlaylistRequest playlistRequest
    ) {
        final String storedId = playlistService
                .createPlaylist(userId, playlistRequest.getTitle());

        return Collections.unmodifiableMap(Stream.of(
            new SimpleEntry<>("id", storedId)
        ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
    }

    /**
     * [3.2] Playlist 노래, 앨범 추가 API.
     * @param locale 사용자의 지역.
     * @param userId 특정 사용자의 ID.
     * @param id 플레이리스트 레코드 대체 식별자.
     * @param inventoryRequest 플레이리스트에 추가할 음원 요청 정보.
     */
    @PostMapping("/playlist/{userId}/{id}/inventory")
    public void addInventory(
            Locale locale,
            @PathVariable("userId") String userId,
            @PathVariable("id") String id,
            @RequestBody InventoryRequest inventoryRequest
    ) {
        playlistInventoryService.addInventory(
                userId, id, locale.getLanguage().toLowerCase(), inventoryRequest
        );
    }

    /**
     * [3.3] Playlist 목록 API.
     * @param userId 특정 사용자의 ID.
     * @return 특정 사용자의 플레이 리스트 목록.
     */
    @GetMapping("/playlist/{userId}")
    public List<Playlist> getPlaylist(@PathVariable("userId") String userId) {
        return playlistService.getPlaylist(userId);
    }

    /**
     * [3.4] Playlist 삭제 API.
     * @param userId 특정 사용자의 ID.
     * @param id 플레이리스트 레코드 대체 식별자.
     */
    @DeleteMapping("/playlist/{userId}/{id}")
    public void deletePlaylist(
            @PathVariable("userId") String userId,
            @PathVariable("id") String id
    ) {
        playlistService.deletePlaylist(userId, id);
    }

    /**
     * 플레이리스트 수정.
     * @param userId 특정 사용자의 ID.
     * @param id 플레이리스트 레코드 대체 식별자.
     * @param playlistRequest 플레이리스트 수정 요청 정보.
     */
    @PutMapping("/playlist/{userId}/{id}")
    public void putPlaylist(
            @PathVariable("userId") String userId,
            @PathVariable("id") String id,
            @RequestBody @Valid PlaylistRequest playlistRequest
    ) {
        playlistService.updatePlaylist(userId, id, playlistRequest.getTitle());
    }

    /**
     * 플레이리스트 음원 목록 조회.
     * @param userId 특정 사용자의 ID.
     * @param id 플레이리스트 레코드 대체 식별자.
     * @return 플레이리스트에 저장되어 있는 음원 목록.
     */
    @GetMapping("/playlist/{userId}/{id}/inventory")
    public List<Inventory> getInventory(
            @PathVariable("userId") String userId,
            @PathVariable("id") String id
    ) {
       return playlistInventoryService.getInventory(userId, id);
    }

    /**
     * 플레이리스트 음원 제거.
     * @param userId 특정 사용자의 ID.
     * @param id 플레이리스트 레코드 대체 식별자.
     * @param inventoryId 플레이리스트에서 제거할 컨텐츠 식별자 목록.
     */
    @DeleteMapping("/playlist/{userId}/{id}/inventory")
    public void removeInventory(
            @PathVariable("userId") String userId,
            @PathVariable("id") String id,
            @RequestBody List<String> inventoryId
    ) {
        playlistInventoryService.removeInventory(userId, id, inventoryId);
    }
}
