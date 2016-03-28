package project.loader;
/**
 * @author coco
 * @created 2015-3-7
 */

/**
 * @author coco
 * @created 2015-3-6
 */

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class SelfClassLoader extends ClassLoader {

	private String name; // �������������

	private String path = "F://"; // �������·��

	private final String fileType = ".class"; // .class�ļ���չ��

	public SelfClassLoader(String name) {
		super();// ��ϵͳ��������Ϊ����ļ������ĸ��������

		this.name = name;
	}

	public SelfClassLoader(ClassLoader parent, String name) {
		super(parent); // ��ʾָ������������ĸ�������
		this.name = name;
	}

	@Override
	public String toString() {
		return this.name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * ��ȡclass�ļ���Ϊ�����������뵽byte������ȥ
	 * 
	 * @param name
	 * @return
	 */
	private byte[] loadClassData(String name) {
		InputStream in = null;
		byte[] data = null;
		ByteArrayOutputStream baos = null;

		try {
			name = name.replace(".", "\\");
			in = new BufferedInputStream(new FileInputStream(new File(path
					+ name + fileType)));
			baos = new ByteArrayOutputStream();
			int ch = 0;
			while (-1 != (ch = in.read())) {
				baos.write(ch);
			}
			data = baos.toByteArray();
		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					baos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return data;
	}

	/**
	 * JVM���õļ������ķ���
	 */

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		byte[] data = this.loadClassData(name);
		return this.defineClass(name, data, 0, data.length);
	}

	/*
	 * public static void main(String[] args) throws Exception { MyClassLoader
	 * loader1 = new MyClassLoader("loader1"); loader1.setPath("F://");
	 * //test(loader1); }
	 */
	public static void test(ClassLoader loader) throws Exception {
		Class<?> clazz = loader.loadClass("C_after");
		Object object = clazz.newInstance();
		System.out.println(object.toString() + object.getClass());
		/*
		 * C_after c = new C_after(); c.m(); Class cc = c.getClass();
		 */
	}

}
