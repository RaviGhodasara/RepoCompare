package com.db.erm.compare;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import org.apache.commons.io.FileUtils;

import com.db.erm.constant.Constants;

public class FileCompare implements Callable<Map<String, Boolean>> {

    Collection<String> fileList;
    Map<String, String> properties;

    public FileCompare( Collection<String> fileList, Map<String, String> properties ) {
        super();
        this.fileList = fileList;
        this.properties = properties;
    }

    @Override
    public Map<String, Boolean> call() throws Exception {
        Map<String, Boolean> map = new HashMap<String, Boolean>();
        try {
            for ( String file : fileList ) {
                File repo1File = new File( this.properties.get( Constants.PATH_TO_CLONE ) + this.properties.get( Constants.REPO1_NAME ) + file );
                File repo2File = new File( this.properties.get( Constants.PATH_TO_CLONE ) + this.properties.get( Constants.REPO2_NAME ) + file );
                boolean isTwoEqual = FileUtils.contentEquals( repo1File, repo2File );
                map.put( file, isTwoEqual );
            }
        } catch ( Exception e ) {
            e.printStackTrace();
        }
        return map;
    }
}