package me.gogosing.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.time.ZonedDateTime;

/**
 * 플레이 리스트 수록곡 정보 Entity.
 * Created by JinBum Jeong on 2020/02/06.
 */
@Entity
@Table(
    name = "PL_INVENTORY",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"INVENTORY_ID"})}
)
public class PlaylistInventoryEntity {

    /**
     * 레코드 식별자.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "INVENTORY_KEY")
    private Long key = null;

    /**
     * 레코드 대체 식별자.
     */
    @Column(name = "INVENTORY_ID", nullable = false, columnDefinition = "char(14)")
    private String id;

    /**
     * 플레이리스트 정보.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PLAYLIST_KEY", referencedColumnName = "PLAYLIST_KEY", nullable = false)
    private PlaylistEntity playlistEntity;

    /**
     * 곡 정보.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SONG_KEY", referencedColumnName = "SONG_KEY", nullable = false)
    private SongEntity songEntity;

    /**
     * 레코드 생성일시.
     */
    @Column(name = "CREATE_UTC", nullable = false, updatable = false)
    private ZonedDateTime createOn;

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

    public PlaylistEntity getPlaylistEntity() {
        return playlistEntity;
    }

    public void setPlaylistEntity(PlaylistEntity playlistEntity) {
        this.playlistEntity = playlistEntity;
    }

    public SongEntity getSongEntity() {
        return songEntity;
    }

    public void setSongEntity(SongEntity songEntity) {
        this.songEntity = songEntity;
    }

    public ZonedDateTime getCreateOn() {
        return createOn;
    }

    public void setCreateOn(ZonedDateTime createOn) {
        this.createOn = createOn;
    }
}
