package com.db.erm.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.db.erm.constant.Constants;

public class CommonUtil {

    public static void writeToLogFile( String[] args ) {

        if ( args != null && Arrays.stream( args ).anyMatch( Constants.ENABLE_LOG::equals ) ) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern( "dd-MM-yyyy_HH-mm-ss" );
            LocalDateTime now = LocalDateTime.now();
            File file = new File( "." + File.separator + "log" + dtf.format( now ) + ".txt" );
            PrintStream stream;
            try {
                stream = new PrintStream( file );
                System.out.println( ":::::::::::::::::::::From now on " + file.getAbsolutePath() + " will be your console:::::::::::::::::::::" );
                System.setOut( stream );
                System.setErr( stream );
            } catch ( FileNotFoundException e ) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public static List<String> getListFromPropertiesField( String value, String prefix, boolean concatWithPrefix ) {
        List<String> lst = new ArrayList<String>();
        if ( value != null && !value.isEmpty() ) {
            if ( concatWithPrefix ) {
                lst = Arrays.stream( value.split( "\\|" ) ).map( String::trim ).map( s -> prefix.concat( s ) ).collect( Collectors.toList() );
            } else {
                lst = Arrays.stream( value.split( "\\|" ) ).map( String::trim ).collect( Collectors.toList() );
            }
        }
        return lst;
    }

    public static Collection<String> similarAndDifferentFilesInRepo( List<String> repo1, List<String> repo2, Map<String, String> properties ) {
        List<String> filesRepo1 = repo1.stream().map( file -> file.replace( properties.get( Constants.PATH_TO_CLONE ) + properties.get( Constants.REPO1_NAME ), "" ) ).collect( Collectors.toList() );
        List<String> filesRepo2 = repo2.stream().map( file -> file.replace( properties.get( Constants.PATH_TO_CLONE ) + properties.get( Constants.REPO2_NAME ), "" ) ).collect( Collectors.toList() );

        Collection<String> similar = new HashSet<String>( filesRepo1 );
        Collection<String> different = new HashSet<String>();
        different.addAll( filesRepo1 );
        different.addAll( filesRepo2 );

        similar.retainAll( filesRepo2 );
        different.removeAll( similar );
        System.out.println( ":::::::::::::::::::::Similar Files START:::::::::::::::::::::" );
        similar.stream().forEach( System.out::println );
        System.out.println( ":::::::::::::::::::::Similar Files END:::::::::::::::::::::" );
        System.out.println( ":::::::::::::::::::::Different Files START:::::::::::::::::::::" );
        System.out.println( ":::::::::::::::::::::Different Files in Repository 1 START:::::::::::::::::::::" );
        Output.repo1ExtraFiles = different.stream().filter( file -> filesRepo1.contains( file ) ).collect( Collectors.toList() );
        Output.repo1ExtraFiles.forEach( System.out::println );
        System.out.println( ":::::::::::::::::::::Different Files in Repository 1 END:::::::::::::::::::::" );
        System.out.println( ":::::::::::::::::::::Different Files in Repository 2 START:::::::::::::::::::::" );
        Output.repo2ExtraFiles = different.stream().filter( file -> filesRepo2.contains( file ) ).collect( Collectors.toList() );
        Output.repo2ExtraFiles.forEach( System.out::println );
        System.out.println( ":::::::::::::::::::::Different Files in Repository 2 END:::::::::::::::::::::" );
        System.out.println( ":::::::::::::::::::::Different Files END:::::::::::::::::::::" );

        return similar;
    }

    public static void writeOutputInFile() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern( "dd-MM-yyyy_HH-mm-ss" );
        LocalDateTime now = LocalDateTime.now();
        File file = new File( "." + File.separator + "Output" + dtf.format( now ) + ".txt" );
        try {
            file.createNewFile();
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);

            // Write in file
            bw.write(":::::::::::::::::::::OUTPUT:::::::::::::::::::::\n\n");
            if(Output.repo1ExtraFiles!=null && Output.repo1ExtraFiles.size()>0) {
                bw.write("\n:::::::::::::::::::::Files Which is not found in repository 2:::::::::::::::::::::\n\n");
                Output.repo1ExtraFiles.forEach( fileName->{
                    try {
                        bw.write( fileName.substring( 1 ) + "\n");
                    } catch ( IOException e ) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    
                });
            }
            if(Output.repo2ExtraFiles!=null && Output.repo2ExtraFiles.size()>0) {
                bw.write("\n:::::::::::::::::::::Files Which is not found in repository 1:::::::::::::::::::::\n\n");
                Output.repo2ExtraFiles.forEach( fileName->{
                    try {
                        bw.write( fileName.substring( 1 )+"\n" );
                    } catch ( IOException e ) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    
                });
            }
            
            if(Output.compareResult!=null && Output.compareResult.size()>0) {
                bw.write("\n:::::::::::::::::::::Files Comparison Result:::::::::::::::::::::\n\n");
                for ( Entry<String, String> en : Output.compareResult.entrySet() ) {
                    bw.write( en.getValue() + en.getKey()+"\n");
                }
            }
            bw.write("\n:::::::::::::::::::::FINISH:::::::::::::::::::::");

            // Close connection
            bw.close();
        } catch ( IOException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
