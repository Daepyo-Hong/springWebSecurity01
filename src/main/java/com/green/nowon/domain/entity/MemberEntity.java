package com.green.nowon.domain.entity;


import com.green.nowon.security.MemberRole;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@ToString
@Builder
@AllArgsConstructor
@Getter
@NoArgsConstructor
@SequenceGenerator( name = "gen_seq_mem",   //Generator 이름
        sequenceName = "seq_mem",       //시퀀스 이름
        initialValue = 1,               //시작값
        allocationSize = 1              //증가값
)
@Table(name = "member")   // 테이블명 설정가능
@Entity                   //물리 DB에 매핑되는 자바 클래스 정보
public class MemberEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(generator = "gen_seq_mem", strategy = GenerationType.SEQUENCE)
    private long mno;

    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String pass;
    @Column(nullable = false)
    private String name;
    @Column(unique = true)
    private String nickName;

    // enum MemberRole


    //mybatis는 테이블 직접 만들어서 연결관계 설정해야하는데 JPA 는 제공함
    //Laze(지연로딩)디폴트임 : getRoleSet() 호출해야 로딩, EAGER(즉시로딩)는 테이블 접근시 로딩
    @Enumerated(EnumType.STRING)  //저장할 때 EnumType.ORDINAL 이 디폴트, STRING으로 지정해주어야 확장시 순서뒤바뀜에 영향 X
    @Builder.Default  //new HashSet<>() 적용  : 빌더패턴은 필드가 null로 세팅이 되기때문에 넣어줘야함
    @CollectionTable(name = "memberRole",joinColumns = {@JoinColumn(name ="mno")})   //DB에 생성되는 테이블명, 조인컬럼명 직접 설정
    @ElementCollection(fetch = FetchType.EAGER)   // (fetch = FetchType.LAZY)(지연로딩: getRoleSet() 호출시점에서데이터읽어옴), 디비연결확인해야함
    private Set<MemberRole> roleSet = new HashSet<>(); //회원(1) : MemberRole(M) 관계

    //권한은 보통 USER<SELLER<ADMIN 포함관계이므로 롤 추가/삭제 편의 메서드 구현
    public MemberEntity addRole(MemberRole role){
        roleSet.add(role);
        return this;
    }
    public MemberEntity removeRole(MemberRole role){
        roleSet.remove(role);
        return this;
    }
}
