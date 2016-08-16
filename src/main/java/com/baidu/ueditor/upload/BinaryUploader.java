package com.baidu.ueditor.upload;

import com.baidu.ueditor.PathFormat;
import com.baidu.ueditor.define.AppInfo;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.FileType;
import com.baidu.ueditor.define.State;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@SuppressWarnings("Duplicates")
public class BinaryUploader {

    public static final State save(HttpServletRequest request,
                                   Map<String, Object> conf) {
        FileItemStream fileStream = null;
        boolean isAjaxUpload = request.getHeader("X_Requested_With") != null;

        if (!ServletFileUpload.isMultipartContent(request)) {
            return new BaseState(false, AppInfo.NOT_MULTIPART_CONTENT);
        }

//		ServletFileUpload upload = new ServletFileUpload(
//				new DiskFileItemFactory());
//
//        if ( isAjaxUpload ) {
//            upload.setHeaderEncoding( "UTF-8" );
//        }

//		try {
//			FileItemIterator iterator = upload.getItemIterator(request);
//
//			while (iterator.hasNext()) {
//				fileStream = iterator.next();
//
//				if (!fileStream.isFormField())
//					break;
//				fileStream = null;
//			}
//
//			if (fileStream == null) {
//				return new BaseState(false, AppInfo.NOTFOUND_UPLOAD_DATA);
//			}
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());

        if (multipartResolver.isMultipart(request)) {
            // 转换成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            // 取得request中的所有文件名
            Iterator<String> iter = multiRequest.getFileNames();
            while (iter.hasNext()) {
                // 取得上传文件
                MultipartFile file = multiRequest.getFile(iter.next());
                if (!file.isEmpty()) {
                    try {
                        String savePath = (String) conf.get("savePath");
                        String originFileName = file.getOriginalFilename();
                        String suffix = FileType.getSuffixByFilename(originFileName);

                        originFileName = originFileName.substring(0,
                                originFileName.length() - suffix.length());
                        savePath = savePath + suffix;

                        long maxSize = ((Long) conf.get("maxSize")).longValue();

                        if (!validType(suffix,
                                (String[]) conf.get("allowFiles"))) {
                            return new BaseState(false,
                                    AppInfo.NOT_ALLOW_FILE_TYPE);
                        }

                        savePath = PathFormat.parse(savePath, originFileName);

                        String physicalPath = (String) conf.get("rootPath")
                                + savePath;

                        InputStream is = file.getInputStream();
                        State storageState = StorageManager
                                .saveFileByInputStream(is, physicalPath,
                                        maxSize);
                        is.close();

                        if (storageState.isSuccess()) {
                            storageState.putInfo("url",
                                    PathFormat.format(savePath));
                            storageState.putInfo("type", suffix);
                            storageState.putInfo("original", originFileName
                                    + suffix);
                        }

                        return storageState;
                    } catch (IOException e) {
                    }
                }
            }
        }
        return new BaseState(false, AppInfo.IO_ERROR);
    }

    private static boolean validType(String type, String[] allowTypes) {
        List<String> list = Arrays.asList(allowTypes);

        return list.contains(type);
    }
}
