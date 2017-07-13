package com.hulk.cloud.limiting;

import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ZoneAvoidanceRule;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * Created by charles on 2017/5/22.
 */
public abstract class LabelAndWeightMetadataRule extends ZoneAvoidanceRule {
    public static final String META_DATA_KEY_LABEL_AND = "labelAnd";
    public static final String META_DATA_KEY_LABEL_OR = "labelOr";

    public static final String META_DATA_KEY_WEIGHT = "weight";

    private Random random = new Random();

    @Override
    public Server choose(Object key) {
        List<Server> serverList = this.getPredicate().getEligibleServers(this.getLoadBalancer().getAllServers(), key);
        if (CollectionUtils.isEmpty(serverList)) {
            return null;
        }

        // 计算总值并剔除0权重节点
        int totalWeight = 0;
        Map<Server, Integer> serverWeightMap = new HashMap<>();
        for (Server server : serverList) {

            Map<String, String> metadata = getMetadata(server);

            // 优先匹配label
            String labelOr = metadata.get(META_DATA_KEY_LABEL_OR);
            if(!StringUtils.isEmpty(labelOr)){
                List<String> metadataLabel = Arrays.asList(labelOr.split(LimitingHeaderInterceptor.HEADER_LABEL_SPLIT));
                for (String label : metadataLabel) {
                    if(LimitingHeaderInterceptor.label.get().contains(label)){
                        return server;
                    }
                }
            }

            String labelAnd = metadata.get(META_DATA_KEY_LABEL_AND);
            if(!StringUtils.isEmpty(labelAnd)){
                List<String> metadataLabel = Arrays.asList(labelAnd.split(LimitingHeaderInterceptor.HEADER_LABEL_SPLIT));
                if(LimitingHeaderInterceptor.label.get().containsAll(metadataLabel)){
                    return server;
                }
            }

            String strWeight = metadata.get(META_DATA_KEY_WEIGHT);

            int weight = 100;
            try {
                weight = Integer.parseInt(strWeight);
            } catch (Exception e) {
                // 无需处理
            }

            if (weight <= 0) {
                continue;
            }

            serverWeightMap.put(server, weight);
            totalWeight += weight;
        }

        // 权重随机
        int randomWight = this.random.nextInt(totalWeight);
        int current = 0;
        for (Map.Entry<Server, Integer> entry : serverWeightMap.entrySet()) {
            current += entry.getValue();
            if (randomWight <= current) {
                return entry.getKey();
            }
        }

        return null;
    }

    protected abstract Map<String, String> getMetadata(Server server );

}
