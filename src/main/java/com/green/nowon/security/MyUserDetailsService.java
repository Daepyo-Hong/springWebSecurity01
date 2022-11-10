package com.green.nowon.security;

import com.green.nowon.domain.entity.MemberEntity;
import com.green.nowon.domain.entity.MemberEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class MyUserDetailsService implements UserDetailsService {

    private final MemberEntityRepository repository;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // username == email : email 회원이 존재하는지 체크 존재하면 회원정보 리턴
        // 토큰을 통해서 전달되는 email정보를 확인해봅시다.
        //System.out.println("로그인페이지에서 입력한 이메일(username) >>>>> "+username);

        //https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation
        //jpa는 메서드 이름을 키워드 이용하여 쿼리를 직접 안쓰고 사용할 수 있다.
        //컬럼명은 물리디비가 아닌 엔티티의 이름으로 조회하면 된다.
        //혹시 존재안하면 null이 들어올 수 있기때문에 null을 방지하기 위해 Optional 사용함.
        MemberEntity result = repository.findByEmail(username).orElseThrow();
        /*if(result.isEmpty()) {
            throw new NoSuchElementException("회원이 존재하지 않습니다.");
        }*/

        System.out.println(">>>>>>>>>>회원정보: "+result);//LAZY면 데이터베이스 연결을 유지하기 위해 @Transactional 추가하던가 //EAGER로 바꾸면 됨.
        // Set<MemberRole> --> Set<SimpleGrantedAuthority>
        // new SimpleGrantedAuthority(이 안에는 "ROLL_"가 프리픽스로 들어가야함. 그래서 롤네임을 호출한것.)
        Set<SimpleGrantedAuthority> authorities= result.getRoleSet().stream()
                .map(role-> new SimpleGrantedAuthority(role.roleName()))
                .collect(Collectors.toSet());
/*
        //for문 이용해도 됩니다.
        Set<SimpleGrantedAuthority> authorities2 = new HashSet<>();
        for(MemberRole role:result.getRoleSet()){
            authorities2.add(new SimpleGrantedAuthority(role.roleName()));
        }
*/

        return new User(username, result.getPass(), authorities);
    }
}
