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
                if (Objects.equals(key, "07")) {
                    popAndAddToList();
                }
                RuleSet build = getRuleSet(key, value);
                ruleSetList.add(build);
                System.out.println("build" + build);
            } else{
                if(!nodeProcess.isEmpty() && (Objects.equals(key, nodeProcess.peek().getRuleType()) || Integer.parseInt(key) < Integer.parseInt(nodeProcess.peek().getRuleType()))){
                    popAndAddToList();
                    buildStackElement(key, value);
                } else{
                    buildStackElement(key, value);
                }
            }
        }
        while (!nodeProcess.isEmpty()){
            popAndAddToList();
        }

        System.out.println("Stack size" + nodeProcess.size());
        System.out.println("stack trace" + nodeProcess);
        System.out.println("Element size" + ruleSetList.size());
        List<RuleSet> collect = ruleSetList.stream().sorted(Comparator.comparing(RuleSet::getLeftNode)).toList();
        collect.forEach(System.out::println);
    }

    private void popAndAddToList() {
        RuleSet popElement = nodeProcess.pop();
        popElement.setRightNode(atomicCounter.incrementAndGet());
        ruleSetList.add(popElement);
    }

    private RuleSet getRuleSet(String key, String value) {
        return RuleSet.builder()
                .leftNode(atomicCounter.incrementAndGet())
                .rightNode(atomicCounter.incrementAndGet())
                .ruleType(key)
                .value(value)
                .build();
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
        return Objects.equals(key, "02") || Objects.equals(key, "05") || Objects.equals(key, "06") ||  Objects.equals(key, "07");
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
