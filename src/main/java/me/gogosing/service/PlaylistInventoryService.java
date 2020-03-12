package me.gogosing.service;

import me.gogosing.model.Inventory;
import me.gogosing.model.InventoryRequest;

import java.util.List;

/**
 * 플레이리스트 컨텐츠 서비스 Interface.
 * Created by JinBum Jeong on 2020/02/07.
 */
public interface PlaylistInventoryService {

    /**
     * 특정 플레이리스트에 포함된 음원 목록 조회.
     * @param userId 사용자 아이디.
     * @param id 플레이리스트 레코드 대체 식별자.
     * @return 플레이리스트에 저장되어 있는 음원 목록.
     */
    List<Inventory> getInventory(String userId, String id);

    /**
     * 플레이리스트에 음원 추가.
     * @param userId 사용자 아이디.
     * @param id 플레이리스트 레코드 대체 식별자.
     * @param locale 추가를 요청하는 사용자의 지역.
     * @param inventoryRequest 플레이리스트에 적용될 음원 정보.
     */
    void addInventory(String userId, String id, String locale, InventoryRequest inventoryRequest);

    /**
     * 플레이리스트에서 음원 제거.
     * @param userId 사용자 아이디.
     * @param id 플레이리스트 레코드 대체 식별자.
     * @param inventoryId 제거대상 컨텐츠 레코드 대체 식별자 목록.
     */
    void removeInventory(String userId, String id, List<String> inventoryId);
}
