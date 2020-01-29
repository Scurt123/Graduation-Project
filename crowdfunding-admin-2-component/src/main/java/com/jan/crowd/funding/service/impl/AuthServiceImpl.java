package com.jan.crowd.funding.service.impl;

import com.jan.crowd.funding.entity.Auth;
import com.jan.crowd.funding.entity.AuthExample;
import com.jan.crowd.funding.mapper.AuthMapper;
import com.jan.crowd.funding.service.api.AuthService;
import com.jan.crowd.funding.util.CrowdFundingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthMapper authMapper;

    public List<Auth> getAllAuth() {
        return authMapper.selectByExample(new AuthExample());
    }

    public List<Integer> getAssignedAuthIdList(Integer roleId) {
        return authMapper.selectAssignedAuthIdList(roleId);
    }

    public void updateRelationShipBetweenRoleAndAuth(Map<String, List<Integer>> assignDataMap) {
        // 1.获取两部分List数据
        List<Integer> roleIdList = assignDataMap.get("roleIdList");
        List<Integer> authIdList = assignDataMap.get("authIdList");

        // 2.取出roleId
        Integer roleId = roleIdList.get(0);

        // 3.删除旧数据
        authMapper.deleteOldRelationship(roleId);

        // 4.保存新数据
        if(CrowdFundingUtils.collectionEffective(authIdList)) {
            authMapper.insertNewRelationship(roleId, authIdList);
        }

    }
}
