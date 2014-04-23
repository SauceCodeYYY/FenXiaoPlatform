package com.lhq.prj.bms.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class ImportFileStream {
	public static InputStream importExl(HttpServletRequest request)
			throws IOException, FileUploadException {
		InputStream in = null;
		String importRemark;
		/**
		 * 是否覆盖导入：1：覆盖带入；2：不覆盖导入
		 */
		int type;
		/**
		 * 记录导入文件的类型
		 */
		String fileType;

		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setFileSizeMax((long) Math.pow(1024, 2) * 500);

		List<FileItem> items = upload.parseRequest(request);
		Iterator iter = items.iterator();
		while (iter.hasNext()) {
			FileItem item = (FileItem) iter.next();
			if (item.getFieldName().equals("importRemark")) {
				importRemark = item.getString();
				importRemark = UnicodeUtil.decodeUnicode(importRemark);
			}
			if (item.getFieldName().equals("importType")) {
				type = Integer.parseInt(item.getString());
			}
			// 如果是表单域 ，就是非文件上传元素
			if (!item.isFormField()) {
				String fileName = item.getName();
				fileType = fileName.substring(fileName.lastIndexOf(".") + 1)
						.toLowerCase();
				in = item.getInputStream();
			}
		}

		return in;

	}

}
