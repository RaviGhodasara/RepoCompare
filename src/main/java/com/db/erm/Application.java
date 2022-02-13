package com.db.erm;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.db.erm.util.CloneRepo;
import com.db.erm.util.CommonUtil;
import com.db.erm.util.CompareUtil;
import com.db.erm.util.ReadProperties;

public class Application {
    public static void main( String[] args ) throws Exception {

        CommonUtil.writeToLogFile(args);
        
        System.out.println( "::::::::::::::::::::: START :::::::::::::::::::::" );
        
        Map<String, String> p = new ReadProperties().read();

        ReadProperties.cleanDirectory( p );

        CloneRepo.cloneRepoToDirectory( p );

        List<String> repo1Files = CloneRepo.getListOfFilesAfterExcludeDirAndFilesRepo1( p );

        List<String> repo2Files = CloneRepo.getListOfFilesAfterExcludeDirAndFilesRepo2( p );

        Collection<String> similarFiles = CommonUtil.similarAndDifferentFilesInRepo( repo1Files, repo2Files, p );

        CompareUtil.compare( similarFiles, p );
        
        CommonUtil.writeOutputInFile();
        
        System.out.println( "::::::::::::::::::::: FINISH :::::::::::::::::::::" );
    }

}
