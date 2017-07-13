package com.hulk.cloud.limiting;

import com.netflix.loadbalancer.Server;
import org.springframework.cloud.consul.discovery.ConsulServer;

import java.util.Map;

/**
 * Created by charles on 2017/5/22.
 */
public class LabelAndWeightMetadataRuleConsul extends LabelAndWeightMetadataRule {

    @Override
    protected Map<String, String> getMetadata(Server server) {
        Map<String, String> metadata = ((ConsulServer) server).getMetadata();
        return metadata;
    }
}
