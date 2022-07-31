package com.ashin.vplayer.utils;

public class Util {

    private static final String TAG = "";
    public static long[] getRange(String rangeHeader,long sourceLength){

        //Log.i(TAG, "Line ==> rangeHeader = " + rangeHeader);
        long[] result = new long[2];

        long targetStart = -1;
        long targetEnd = -1;
        if(sourceLength > 0){
            long[] ranges = parseRange(rangeHeader);
            targetStart = ranges[0];
            if(ranges[1] == 0L){
                targetEnd = sourceLength - 1;
            }else{
                targetEnd = ranges[1];
            }
        }

        result[0] = targetStart;
        result[1] = targetEnd;
        return  result;
    }

    //bytes=123-    ==> 123 0
    //bytes=-123    ==> 0   123
    //bytes=123-222 ==> 123 222
    private static long[] parseRange(String rangeText){

        String range = rangeText.replace("bytes=","");

        if(range.contains("-")){
            if(range.startsWith("-")){
                //-123 ==> 0-123
                range = "0" + range;
            }else if(range.endsWith("-")){
                //123- ==> 123-0
                range = range + "0";
            }
            String[] ranges = range.split("-");
            if(ranges.length == 2){ //must
                long[] result = new long[]{Long.parseLong(ranges[0]),Long.parseLong(ranges[1])};
                return result;
            }
        }
        return null;
    }
}
