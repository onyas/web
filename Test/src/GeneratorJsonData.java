import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class GeneratorJsonData {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		generatorContentJson();
	}

	private static void generatorSystemparamJson() {
		try {
			File file = new File("content.json");
			StringBuilder sb = new StringBuilder();
			sb.append("[");
			for (int i = 1; i < 100; i++) {
				sb.append("{\"name\":\"Test" + i
						+ "\",\"size\":\"1k"
						+ "\",\"type\":\"doc"
						+ "\",\"createTime\":\"2014-5-4" 
						+ "\",\"modifyTime\":\"2014-5-4" 
						+ "\",\"version\":\"1.0" 
						+ "\",\"read\":\"" 
						+ "\"},");
			}
			sb.setCharAt(sb.toString().length() - 1, ' ');
			sb.append("]");
			FileUtils.writeStringToFile(file, sb.toString());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void generatorContentJson() {
		try {
			File file = new File("data.json");
			StringBuilder sb = new StringBuilder();
			sb.append("[");
			for (int i = 10; i < 100; i++) {
				sb.append("{\"name\":\"UDS主路asdxxxx" + i
						+ "\",\"paramname\":\"UDS_SERVER_URL" + i
						+ "\",\"paramvalue\":\"1" + i
						+ "\",\"comment\":\"批量查询数据" + i + "\"},");
			}
			sb.setCharAt(sb.toString().length() - 1, ' ');
			sb.append("]");
			FileUtils.writeStringToFile(file, sb.toString());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
