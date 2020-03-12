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
 * 앨범 서비스 가능 지역 코드 정보 Entity.
 * Created by JinBum Jeong on 2020/02/07.
 */
@Entity
@Table(
    name = "ALB_LOCALE",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"ALBUM_KEY", "LOCALE_CODE"})}
)
public class AlbumLocaleEntity {

    /**
     * 레코드 식별자.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LOCALE_KEY")
    private Long key = null;

    /**
     * 소속 앨범 레코드 식별자.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ALBUM_KEY", referencedColumnName = "ALBUM_KEY", nullable = false)
    private AlbumEntity albumEntity;

    /**
     * 서비스 가능 지역 코드(ISO 3166-1 alpha-2).
     */
    @Column(name = "LOCALE_CODE", nullable = false, columnDefinition = "char(3)")
    private String localeCode;

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

    public AlbumEntity getAlbumEntity() {
        return albumEntity;
    }

    public void setAlbumEntity(AlbumEntity albumEntity) {
        this.albumEntity = albumEntity;
    }

    public String getLocaleCode() {
        return localeCode;
    }

    public void setLocaleCode(String localeCode) {
        this.localeCode = localeCode;
    }

    public ZonedDateTime getCreateOn() {
        return createOn;
    }

    public void setCreateOn(ZonedDateTime createOn) {
        this.createOn = createOn;
    }
}
