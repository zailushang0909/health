package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.MemberDao;
import com.itheima.pojo.Member;
import com.itheima.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service(interfaceClass = MemberService.class)
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberDao memberDao;
    @Override
    public Member findMemberByTel(String telephone) {
        return  memberDao.findMemberByTelephone(telephone);
    }

    @Override
    public void addMember(Member member) {
        memberDao.addMember(member);
    }
}
