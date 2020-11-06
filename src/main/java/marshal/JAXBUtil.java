package marshal;


import org.apache.log4j.Logger;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.*;

/**
 * <p>
 * 使用JAXB在Java Bean和XML内容间相互转换。
 *
 * @author Danne Leung
 */

public class JAXBUtil {

    private static final Logger logger = Logger.getLogger(JAXBUtil.class);
	/**
	 * 把相应的java数据对象解析为相应的XML格式文件.
	 *
	 * @param obj
	 *            java数据对象
	 * @return 解析后的信息
	 */
	public static String marshal(Object obj) {
		StringWriter sw = new StringWriter();
		JAXB.marshal(obj, sw);
		return sw.toString();
	}
	/**
	 * 把相应的java数据对象解析为相应的XML格式文件.
	 * 报文头自定义
	 * @param obj  java数据对象
	 * @param mClass java对象的Class
	 * @return 解析后的信息
	 */
	public static String marshalNoFragment(Object obj, Class<?> mClass) {
		StringWriter writer = new StringWriter();
		try {
			JAXBContext context = JAXBContext.newInstance(mClass);
			writer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "\n");
			// 获得Marshaller对象
			Marshaller marshaller = context.createMarshaller();
			// 格式化xml格式
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			// 去掉生成xml的默认报文头
			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
			// java 对象生成到writer
			marshaller.marshal(obj, writer);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return writer.toString();

	}

	public static <T> T unmarshal(File file, Class<T> type) {
		return JAXB.unmarshal(file, type);
	}

	/**
	 * 把XML信息文件转换为给定类型的java对象，XML信息文本默认以"UTF-8"编码读取。
	 *
	 * @param type
	 *            给定的java对象类。
	 * @param xmlcontent
	 *            信息内容
	 * @return 返回给定类型的java对象。
	 */
	public static <T> T unmarshal(String xmlcontent, Class<T> type) {
		return unmarshal(xmlcontent, "UTF-8", type);
	}

	public static <T> T unmarshalGBK(String xmlcontent, Class<T> type) {
		return unmarshalGBK(xmlcontent, "GBK", type);
	}

	/**
	 * 把XML信息文件转换为给定类型的java对象，XML信息文本默认以"UTF-8"编码读取。
	 *
	 * @param type
	 *            给定的java对象类。
	 * @param contentEncoding
	 *            XML信息内容的编码。
	 * @param xmlcontent
	 *            信息内容
	 * @return 返回给定类型的java对象。
	 */
	public static <T> T unmarshal(String xmlcontent, String contentEncoding, Class<T> type) {
		if (xmlcontent == null || "".equals(xmlcontent)) {
			return null;
		}
		if (contentEncoding == null || "".equals(contentEncoding.trim())) {
			contentEncoding = "UTF-8";
		}
		InputStream is = null;
		try {
			is = new ByteArrayInputStream(xmlcontent.getBytes(contentEncoding));
			return JAXB.unmarshal(is, type);
		} catch (UnsupportedEncodingException e) {
			logger.error("Exception", e);
			return null;
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					logger.error("Exception", e);
				}
				is = null;
			}
		}
	}

	public static <T> T unmarshalGBK(String xmlcontent, String contentEncoding, Class<T> type) {
		if (xmlcontent == null || "".equals(xmlcontent)) {
			return null;
		}
		if (contentEncoding == null || "".equals(contentEncoding.trim())) {
			contentEncoding = "GBK";
		}
		InputStream is = null;
		try {
			is = new ByteArrayInputStream(xmlcontent.getBytes(contentEncoding));
			return JAXB.unmarshal(is, type);
		} catch (UnsupportedEncodingException e) {
			logger.error("Exception", e);
			return null;
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					logger.error("Exception", e);
				}
				is = null;
			}
		}
	}
}
