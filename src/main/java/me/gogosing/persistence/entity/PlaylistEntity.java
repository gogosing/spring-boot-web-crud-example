package me.gogosing.persistence.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.time.ZonedDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 사용자 플레이리스트 Entity.
 * Created by JinBum Jeong on 2020/02/06.
 */
@Entity
@Table(
    name = "PLAYLIST",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"PLAYLIST_ID"})}
)
public class PlaylistEntity {

    /**
     * 레코드 식별자.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PLAYLIST_KEY")
    private Long key = null;

    /**
     * 레코드 대체 식별자.
     */
    @Column(name = "PLAYLIST_ID", nullable = false, columnDefinition = "char(14)")
    private String id;

    /**
     * 제목.
     */
    @Column(name = "TITLE", nullable = false, length = 150)
    private String title;

    /**
     * 레코드 생성 사용자 식별자.
     */
    @Column(name = "CREATE_BY", nullable = false, updatable = false, columnDefinition = "char(14)")
    private String createBy;

    /**
     * 레코드 생성일시.
     */
    @Column(name = "CREATE_UTC", nullable = false, updatable = false)
    private ZonedDateTime createOn;

    /**
     * 플레이리스트 컨텐츠 목록.
     */
    @OneToMany(mappedBy = "playlistEntity", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
    private Set<PlaylistInventoryEntity> playlistInventoryEntities = new LinkedHashSet<>();

    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public ZonedDateTime getCreateOn() {
        return createOn;
    }

    public void setCreateOn(ZonedDateTime createOn) {
        this.createOn = createOn;
    }

    public Set<PlaylistInventoryEntity> getPlaylistInventoryEntities() {
        return playlistInventoryEntities;
    }

    public void setPlaylistInventoryEntities(Set<PlaylistInventoryEntity> playlistInventoryEntities) {
        this.playlistInventoryEntities = playlistInventoryEntities;
    }
}
