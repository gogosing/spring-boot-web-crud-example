package me.gogosing.persistence.entity;

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
 * 앨범 정보 Entity.
 * Created by JinBum Jeong on 2020/02/05.
 */
@Entity
@Table(
    name = "ALBUM",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"ALBUM_ID"})}
)
public class AlbumEntity {

    /**
     * 레코드 식별자.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ALBUM_KEY")
    private Long key = null;

    /**
     * 레코드 대체 식별자.
     */
    @Column(name = "ALBUM_ID", nullable = false, columnDefinition = "char(14)")
    private String id;

    /**
     * 제목.
     */
    @Column(name = "TITLE", nullable = false, length = 150)
    private String title;

    /**
     * 삭제여부.
     */
    @Column(name = "DELETED", nullable = false)
    private boolean deleted = false;

    /**
     * 레코드 생성일시.
     */
    @Column(name = "CREATE_UTC", nullable = false, updatable = false)
    private ZonedDateTime createOn;

    /**
     * 앨범 소속 곡 목록.
     */
    @OneToMany(mappedBy = "albumEntity", fetch = FetchType.LAZY)
    private Set<SongEntity> songEntities = new LinkedHashSet<>();

    /**
     * 앨범 서비스 가능 지역 코드 목록.
     */
    @OneToMany(mappedBy = "albumEntity", fetch = FetchType.LAZY)
    private Set<AlbumLocaleEntity> albumLocaleEntities = new LinkedHashSet<>();

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

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public ZonedDateTime getCreateOn() {
        return createOn;
    }

    public void setCreateOn(ZonedDateTime createOn) {
        this.createOn = createOn;
    }

    public Set<SongEntity> getSongEntities() {
        return songEntities;
    }

    public void setSongEntities(Set<SongEntity> songEntities) {
        this.songEntities = songEntities;
    }

    public Set<AlbumLocaleEntity> getAlbumLocaleEntities() {
        return albumLocaleEntities;
    }

    public void setAlbumLocaleEntities(Set<AlbumLocaleEntity> albumLocaleEntities) {
        this.albumLocaleEntities = albumLocaleEntities;
    }
}
