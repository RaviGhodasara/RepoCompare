package com.db.erm.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class Output {

    public static List<String> repo1ExtraFiles = new ArrayList<String>();
    public static List<String> repo2ExtraFiles = new ArrayList<String>();
    public static Map<String, String> compareResult = new HashMap<String, String>();
}
