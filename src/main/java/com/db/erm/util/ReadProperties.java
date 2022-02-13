package com.db.erm.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.db.erm.constant.Constants;

public class ReadProperties {

    public Map<String, String> read() {
        System.out.println( ":::::::::::::::::::::Reading Properties File Start:::::::::::::::::::::" );
        HashMap<String, String> propertiesMap = new HashMap<String, String>();
        try (InputStream input = new FileInputStream( "." + File.separator + "config.properties" )) {
            BufferedReader br = new BufferedReader( new InputStreamReader( input ) );
            String strLine;
            //Read File Line By Line
            while ( ( strLine = br.readLine() ) != null ) {
                if ( !strLine.isEmpty() && !strLine.startsWith( "#" ) && strLine.contains( "=" ) ) {
                    String[] arr = strLine.trim().split( "=" );
                    if ( arr.length > 0 ) {
                        if ( arr.length < 2 )
                            propertiesMap.put( arr[0].trim(), null );
                        else
                            propertiesMap.put( arr[0].trim(), arr[1].trim() );
                    }
                }
            }

        } catch ( IOException ex ) {
            ex.printStackTrace();
        }

        if ( propertiesMap.get( Constants.COMMON_EXCLUDE_DIR ) != null && !propertiesMap.get( Constants.COMMON_EXCLUDE_DIR ).isEmpty() ) {
            String repo1ExcludeDir = propertiesMap.get( Constants.REPO1_EXCLUDE_DIR );
            String repo2ExcludeDir = propertiesMap.get( Constants.REPO2_EXCLUDE_DIR );
            propertiesMap.put( Constants.REPO1_EXCLUDE_DIR, repo1ExcludeDir + "|" + propertiesMap.get( Constants.COMMON_EXCLUDE_DIR ).trim() );
            propertiesMap.put( Constants.REPO2_EXCLUDE_DIR, repo2ExcludeDir + "|" + propertiesMap.get( Constants.COMMON_EXCLUDE_DIR ).trim() );
        }

        if ( propertiesMap.get( Constants.COMMON_EXCLUDE_FILES ) != null && !propertiesMap.get( Constants.COMMON_EXCLUDE_FILES ).isEmpty() ) {
            String repo1ExcludeFiles = propertiesMap.get( Constants.REPO1_EXCLUDE_FILES );
            String repo2ExcludeFiles = propertiesMap.get( Constants.REPO2_EXCLUDE_FILES );
            propertiesMap.put( Constants.REPO1_EXCLUDE_FILES, repo1ExcludeFiles + "|" + propertiesMap.get( Constants.COMMON_EXCLUDE_FILES ).trim() );
            propertiesMap.put( Constants.REPO2_EXCLUDE_FILES, repo2ExcludeFiles + "|" + propertiesMap.get( Constants.COMMON_EXCLUDE_FILES ).trim() );
        }
        System.out.println( ":::::::::::::::::::::Reading Properties File Complete:::::::::::::::::::::" );
        return propertiesMap;
    }

    public static void cleanDirectory( Map<String, String> p ) {
        System.out.println( ":::::::::::::::::::::Cleaning Directory Start " + p.get( Constants.PATH_TO_CLONE ) + ":::::::::::::::::::::" );
        try {
            FileUtils.deleteDirectory( new File( p.get( Constants.PATH_TO_CLONE ) ) );
        } catch ( IOException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println( ":::::::::::::::::::::Cleaning Directory Complete:::::::::::::::::::::" );
    }

}
