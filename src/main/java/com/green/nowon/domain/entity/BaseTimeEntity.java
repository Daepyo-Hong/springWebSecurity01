package com.green.nowon.domain.entity;

import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
public class BaseTimeEntity {

    @CreationTimestamp      //디비에서 설정 직접 해서 제대로 맞추는 것이 좋음.(뜻대로 만들어지지 않음)
    @Column(columnDefinition = "timestamp(6)")
    private LocalDateTime createdDate;
    @UpdateTimestamp
    @Column(columnDefinition = "timestamp(6)")
    private LocalDateTime updatedDate;
}
