package com.cmbird.factory;

import com.cmbird.strategy.PrintStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 打印 策略工厂模式
 * @author zyj
 * @Date 2020-7-8 14:42:14
 */
@Service
public class FactoryForPrintStrategy {

    @Autowired
    Map<String, PrintStrategy> printStrategyMap = new ConcurrentHashMap<>(3);

    /**
     *
     * @param type 业务类型
     * @return
     * @throws Exception
     */
    public PrintStrategy getStrategy(String type) throws Exception{
        PrintStrategy strategy = printStrategyMap.get(type);
        if(strategy == null) {
            throw new RuntimeException("no strategy defined");
        }
        return strategy;
    }


}