package io.reactivestax.service;

import io.reactivestax.repository.entity.RuleSet;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Objects;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class FileProcessor {

    private final ConcurrentHashMap<String, RuleSet> node = new ConcurrentHashMap<>();
    private final  Stack<String> nodeProcess = new Stack<>();
    private final AtomicInteger atomicCounter = new AtomicInteger();

    public void processFile(Map<String, String> ruleValueSet) {
        for (Map.Entry<String, String> dataSet : ruleValueSet.entrySet()) {
            String key = dataSet.getKey().split(":")[1];
            String value = dataSet.getValue().split(":")[0];
            nodeProcess.push(key);
            RuleSet build = RuleSet.builder()
                    .leftNode(atomicCounter.incrementAndGet())
                    .rightNode(trackingRightNode(key))
                    .ruleType(key)
                    .value(value)
                    .build();

            System.out.println("build" + build);
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

    //if the intermediate terminal 04 is there then check for the terminal node ( which is 04 and 06 )
    // push in the stack once it hit the terminal operator pop it and add 1 to get the right node
    public void trackingIntermediateNode(String key){
        if(Objects.equals(key, "04")){

        }
    }
}
