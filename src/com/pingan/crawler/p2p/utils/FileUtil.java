package com.pingan.crawler.p2p.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.pingan.crawler.p2p.model.Platform;

public class FileUtil {
	public static void mkRootPath(String path) {
		File rootPath = new File(path);
		if (!rootPath.exists()) {
			rootPath.mkdirs();
		}
	}

	public static void write2File(List list, String path, boolean isfile) {
		try {
			File file = new File(path);
			if (!file.exists()) {
				file.createNewFile();
			} else {
				FileWriter fw = new FileWriter(file);
				fw.write("");
				fw.flush();
				fw.close();
			}

			if (isfile) {
				writeFile(list, file);
			} else {
				writeObject(list, file);
			}

			System.out.println(path + "写文件完毕。");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void writeFile(List<Platform> list, File file) throws IOException {
		FileOutputStream fStream = new FileOutputStream(file, true);
		OutputStreamWriter oWriter = new OutputStreamWriter(fStream, "UTF-8");
		BufferedWriter bWriter = new BufferedWriter(oWriter);

		for (Platform record : list) {
			bWriter.write(record.toString() + "\n\r");
		}

		bWriter.flush();
		bWriter.close();
	}

	private static void writeObject(List<Platform> list, File file) throws IOException {
		FileOutputStream outStream = new FileOutputStream(file, true);
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
		oos.writeObject(list);
		
		oos.close();
		outStream.close();
	}

	@SuppressWarnings("unchecked")
	public static List<Platform> readListObjcet(File file){
		ObjectInputStream ois = null;
		
		List<Platform> result = new ArrayList<Platform>();
        try {  
            ois = new ObjectInputStream(new FileInputStream(file));  
            result.addAll((ArrayList<Platform>)ois.readObject());
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } catch (ClassNotFoundException e) {  
            e.printStackTrace();  
        } finally {
			try {
				ois.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
        
        return result;
	}
	
	/**
     * 获取目录下所有文件(按页码排序)
     * 
     * @param path
     * @return
     */
	private static List<File> getFileSort(String path) {
        List<File> list = getFiles(path, new ArrayList<File>());
 
        if (list != null && list.size() > 0) {
            Collections.sort(list, new Comparator<File>() {
                public int compare(File file, File newFile) {
                	int filePageNum = Integer.parseInt(file.getName().substring(4));
                	int newFilePageNum = Integer.parseInt(newFile.getName().substring(4));
                	return filePageNum == newFilePageNum ? 0 : (filePageNum > newFilePageNum ? 1 : -1);
                }
            });
        }
 
        return list;
    }
 
    /**
     * 
     * 获取目录下所有文件
     * 
     * @param realpath 文件路径
     * @param files 
     * @return
     */
    private static List<File> getFiles(String realpath, List<File> files) {
        File realFile = new File(realpath);
        if (realFile.isDirectory()) {
            File[] subfiles = realFile.listFiles();
            for (File file : subfiles) {
                if (file.isDirectory()) {
                    getFiles(file.getAbsolutePath(), files);
                } else {
            		files.add(file);
                }
            }
        }
        
        return files;
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static List readFileList(String filePath) {
		List result = new ArrayList();

		List<File> fileList = FileUtil.getFileSort(filePath);
		for (File file : fileList) {
			result.addAll(FileUtil.readListObjcet(file));
		}

		return result;
	}
    
    public static String parseCjTime(String timeStr){
		String[] times = timeStr.split("[-]");
		return times[0] + "." + times[1];
	}
}
