package me.gogosing.persistence.entity;

import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 이력 관리 Entity.
 */
@Entity
@Table(name = "REVINFO")
@org.hibernate.envers.RevisionEntity
public class RevisionEntity {

    /**
     * 리비전 번호.
     */
    @Id
    @RevisionNumber
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REV")
    private Long rev;

    /**
     * 리비전 타임스탬프.
     */
    @RevisionTimestamp
    @Column(name = "REVTSTMP")
    private Long timestamp;

    public Long getRev() {
        return rev;
    }

    public void setRev(Long rev) {
        this.rev = rev;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
