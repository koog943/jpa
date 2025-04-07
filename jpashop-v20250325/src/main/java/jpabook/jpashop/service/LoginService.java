package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.querydsl.MemberQueryDsl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LoginService {

    @Autowired
    private final MemberQueryDsl memberQueryDsl;

    public static final String LOGIN_MEMBER = "loginMember";

    public Member login(String name, String password) throws IllegalAccessException {
        return memberQueryDsl.findByLoginId(name, password).orElse(null);
    }



}
