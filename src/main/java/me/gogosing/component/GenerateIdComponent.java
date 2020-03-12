package me.gogosing.component;

/**
 * 레코드 대체 식별자 생성 Component.
 * Created by JinBum Jeong on 2020/02/07.
 */
public interface GenerateIdComponent {

    /**
     * 플레이리스트 레코드 대체 식별자 생성.
     * @return 생성된 플레이리스트 레코드 대체 식별자.
     */
    String playlistId();

    /**
     * 플레이리스트 컨텐츠 레코드 대체 식별자 생성.
     * @return 생성된 플레이리스트 컨텐츠 레코드 대체 식별자.
     */
    String inventoryId();
}
