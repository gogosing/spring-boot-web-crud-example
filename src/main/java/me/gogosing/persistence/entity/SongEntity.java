package me.gogosing.persistence.entity;

import me.gogosing.persistence.converter.BooleanToCharConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
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
 * 곡 정보 Entity.
 * Created by JinBum Jeong on 2020/02/05.
 */
@Entity
@Table(
    name = "SONG",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"SONG_ID"})}
)
public class SongEntity {

    /**
     * 레코드 식별자.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SONG_KEY")
    private Long key = null;

    /**
     * 레코드 대체 식별자.
     */
    @Column(name = "SONG_ID", nullable = false, columnDefinition = "char(14)")
    private String id;

    /**
     * 소속 앨범 레코드 식별자.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ALBUM_KEY", referencedColumnName = "ALBUM_KEY", nullable = false)
    private AlbumEntity albumEntity;

    /**
     * 제목.
     */
    @Column(name = "TITLE", nullable = false, length = 150)
    private String title;

    /**
     * 곡 길이 (단위: 초(second)).
     */
    @Column(name = "LENGTH", nullable = false)
    private int length;

    /**
     * 트랙 번호.
     */
    @Column(name = "TRACK_NO", nullable = false, columnDefinition = "tinyint")
    private int trackNo;

    /**
     * 삭제여부.
     */
    @Convert(converter = BooleanToCharConverter.class)
    @Column(name = "DELETED", nullable = false, columnDefinition = "char(1)")
    private boolean deleted = false;

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

    public AlbumEntity getAlbumEntity() {
        return albumEntity;
    }

    public void setAlbumEntity(AlbumEntity albumEntity) {
        this.albumEntity = albumEntity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getTrackNo() {
        return trackNo;
    }

    public void setTrackNo(int trackNo) {
        this.trackNo = trackNo;
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
}
