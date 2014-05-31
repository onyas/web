package lib;

import java.io.File;
import javax.servlet.http.*;

public class pdf2swf extends common{

	HttpServletRequest request = null;

	public pdf2swf(HttpServletRequest request){
		this.request = request;
	}

	/**
	 * 调用SWFTools的pdf2swf.exe，将pdf转为swf
	 * @param doc  要转化的文件
	 * @param page 页码的范围
	 * @return
	 */
	public String convert(String doc, String page) {
		String pdfFilePath	= separate(getConfig("path.pdf", "")) + doc;
		String swfFilePath	= separate(getConfig("path.swf", "")) + doc + page + ".swf";

		String command		= getConfig("cmd.conversion.singledoc", "");
		if("true".equals(getConfig("splitmode", "")))
			command = getConfig("cmd.conversion.splitpages", "");

		command = command.replace("{path.pdf}", separate(getConfig("path.pdf", "")));
		command = command.replace("{path.swf}", separate(getConfig("path.swf", "")));
		command = command.replace("{pdffile}", doc);

		try {
			if (!isNotConverted(pdfFilePath ,swfFilePath)) {
				return "[Converted]";
			}
		} catch (Exception ex) {
			return "Error," + ex.toString();
		}

		boolean return_var = false;

		if("true".equals(getConfig("splitmode", ""))){
			String pagecmd = command.replace("%", page);
			pagecmd = pagecmd + " -p " + page;

			return_var = exec(pagecmd);
			//执行每一页的命令都不相同，所以hash值也不同，这样，当执行同一页时，不用在调用swftools的pdf2swf命令了
			//对于新的一页，因为还没有将它转化为pdf,所以得到的conversion为null，需要进行转化
			int hash = getStringHashCode(command);
			HttpSession session = request.getSession(true);
			String constr = "CONVERSION_" + hash;
			String conversion = (String) session.getAttribute(constr);
            if(conversion == null){
                exec(command);
                session.setAttribute(constr, "true");
            }
		}else
			return_var = exec(command);
		String s = "Error converting document, make sure the conversion tool is installed and that correct user permissions are applied to the SWF Path directory" + 
					getDocUrl();
		if(return_var) {
			s="[Converted]";
		}
		return s;
	}

	/**
	 * 判断pdf文件是否已经转化为了swf文件
	 * @param pdfFilePath pdf文件
	 * @param swfFilePath swf文件
	 * @return
	 * @throws Exception
	 */
	public boolean isNotConverted(String pdfFilePath,String swfFilePath) throws Exception {
		File f = new File(pdfFilePath);
		if (!f.exists()) {
			throw new Exception("Document does not exist");
		}
		if (swfFilePath == null) {
			throw new Exception("Document output file name not set");
		} else {
			File s = new File(swfFilePath);
			if (!s.exists()) {
				return true;
			} else {
				if(f.lastModified() > s.lastModified()) return true;
			}
		}
		return false;
	}
}