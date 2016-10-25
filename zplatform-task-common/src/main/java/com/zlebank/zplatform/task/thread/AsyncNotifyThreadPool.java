/* 
 * AsyncNotifyThreadPool.java  
 * 
 * version TODO
 *
 * 2016年8月3日 
 * 
 * Copyright (c) 2016,zlebank.All rights reserved.
 * 
 */
package com.zlebank.zplatform.task.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class Description
 *
 * @author guojia
 * @version
 * @date 2016年8月3日 上午10:27:27
 * @since 
 */
public class AsyncNotifyThreadPool {
	private static final Logger log = LoggerFactory.getLogger(AsyncNotifyThreadPool.class);
    private static ExecutorService executorService;
    private static AsyncNotifyThreadPool pool;
    private AsyncNotifyThreadPool(){
        if(executorService==null){
            executorService = Executors.newCachedThreadPool();
        }else{
            if(executorService.isShutdown()||executorService.isTerminated()){
                executorService = Executors.newCachedThreadPool();
            }
            
        }
    }
    
    public static synchronized AsyncNotifyThreadPool getInstance(){
        if(pool==null){
            pool = new AsyncNotifyThreadPool();
        }
        return pool;
    }
    
    public void executeMission(Runnable runnable){
        if(executorService.isShutdown()||executorService.isTerminated()){
            executorService = Executors.newCachedThreadPool();
        }
        log.info("thread pool :"+executorService);
        
        executorService.execute(runnable);
        log.info("thread pool :"+executorService);
        //runnable.run();
    }
}
