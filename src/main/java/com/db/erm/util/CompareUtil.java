package com.db.erm.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import com.db.erm.compare.FileCompare;
import com.db.erm.constant.Constants;

public class CompareUtil {

    public static void compare( Collection<String> similarFiles, Map<String, String> p ) {
        
        Map<String, String> result = new HashMap<String, String>();
        int threadCount = Integer.parseInt( p.get( Constants.THREAD_COUNT ) );
        List<String> files = similarFiles.stream().collect( Collectors.toList() );
        List<String> threadFiles = new ArrayList<>();
        System.out.println( "::::::::::::::::::::: Compare File Count ->" + files.size() + " :::::::::::::::::::::" );
        ExecutorService executorService = Executors.newFixedThreadPool( threadCount );
        int fileCount = files.size() / threadCount;
        int count = 0;
        List<Future<Map<String, Boolean>>> futList = new ArrayList<>();
        for ( int i = 0; i < files.size(); i++ ) {
            count++;
            threadFiles.add( files.get( i ) );
            if ( count == fileCount ) {
                Future<Map<String, Boolean>> objFut = executorService.submit( new FileCompare( threadFiles, p ) );
                threadFiles = new ArrayList<>();
                count = 0;
                futList.add( objFut );
            }
        }
        Future<Map<String, Boolean>> objFut = executorService.submit( new FileCompare( threadFiles, p ) );
        futList.add( objFut );

        futList.stream().forEach( fut -> {
            try {
                for ( Entry<String, Boolean> en : fut.get().entrySet() ) {
                    System.out.println( en.getValue() + " :: " + en.getKey() );
                    result.put( en.getKey().substring( 1 ), en.getValue()? "Same        ":"Different   " );
                }
            } catch ( InterruptedException | ExecutionException e ) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        } );
        Output.compareResult = result;

        executorService.shutdown();
        //executorService.awaitTermination( 800, TimeUnit.MINUTES );
    }

}
