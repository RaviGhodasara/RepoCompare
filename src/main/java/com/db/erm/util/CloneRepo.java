package com.db.erm.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import com.db.erm.constant.Constants;

public class CloneRepo {

    public static void cloneRepo( String repoUrl, String userName, String password, String repoPath ) {
        try {
            System.out.println( ":::::::::::::::::::::Cloning " + repoUrl + " into " + repoPath + ":::::::::::::::::::::" );
            Git.cloneRepository().setURI( repoUrl ).setCredentialsProvider( new UsernamePasswordCredentialsProvider( userName, password ) ).setDirectory( Paths.get( repoPath ).toFile() ).call();
            System.out.println( ":::::::::::::::::::::Completed Cloning For " + repoUrl + ":::::::::::::::::::::" );
        } catch ( GitAPIException e ) {
            System.out.println( ":::::::::::::::::::::Exception occurred while cloning repo:::::::::::::::::::::" );
            e.printStackTrace();
        } catch ( Exception ex ) {
            System.out.println( ":::::::::::::::::::::Exception occurred while cloning repo:::::::::::::::::::::" );
            ex.printStackTrace();
        }
    }

    public static void cloneRepoToDirectory( Map<String, String> p ) {
        String repo1Path = p.get( Constants.PATH_TO_CLONE ) + p.get( Constants.REPO1_NAME ) + File.separator;
        cloneRepo( p.get( Constants.REPO1_URL ), p.get( Constants.REPO1_USERNAME ), p.get( Constants.REPO1_PASSWORD ), repo1Path );

        String repo2Path = p.get( Constants.PATH_TO_CLONE ) + p.get( Constants.REPO2_NAME ) + File.separator;
        cloneRepo( p.get( Constants.REPO2_URL ), p.get( Constants.REPO2_USERNAME ), p.get( Constants.REPO2_PASSWORD ), repo2Path );
    }

    public static List<String> getListOfFilesAfterExcludeDirAndFilesRepo1( Map<String, String> p ) {

        List<String> dirLst1 = CommonUtil.getListFromPropertiesField( p.get( Constants.REPO1_EXCLUDE_DIR ), p.get( Constants.PATH_TO_CLONE ) + p.get( Constants.REPO1_NAME ) + File.separator, true );
        System.out.println( ":::::::::::::::::::::Exclude Directory List for Repo 1 START:::::::::::::::::::::" );
        dirLst1.forEach( System.out::println );
        System.out.println( ":::::::::::::::::::::Exclude Directory List for Repo 1 END:::::::::::::::::::::" );
        List<String> fileLst1 = CommonUtil.getListFromPropertiesField( p.get( Constants.REPO1_EXCLUDE_FILES ), p.get( Constants.PATH_TO_CLONE ) + p.get( Constants.REPO1_NAME ) + File.separator, true );
        System.out.println( ":::::::::::::::::::::Exclude File List for Repo 1 START:::::::::::::::::::::" );
        fileLst1.forEach( System.out::println );
        System.out.println( ":::::::::::::::::::::Exclude File List for Repo 1 END:::::::::::::::::::::" );

        List<String> lst = new ArrayList<String>();
        try {
            lst = Files.walk( Paths.get( p.get( Constants.PATH_TO_CLONE ) + p.get( Constants.REPO1_NAME ) + File.separator ) ).filter( Files::isRegularFile ).filter( file -> dirLst1.stream().noneMatch( dir -> file.toString().startsWith( dir ) ) ).filter(
                    file -> fileLst1.stream().noneMatch( excFile -> file.toString().equals( excFile ) ) ).map( file -> file.toString() ).collect( Collectors.toList() );
        } catch ( IOException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println( "::::::::::::::::::::: File List for Repo 1 After Filtering dir and files START:::::::::::::::::::::" );
        lst.stream().forEach( x -> {
            System.out.println( x );
        } );
        System.out.println( "::::::::::::::::::::: File List for Repo 1 After Filtering dir and files END:::::::::::::::::::::" );
        return lst;
    }

    public static List<String> getListOfFilesAfterExcludeDirAndFilesRepo2( Map<String, String> p ) {

        List<String> dirLst2 = CommonUtil.getListFromPropertiesField( p.get( Constants.REPO2_EXCLUDE_DIR ), p.get( Constants.PATH_TO_CLONE ) + p.get( Constants.REPO2_NAME ) + File.separator, true );
        System.out.println( ":::::::::::::::::::::Exclude Directory List for Repo 2 START:::::::::::::::::::::" );
        dirLst2.forEach( System.out::println );
        System.out.println( ":::::::::::::::::::::Exclude Directory List for Repo 2 END:::::::::::::::::::::" );
        List<String> fileLst2 = CommonUtil.getListFromPropertiesField( p.get( Constants.REPO2_EXCLUDE_FILES ), p.get( Constants.PATH_TO_CLONE ) + p.get( Constants.REPO2_NAME ) + File.separator, true );
        System.out.println( ":::::::::::::::::::::Exclude File List for Repo 2 START:::::::::::::::::::::" );
        fileLst2.forEach( System.out::println );
        System.out.println( ":::::::::::::::::::::Exclude File List for Repo 2 END:::::::::::::::::::::" );

        List<String> lst = new ArrayList<String>();
        try {
            lst = Files.walk( Paths.get( p.get( Constants.PATH_TO_CLONE ) + p.get( Constants.REPO2_NAME ) + File.separator ) ).filter( Files::isRegularFile ).filter( file -> dirLst2.stream().noneMatch( dir -> file.toString().startsWith( dir ) ) ).filter(
                    file -> fileLst2.stream().noneMatch( excFile -> file.toString().equals( excFile ) ) ).map( file -> file.toString() ).collect( Collectors.toList() );
        } catch ( IOException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println( "::::::::::::::::::::: File List for Repo 2 After Filtering dir and files START:::::::::::::::::::::" );
        lst.stream().forEach( x -> {
            System.out.println( x );
        } );
        System.out.println( "::::::::::::::::::::: File List for Repo 2 After Filtering dir and files END:::::::::::::::::::::" );
        return lst;
    }
}
