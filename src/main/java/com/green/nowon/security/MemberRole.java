package com.green.nowon.security;


import lombok.RequiredArgsConstructor;

//인증유저들 .. 권한 구분 : 권한에 따라 서비스 적용범위가 달라져요
@RequiredArgsConstructor
public enum MemberRole {
    //Enum name, ordinal : 순서(숫자 : 0부터시작)
    USER("고객", "ROLE_USER"),   //Enum name(), ordinal():0
    SELLER("판매자", "ROLE_SELLER"), //Enum name(), ordinal():1
    ADMIN("관리자", "ROLE_ADMIN");  //Enum name(), ordinal():2
    //나중에 롤 추가할 때를 생각하면, 저장하거나 읽어올 때 문자로 하는게 나음(순서 뒤바뀔 수 있으니)
    private final String koName;
    private final String roleName;

    //Enum처럼  쓰려고 get 지움
    public String koName() {return koName;}
    public String roleName() {return roleName;}

    /*
    //final 필드는 생성자 또는 필드에서 무조건 초기화 해야함
    // == 롬복 @RequiredArgsConstructor
    MemberRole(String koName, String roleName) {
        this.koName = koName;
        this.roleName = roleName;
    }*/
}
