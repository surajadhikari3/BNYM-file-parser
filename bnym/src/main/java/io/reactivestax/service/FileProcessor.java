package io.reactivestax.service;

import io.reactivestax.repository.entity.RuleSet;

import java.util.Map;
import java.util.Objects;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class FileProcessor {

    private final ConcurrentHashMap<String, RuleSet> node = new ConcurrentHashMap<>();
    private final  Stack<String> nodeProcess = new Stack<>();
    private final AtomicInteger atomicCounter = new AtomicInteger();

    public void processFile(Map<String, String> ruleValueSet) {
        for (Map.Entry<String, String> dataSet : ruleValueSet.entrySet()) {
            String key = dataSet.getKey();
            String value = dataSet.getValue();
            boolean b = checkNodeType(key);
            nodeProcess.push(key);
            RuleSet.builder()
                    .leftNode(atomicCounter.incrementAndGet())
                    .rightNode(trackingRightNode(key))
                    .ruleType(key)
                    .value(value)
                    .build();
        }
    }


    public boolean checkNodeType(String key) {
        return Objects.equals(key, "02") || Objects.equals(key, "05") || Objects.equals(key, "06") || Objects.equals(key, "07");
    }

    public int trackingRightNode(String key){
        if(checkNodeType(key)) {
            return atomicCounter.incrementAndGet();
        }
        //need to work on the more robust tracking process....
        nodeProcess.push(key);
        return  -1;
    }
}
