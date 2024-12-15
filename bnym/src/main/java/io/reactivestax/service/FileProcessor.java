package io.reactivestax.service;

import io.reactivestax.repository.entity.RuleSet;
import io.reactivestax.repository.entity.TempData;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
public class FileProcessor {

    private final ConcurrentHashMap<String, RuleSet> node = new ConcurrentHashMap<>();
    private final  Stack<RuleSet> nodeProcess = new Stack<>();
    private final AtomicInteger atomicCounter = new AtomicInteger();
    private final List<RuleSet> ruleSetList = new ArrayList<>();

    public void processFile(List<String> ruleValueSet) {


        for (String dataSet : ruleValueSet) {
            String key = dataSet.substring(0, 2);
            String value = dataSet.substring(2);
            if (checkNodeType(key)) {
                RuleSet build = RuleSet.builder()
                        .leftNode(atomicCounter.incrementAndGet())
                        .rightNode(atomicCounter.incrementAndGet())
                        .ruleType(key)
                        .value(value)
                        .build();
                ruleSetList.add(
                        build
                );
                System.out.println("build" + build);
            } else{
                if(!nodeProcess.isEmpty() && (Objects.equals(key, nodeProcess.peek().getRuleType()) || Integer.parseInt(key) < Integer.parseInt(nodeProcess.peek().getRuleType()))){
                    RuleSet popElement = nodeProcess.pop();
                    popElement.setRightNode(atomicCounter.incrementAndGet());
                    ruleSetList.add(popElement);
                    buildStackElement(key, value);
                } else{
                    buildStackElement(key, value);
                }
            }
        }
        System.out.println("Element size" + ruleSetList.size());
//        System.out.println("list element" + ruleSetList + "\n");
        List<RuleSet> collect = ruleSetList.stream().sorted(Comparator.comparing(RuleSet::getRuleType)).toList();
        collect.forEach(System.out::println);
    }

    private void buildStackElement(String key, String value) {
        RuleSet tempData = RuleSet.builder()
                .leftNode(atomicCounter.incrementAndGet())
                .rightNode(-1)
                .ruleType(key)
                .value(value)
                .build();
        nodeProcess.push(tempData);
    }


    public boolean checkNodeType(String key) {
        return Objects.equals(key, "02") || Objects.equals(key, "05") || Objects.equals(key, "06") || Objects.equals(key, "07");
    }

    public int trackingRightNode(String key){
        if(checkNodeType(key)) {
            return atomicCounter.incrementAndGet();
        }
        //need to work on the more robust tracking process....
//        nodeProcess.push(key);
        return  -1;
    }

    //if the intermediate terminal 04 is there then check for the terminal node ( which is 04 and 06 )
    // push in the stack once it hit the terminal operator pop it and add 1 to get the right node
    public void trackingIntermediateNode(String key){
        if(Objects.equals(key, "04")){

        }
    }
}
