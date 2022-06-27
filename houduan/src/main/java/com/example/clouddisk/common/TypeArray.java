package com.example.clouddisk.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TypeArray {
 private static ArrayList<String> arrayList= new ArrayList<String>(Arrays.asList("png","jpg","jpeg","gif"));
 private static ArrayList<String> arrayList1= new ArrayList<String>(Arrays.asList("mp4","m4v","avi","mkv","rmvb","wmv"));
 private static ArrayList<String> arrayList2= new ArrayList<String>(Arrays.asList("txt","pdf","doc","docx","java","c"));
 private static ArrayList<String> arrayList3= new ArrayList<String>(Arrays.asList("mp3","mpeg","wma","acc"));
 private static ArrayList<String> arrayList4= new ArrayList<String>(Arrays.asList("7z","zip","rar"));
   public static  List<String> getArrayList() {
       return arrayList;
   }
   public static  List<String> getArrayList1() {
       return arrayList1;
   }
   public static  List<String> getArrayList2() {
       return arrayList2;
   }
   public static  List<String> getArrayList3() {
       return arrayList3;
   }
   public static  List<String> getArrayList4() {
       return arrayList4;
   }

}
