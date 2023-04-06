package com.abysscat.usercenter.web.config;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.google.common.collect.Lists;

/**
 * Sentinel限流配置
 */
@Component
public class SentinelConfig {

    /**
     * 单机单个接口通用限流 - QPS上限
     */
    @Value("${flow.qps.common:40}")
    private Integer commonQpsLimitNum;

    /**
     * Sentinel资源名称定义
     */
    public static final String RESOURCE_USER_API_REGISTER_USER = "userAPI.registerUser";
    public static final String RESOURCE_USER_API_GET_USER_LIST = "userAPI.getUserList";
    public static final String RESOURCE_USER_API_BAN_USER_LIST = "userAPI.banUserList";
    public static final String RESOURCE_USER_API_UPDATE_USER = "userAPI.updateUser";
    public static final String RESOURCE_USER_API_ALL_USER = "userAPI.getAllUserList";

    /**
     * 初始化限流规则（绑定资源名称）
     */
    @PostConstruct
    public void initSentinelRule() {
        List<String> resourceNameList = Lists.newArrayList(
                RESOURCE_USER_API_REGISTER_USER,
                RESOURCE_USER_API_GET_USER_LIST,
                RESOURCE_USER_API_BAN_USER_LIST,
                RESOURCE_USER_API_UPDATE_USER,
                RESOURCE_USER_API_ALL_USER);

        List<FlowRule> rules = new ArrayList<>();

        for (String resourceName : resourceNameList) {
            FlowRule rule = new FlowRule();
            rule.setResource(resourceName);
            rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
            rule.setCount(commonQpsLimitNum);
            rules.add(rule);
        }
        FlowRuleManager.loadRules(rules);
    }
}
