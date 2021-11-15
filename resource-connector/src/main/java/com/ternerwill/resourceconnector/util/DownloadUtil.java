package com.ternerwill.resourceconnector.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.ClientAbortException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

@Slf4j
@Component
public class DownloadUtil {

    /**
     * @param filePath
     * @param request
     * @param response
     */
    public void fileChunkDownload(String filePath, int id,HttpServletRequest request, HttpServletResponse response){
        File file = new File(filePath);
        if(!file.exists()||!file.isFile()){
            throw new IllegalArgumentException(filePath+"doesn't exist! or is not valid file");
        }
        long fileLength = file.length();
        long start = 0;
        long end = fileLength - 1;
        RandomAccessFile input = null;

        // 配置range信息

        String requestRange = request.getHeader("range");
        if(requestRange!=null){
            log.info("request range is{}",requestRange);
            String info = requestRange.substring(6,requestRange.length());
            String[] rangeInfo = info.split("-");
            if(rangeInfo.length==1){
                if(info.endsWith("-")){
                    // range为number-类型
                    start = Long.valueOf(rangeInfo[0]);
                }
                else if(info.startsWith("-")){
                    // range为-number,目前没看到过这种类型
                    end = Long.valueOf(rangeInfo[0]);
                }
                else {
                    throw new IllegalArgumentException(requestRange+"format error");
                }
            }
            else if(rangeInfo.length == 2){
                start = Long.valueOf(rangeInfo[0]);
                end = Long.valueOf(rangeInfo[1]);
            }
            else {
                throw new IllegalArgumentException(requestRange+"format error");
            }
        }

        // 配置相关头部
        long contentLength = end - start + 1;
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss 'GMT'", Locale.US);
        String fileName = id+file.getName().substring(file.getName().lastIndexOf('.'),file.getName().length());
        dateFormat.setTimeZone(TimeZone.getTimeZone("GWT"));
        String lastModified = dateFormat.format(new Date(file.lastModified()));
        log.info("last Modified{}",lastModified);
        response.setHeader("Content-Type",request.getServletContext().getMimeType(file.getName()));
        response.setHeader("Content-Length", String.valueOf(contentLength));
        response.setHeader("Accept-Ranges","bytes");
        response.setHeader("Content-Disposition","inline");
        response.setHeader("Last-Modified",lastModified);
        String contentRange = String.format("bytes %d-%d/%d",start,end,fileLength);
        log.info("contentRange{}",contentRange);
        response.setHeader("Content-Range",contentRange);
        response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);

        // 开始读写
        byte[] buffer = new byte[4096];
        long transmitted = 0;
        int len = 0;
        try{
            input = new RandomAccessFile(file,"r");
            input.seek(start);
            BufferedOutputStream output = new BufferedOutputStream(response.getOutputStream());
            // 读取文件不能超出文件范围，也要在contentLength范围内
            while((transmitted + len <= contentLength) && ((len = input.read(buffer)) != -1)){
                transmitted += len;
                output.write(buffer,0,len);
            }
            // 处理没有读取完的文件部分
            if(transmitted < contentLength){
                len = input.read(buffer,0, (int) (contentLength - transmitted));
                transmitted += len;
                output.write(buffer,0,len);
            }
            input.close();
            output.flush();
            response.flushBuffer();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch(ClientAbortException ex){
            log.info("客户端中断连接");
        } catch(IOException ex){
            ex.printStackTrace();
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void download(String filePath, int id, HttpServletResponse response) throws FileNotFoundException {
        File file = new File(filePath);
        if(!file.exists()||!file.isFile()) {
            throw new IllegalArgumentException(filePath + "doesn't exist! or is not valid file");
        }
        // 配置相关头部
        String fileType = filePath.substring(filePath.lastIndexOf('.'),filePath.length());
        response.setHeader("Content-Type","application/octet-stream");
        response.setHeader("Content-Length", String.valueOf(file.length()));
        response.setHeader("Content-Disposition","attachment;filename="+id+fileType);
        // 开始读写
        BufferedInputStream input = new BufferedInputStream( new FileInputStream(file));
        byte[] buffer = new byte[4096];
        int len = 0;
        try{
            BufferedOutputStream output = new BufferedOutputStream(response.getOutputStream());
            while((len = input.read(buffer)) != -1){
                output.write(buffer,0,len);
            }
            input.close();
            output.flush();
            response.flushBuffer();
        } catch(ClientAbortException ex){
            log.info("客户端中断连接");
        } catch(IOException ex){
            ex.printStackTrace();
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
