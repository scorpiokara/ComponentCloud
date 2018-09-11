package com.scorpiokara.cloud;

import android.os.Environment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by .
 *
 * @author TaoYJ
 * @date 2018/8/15
 */
public class CrashHandler {

    public Thread.UncaughtExceptionHandler exceptionHandler=new Thread.UncaughtExceptionHandler(){

        @Override
        public void uncaughtException(Thread thread, Throwable throwable) {
            try {
                saveCrashLog(throwable);
            } catch (Exception e) {
                e.printStackTrace();
            }
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    };


    public Thread.UncaughtExceptionHandler getExceptionHandler() {
        return exceptionHandler;
    }

    private void saveCrashLog(Throwable throwable) {
        StringBuffer sb=new StringBuffer();
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-DD HH:mm:ss");
        sb.append("DateTime:").append(sdf.format(new Date())).append("\n");
        sb.append("crash: \n");
        Writer writer=new StringWriter();
        PrintWriter printWriter=new PrintWriter(writer);
        throwable.printStackTrace(printWriter);
        Throwable cause = throwable.getCause();
        if (null!=cause){
            cause.printStackTrace(printWriter);
            cause=cause.getCause();
        }
        printWriter.close();
        String result=writer.toString();
        sb.append("[Excetpion: ]\n").append(result);

        saveLog(sb.toString());
    }

    private void saveLog(String log) {
        String path= Environment.getExternalStorageDirectory()+ File.separator+"mxrlog"+File.separator+"log_myApp.txt";
        File file=new File(path);
        if (!file.exists()){
            try {
                String parentPath = file.getParent();
                File parentFile=new File(parentPath);
                if (!parentFile.exists()){
                    parentFile.mkdirs();
                    file.createNewFile();
                }else{
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file,true);
//            无效了
//            String[] split = log.split(",");
//            for (String s:split){
//                fileWriter.append(s+"\n");
//            }
//            fileWriter.append("\n\n");
            fileWriter.append(log);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
