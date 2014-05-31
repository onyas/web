package lib;

import java.io.File;
import java.util.*;

import javax.servlet.http.*;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class uploadify {
	public uploadify() {
	}

	/**
	 * 上传文件
	 * @param request
	 */
	public void upload(HttpServletRequest request) {
		try {
			Config con = new Config();
			File f_midir = new File(con.getConfig("path.pdf"));
			if (!f_midir.isDirectory() && !f_midir.mkdirs())
				f_midir = new File("/");
			request.setCharacterEncoding("utf-8");
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(4096);
			factory.setRepository(f_midir);
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setSizeMax(100 * 1024 * 1024);// 上传文件最大值为100M
			boolean isMultipart = ServletFileUpload.isMultipartContent(request);
			if (!isMultipart)
				return;
			List items = upload.parseRequest(request);
			Iterator iter = items.iterator();
			try {
				while (iter.hasNext()) {
					FileItem item = (FileItem) iter.next();
					// item.isFormField()用来判断当前对象是否是file表单域的数据
					// 如果返回值是true说明不是file表单而是普通表单域,相反，则为file表单域中的数据
					if (!item.isFormField()) {
						String fileName = item.getName();// 获取上传文件的名字，有的浏览器直接得到文件名，有的则是包括全路径
						if (!fileName.equals("") && fileName != null) {
							int idx = fileName.lastIndexOf("\\");
							if (idx == -1) {
								idx = fileName.lastIndexOf("/");
							}
							fileName = fileName.substring(idx + 1);// 得到文件的基本名称
							String tempfileName = fileName;
							File save_file = new File(f_midir.getPath(),
									fileName);
							// 如果文件已经存在，则重新命名，直到没有存在相同名字的文件为止
							if (save_file.exists()) {
								for (int i = 1; true; i++) {
									fileName = i + "_" + tempfileName;
									save_file = new File(f_midir.getPath(),
											fileName);
									if (!save_file.exists()) {
										break;
									}
								}
							}
							f_midir = new File(f_midir.getPath(), fileName);
							//直接将数据写入到目标目录中
							item.write(f_midir);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
