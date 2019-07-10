package com.itheima.dao;

import com.itheima.pojo.Member;
import org.apache.ibatis.annotations.Param;

public interface MemberDao {
    Member findMemberByTelephone(@Param("telephone")String telephone);

    void addMember(Member member);
}
