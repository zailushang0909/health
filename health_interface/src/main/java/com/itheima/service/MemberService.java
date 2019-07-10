package com.itheima.service;

import com.itheima.pojo.Member;

public interface MemberService {
    Member findMemberByTel(String telephone);

    void addMember(Member member);
}
